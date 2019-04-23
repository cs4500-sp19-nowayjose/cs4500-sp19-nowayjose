pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
       git url: 'https://github.com/cs4500-sp19-nowayjose/cs4500-sp19-nowayjose.git'
      }
    }
    stage('Build') {
        steps {
            sh 'mvn -B -DskipTests clean package'
        }
    }
    stage('Test') {
        steps {
            sh 'mvn test'
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
  }
}
