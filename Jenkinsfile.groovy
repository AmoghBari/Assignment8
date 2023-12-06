pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
    }

    parameters {
        booleanParam(defaultValue: false, description: 'Run Terraform Destroy?', name: 'RunDestroy')
    }

    stages {
        stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Create Infrastructure') {
            when {
                expression { params.RunDestroy == false }
            }
            stages {
                stage('Terraform Plan/Apply') {
                    steps {
                        script {
                            withAWS(region: AWS_REGION, credentials: 'AWS_ID') {
                                sh 'terraform plan'
                                sh 'terraform apply -auto-approve'
                            }
                        }
                    }
                }
                stage('Get Private Key') {
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
                expression { params.RunDestroy == true }
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
