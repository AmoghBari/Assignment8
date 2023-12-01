pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
    }
    
    stages {
        stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    sh 'terraform plan'
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

            // stage('Terraform Destroy') {
            //     steps {
            //         script  {
            //             sh 'terraform destroy -auto-approve'
            //         }
            //     }
            // }
        }
