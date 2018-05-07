pipeline {
    agent { docker { image 'maven:3.3.3' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh 'ls -al'
                sh 'cd SpringProjects/QnA'
                sh 'ls -al'
            }
        }
    }
}