pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "saivamshi1432/springboot-app"
        DOCKER_TAG = "${DOCKER_IMAGE}:${BUILD_NUMBER}"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        KUBE_NAMESPACE = "default"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    git branch: 'main', url: 'https://github.com/SaiVamshiDasari/swe-645-springboot.git'
                }
            }
        }

        stage('Build and Test Spring Boot Application') {
    steps {
        script {
            sh '''
                # Gracefully stop any running application
                pgrep -f target/demo-0.0.1-SNAPSHOT.jar && pkill -f target/demo-0.0.1-SNAPSHOT.jar || true
                mvn clean package
            '''
        }
    }
}


        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, 'dockerhub-cred') {
                        docker.image("${DOCKER_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
    steps {
        script {
            sh '''
            kubectl --kubeconfig=/var/lib/jenkins/.kube/config config set-context --current --namespace=default
            sed -i "s|\\\${BUILD_NUMBER}|${BUILD_NUMBER}|g" deployment.yaml
                kubectl --kubeconfig=/var/lib/jenkins/.kube/config apply -f deployment.yaml
            kubectl --kubeconfig=/var/lib/jenkins/.kube/config apply -f service.yaml
            timeout 5m kubectl --kubeconfig=/var/lib/jenkins/.kube/config rollout status deployment/springboot-deployment
            '''
        }
    }
}

    }

    post {
        always {
            cleanWs() // Clean up workspace
        }
    }
}
