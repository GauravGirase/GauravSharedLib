package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(Map config) {
    steps.git"{ branch: '${Map.main}', url: '${Map.repoUrl}'}"
  }
}
