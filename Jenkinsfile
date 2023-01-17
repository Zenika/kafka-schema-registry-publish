pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('publish schemas') {
            steps {
                sh './gradlew publishSchemas'
            }
        }
        stage('upload artifact') {
            steps {
                sh './gradlew publish'
            }
        }
    }
}