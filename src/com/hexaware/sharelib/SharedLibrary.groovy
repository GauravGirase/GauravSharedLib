package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  
  SharedLibrary(steps) {
    this.steps = steps
  }
  
  void cloneRepository(){
    
    this.steps.git(
                url           : "https://${GITHUB_DOMAIN}/${this.repository}.git",
                branch        : "main'
            )
  
  
  
  
  }
 
}

