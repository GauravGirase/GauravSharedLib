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
 
 
}


this.steps.withCredentials([this.steps.usernamePassword(
                credentialsId: this.usernamePasswordCredential,
                passwordVariable: 'PASSWORD',
                usernameVariable: 'USERNAME'
            )]) {
                // $USERNAME is only available in step.sh/echo, not "\${USERNAME}" directly
                this.username = this.steps.sh(script: "echo \"\${USERNAME}\"", returnStdout: true).trim()
                // FIXME: encode username/passsword?
                this.steps.sh "echo \"https://\${USERNAME}:\${PASSWORD}@${GITHUB_DOMAIN}\" > ~/.git-credentials"
            }


