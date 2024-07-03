FROM openjdk:latest
COPY  ./app.jar /application.jar
COPY ./config.xml  /src/main/java/com/restapi/IndoorAtmosphereControl/xml/config.xml
ENTRYPOINT ["java","-jar","/application.jar"]