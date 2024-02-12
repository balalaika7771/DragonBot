pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Устанавливаем оптимальные опции для Maven
                    def mvnHome = tool 'Maven'
                    def mvnCMD = "${mvnHome}/bin/mvn"

                    // Сборка без выполнения тестов
                    sh "${mvnCMD} clean install -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Сборка Docker образа
                    sh "docker build -t dragcave-bot ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    def containerExists = sh(script: 'docker inspect -f {{.State.Running}} dragcave', returnStatus: true) == 0
                    if (containerExists) {
                        echo "Контейнер с именем 'dragcave' уже существует. Остановим его и запустим новый."
                        sh "docker stop dragcave"
                        sh "docker rm dragcave"
                    }
                    sh "docker run --name dragcave --memory 500m -p 8080:8080 -d dragcave-bot"
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Очистка временных файлов и образов
                    sh "docker system prune -f"
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
    }
}