Employee Service
================

Overview
--------

Basic shared REST service for employee data.

### Java
* Jersey/JAX-RS, Gson, Hibernate
### Postgresql

Running Service:
---------------

### Database (Postgres)

Create role and db per the hibernate.cfg.xml

### Starting the Service

(you need maven)

Run:
```
mvn jetty:run
```

Service Calls:
--------------

```
$ curl -i "http://localhost:8080/webapi/employee"

HTTP/1.1 200 OK
Content-Length: 2
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

[]

$ curl i -H"Content-Type: application/json" -d"{ name: 'Test Name', department: 'Test Department'}" -X POST "http://localhost:8080/webapi/employee"
HTTP/1.1 201 Created
Content-Length: 64
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

{"id":"67688","name":"Test Name","department":"Test Department"}

$ curl -i "http://localhost:8080/webapi/employee"
HTTP/1.1 200 OK
Content-Length: 67
Content-Type: application/json
Server: Jetty(8.1.16.v20140903)

[{"id":"67688","name":"Test Name","department":"Test Department"}]

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
