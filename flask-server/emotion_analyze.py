import cv2
from fer import FER

# FER 라이브러리를 사용하여 감정 분석을 수행
def analyze_emotion(video_path):
    # 감정 분석 모델 초기화
    detector = FER(mtcnn=True)

    # 비디오 파일 열기
    video = cv2.VideoCapture(video_path)
    emotions_summary = {
        "angry": 0,
        "disgust": 0,
        "fear": 0,
        "happy": 0,
        "sad": 0,
        "surprise": 0,
        "neutral": 0
    }
    frame_count = 0

    while video.isOpened():
        ret, frame = video.read()
        if not ret:
            break
        frame_count += 1

        # 각 프레임에서 감정 예측
        emotion_data = detector.top_emotion(frame)
        if emotion_data:
            emotion, score = emotion_data
            if emotion in emotions_summary:
                emotions_summary[emotion] += 1

    video.release()

    # ZeroDivisionError 방지
    if frame_count == 0:
        return {emotion: 0 for emotion in emotions_summary}

    # 프레임별 감정 비율 계산
    for emotion in emotions_summary:
        emotions_summary[emotion] /= frame_count

    return emotions_summary
