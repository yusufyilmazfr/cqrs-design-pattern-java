# <div align="center">CQRS Design Pattern Java </div>
This repository contains CQRS implementation in Java. I've written this code-base step by step on Medium that is my Turkish content as called "Java ile CQRS Design Pattern | Docker, Elasticsearch, RabbitMQ, Spring, MySQL"

<div align="center">

[🇹🇷 Java ile CQRS Design Pattern | Docker, Elasticsearch, RabbitMQ, Spring, MySQL](https://medium.com/@yusufyilmazfr/java-ile-cqrs-design-pattern-docker-elasticsearch-rabbitmq-spring-mysql-bf2278c8c1c4)
  
</div>
  
<div align="center">

![1_n3zJulJ4aZA1_tVCjJ9SBw](https://user-images.githubusercontent.com/43035417/151057004-de91dfe4-2fcf-4573-9928-0ce7600992fe.png)

</div>


# Setup
There are several basic steps below that we need to execute.

## Docker Compose && Environment

Firstly, we need executing docker-compose.yml file, that is given below, due to setuping environment tech. Compose file is already here. [docker-compose.yml](https://github.com/yusufyilmazfr/cqrs-design-pattern-java/blob/main/docker-compose.yml)


```yml
version: "3.9"

services:
  database:
    container_name: classifieds_mysql_container
    image: mysql:latest
    restart: always
    ports:
      - "3307:3306"
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: classifieds
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    volumes:
      - mysql_database:/var/lib/mysql

  rabbitmq:
    container_name: classifieds_rabbitmq_container
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"

  elasticsearch:
    container_name: classifieds_elasticsearch
    image: docker.elastic.co/elasticsearch/elasticsearch:7.15.0
    volumes:
      - esdata:/usr/share/elasticsearch/data
    environment:
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - discovery.type=single-node
    logging:
      driver: none
    ports:
      - "9300:9300"
      - "9200:9200"

volumes:
  mysql_database:
  esdata:
```

```
docker-compose up
```

## MySQL Database Table

After executing docker-compose file we need creating classified, that is a entity we use during the application, table on MySQL.
Database connection information is already defined in docker-compose.yml file, after the MySQL connection we use this schema that is below.

```mysql
CREATE TABLE `classified` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `detail` text,
  `categoryId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

## Elasticsearch Index Creating And Mapping
We need create index on Elasticsearch because of representing database table on it. If you need checking Elasticsearch container status, you may use cURL code that is stay below.

```
curl -XGET "http://localhost:9200/_cat/health?format=json&pretty"
```

Create Index with mapping on Elasticsearch: 

```
curl --location --request PUT 'http://localhost:9200/classifieds' \
--header 'Content-Type: application/json' \
--data-raw '{
    "settings": {
        "index": {
            "number_of_shards": 1,
            "number_of_replicas": 1
        }
    },
    "mappings": {
        "properties": {
            "id": {
                "type": "long"
            },
            "title": {
                "type": "text"
            },
            "price": {
                "type": "double"
            },
            "detail": {
                "type": "text"
            },
            "categoryId": {
                "type": "long"
            }
        }
    }
}'
```

We will see mapping on Elasticsearch if there is no any error. We may use this cURL code that is below for showing mapping.
```
curl -XGET "http://localhost:9200/classifieds/_mapping?pretty&format=json"
```

It show use created index's mapping.

## RabbitMQ
We've bound RabbitMQ port in docker-compose file then we've used default RabbitMQ port, we may need checking RabbitMQ status, we are able to go this link to show RabbitMQ dashboard. http://localhost:15672

# Usages

Sending request to api then creating data on MySQL then sending RabbitMQ event that will update Elasticsearch:

```
curl --location --request POST 'http://localhost:8080/classifieds' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Macbook Pro 2019",
    "detail": "Sahibinden çok temiz Macbook Pro 2019.",
    "price": 27894,
    "categoryId": 47
}'
```

Reading classified list from Elasticsearch: 

```
curl --location --request GET 'http://localhost:8080/classifieds'
```

