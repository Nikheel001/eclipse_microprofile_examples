either download tomee zip with microprofile classifier manually, configure it and deploy war there *or* </br>
add dependency and plugin with proper config in pom.xml </br>
and use maven to run server </br>
use proper version of tomee based on api version and jdk version

```
mvn clean package tomee:run
http://localhost:8080/empconfigtry
http://localhost:8080/empconfigtry/msg
```
either download openliberty zip with microprofile classifier manually, configure it and deploy war there *or*
checkout openliberty config *src/main/liberty/config/server.xml* </br>
configure *pom.xml* for openliberty server </br>

```
mvn liberty:run
http://localhost:9080/
http://localhost:9080/msg
http://localhost:9080/openapi/
http://localhost:9080/openapi/ui/
http://localhost:9080/health/
http://localhost:9080/metrics/
```