pipeline {
    stages {
        stage('build, tests and upload') {
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