def call(String repoUrl){
  
  pipeline {
    agent any

    stages {
        stage('CheckOut') {
            steps {
              git branch: 'main', url: '${repoUrl}'

            }
        }
        stage('Build') {
            tools {
                    maven "M3"
                 }
            steps {
                sh 'mvn clean package'

            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER} ."

            }
        }
        stage('Push Docker image') {
            steps {
                withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')]) {
                        // some block
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                         sh "docker push gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
                    }
                   
            }
        }
        stage('Removing Docker image from local') {
            steps {
                
                sh "docker rmi gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
            }
        }
        stage("Deploy An application"){
            steps {
                sshagent(['dev-server']) {
                    // some block
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker rm -f ${env.JOB_NAME} || true"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker run -p 9001:8080 -d --name ${env.JOB_NAME} gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
            }
        }
        }
    }
    
}
}
