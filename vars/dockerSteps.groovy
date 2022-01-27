def login(){
    withCredentials([string(credentialsId: 'docpwd', variable: 'dockerpass')]) {
                        sh "docker login -u gauravdocker1234 -p ${dockerpass}"
                    }
}
