docker login \
&& docker build -t cooperative-app . \
&& docker tag cooperative-app:latest zherro/cooperative-app:latest \
&& docker push zherro/cooperative-app:latest \
&& docker pull zherro/cooperative-app:latest
