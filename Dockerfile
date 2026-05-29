# Etapa 1: Compilación
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos solo lo necesario para compilar
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto omitiendo los tests para acelerar el proceso
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución (Ultra ligera)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "-Dserver.port=8080", "app.jar"]