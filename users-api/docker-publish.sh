./gradlew clean build \
&& docker login \
&& docker build -t cooperative-users-api . \
&& docker tag cooperative-users-api:latest zherro/cooperative-users-api:latest \
&& docker push zherro/cooperative-users-api:latest \
&& docker pull zherro/cooperative-users-api:latest
