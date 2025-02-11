# error 로그 처리
curl -X GET "http://localhost:8080/api/logs/error"

# warn 로그 처리
curl -X GET "http://localhost:8080/api/logs/warn"

 # info 로그 처리
curl -X POST "http://localhost:8080/api/logs/info" \
     -H "Content-Type: application/json" \
     -d "System initialized successfully."