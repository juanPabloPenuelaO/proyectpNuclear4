 # Usar una imagen base de Java
FROM openjdk:21

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/modulo-prestamos-0.0.1-SNAPSHOT.jar /app/modulo-prestamos.jar

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8083

# Comando para ejecutar la aplicación Java
CMD ["java", "-jar", "modulo-prestamos.jar"]