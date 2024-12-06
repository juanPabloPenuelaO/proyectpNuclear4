#  # Usar una imagen base de Java
# FROM openjdk:21

# # Establecer el directorio de trabajo en el contenedor
# WORKDIR /app

# # Copiar el archivo JAR de la aplicación al contenedor
# COPY target/modulo-prestamos-0.0.1-SNAPSHOT.jar /app/modulo-prestamos.jar

# # Exponer el puerto en el que la aplicación escuchará
# EXPOSE 8083

# # Comando para ejecutar la aplicación Java
# CMD ["java", "-jar", "modulo-prestamos.jar"]




# Usar una imagen de Maven para construir la aplicación
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente al contenedor
COPY src ./src

# Construir el proyecto
RUN mvn package -DskipTests

# Usar una imagen ligera para ejecutar la aplicación
FROM eclipse-temurin:21-jre

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/modulo-prestamos-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
