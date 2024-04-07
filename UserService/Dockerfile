FROM openjdk:8
ARG JAR_fILE=target/*.jar
COPY ${JAR_fILE} userService.jar
ENTRYPOINT ["java", "-jar", "/userService.jar"]
EXPOSE 8085

# Build jar : mvn clean package -DskipTests=true
# docker build -t nhlinh2207/userservice:1 .
# docker run -p 8085:8085 --name userService -e EUREKA_SERVER_ADDRESS=http://192.168.25.128:8761/eureka -e DB_URL=jdbc:mysql://192.168.25.128:3306/microProduct -e DB_USER=root -e DB_PASS=fireice22072001 -e CONFIG_SERVER_URL=http://192.168.25.128:9296 -e KEYCLOAK_CLIENT_SECRET=CFI3SEOp6wB3wKdJLmU7UWqEXGpgxkvF -e KEYCLOAK_URL=http://192.168.25.128:8080/ -e KEYCLOAK_REALM=Micro-Realm -e KEYCLOAK_CLIENT_ID=Micro-Client-Id nhlinh2207/userservice:1