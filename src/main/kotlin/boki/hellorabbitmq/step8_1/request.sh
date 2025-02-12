# 성공 요청
curl -X GET "http://localhost:8080/api/order?message=success"

# 실패(재시도) 요청
curl -X GET "http://localhost:8080/api/order?message=fail"