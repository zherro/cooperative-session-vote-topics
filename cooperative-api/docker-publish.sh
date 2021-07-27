./gradlew clean build \
&& docker login \
&& docker build -t cooperative-api . \
&& docker tag cooperative-api:latest zherro/cooperative-api:latest \
&& docker push zherro/cooperative-api:latest \
&& docker pull zherro/cooperative-api:latest
