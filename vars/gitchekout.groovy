def call(giturl){
    echo "${giturl}"
    git branch: 'main', url: $giturl
}
