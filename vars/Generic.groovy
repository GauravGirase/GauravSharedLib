  class Utilities implements Serializable {
    def steps
    Utilities(steps) {this.steps = steps}
    def gitcheckout(args){
        steps.git "branch: 'main', url: ${repoUrl}"
    } 
 }
