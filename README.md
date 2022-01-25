# <div align="center">CQRS Design Pattern Java </div>
This repository contains CQRS implementation in Java. I've written this code-base step by step on Medium that is my Turkish content as called "Java ile CQRS Design Pattern | Docker, Elasticsearch, RabbitMQ, Spring, MySQL"

<div align="center">

![1_n3zJulJ4aZA1_tVCjJ9SBw](https://user-images.githubusercontent.com/43035417/151057004-de91dfe4-2fcf-4573-9928-0ce7600992fe.png)

</div>


# Usage and Install

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
