pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        AWS_CREDENTIALS_ID = 'AWS_ID' 
    }

    stages {
        stage('Terraform Init') {
            steps {
                script {
                        withAWS(region: AWS_REGION, credentials: AWS_CREDENTIALS_ID) {
                            sh 'terraform init'
                        }
                    }
                }
            }
        stage('Terraform Plan') {
            steps {
                script {
                        withAWS(region: AWS_REGION, credentials: AWS_CREDENTIALS_ID) {
                            sh 'terraform plan'
                        }
                    }
                }
            }
        stage('Terraform Apply') {
            steps {
                script {
                        withAWS(region: AWS_REGION, credentials: AWS_CREDENTIALS_ID) {
                            sh 'terraform apply -auto-approve'
                        }
                    }
                }
            }
    }
