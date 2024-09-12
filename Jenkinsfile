pipeline {
    agent any
    environment {
          AWS_REGION = 'ap-southeast-1'  // Replace with your region
          AWS_ACCOUNT_ID = credentials('AWS_ACCOUNT_ID')
          ECR_REPO_NAME = 'food-delivery-discovery-service'  // Replace with your ECR repository name
          IMAGE_TAG = "${env.BUILD_ID}"  // Or use 'latest' or any other tag
          WORKSPACE = "/var/lib/jenkins/workspace/food-delivery-discovery-server"
    }
    stages {
        stage('Clone Repo to Build') {
            steps {
                git url: 'https://github.com/Topsan2002/food-delivery-discovery-service.git',
                    branch:'master'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}")
                }
            }
        }
        stage('Login to AWS ECR') {
            steps {
                script {
                    // Login to ECR
                    sh '''
                    aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                    '''
                }
            }
        }
        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // Push the Docker image to ECR
                    dockerImage.push()
                }
            }
        }
        stage('Deploy or Run Docker Container') {
            environment {
                WORKSPACE = '/var/lib/jenkins/workspace/food-delivery-compose'

            }
            steps {
                script {
                    def composeFile = "/var/jenkins_home/workspace/food-delivery/food-delivery-docker-compose/docker-compose.yml"
                    def serviceName = "food-delivery-discovery-service"

                    echo "Running Docker container for service '${serviceName}' using Docker Compose file located at: ${composeFile}"


                    sh "docker-compose up -d -f ${composeFile} --no-deps --force-recreate ${serviceName}"
                    sh "docker image prune"
                    // Run the docker-compose up command to start the container
//                     sh "docker-compose -f ${composeFile} up -d ${serviceName}"


                    echo "Docker container for service '${serviceName}' is now running."
                }
            }
        }

    }
}