pipeline {

    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = 'ivalidmit/signalloop-notification-system'
    }

    stages {

        stage('Environment Check') {
            steps {
                sh '''
                    echo "===== Environment ====="

                    echo "Java:"
                    java --version

                    echo "Maven:"
                    ./mvnw --version

                    echo "Docker:"
                    docker --version

                    echo "Working directory:"
                    pwd
                '''
            }
        }

        stage('Build & Test') {
            steps {
                sh '''
                    echo "===== Maven Build ====="
                    ./mvnw clean package
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    echo "===== Docker Build ====="

                    docker build \
                      -t ${DOCKER_IMAGE}:${BUILD_NUMBER} \
                      .

                    docker images | grep signalloop-notification-system
                '''
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | \
                        docker login \
                        -u "$DOCKER_USERNAME" \
                        --password-stdin
                    '''
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    echo "===== Docker Push ====="

                    docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                '''
            }
        }
    }

    post {
        success {
            echo 'CI pipeline completed successfully.'
        }

        failure {
            echo 'CI pipeline failed.'
        }
    }
}
