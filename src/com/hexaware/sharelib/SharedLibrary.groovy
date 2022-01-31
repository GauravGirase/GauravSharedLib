package com.hexaware.sharedlib;
// import org.yaml.snakeyaml.Yaml
// import groovy.yaml.YamlSlurper



public class SharedLibrary {
  def steps
  def pipelineConfig 
  def configFile = 'config.yml'

  
  SharedLibrary(steps) {
    this.steps = steps
  }
  
  
  void readConfigFile(){
    pipelineConfig  = this.steps.readYaml(text: this.steps.libraryResource(this.configFile))
  }
  
  
  void cloneRepository(){
    
    this.steps.git(
                url           : "${this.pipelineConfig.git.url}",
                branch        : "${this.pipelineConfig.git.branch}"
            )
    
  }
  
  void buildPackages(){
    
    this.steps.sh "mvn clean package"
    
  }
  
  void buildDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker build -t gauravdocker1234/${jobName}:${buildNumber} ."
    
  }
  
  void pushDockerImage(String jobName, String buildNumber){
    
    this.steps.withCredentials([this.steps.usernamePassword(
          credentialsId: 'dockerhubcredentials',
          passwordVariable: 'DOCKERPASS',
          usernameVariable: 'DOCKERUSERNAME'
        )]){
               this.steps.sh "docker login -u \${DOCKERUSERNAME} -p \${DOCKERPASS}"
               this.steps.sh "docker push gauravdocker1234/${jobName}:${buildNumber}"
           }
      }
  
  void removeLocalDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker rmi gauravdocker1234/${jobName}:${buildNumber}"
    
  }
  
   void applicationDeploy(String jobName, String buildNumber){
    
    this.steps.sshagent(['dev-server']) {
                    
//                     this.steps.sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker rm -f ${jobName} || true"
                    this.steps.sh "ssh -o StrictHostKeyChecking=no ${this.pipelineConfig.ssh.host_user}@${this.pipelineConfig.ssh.host_ip} docker rm -f ${jobName} || true"
                    this.steps.sh "ssh -o StrictHostKeyChecking=no ${this.pipelineConfig.ssh.host_user}@${this.pipelineConfig.ssh.host_ip} docker run -p ${this.pipelineConfig.ssh.port}:8080 -d --name ${jobName} gauravdocker1234/${jobName}:${buildNumber}"
//                     this.steps.sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.34.100 docker run -p 9001:8080 -d --name ${jobName} gauravdocker1234/${jobName}:${buildNumber}"
            }
    
      }
  
  void test(){    
      this.steps.echo "The config content ${this.pipelineConfig.git.url}"
    }  
}
