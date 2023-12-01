pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
    }
  stages {
        stage('Init/Plan/Apply') {
            steps {
                script {
                    withAWS(region: AWS_REGION, credentials: 'MyAWSCredentials') {
                        sh 'terraform init'
                        sh 'terraform plan'
                        sh 'terraform apply -auto-approve'
                    }
                }
            }
        }
    }
}
