import logging
import cv2
import requests
from flask import Flask, request, jsonify
import os

from flask_cors import CORS

from audio_extraction import extract_audio
from emotion_analyze import analyze_emotion  # 감정 분석 함수가 포함된 파일에서 import
from gazeTracking import gaze_track
import glob

from habit_analysis import analyze_habit

# 로그 설정
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    handlers=[
                        logging.FileHandler("app.log"),  # 로그 파일에 기록
                        logging.StreamHandler()  # 콘솔에 출력
                    ])

app = Flask(__name__)
CORS(app)
UPLOAD_FOLDER = 'uploads/'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

# 분석 결과를 저장할 전역 딕셔너리
analysis_results = {
    "user_id": None,  # user_id 추가
    "user_name": None,
    "job": None,
    "questions": None,
    "emotion_results": None,
    "gaze_results": None,
    "habit_results": None
}

@app.route('/upload-files', methods=['POST'])
def upload_files():
    files = []
    logging.info("파일 업로드 요청 받음.")

    # 사용자 정보 받기
    user_id = request.form.get("user_id")
    user_name = request.form.get("userName")
    job = request.form.get("job")

    # 질문 목록 개별적으로 수신
    questions = [request.form.get(f"question_{i + 1}") for i in range(5)]

    # 받은 사용자 정보와 질문 로그에 출력
    logging.info(f"받은 사용자 ID: {user_id}")
    logging.info(f"받은 사용자 이름: {user_name}")
    logging.info(f"받은 직업: {job}")
    for index, question in enumerate(questions, start=1):
        logging.info(f"받은 질문 {index}: {question}")

    # 사용자 정보를 전역 딕셔너리에 저장
    analysis_results["user_id"] = user_id
    analysis_results["user_name"] = user_name
    analysis_results["job"] = job
    analysis_results["questions"] = questions  # 질문 리스트 저장

    # 파일 업로드
    for i in range(1, 6):  # 5개의 파일 예상
        video_file = request.files.get(f'videoFile_{i}')
        if video_file:
            file_path = os.path.join(UPLOAD_FOLDER, f"recording_{i}.webm")
            video_file.save(file_path)
            files.append(file_path)
            logging.info(f"파일 {i} 업로드 성공: {file_path}")

    if len(files) == 5:
        logging.info("모든 파일이 성공적으로 업로드됨.")
        return jsonify({"message": "All files uploaded successfully"}), 200
    else:
        logging.warning("파일 업로드 실패: 일부 파일이 누락됨.")
        return jsonify({"error": "Failed to upload some files"}), 400


@app.route('/analyze-emotions', methods=['POST'])
def analyze_emotions():
    logging.info("감정 분석 시작.")
    # 업로드된 모든 동영상 파일 가져오기
    video_files = sorted(glob.glob(os.path.join(UPLOAD_FOLDER, "recording_*.webm")))
    results = {}

    for index, video_file in enumerate(video_files, start=1):
        try:
            # 감정 분석 함수 호출
            emotion_result = analyze_emotion(video_file)
            results[f'question_{index}'] = emotion_result
            logging.info(f"질문 {index}의 감정 분석 결과: {emotion_result}")
        except Exception as e:
            results[f'question_{index}'] = {"error": str(e)}
            logging.error(f"질문 {index} 감정 분석 중 오류 발생: {str(e)}")

    # 감정 분석 결과를 전역 딕셔너리에 저장
    analysis_results["emotion_results"] = results
    return jsonify(results), 200

@app.route('/analyze-gaze', methods=['POST'])
def analyze_gaze():
    logging.info("시선 분석 시작.")
    video_files = sorted(glob.glob(os.path.join(UPLOAD_FOLDER, "recording_*.webm")))
    gaze_results = {}

    for index, video_file in enumerate(video_files, start=1):
        try:
            # 시선 분석 호출, 업로드 폴더와 파일 이름을 전달
            gaze_result = gaze_track(os.path.basename(video_file), UPLOAD_FOLDER)
            gaze_results[f'question_{index}'] = gaze_result
            logging.info(f"질문 {index}의 시선 분석 결과: {gaze_result}")
        except Exception as e:
            gaze_results[f'question_{index}'] = {"error": str(e)}
            logging.error(f"질문 {index} 시선 분석 중 오류 발생: {str(e)}")

    # 시선 분석 결과를 전역 딕셔너리에 저장
    analysis_results["gaze_results"] = gaze_results
    return jsonify(gaze_results), 200

@app.route('/analyze-habit-words', methods=['POST'])
def analyze_habit_words():
    logging.info("습관어 분석 시작.")
    video_files = sorted(glob.glob(os.path.join(UPLOAD_FOLDER, "recording_*.webm")))
    habit_results = {}

    for index, video_file in enumerate(video_files, start=1):
        try:
            audio_file_path = extract_audio(video_file)  # 오디오 추출
            habit_result = analyze_habit(audio_file_path)  # 습관어 분석
            habit_results[f'question_{index}'] = habit_result
            logging.info(f"질문 {index}의 습관어 분석 결과: {habit_result}")
        except Exception as e:
            habit_results[f'question_{index}'] = {"error": str(e)}
            logging.error(f"질문 {index} 습관어 분석 중 오류 발생: {str(e)}")

    # 습관어 분석 결과를 전역 딕셔너리에 저장
    analysis_results["habit_results"] = habit_results
    return jsonify(habit_results), 200

# 모든 분석 결과를 반환하는 엔드포인트
@app.route('/get-analysis-results', methods=['GET'])
def get_analysis_results():
    # 모든 분석이 완료되었는지 확인
    if None in analysis_results.values():
        logging.warning("일부 분석 결과가 누락되었습니다. 모든 분석 결과가 완료되지 않음.")
        return jsonify({"error": "Some analysis results are missing"}), 400

    # 모든 결과를 반환
    logging.info("모든 분석 결과가 완료되었고 Spring 서버로 전송 준비가 되었습니다.")
    logging.debug(f"최종 분석 결과: {analysis_results}")
    return jsonify(analysis_results), 200

# Spring 서버로 분석 결과를 전송하는 함수
def send_results_to_spring():
    logging.info("Spring 서버로 분석 결과를 전송하는 중입니다.")

    # Flask 서버의 get_analysis_results에서 분석 결과 가져오기
    try:
        response = requests.get("http://localhost:5000/get-analysis-results", timeout=10)
        response.raise_for_status()  # 상태 코드가 200이 아닌 경우 예외 발생
    except requests.exceptions.RequestException as e:
        logging.error(f"분석 결과를 가져오는 데 실패했습니다. 오류: {e}")
        return

    # 분석 결과 JSON 데이터 저장
    analyzed_data = response.json()
    logging.info(f"Spring 서버로 전송할 분석 결과: {analyzed_data}")

    # Spring 서버의 엔드포인트 URL 설정
    spring_server_url = "http://localhost:8080/api/save-analysis"

    # Spring 서버로 POST 요청 전송
    try:
        analyzed_data["user_id"] = analysis_results["user_id"]
        send_response = requests.post(spring_server_url, json=analyzed_data, timeout=10)
        send_response.raise_for_status()  # 상태 코드가 200이 아닌 경우 예외 발생
        logging.info(f"Spring 서버로 분석 결과 전송 성공: 상태 코드 {send_response.status_code}")

        # Spring 서버로 데이터 전송 성공 시 uploads 폴더 비우기
        for file_path in glob.glob(os.path.join(UPLOAD_FOLDER, "*")):
            try:
                os.remove(file_path)
                logging.info(f"파일 삭제 성공: {file_path}")
            except Exception as e:
                logging.error(f"파일 삭제 중 오류 발생: {file_path}, 오류: {e}")

    except requests.exceptions.RequestException as e:
        logging.error(f"Spring 서버로 데이터 전송 중 오류 발생: {e}")


# Spring 서버로 분석 결과 전송을 위한 엔드포인트
@app.route('/analyze-and-send', methods=['POST'])
def analyze_and_send():
    logging.info("Spring 서버로 분석 결과 전송을 시작합니다.")
    send_results_to_spring()
    return jsonify({"message": "Analysis data sent to Spring server"}), 200

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
