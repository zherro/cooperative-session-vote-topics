docker-compose stop \
	&& docker rm -f cooperative-api \
	&& docker rm -f cooperative-users-api \
	&& docker rm -f cooperative-postgres  \
	&& docker-compose down \
	&& cd cooperative-api/ \
	 && ./gradlew clean build \
	 && docker build -t cooperative-api . \
	&& cd ../ \
	&& cd users-api/ \
	 && ./gradlew clean build \
	 && docker build -t cooperative-users-api . \
	&& cd ../ \
	&& docker-compose up
