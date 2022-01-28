package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(String repoUrl) {
      steps.sh '''
    git fetch origin
    git checkout ${repoUrl}
    git tag ${tag}
      '''
  }
}
