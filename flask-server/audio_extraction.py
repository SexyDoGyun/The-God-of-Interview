# audio_extraction.py
from pydub import AudioSegment

def extract_audio(video_file):
    audio_file_path = video_file.replace('.webm', '.wav')
    audio = AudioSegment.from_file(video_file, format='webm')
    audio.export(audio_file_path, format='wav')
    return audio_file_path
