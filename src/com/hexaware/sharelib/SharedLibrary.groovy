package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  
  SharedLibrary(steps) {
    this.steps = steps
  }
  
  void cloneRepository(){
    
    this.steps.git(
                url           : "https://github.com/GauravGirase/springBootAppDemo.git",
                branch        : "main"
            )
    
  }
  
  void buildPackages(){
    
    this.steps.sh "mvn clean package"
    
  }
  
  void buildDockerImage(){
    
    this.steps.sh "docker build -t gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER} ."
    
  }
  
  void pushDockerImage(){
    
    this.steps. withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')])
                   {
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                        sh "docker push gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
                    }
    
      }
  
  void removeLocalDockerImage(){
    
    this.steps.sh "docker rmi gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
    
  }
  
   void applicationDeploy(){
    
    this.steps.sshagent(['dev-server']) {
                    // some block
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker rm -f ${env.JOB_NAME} || true"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker run -p 9001:8080 -d --name ${env.JOB_NAME} gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
            }
    
      }
  
}





