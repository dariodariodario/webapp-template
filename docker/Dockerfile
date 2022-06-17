FROM amazoncorretto:17-alpine-jdk
RUN apk add --no-cache nodejs maven npm
WORKDIR /app
COPY . .
RUN npm install -D webpack webpack-cli
RUN scripts/clean.sh & scripts/build.sh
RUN mvn package -DskipTests
CMD ["java", "-jar", "target/app.jar" ]