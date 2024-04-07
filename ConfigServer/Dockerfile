FROM openjdk:8
ARG JAR_fILE=target/*.jar
COPY ${JAR_fILE} configServer.jar
ENTRYPOINT ["java", "-jar", "/configServer.jar"]
EXPOSE 9296

# Build jar : mvn clean package -DskipTests=true
# docker build -t nhlinh2207/configserver:1 .
# docker run -p 9296:9296 -e EUREKA_SERVER_ADDRESS=http://192.168.25.128:8761/eureka --name configserver  nhlinh2207/configserver:1