docker rm -f kafka-local elasticsearch-local
docker run -d -e "ADVERTISED_HOST=172.17.42.1" -e "ADVERTISED_PORT=9092" -p 9092:9092 -p 2181:2181 --name=kafka-local "registry.dev.crwd.mx/kafka-test:14"
docker run -d -p 9200:9200 -p 9300:9300 --name=elasticsearch-local "elasticsearch:latest"
