# 성공
curl -X POST "http://localhost:8080/api/message?testCase=success" \
     -H "Content-Type: application/json" \
     -d '{"userId": "testUser", "stock": 100}'

# 실패
curl -X POST "http://localhost:8080/api/message?testCase=fail" \
     -H "Content-Type: application/json" \
     -d '{"userId": "testUser", "stock": 100}'