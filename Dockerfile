FROM maven:3-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos archivos de configuración de Maven
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw .

RUN chmod +x mvnw

# Copiamos el código y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- Etapa 2: Ejecución (Run) ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# --- CORRECCIÓN CLAVE ---
# Usamos *.jar para que funcione sin importar cómo se llame tu proyecto
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]