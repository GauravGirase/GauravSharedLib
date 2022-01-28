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
  
  void buildDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker build -t gauravdocker1234/${jobName}:${buildNumber} ."
    
  }
  
  void pushDockerImage(String jobName, String buildNumber){
    
    this.steps.withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')])
                   {
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                        sh "docker push gauravdocker1234/${jobName}:${jobName}"
                    }
    
      }
  
  void removeLocalDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker rmi gauravdocker1234/${jobName}:${buildNumber}"
    
  }
  
   void applicationDeploy(String jobName, String buildNumber){
    
    this.steps.sshagent(['dev-server']) {
                    // some block
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker rm -f ${jobName} || true"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker run -p 9001:8080 -d --name ${jobName} gauravdocker1234/${jobName}:${buildNumber}"
            }
    
      }
  
  void test(String buildNumber){
      this.steps.echo "Build Number is ${buildNumber}"
    }  
}





