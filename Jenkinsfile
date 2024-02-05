pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    // Сборка проекта (предполагается, что Maven установлен)
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Собираем Docker-образ
                    sh 'docker build -t dragcavebot:latest .'
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Запускаем Docker-контейнер
                    sh 'docker run -d --name dragcavebot -p 8080:8080 dragcavebot:latest'
                }
            }
        }
    }

    post {
        always {
            // Очистка ресурсов (например, остановка и удаление контейнера)
            script {
                sh 'docker stop dragcavebot || true'
                sh 'docker rm dragcavebot || true'
            }
        }
    }
}
