package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(String name) {
    
    steps.stage('Checkout') {
      steps.steps {
            sh 'echo "Step 1"'
            sh 'echo "Step 2"'
            sh 'echo "Step 3"'
         }
     } 
  }
}
