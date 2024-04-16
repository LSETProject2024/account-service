#!/bin/sh
DOCKER_DEFAULT_PLATFORM="linux/amd64" docker build -t account-service .
docker tag account-service zonopact/account-service:1.0.0
docker push zonopact/account-service:1.0.0
kubectl apply -f deployment.yaml