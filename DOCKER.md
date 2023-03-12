https://hub.docker.com/_/postgres

```bash
$ sudo docker run \
  --name geocoder-postgres \
  -e POSTGRES_PASSWORD=geocoder \
  -d postgres:15

$ sudo apt docker exec -it geocoder-postgres /bin/bash

```
