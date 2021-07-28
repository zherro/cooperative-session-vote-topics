docker-compose stop \
	&& docker rm -f zherro-cooperative-api \
	&& docker rm -f zherro-cooperative-users-api \
	&& docker rm -f zherro-cooperative-postgres  \
	&& docker rm -f zherro-cooperative-app  \
	&& docker-compose down \
	&& cd cooperative-api/ \
	 && ./gradlew clean build \
	 && docker build -t zherro-cooperative-api . \
	&& cd ../ \
	&& cd users-api/ \
	 && ./gradlew clean build \
	 && docker build -t zherro-cooperative-users-api . \
	&& cd ../ \
	&& cd cooperative-mvp-app/ \
	&& docker build -t zherro-cooperative-app . \
	&& cd ../ \
	&& docker-compose -f docker-compose-dev.yml up
