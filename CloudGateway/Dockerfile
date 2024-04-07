FROM openjdk:8
ARG JAR_fILE=target/*.jar
COPY ${JAR_fILE} cloudGateway.jar
ENTRYPOINT ["java", "-jar", "/cloudGateway.jar"]
EXPOSE 9090

# Build jar : mvn clean package -DskipTests=true
# docker build -t nhlinh2207/cloudgateway:1 .
# docker run -p 9090:9090 --name cloudgateway -e CONFIG_SERVER_URL=http://192.168.25.128:9296 -e EUREKA_SERVER_ADDRESS=http://192.168.25.128:8761/eureka -e KEYCLOAK_REAML_URL=http://192.168.25.128:8080/realms/Micro-Realm -e KEYCLOAK_CLIENT_ID=Micro-Client-Id -e KEYCLOAK_CLIENT_SECRET=CFI3SEOp6wB3wKdJLmU7UWqEXGpgxkvF -e KEYCLOAK_REDIRECT_URL=http://192.168.25.128:9090/login/oauth2/code/gateway  nhlinh2207/cloudgateway:1