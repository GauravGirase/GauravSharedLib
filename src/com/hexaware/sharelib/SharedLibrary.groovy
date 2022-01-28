package com.hexaware.sharedlib;
import com.hexaware.sharelib.checkOut;

public class SharedLibrary {
  def steps
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
    steps.sh '''
    docker login --help
    '''
  }
  
  public void startCheckout() {
    steps.checkOut("https://github.com/GauravGirase/springBootAppDemo.git")
  }
  
  
 
}

