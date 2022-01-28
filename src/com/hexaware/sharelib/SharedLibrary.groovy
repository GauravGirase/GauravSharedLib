package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(String repoUrl) {
     git branch: 'main', url: '${repoUrl}'
  }
}
