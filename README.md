# pod-tp2

## Compilation

```bash
cd tpe2
chmod u+x build
./build
```

## Run Server

```bash
pwd # => $PROJECT_ROOT/tpe2
chmod u+x run-server
./run-server '10.6.1.*' # starts server. Pass IP range as parameter to change <interfaces>
```

## Run Queries

```bash
pwd # => $PROJECT_ROOT/tpe2
chmod u+x query*
# query 1 writes output to $outPath/query1.csv and expects to find
# aeropuertos.csv and movimientos.csv at $inPath
./query1 -Daddresses='10.6.1.10;10.6.1.11:5702' -DinPath=/home/mlund -DoutPath=.
```
*Note*: there is a script for every query. Some may require additional properties to be set.

## Installing Hazelcast Management Center v3.7.8 (Docker Image)

```bash
sudo docker pull hazelcast/management-center@sha256:b1cdb8b6854ced1d272783560f58eb3df0a82724e298c6a27aa4c8dec627ed10
```

To run manually:

```bash
sudo docker run -p 127.0.0.1:8080:8080/tcp e28e49d694bc
```

## Useful docker commands

```bash
sudo docker ps # list docker containers
sudo docker image ls # show pulled images
sudo docker image inspect <IMAGE_ID> # inspect the specified image to see configurations
sudo docker container ls # show all running containers
sudo docker stop <CONTAINER_ID> # stop specific container
```
