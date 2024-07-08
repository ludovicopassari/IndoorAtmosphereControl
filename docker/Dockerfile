FROM openjdk:latest
COPY  ./app.jar /application.jar
COPY ./config.xml  /src/main/java/com/restapi/IndoorAtmosphereControl/xml/config.xml
EXPOSE 4040 8080
ENTRYPOINT ["java","-jar","/application.jar"]