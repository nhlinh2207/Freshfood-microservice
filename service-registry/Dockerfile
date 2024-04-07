FROM openjdk:8
ARG JAR_fILE=target/*.jar
COPY ${JAR_fILE} serviceRegistry.jar
ENTRYPOINT ["java", "-jar", "/serviceRegistry.jar"]
EXPOSE 8761

# Build jar : mvn clean package -DskipTests=true
# docker build -t nhlinh2207/serviceregistry:1 .
# docker run -p 8761:8761 nhlinh2207/serviceregistry:1