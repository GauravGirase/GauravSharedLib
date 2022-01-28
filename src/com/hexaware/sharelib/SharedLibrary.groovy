package com.hexaware.sharedlib;

public class SharedLibrary {
  def steps
  
  public SharedLibrary(steps) {
    this.steps = steps
  }
  
  public void startBuild(String repoUrl) {
      steps.sh '''now="$(date)"
                    printf "Current date and time %s\n" "$now"
                    now="$(date +'%d/%m/%Y')"
                    printf "Current date in dd/mm/yyyy format %s\n" "$now"
                    echo "Starting backup at $now, please wait..."'''
  }
}
