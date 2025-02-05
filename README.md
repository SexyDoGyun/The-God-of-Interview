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
   - 마이페이지에서는 회원탈퇴, 면접 피드백, 비밀번호 변경, 내 정보를 확인 할 수 있는 기능을 구현하였습니다.

- `이메일 인증`
    - 회원가입 시 이메일로 인증번호가 전송되며, 사용자는 해당 인증번호를 입력하여 회원가입을 완료할 수 있습니다.
 
- `커뮤니티`
    - 커뮤니티는 기본적으로 로그인을 완료한 사용자만 사용이 가능합니다.
    - 커뮤니티에서는 기본적으로 글 상세보기, 글 쓰기, 좋아요, 조회수, 댓글 작성, 글 수정, 사진 업로드 기능이 있습니다.
    - 글 작성 후 작성자에게는 자신이 작성한 글에 글 수정, 삭제 권한이 주어져 글 수정, 삭제 기능을 사용할 수 있습니다.(작성자 이외에는 수정, 삭제 버튼이 보이지 않습니다.)     

- `AI모의면접`
    - 사용자는 index.html 페이지의 상단에 보이는 'AI모의면접'을 클릭하여, 모의면접을 진행하는 페이지로 이동합니다.
    - 모의면접은 총 4단계로 이루어져 있으며, 로그인을 한 회원만 이용이 가능합니다.
    - 로그인을 하지 않은채 'AI모의면접'을 클릭 시, 로그인 페이지로 이동하여 회원가입 후 로그인을 진행하여야 합니다.

-  `AI모의면접 - 1단계`
    - 'AI 모의면접 - 1단계'는 사용자에게 면접에 대한 가이드를 제공합니다.

     
-  `AI모의면접 - 2단계`
    - 'AI 모의면접 - 2단계'는 사용자가 자신이 면접을 희망하는 산업군, 직무, 직업을 순서대로 선택합니다.
    - 사용자가 산업군, 직무, 직업을 선택하지 않으면 '다음'버튼이 비활성화 됩니다.
    - 모든 선택이 완료된 후, 오른쪽 하단의 '다음'버튼을 클릭 시 'AI 모의면접 - 3단계'로 이동합니다.

-  `AI모의면접 - 3단계`
    - 'AI 모의면접 - 3단계'는 사용자가 이전 단계에서 선택한 직업에 대한 질문으로 면접을 진행하게 됩니다.
    - 질문은 총 5개로 면접 시작 버튼을 클릭 시, 화면에 카메라를 이용하여 사용자의 면접 내용을 녹화하게 됩니다.
    - 한 질문당 응답시간은 1분입니다.
    - 1분이 지나거나, 사용자가 '다음 질문' 버튼을 클릭 시 다음 질문으로 넘어가게 됩니다.
    - 5번째 질문에 대한 응답이 모두 완료되었을 시, '다음 질문' 버튼이 '면접 종료' 버튼으로 변경됩니다.
    - '면접 종료' 버튼 클릭 시, 화면에 로딩페이지가 나옵니다.
    - 로딩이 모두 완료되었으면 'AI 모의면접 - 4단계'로 이동하게 됩니다.
      
-  `AI모의면접 - 3단계(면접 분석)`
    - 사용자가 AI모의면접 - 3단계 5번째 질문에서 면접 종료 버튼을 눌렀을 시, 사용자의 이름, 선택한 직업, 녹화된 동영상 파일 5개가 Spring 서버에서 Flask 서버로 전송됩니다.
    - Flask 서버에서는 감정분석, 시선분석, 습관어 분석을 차례대로 진행합니다.
    - 분석이 완료된 데이터는 Json형식으로 다시 Flask 서버에서 Spring 서버로 전송이 되어 Spring 서버의 데이터베이스에 분석 결과가 저장이됩니다.
    - 이 분석 데이터를 가지고 FeedbackService에서 사용자의 피드백을 추출합니다.

-  `AI모의면접 - 4단계`
    - 'AI 모의면접 - 4단계'는 사용자에게 진행한 면접의 피드백을 보여주는 페이지입니다.
    - 분석단계에서 데이터베이스에 저장된 결과값을 불러와 피드백을 보여줍니다.
    - 피드백은 총 평가, 감정분석, 시선분석, 습관어분석에 대한 피드백으로 구성되어있습니다.
    - 각 평가마다 상, 중, 하로 나누어져 있으며 각 평가에서 가장 부족하였던 질문 2가지를 추출하여 사용자에게 "연습이 필요한 문항"으로 보여줍니다.
    - 피드백 결과도 마찬가지로 데이터베이스에 저장되며, 마이페이지 버튼의 피드백 버튼을 클릭하여 자신이 받은 피드백을 다시 한번 확인할 수 있습니다.
    

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
![image](https://github.com/user-attachments/assets/65bae499-1dea-46c9-b0e7-e94fe2a734d3)


## ✨ 기술 특이점
- 미작성

# 📂 기획 및 설계 산출물
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
  - 팀장, 기획, 프론트 개발, 백엔드 개발(회원관리, 커뮤니티(글쓰기, 댓글, 좋아요, 조회수, 글 수정), AI면접 구현(1~4단계), 면접 진행 후 피드백화면 구현)

- **석재민**
  - 프론트 설계 및 개발, 프론트 개발 
