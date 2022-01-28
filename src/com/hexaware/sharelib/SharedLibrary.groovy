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
    
    this.steps.sh "docker build -t gauravdocker1234/${this.steps.env.JOB_NAME}:${this.steps.env.BUILD_NUMBER} ."
    
  }
  
  void pushDockerImage(){
    
    this.steps. withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')])
                   {
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                        sh "docker push gauravdocker1234/${this.steps.env.JOB_NAME}:${this.steps.env.BUILD_NUMBER}"
                    }
    
      }
  
  void removeLocalDockerImage(){
    
    this.steps.sh "docker rmi gauravdocker1234/${this.steps.env.JOB_NAME}:${this.steps.env.BUILD_NUMBER}"
    
  }
  
   void applicationDeploy(){
    
    this.steps.sshagent(['dev-server']) {
                    // some block
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker rm -f ${this.steps.env.JOB_NAME} || true"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker run -p 9001:8080 -d --name ${this.steps.env.JOB_NAME} gauravdocker1234/${this.steps.env.JOB_NAME}:${this.steps.env.BUILD_NUMBER}"
            }
    
      }
  
  void test(){
    
    this.steps.echo "current Build Number is : ${this.steps.BUILD_NUMBER}"
    
  }
  
}





