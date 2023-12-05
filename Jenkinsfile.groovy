pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
    }
  stages {
        stage('Init/Plan/Apply') {
            steps {
                script {
                    withAWS(region: AWS_REGION, credentials: 'AWS_ID') {
                        sh 'terraform init'
                        sh 'terraform plan'
                        sh 'terraform apply -auto-approve'
                    }
                }
            }
        }

        stage('Print Workspace Directory') {
            steps {
                script {
                    echo "Workspace Directory: ${workspace}"
                }
            }
        }
    }
}
