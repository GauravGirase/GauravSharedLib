package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(String name) {
    
    steps.stage('Checkout') {
      steps.echo "Git url '${name}'"
        }
    
  }
}
