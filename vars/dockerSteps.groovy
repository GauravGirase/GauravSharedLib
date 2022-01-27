def gitcheckout(String repoUrl){
    git branch: 'main', url: '${repoUrl}'
}


def login(){
    withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')]) {
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                   }
}

def build(){
    sh "docker build -t gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER} ."
}

def push(){
    sh "docker push gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
}

def removeimage(){
    sh "docker rmi gauravdocker1234/${env.JOB_NAME}:${env.BUILD_NUMBER}"
}
