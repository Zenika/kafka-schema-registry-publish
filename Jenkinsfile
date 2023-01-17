pipeline {
    agent none
    stages {
        stage('build, tests and upload') {
            agent {
                docker {
                    image 'maven:3.8.5-openjdk-17-slim'
                    args '-v $PWD:$PWD -w $PWD -v /var/run/docker.sock:/var/run/docker.sock -v $HOME/.m2:/root/.m2:z -u root'
                }
            }
            stages {
                stage('build') {
                    steps {
                        sh './gradlew clean package'
                    }
                }
                stage('upload artifact') {
                    steps {
                        sh './gradlew publish'
                    }
                }
            }
        }
    }
}