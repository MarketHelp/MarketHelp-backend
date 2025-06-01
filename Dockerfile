FROM eclipse-temurin:21

COPY . .
RUN chmod +x gradlew
RUN ./gradlew --no-daemon distTar
RUN tar -xf build/distributions/app.tar -C /opt

ENV CLASSPATH=/opt/app/lib/*
ENV STORAGE_TEMPLATES=/templates
ENV STORAGE_OUTPUT=/visualizations

USER root
RUN mkdir /visualizations
RUN chmod 777 /visualizations
USER 2000
WORKDIR /opt/app/lib


CMD ["java", "ru.ovrays.graphontext.ApplicationKt"]
