# kafka-schema-registry-publish
Publish schemas to your schemas registry using CI-CD

Get jenkins password
```shell
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Get nexus password
```shell
docker exec -it nexus cat /nexus-data/admin.password
```