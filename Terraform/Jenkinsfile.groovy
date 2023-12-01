pipeline {
    agent any

    environment {
        AWS_CREDENTIALS = 'MyAWSCredentials'
    }
    
    stages {
        stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    sh 'terraform apply -auto-approve'
                }
            }
        }
    }

    post {
        always {
            stage('Terraform Destroy') {
                steps {
                    script  {
                        sh 'terraform destroy -auto-approve'
                    }
                }
            }
        }
    }
}