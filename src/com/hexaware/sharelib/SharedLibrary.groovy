package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
    steps.echo "Hello World..."
  }
}

def gitcheckout(String repoUrl){
    git branch: 'main', url: '${repoUrl}'
}
