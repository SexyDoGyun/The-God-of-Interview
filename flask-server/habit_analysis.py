# habit_analysis.py
import speech_recognition as sr

def analyze_habit(audio_file):
    recognizer = sr.Recognizer()
    habit_words = ["음", "그", "아", "저", "이런", "저런"]
    word_count = {word: 0 for word in habit_words}

    with sr.AudioFile(audio_file) as source:
        audio_data = recognizer.record(source)
        try:
            text = recognizer.recognize_google(audio_data, language="ko-KR")
            for word in habit_words:
                word_count[word] += text.count(word)
        except sr.UnknownValueError:
            return {"error": "음성을 인식할 수 없습니다."}
        except sr.RequestError as e:
            return {"error": f"음성 인식 서비스 오류: {e}"}

    return word_count
