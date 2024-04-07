FROM openjdk:8
ARG JAR_fILE=target/*.jar
COPY ${JAR_fILE} productService.jar
ENTRYPOINT ["java", "-jar", "/productService.jar"]
EXPOSE 8088

# Build jar : mvn clean package -DskipTests=true
# docker build -t nhlinh2207/productservice:1 .
# docker run -p 8088:8088 --name productService -e EUREKA_SERVER_ADDRESS=http://192.168.25.128:8761/eureka -e DB_URL=jdbc:mysql://192.168.25.128:3306/microProduct -e DB_USER=root -e DB_PASS=fireice22072001 -e CONFIG_SERVER_URL=http://192.168.25.128:9296 -e KEYCLOAK_REAML_URL=http://192.168.25.128:8080/realms/Micro-Realm nhlinh2207/productservice:1