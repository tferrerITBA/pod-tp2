# pod-tp2

## Compilation

```bash
cd tpe2
mvn clean package -e
```

## Run Server

```bash
cd server/target
tar xzf tpe2-server-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-server-1.0-SNAPSHOT-bin/tpe2-server-1.0-SNAPSHOT
java -cp 'lib/jars/*' "ar.edu.itba.pod.server.Server" $*
```

## Run Client
```bash
cd client/target
tar xzf tpe2-client-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-client-1.0-SNAPSHOT-bin/tpe2-client-1.0-SNAPSHOT
java -Daddresses='127.0.0.1' -DinPath=/home/mlund -DoutPath=. -cp 'lib/jars/*' "ar.edu.itba.pod.client.Client"
```

## Installing Hazelcast Management Center v3.7.8 (Docker Image)

```bash
sudo docker pull hazelcast/management-center@sha256:b1cdb8b6854ced1d272783560f58eb3df0a82724e298c6a27aa4c8dec627ed10
```

To run:

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
