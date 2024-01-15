# Запуск среды окружения

1. Скачать и установить Docker Desktop, для вашей операционной системы [Docker-Desktop](https://www.docker.com/products/docker-desktop/)
2. Выполнить в командной строке следующие команды:

docker run -d -p 8099:80  --name frontend alphateam35/frontend:local

docker network create socnet

docker pull redis

docker run -d --name kafkad -p 9092:9092 -p 9094:9094 -v C:/KafkaInst/snKafka/data:/bitnami/kafka/data --network socnet skillboxgroup40/kafka:1.0.0

docker run --name snredis -p 6379:6379 --network socnet -d redis

docker run -it -d --name snpostgres --network socnet -p 5432:5432 -e POSTGRES_PASSWORD=skillbox -e POSTGRES_USER=skillbox -e POSTGRES_DB=skillbox -d  skillboxgroup40/kafka:custom_postgres

docker run -p 8080:8080 --name socnet -e HOST=snpostgres  -e HOST_REDIS=snredis -e KAFKA_HOST=kafkad -e KAFKA_PORT=9094 --network socnet -d skillboxgroup40/social_network:latest

# Подключен swagger

Доступ к swagger открыт по адресу http://localhost:8080/api/v1/sw/