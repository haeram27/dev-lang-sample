# Getting started with the Elastic Stack and Docker-Compose

This repo is in reference to the blog [Getting started with the Elastic Stack and Docker-Compose](https://www.elastic.co/blog/getting-started-with-the-elastic-stack-and-docker-compose)

Please feel free to ask any questions via issues [here](https://github.com/elkninja/elastic-stack-docker-part-one/issues), our [Community Slack](https://ela.st/slack), or over in our [Discuss Forums](https://discuss.elastic.co/).

Pull Requests welcome :)

## blog post about learning elasticsearch with this docker compose samples

<https://www.elastic.co/blog/getting-started-with-the-elastic-stack-and-docker-compose>

## example docker compose files for elasticsearch

<https://github.com/elkninja/elastic-stack-docker-part-one>

## refs: docker compose commands

```bash
docker compose up         create + start service in dockercompose.yml
docker compose down       stop and remove containers + networks
docker compose down -v    also volumes
docker compose start      existing stopped container > start
docker compose stop       existing started container > stop
```

### run service with designated docker-compose.yaml

```bash
docker compose up -d -f my-docker-compose.yaml
```

### copy elasticsearch's ca.crt file to local host

```bash
mkdir /tmp/es
docker cp elasticsearch-es01-1:/usr/share/elasticsearch/config/certs/ca/ca.crt /tmp/es
```

### connect to elasticsearch using curl from local host

```bash
curl --cacert /tmp/es/ca.crt -u elastic:changeme https://localhost:9200
```

### query to elasticsearch using curl

```bash
curl -X GET --cacert /tmp/es/ca.crt -u elastic:changeme https://localhost:9200/<index>\?pretty
```

## Using kibana

### connect kibana using browser

`http://localhost:5601`
credetials=elastic:changeme

### using kibana console for query

For using GUI console,
kibana > Mangagement > Dev tools > Console

```text
# show cluster health
GET /_cluster/health

# show indices
GET /_cat/indices?v

# show shards
GET /_cat/shards?v

PUT /_settings
  {
  "index" : {
  "number_of_replicas":0
  }
}

PUT /my_index
{
  "settings": {
    "index": {
      "number_of_shards": 1,   // You can specify the number of shards here
      "number_of_replicas": 0  // Set to 0 for no replicas
    }
  }
}

POST /my_index/_doc/
{
  "title": "Elasticsearch Basics",
  "author": "John Doe",
  "publish_date": "2024-05-09",
  "tags": ["search", "analytics"]
}

GET /my_index/_doc/cPIOYY8B_XiVWGWwT5R1

POST /my_index/_search
{
  "query": {
    "match": {
      "author": "John Doe"
    }
  }
}

DELETE /my_index
```
