import os
import cv2
import logging
from gaze_tracking import GazeTracking

def gaze_track(video_file_path, upload_folder):
    gaze = GazeTracking()
    video_path = os.path.join(upload_folder, video_file_path)

    # 비디오 파일 열기 시도
    webcam = cv2.VideoCapture(video_path)

    # 비디오 파일이 열리지 않을 경우 오류 메시지
    if not webcam.isOpened():
        logging.error(f"Error: Could not open video file {video_path}")
        return {"error": "Could not open video file"}

    gaze_score = {"right": 0, "left": 0, "center": 0, "blinking": 0}
    frame_count = 0

    while True:
        ret, frame = webcam.read()
        if not ret:
            break

        gaze.refresh(frame)
        frame_count += 1

        # 시선 상태를 로그로 기록
        if gaze.is_right():
            gaze_score["right"] += 1
        elif gaze.is_left():
            gaze_score["left"] += 1
        elif gaze.is_blinking():
            gaze_score["blinking"] += 1
        elif gaze.is_center():
            gaze_score["center"] += 1

    webcam.release()
    cv2.destroyAllWindows()

    # 프레임 수에 따른 비율 계산
    for key in gaze_score:
        gaze_score[key] = gaze_score[key] / frame_count if frame_count else 0

    return gaze_score
