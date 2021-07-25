docker-compose stop && docker rm cooperative-api && docker rm cooperative-postgres && docker-compose down && cd cooperative-api/ && docker build -t cooperative-api . && cd ../ && docker-compose up
