FROM eclipse-temurin:21-jdk

# Instalar utilidades necesarias
RUN apt-get update && apt-get install -y wget unzip curl git

# Variables de entorno para Android SDK
ENV ANDROID_SDK_ROOT=/sdk
ENV PATH=${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

WORKDIR /sdk

# Descarga y configura Android command line tools
RUN mkdir -p cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O cmdline-tools.zip && \
    unzip cmdline-tools.zip -d cmdline-tools && \
    rm cmdline-tools.zip && \
    mv cmdline-tools/cmdline-tools cmdline-tools/latest

# Aceptar licencias SDK
RUN yes | sdkmanager --licenses

# Instalar plataformas y build-tools para API 36
RUN sdkmanager "platform-tools" "platforms;android-36" "build-tools;36.0.0" "ndk-bundle"

# Directorio de trabajo en el proyecto
WORKDIR /project

# Copiar proyecto al contenedor
COPY . /project

# Hacer ejecutable gradlew
RUN chmod +x ./gradlew

# Descargar dependencias y compilar el proyecto
RUN ./gradlew build --refresh-dependencies

# Comando por defecto para compilar la app en modo debug
CMD ["./gradlew", "assembleDebug"]
