![image](https://github.com/user-attachments/assets/6c080278-1f4a-493d-97b3-9e1f8bc7035b)


# 📰 면접의 神
##### 🏆 캡스톤디자인 및 졸업 프로젝트 작품: 모의면접 웹 프로그램

### 📜 Contents
 1. [Overview](#-overview)
 2. [서비스 화면](#-서비스-화면)
 3. [주요 기능](#-기능)
 4. [개발 환경](#%EF%B8%8F-개발-환경)
 5. [시스템 아키텍처](#-시스템-아키텍처)
 6. [기술 특이점](#-기술-특이점)
 7. [기획 및 설계 산출물](#-기획-및-설계-산출물)
 8. [Conventions](#-conventions)
 9. [팀원 소개](#-팀원-소개)
 
## ✨ Overview
##### 🏆 개발 기간: 24.03 ~ 24.11
- 현대 채용 프로세스에서 면접은 결정적인 단계로 여겨지지만, 많은 사람들이 면접 과정에서 불안과 긴장을 겪는 문제가 있다.
- 이러한 과정에서 발생할 수 있는 인간의 감정적 요소와 기술적인 한계를 극복하기 위해, 딥러닝 기술을 활용한 모의면접 프로그램은 중요한 해결책으로 자리 잡고 있다.
- 본 연구에서는 '면접의 신'이라 명명한 이 프로그램을 소개한다.
- '면접의 신'은 OpenCV와 음성 인식 기술(STT)을 통해 사용자의 면접 영상을 실시간으로 분석하여, 언어적 요소(습관어의 사용 빈도)와 비언어적 요소(시선 안정성, 표정의 일관성 등)를 정량적으로 평가한다.
- 이러한 종합적인 분석 결과를 바탕으로 사용자에게는 면접 기술 향상을 위한 맞춤형 피드백을 제공하며, 이는 실제 면접에서의 성공 가능성을 높이는 데 기여할 것으로 기대된다.

## 👀 서비스 화면

### 📹 [시연 영상](https://drive.google.com/file/d/1nd-KBvB9GmLw1KwVWOmDVQSmhXiY1eGg/view?usp=sharing)

### 📄 [PPT](https://docs.google.com/presentation/d/1ewIp6Ewz8a023O6pvgxxVSg6ij-5F7TX/edit?usp=drive_link&ouid=102263935085836178064&rtpof=true&sd=true)

### 📑 [논문](https://drive.google.com/file/d/1eO-l2oUUuI2RuD30DO2goKIiBxyq_s8d/view?usp=drive_link)

  
## ✨  기능 

- `회원 관리`
   - 회원가입, 로그인, 비밀번호 찾기, 비밀번호 변경, 이메일 변경, 아이디 찾기 등의 기능을 제공하여 사용자의 계정을 효율적으로 관리합니다.
   - Spring Security와 세션을 사용하여 구현했습니다.
   - 마이페이지에서는 회원 탈퇴 및 프로필 사진을 변경할 수 있는 기능을 제공했습니다.

- `이메일 인증`
    - 회원가입 시 이메일로 비밀번호가 전송되며, 사용자는 해당 비밀번호를 입력하여 회원가입을 완료할 수 있습니다.
   - 이메일 전송은 **JavaMailSender**를 사용하여 처리되며, 마이페이지에서 이메일을과 80%까지 성능을 향상했습니다.
   - GPU를 활용해 훈련 시간을 단축했습니다.


- `DALL-E 모델 연동`
   - OpenAI의 DALL-E 모델을 연동하여 사용자가 입력한 카드뉴스의 내용을 바탕으로 관련 이미지를 실시간으로 생성하는 기능을 구현했습니다.
   - 이미지 생성 요청은 Ajax를 통해 비동기로 처리되며, 생성된 이미지는 서버에 저장된 후 즉시 사용자에게 제공됩니다.
   - 또한 OpenAI API를 활용해 입력된 문구에 따라 AI가 10개의 문구를 생성하며, 사용자는 이를 선택해 이미지에 추가할 수 있습니다.

- `프롬프트 개선`
   - DALLE 모델에서의 프롬프트 구성 방식과 텍스트 구조가 이미지 생성에 미치는 영향을 분석한 결과, 카드뉴스 형식에 적합한 키워드와 구조적인 텍스트를 도출했습니다. 이를 바탕으로 괄호 사용과 공백 처리를 세심하게 다듬어 카드뉴스 형식의 이미지를를 얻었습니다.

   
- `카드뉴스 이미지 편집`
   - 사용자가 생성된 이미지를 기반으로 필터, 텍스트 추가, 펜, 도형 삽입 등의 기능을 제공하여 이미지 편집을 할 수 있습니다.
   - 위치 이동 및 색상 변경 기능을 제공하여 사용자 맞춤형 디자인이 가능합니다.


- `카드뉴스 JSON 편집`
   - 카드뉴스의 각 요소(이미지, 텍스트, 도형 등)는 JSON 형식으로 저장되며, 이를 통해 게시된 카드뉴스를 다시 불러와 자유롭게 편집할 수 있습니다.
   - 사용자는 이전 작업 내용을 그대로 이어서 수정할 수 있습니다.


- `동영상 다운로드`
   - 카드뉴스 이미지와 문구를 결합해 동영상 파일로 다운로드할 수 있는 기능을 제공합니다.
   - TTS(Text-to-Speech) 기능을 활용해 문구를 음성으로 변환하여 동영상에 음성을 추가했으며, gTTS 라이브러리를 사용했습니다.
   - FFmpeg를 사용해 이미지와 오디오를 결합하여 동영상 파일을 생성하고, 사용자는 이를 로컬로 저장할 수 있습니다.


- `카드뉴스 포크 기능`
   - 다른 사용자가 만든 카드뉴스를 포크하여 재편집할 수 있는 기능을 구현했습니다.
   - 포크 시 원본 카드뉴스의 출처가 자동으로 표시되며, 사용자는 이를 기반으로 새로운 디자인을 제작할 수 있습니다.


- `카드뉴스 템플릿`
   - DALL-E 모델로 생성된 이미지를 다른 사용자들이 템플릿으로 활용할 수 있도록 템플릿 리스트에 진열하였습니다.
   - 사용자는 템플릿을 선택하여 카드뉴스 제작 시 활용할 수 있으며, 템플릿 데이터는 서버에서 관리됩니다.


- `카드뉴스 휴지통`
   - 카드뉴스를 삭제하면 휴지통으로 이동하며, 휴지통에서 복구하거나 영구 삭제할 수 있는 기능을 제공합니다.
   - 삭제된 데이터는 30일 동안 유지되며, 서버 측에서 정기적으로 영구 삭제된 데이터를 처리하는 배치 작업이 실행됩니다.
   

- `@Async`
   - OpenAI API 요청이 동시에 2개 들어오는 상황에서 이를 처리하기 위해 @Async를 사용해 비동기 처리로 대응했습니다.
    - 프롬프트에서 불필요한 내용을 제거하여 요청의 효율을 높였습니다.
    - 그 결과 70초에서 약 35초로 API 응답 시간이 줄어들었습니다. 
   

- `웹소켓 적용`
   - 관리자와 사용자 간 실시간 소통을 위한 웹소켓(WebSocket) 기반의 채팅 기능을 구현했습니다.
   - 사용자는 관리자와만 1:1 대화를 할 수 있으며, 관리자는 여러 사용자와 동시 대화를 관리할 수 있습니다.
   - STOMP 프로토콜을 사용해 메시지를 주고받으며, Spring WebSocket을 통해 실시간 통신을 처리합니다.


- `관리자 계정 기능`
   - 관리자는 회원 관리, 게시물 관리, 댓글 관리, 문의 채팅 기능을 통해 전체 서비스를 운영할 수 있습니다.
   - 관리자 계정은 일반 사용자와 구분됩니다.


- `AWS EC2 서버 배포 및 CI/CD 구축축`
   - AWS EC2 프리티어를 사용하여 서버를 배포하였으며
    - 도메인은 가비아에서 구입한 후 Route 53을 사용해 EC2 인스턴스와 연결했습니다. 
    - SSL 인증서를 갱신하여 HTTPS 통신을 적용했습니다.
    - GitHub Actions를 활용해 CI/CD 파이프라인을 구축하였습니다. 코드 푸시 시 자동으로 빌드 및 테스트를 실행하도록 설정했습니다. 성공적인 테스트 후 EC2 서버로 자동 배포되도록 워크플로우를 구성하여 배포 프로세스를 효율화했습니다.


## 🖥️ 개발 환경
**Management Tool**
- 커뮤니케이션 : Discord, Notion

**🐳 Backend**
- Java `17`
- Python `3.10.11`
- Spring Framework `3.2.5`
- Thymeleaf
- Jpa  

**🦊 Frontend**
-  HTML5, CSS3, JAVASCRIPT

**🗂️ DB**
- MySQL `8.0.34`

**🔨 IDE**
- IntellJ `2023.3`

**🖼️ Gradle**
```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
   implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
   implementation 'org.springframework.boot:spring-boot-starter-web'
   implementation 'org.springframework.boot:spring-boot-starter-security'
   implementation 'org.springframework.boot:spring-boot-starter-mail'
   implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
   implementation 'org.springframework.security:spring-security-oauth2-client:6.2.4'
   implementation 'jakarta.validation:jakarta.validation-api'
```

**🖼️ requirements.txt**
```
absl-py==2.1.0
astunparse==1.6.3
audioread==3.0.1
blinker==1.8.2
certifi==2024.8.30
cffi==1.17.1
charset-normalizer==3.4.0
click==8.1.7
colorama==0.4.6
contourpy==1.3.0
cycler==0.12.1
decorator==4.4.2
dlib==19.24.6
facenet-pytorch==2.6.0
fer==22.5.1
ffmpeg==1.4
filelock==3.16.1
Flask==3.0.3
Flask-Cors==5.0.0
flatbuffers==24.3.25
fonttools==4.54.1
fsspec==2024.10.0
gast==0.6.0
google-pasta==0.2.0
grpcio==1.67.1
h5py==3.12.1
idna==3.10
imageio==2.36.0
imageio-ffmpeg==0.5.1
imutils==0.5.4
itsdangerous==2.2.0
Jinja2==3.1.4
joblib==1.4.2
keras==3.6.0
keyboard==0.13.5
kiwisolver==1.4.7
lazy_loader==0.4
libclang==18.1.1
librosa==0.10.2.post1
llvmlite==0.43.0
Markdown==3.7
markdown-it-py==3.0.0
MarkupSafe==3.0.2
matplotlib==3.9.2
mdurl==0.1.2
ml-dtypes==0.4.1
moviepy==1.0.3
mpmath==1.3.0
msgpack==1.1.0
namex==0.0.8
networkx==3.4.2
numba==0.60.0
numpy==1.26.4
opencv-contrib-python==4.10.0.84
opencv-python==4.10.0.84
opt_einsum==3.4.0
optree==0.13.0
packaging==24.1
pandas==2.2.3
pillow==10.2.0
pip==24.3.1
platformdirs==4.3.6
playsound==1.3.0
pooch==1.8.2
proglog==0.1.10
protobuf==5.28.3
PyAudio==0.2.14
pycparser==2.22
pydub==0.25.1
Pygments==2.18.0
pyparsing==3.2.0
python-dateutil==2.9.0.post0
pytz==2024.2
requests==2.32.3
rich==13.9.4
scikit-learn==1.5.2
scipy==1.14.1
setuptools==68.2.0
six==1.16.0
soundfile==0.12.1
soxr==0.5.0.post1
SpeechRecognition==3.11.0
sympy==1.13.3
tensorboard==2.18.0
tensorboard-data-server==0.7.2
tensorflow==2.18.0
tensorflow_intel==2.18.0
tensorflow-io-gcs-filesystem==0.31.0
termcolor==2.5.0
threadpoolctl==3.5.0
torch==2.2.2
torchvision==0.17.2
tqdm==4.66.6
typing_extensions==4.12.2
tzdata==2024.2
urllib3==2.2.3
Werkzeug==3.1.1
wheel==0.41.2
wrapt==1.16.0


```


## 💫 시스템 아키텍처
![img_2.png](img_2.png)

## ✨ 기술 특이점
- DALL-E와 GPT-4o 모델을 활용하여 이미지와 문구가 결합된 카드뉴스를 제작했으며, 생성된 카드뉴스는 이미지, 문구 위치, 색상, 편집 요소 등으로 구성된 JSON 파일과 함께 서버에 저장됩니다. 이를 통해 카드뉴스를 다시 편집할 때 각 요소를 자유롭게 수정할 수 있는 유연성을 제공합니다.
- 카드뉴스 형식에 적합한 이미지를 생성하기 위해, 프롬프트에 카드뉴스에 알맞은 키워드와 구조적인 텍스트를 포함하고 괄호 사용 및 공백 처리를 세심히 조정하여 원하는 결과물을 얻었습니다.
- KoBART 모델의 성능을 기존 66%에서 80%로 향상시켰습니다. 이를 위해 AI 허브에서 데이터를 수집하고 에포크를 10회로 설정해 훈련을 진행했으며, Rouge 점수를 활용해 원본 모델과 훈련된 모델의 정확도를 비교하여 성능 개선을 확인했습니다.
- 소셜 로그인, 실시간 채팅 등 웹에서 가능한 대부분의 기능을 구현했습니다.
- 기존 카드뉴스 생성 시간이 약 70초로 오래 걸렸던 문제를 해결하기 위해, 이미지 생성과 문구 생성을 비동기 방식(@Async)으로 처리하도록 개선했습니다. 이를 통해 생성 시간을 약 30~40초로 단축하며 성능을 크게 향상했습니다.

# 📂 기획 및 설계 산출물

### [💭 요구사항 정의 및 기능 명세](https://www.notion.so/ae9aab2290414d5ebc4510c922177e54)

![img_3.png](img_3.png)


### [✨ ER Diagram]
![image](https://github.com/user-attachments/assets/8de095a6-2309-4501-add8-9262081b0544)




# 💞 팀원 소개
##### ❤️‍🔥 AMCN을 개발한 팀원들을 소개합니다!

|                       **[석재민]**                         | **[김도균](https://github.com/chltmdgh522)**    | 
|:-------------------------------------------------------------------------------------------------------:|:-------------------------------------:| 
| ![image](https://github.com/user-attachments/assets/640cb309-44a3-49e2-b848-f5cf75a53687)|![image](https://github.com/user-attachments/assets/a73109c6-29f5-498d-b7b2-394e777c73ae)|  
|                                          Frontend                                           |              Leader & Backend                | 

## 😃 팀원 역할

- **김도균**
  - 팀장, 기획, 프론트 개발, 백엔드 개발(회원관리, 커뮤니티(글쓰기, 댓글, 좋아요, 조회수, 글 수정), AI면접 구현(전체적으로), 면접 진행 후 피드백화면 구현)

- **석재민**
  - 프론트 설계 및 개발, 프론트 개발 
