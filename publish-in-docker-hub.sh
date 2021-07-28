docker login \
	&& cd cooperative-api/ \
	 && ./gradlew clean build \
		&& docker build -t cooperative-api . \
		&& docker tag cooperative-api:latest zherro/cooperative-api:latest \
		&& docker push zherro/cooperative-api:latest \
		&& docker pull zherro/cooperative-api:latest \
	&& cd ../ \
	&& cd users-api/ \
		&& ./gradlew clean build \
		&& docker build -t cooperative-users-api . \
		&& docker tag cooperative-users-api:latest zherro/cooperative-users-api:latest \
		&& docker push zherro/cooperative-users-api:latest \
		&& docker pull zherro/cooperative-users-api:latest \
	&& cd ../ \
	&& cd cooperative-mvp-app/ \
		&& docker build -t cooperative-app . \
		&& docker tag cooperative-app:latest zherro/cooperative-app:latest \
		&& docker push zherro/cooperative-app:latest \
		&& docker pull zherro/cooperative-app:latest \
	&& cd ../
