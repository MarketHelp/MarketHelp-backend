FROM eclipse-temurin:21  

COPY . .
RUN chmod +x gradlew
RUN ./gradlew --no-daemon distTar
RUN tar -xf build/distributions/app.tar -C /opt
ENV CLASSPATH=/opt/app/lib/*
USER 2000
WORKDIR /opt/app/lib

CMD ["java", "ru.ovrays.graphontext.ApplicationKt"]
