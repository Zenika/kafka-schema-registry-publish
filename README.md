# kafka-schema-registry-publish
Publish schemas to your schemas registry using CI-CD

On host

Authorize jenkins for using the docker socket

https://stackoverflow.com/questions/42164653/docker-in-docker-permissions-error

Get nexus password
```shell
docker exec -it nexus cat /nexus-data/admin.password
```

Create a user `kafka` with password `kafka` on nexus.

Create a hosted repository on nexus named : `kafka` (set Version Policy > Mixed).

Install jenkins and plugins

Create a jenkins user `admin` with password `kafka`

Install docker and docker pipeline plugins on jenkins

Create pipeline in jenkins based on scm:
https://github.com/Zenika/kafka-schema-registry-publish

Create the following credentials in jenkins :

nexus-user (username with password)

```text
username: kafka
password: kafka
```

nexus-repository (secret text): `http://nexus:8081/repository/kafka`


