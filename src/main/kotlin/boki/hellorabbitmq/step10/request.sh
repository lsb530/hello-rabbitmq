# 정상 케이스
curl -X POST "http://localhost:8080/api/message?testCase=false" \
     -H "Content-Type: application/json" \
     -d '{"userId": "USER1", "stock": 10}'

# 실패 - RabbitMQ 전송 실패
curl -X POST "http://localhost:8080/api/message?testCase=true" \
     -H "Content-Type: application/json" \
     -d '{"userId": "USER1", "stock": 10}'

# 실패 - DB 저장 실패
curl -X POST "http://localhost:8080/api/message?testCase=false" \
     -H "Content-Type: application/json" \
     -d '{"userId": "", "stock": 10}'