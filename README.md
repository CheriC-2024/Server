# Cheric-Server

## 구동을 위해 필요한 부분
### google cloud Sdk 설정 필요
1. google cloud platform 프로젝트 생성
2. google cloud vision api 활성 상태로 설정
3. API 및 서비스 > 사용자 인증 정보 > 서비스 계정 생성 > 키 생성(JSON)
4. 해당 키 경로 환경 변수에 변수 이름 `GOOGLE_APPLICATION_CREDENTIALS`로 추가
   (고급 시스템 설정 > 시스템 변수 & 사용자 변수 모두 설정)
5. google cloud CLI 설치
   https://cloud.google.com/sdk/docs/install?hl=ko
6. google cloud platform 계정과 연결
7. 재부팅 후 run server

### API Key & URL application.properties에 설정 필요
- Chat GPT API KEY: `발급받은 API KEY`
- CHAT GPT API URL: `https://api.openai.com/v1/chat/completions`
- Musicgen API KEY: `발급받은 API KEY`

### application.properties에 파일 업로드 경로 자신의 경로로 설정
