<h1 align="center">Welcome to the schemas management demo 👋</h1>
<p>
  <a href="LICENSE" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

> Publish schemas to your Schema Registry using CI-CD

This project show the best practices to handle your schemas.

## Install

This project uses `docker-compose`. The [docker-compose.yml](./docker-compose.yml) file contains the definition of:
* A Kafka Broker and Zookeeper
* A Schema Registry
* [akhq](https://akhq.io/) to see what's happening in Kafka
* A toolkit to create topics
* A [Jenkins](https://www.jenkins.io/) instance
* A [Nexus](https://fr.sonatype.com/products/nexus-repository) instance

Create the folders that will contain the `nexus` and `jenkins` volumes:
```shell
mkdir jenkins nexus
sudo chmod 777 jenkins nexus
```

Run the docker-compose file:
```shell
docker-compose up -d
```

Some configuration is necessary in Jenkins and Nexus.

### Nexus

Connect to the [Nexus](http://localhost:8081) instance.
Sign in as the `admin` user. The password will be needed, it can be found using the following command:
```shell
docker exec -it nexus cat /nexus-data/admin.password
```
Create a user `kafka` with password `kafka`.
Create a `maven2` hosted repository named : `kafka` (set `Version Policy` as `Mixed`).

### Jenkins
Connect to the [Jenkins](http://localhost:8080) instance.

The Jenkins password will be needed, it can be found using the following command:
```shell
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Install Jenkins with the default plugins.

Create a Jenkins user named `admin` with password `kafka`

Create a multi-branch pipeline in Jenkins based on scm with [this project URL](https://github.com/Zenika/kafka-schema-registry-publish).

You can trigger a build of the main branch, to make sure everything is set up correctly.

Triggering the first build will:
- publish the avro schemas in the Schema Registry
- publish the avro schemas generated classes jar in the Nexus repository

## Usage

### Adding a field without a default value

Update the [screening avro schema](./lib/src/main/avro/screening.avsc) and add a field without a default value at the end of the file:

```json
    {
      "name": "newfield",
      "type": "string"
    }
```

Then, commit, and run the pipeline in Jenkins. The pipeline will fail at the `publish schemas` stage.
If you check the stage's log, you'll see the error:
```text
> Unable to publish the schema screening.avsc in the registry: Schema being registered is incompatible with an earlier schema for subject "screening-value"
```

Adding a field doesn't work because it's not compatible with the subject's settings. By default, the subject inherits the default Schema Registry compatibility settings: `BACKWARD`.

The default compatibility setting can be checked using the `Schema Registry REST API`:
```shell
curl localhost:8083/config
```
returns:
```json
{"compatibilityLevel":"BACKWARD"}
```
See the [Schema Registry REST API documentation](https://docs.confluent.io/platform/current/schema-registry/develop/api.html) for more information.

You can change the default compatibility settings of the Schema Registry, or override it at the subject's level.

### Compatibility cheatsheet

Here is a quick compatibility cheatsheet:

`BACKWARD` - Consumers reading data using the new version of the schema can read data written using the previous version of the schema.

`FORWARD` - Consumers reading data using the previous version of the schema can read data written using the new version of the schema.

`BACKWARD_TRANSITIVE` - Consumers reading data using a new version of the schema can read data written using all previous versions of the schema.

`FORWARD_TRANSITIVE` - Consumers reading data using a previous version of the schema can read data written using all newer versions of the schema.

`FULL` - Both `BACKWARD` and `FORWARD`

`FULL_TRANSITIVE` - Both `BACKWARD` and `FORWARD` for all versions.

### Solving the issue

Adding a field without a default breaks the backward compatibility. Why ? 
A consumer developed using the newer schema, which contains the `newfield` field, can't read older messages written without this field.

To solve this problem, a default value can be added to the `newfield` field:
```json
    {
      "name": "newfield",
      "type": "string",
      "default": "default value"
    }
```

This configuration is backward compatible.
If you commit this code, and launch the Jenkins pipeline, the new schema will be published to the Schema Registry. Then, the artifact will be packaged and deployed to Nexus.

## Generate the asyncapi documentation

Install `asyncapi`.

```shell
cd docs
asyncapi generate fromTemplate ../lib/src/main/asyncapi/asyncapp.yaml @asyncapi/html-template --force-write
```

## Author

👤 **Zenika**

* Website: https://zenika.com/
* Github: [@Zenika](https://github.com/zenika)

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2023 [Zenika](https://github.com/zenika).<br />
This project is [MIT](LICENSE) licensed.

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_