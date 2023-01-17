# kafka-schema-registry-publish
Publish schemas to your schemas registry using CI-CD

On host

Authorize jenkins for using the docker socket

https://stackoverflow.com/questions/42164653/docker-in-docker-permissions-error

Get nexus password
```shell
docker exec -it nexus cat /nexus-data/admin.password
```

Create a user kafka kafka on nexus

Create a hosted repository on nexus

Install jenkins and plugins

Create a jenkins user `admin` with password `kafka`

Install docker and docker pipeline plugins on jenkins

Create pipeline in jenkins based on scm:
https://github.com/Zenika/kafka-schema-registry-publish


