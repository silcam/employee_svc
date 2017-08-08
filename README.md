Employee Service
================

Overview
--------

Basic shared REST service for employee data.

### Java
* Jersey/JAX-RS, Gson, Hibernate
### Postgresql

Service Calls:
--------------

```
$ curl -i "http://localhost:8080/webapi/employee"

HTTP/1.1 200 OK
Content-Length: 1
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

$ curl i -H"Content-Type: application/json" -d"{ name: 'Test Name', department: 'Test Department'}" -X POST "http://localhost:8080/webapi/employee"
HTTP/1.1 201 Created
Content-Length: 64
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

{"id":"67688","name":"Test Name","department":"Test Department"}

$ curl -i "http://localhost:8080/webapi/employee"
HTTP/1.1 200 OK
Content-Length: 1
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

1

$ curl -i "http://localhost:8080/webapi/employee/67688/"
HTTP/1.1 200 OK
Content-Length: 64
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

{"id":"67688","name":"Test Name","department":"Test Department"}

$ curl -i -X DELETE "http://localhost:8080/webapi/employee/67688/"
HTTP/1.1 200 OK
Content-Length: 0
Server: Jetty(8.1.16.v20140903)
```
