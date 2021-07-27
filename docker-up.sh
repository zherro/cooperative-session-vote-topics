docker-compose stop \
	&& docker rm -f zherro-cooperative-api \
	&& docker rm -f zherro-cooperative-users-api \
	&& docker rm -f zherro-cooperative-postgres  \
	&& docker rm -f zherro-cooperative-app  \
	&& docker rmi -f zherro/cooperative-api \
	&& docker rmi -f zherro/cooperative-users-api \
	&& docker rmi -f zherro/cooperative-app  \
	&& docker-compose down \
	&& docker-compose up
