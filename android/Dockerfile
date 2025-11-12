FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y wget unzip curl git dos2unix

ENV ANDROID_SDK_ROOT=/sdk
ENV PATH=${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

WORKDIR /sdk

RUN mkdir -p cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O cmdline-tools.zip && \
    unzip cmdline-tools.zip -d cmdline-tools && \
    rm cmdline-tools.zip && \
    mv cmdline-tools/cmdline-tools cmdline-tools/latest

RUN yes | sdkmanager --licenses

RUN sdkmanager "platform-tools" "platforms;android-36" "build-tools;36.0.0" "ndk-bundle"

WORKDIR /project

COPY . /project

# Convertir gradlew a formato Unix y dar permisos ejecutables
RUN dos2unix /project/gradlew
RUN chmod +x /project/gradlew

RUN ls -la /project

RUN sh /project/gradlew build --refresh-dependencies

# Exponer puerto 5005 para depuración JVM remota
EXPOSE 5005

CMD ["sh", "/project/gradlew", "assembleDebug", "-jvmopts", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
