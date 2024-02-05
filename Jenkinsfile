pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    // Устанавливаем Maven (если не установлен)
                    tool name: 'Maven', type: 'maven'
                    // Собираем проект
                    sh "mvn clean package"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Собираем Docker образ
                    sh "docker build -t dragcave-bot ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Запускаем Docker контейнер
                    sh "docker run -p 8080:8080 -d dragcave-bot"
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Очищаем временные файлы и образы
                    sh "docker system prune -f"
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful! Your application is running at http://your_server_ip:8080'
        }
    }
}
