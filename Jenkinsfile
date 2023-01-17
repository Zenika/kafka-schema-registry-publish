pipeline {
    agent any
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