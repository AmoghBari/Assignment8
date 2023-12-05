pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
    }

    parameters {
        booleanParam(defaultValue: false, description: 'Run terraform destroy?', name: 'runDestroy')
    }

    stages {
        stage('Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Create Infrastructure') {
            when {
                expression { params.runDestroy == false }
            }
            stages {
                stage('Plan/Apply') {
                    steps {
                        script {
                            withAWS(region: AWS_REGION, credentials: 'AWS_ID') {
                                sh 'terraform plan'
                                sh 'terraform apply -auto-approve'
                            }
                        }
                    }
                }
                stage('Get private key') {
                    steps {
                        script {
                            sh "sudo chmod 400 /var/lib/jenkins/workspace/'AWS Terraform'/private_key.pem"
                            sh "sudo cp /var/lib/jenkins/workspace/'AWS Terraform'/private_key.pem /home/sigmoid/"
                        }
                    }
                } 
            }
        }
        
        stage('Terraform Destroy') {
            when {
                expression { params.runDestroy == true }
            }
            steps {
                script {
                    withAWS(region: AWS_REGION, credentials: 'AWS_ID') {
                        sh 'terraform destroy -auto-approve'
                    }
                }
            }
        }
    }
}
