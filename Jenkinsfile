pipeline {
    agent {
        docker { image 'maven:3.8.7-eclipse-temurin-11' }
    }
    stages {
        stage('build, tests and upload') {
            stages {
                stage('build') {
                    steps {
                        sh './gradlew clean build'
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