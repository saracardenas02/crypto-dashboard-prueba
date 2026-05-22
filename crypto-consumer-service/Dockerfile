# Etapa 1: BUILD
# Imagen oficial de Maven con Java 21 para compilar
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Se copia el pom.xml primero para aprovechar el cache de capas de Docker
# Si el pom.xml no cambia, Docker no re-descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el codigo fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# Etapa 2: RUN
# Imagen final con solo el JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia solo el JAR generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto que expone este servicio
EXPOSE 8081

# Comando para arrancar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
