# post about learning elasticsearch with this docker compose samples
https://www.elastic.co/blog/getting-started-with-the-elastic-stack-and-docker-compose

# example docker compose files for elasticsearch
https://github.com/elkninja/elastic-stack-docker-part-one

# refs: docker compose commands
docker compose up         create + start service in dockercompose.yml
docker compose down       stop and remove containers + networks
docker compose down -v    also volumes
docker compose start      existing stopped container > start
docker compose stop       existing started container > stop



### connect kibana using browser
http://localhost:5601
elastic:changeme


### copy elasticsearch's ca.crt file to local host
```
mkdir /tmp/es
docker cp elasticsearch-es01-1:/usr/share/elasticsearch/config/certs/ca/ca.crt /tmp/es
```

### connect to elasticsearch using curl from local host
```
curl --cacert /tmp/es/ca.crt -u elastic:changeme https://localhost:9200
```

### query to elasticsearch using curl
```
curl -X GET --cacert /tmp/es/ca.crt -u elastic:changeme https://localhost:9200/\<index\>\\?pretty
``` 