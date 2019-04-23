pipeline {
  agent any
  tools {
    maven 'Maven 3.6.0'
    jdk 'jdk8'
  }
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
    stage('Deploy') {
        steps {
            sh './scripts/deploy.sh'
        }
    }
  }
}
