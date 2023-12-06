pipeline {
    agent any

    environment {
        TF_HOME = "/var/lib/jenkins/workspace/'AWS Terraform'"
        AWS_REGION = "ap-south-1"
    }

    stages {
        stage('Terraform Init') {
            steps {
                script {
                    // Create Terraform home directory
                    dir(TF_HOME) {
                        // Initialize Terraform
                        sh "terraform init -input=false"
                    }
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    // Run Terraform plan
                    dir(TF_HOME) {
                        sh "terraform plan -out=tfplan -input=false"
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Apply Terraform changes
                    dir(TF_HOME) {
                        withAWS(region: AWS_REGION, credentials: 'AWS_ID') {
                            sh "terraform apply -input=false -auto-approve tfplan"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Terraform apply succeeded!'
        }
        failure {
            echo 'Terraform apply failed!'
        }
    }
}
