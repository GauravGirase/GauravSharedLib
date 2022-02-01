package com.hexaware.sharedlib;


public class SharedLibrary {
   /**
     * Reference to the groovy pipeline variables.
     */
  
  def steps
  def env
  
  /**
     * Reference to the yaml configurations.
     */
  
  def pipelineConfig 
  
   /**
     * yaml configurations file.
     */
  
  def configFile = 'config.yml'
  
   /**
     * Constructs the class.
     *
     * When invoking from a Jenkins pipeline script, the Pipeline must be passed
     * the current environment of the Jenkinsfile to have access to the steps.
     */
  SharedLibrary(steps,env) {
    this.steps = steps
    this.env = env
  }
  
  /**
     * Read resource file
     */
  void readConfigFile(){
    pipelineConfig  = this.steps.readYaml(text: this.steps.libraryResource(this.configFile))
  }
  
   /**
     * Git clonning
     */
  void cloneRepository(){
    
    this.steps.git(
                url           : "${this.pipelineConfig.git.url}",
                branch        : "${this.pipelineConfig.git.branch}"
            )
    
  }
  
  
  /**
     * Build the maven package in order to generate war in var/lib/jenkins/target folder
     */
  void buildPackages(){
    
    this.steps.sh "mvn clean package"
    
  }
  
  /**
     * Build the docker image
     */
  void buildDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker build -t gauravdocker1234/${jobName}:${buildNumber} ."
    
  }
  
   /**
     * Pushing the docker image after buillding locally to DockerHub.
     */
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
  
  /**
     * Removing the docker image creared locally after pushing to DockerHub.
     */
  void removeLocalDockerImage(String jobName, String buildNumber){
    
    this.steps.sh "docker rmi gauravdocker1234/${jobName}:${buildNumber}"
    
  }
  
   /**
     * Deploying an application on Dev server using ssh utility
     */
   void applicationDeploy(String jobName, String buildNumber){
    
    this.steps.sshagent(['dev-server']) {
                    this.steps.sh "ssh -o StrictHostKeyChecking=no ${this.pipelineConfig.ssh.host_user}@${this.pipelineConfig.ssh.host_ip} docker rm -f ${jobName} || true"
                    this.steps.sh "ssh -o StrictHostKeyChecking=no ${this.pipelineConfig.ssh.host_user}@${this.pipelineConfig.ssh.host_ip} docker run -p ${this.pipelineConfig.docker.port}:8080 -d --name ${jobName} gauravdocker1234/${jobName}:${buildNumber}"
            }
    
      }
  
  void test(){    
      this.steps.echo "The Job name is ${this.env.JOB_NAME}"
      this.steps.echo "The Build Number ${this.env.BUILD_NUMBER}"
    }  
}
