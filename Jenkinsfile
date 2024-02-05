pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'dragcavebot:latest'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the project
                    sh './mvnw clean package'
                }
            }
        }

        stage('Dockerize') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Run the Docker container
                    sh "docker run -p 8080:8080 -d ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker containers after the build
            script {
                sh 'docker ps -a -q | xargs docker rm -f'
            }
        }
    }
}
