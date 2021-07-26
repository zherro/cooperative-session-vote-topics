docker-compose stop \
	&& docker rm -f cooperative-api \
	&& docker rm -f cooperative-users-api \
	&& docker rm -f cooperative-postgres  \
	&& docker rm -f cooperative-app  \
	&& docker-compose down \
	&& cd cooperative-api/ \
	 && ./gradlew clean build \
	 && docker build -t cooperative-api . \
	&& cd ../ \
	&& cd users-api/ \
	 && ./gradlew clean build \
	 && docker build -t cooperative-users-api . \
	&& cd ../ \
	&& cd cooperative-mvp-app/ \
	&& docker build -t cooperative-app . \
	&& cd ../ \
	&& docker-compose up
