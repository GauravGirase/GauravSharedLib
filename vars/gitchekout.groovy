def call {
   script{
         git branch: 'main', url: 'https://github.com/spring-projects/spring-petclinic.git'
   }
}
