The repo creates an in-memory h2 sql database and exposes the contents over a REST API using spring boot and hibernate.

Tables:  
Employee   
Department  

RestAPI endpoints:

POST /departments {"departmentName": "IT"}   -inserts an IT department  
POST /employees {"employeeName": "Alice", "departmentName":"IT"}'  -inserts a person named Alice in the IT department  
GET /employees?departmentName=IT  -return all employees in department IT  
GET /employees/1  -returns employee with id 1  
GET /employees/  -returns all employees  
GET /departments/  -returns all departments  


Example curl commands:

curl -X POST http://localhost:8080/departments -H "Content-Type: application/json" -d '{"departmentName": "IT"}'  
curl -X POST http://localhost:8080/employees -H "Content-Type: application/json" -d '{"employeeName": "Alice", "departmentName":"IT"}'  
curl "http://localhost:8080/employees?departmentName=IT"  
curl "http://localhost:8080/employees/1"  
curl "http://localhost:8080/employees/"  
curl "http://localhost:8080/departments/"  