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
                sh '''
                pkill -f target/demo-0.0.1-SNAPSHOT.jar || true
                mvn clean package
                java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8081 &
                sleep 10
                pkill -f target/demo-0.0.1-SNAPSHOT.jar
                '''
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
                    // Apply the deployment and service YAML files from the repository
                    sh '''
                    kubectl --kubeconfig=${KUBECONFIG} config set-context --current --namespace=${KUBE_NAMESPACE}
                    kubectl --kubeconfig=${KUBECONFIG} apply -f deployment.yaml
                    kubectl --kubeconfig=${KUBECONFIG} apply -f service.yaml
                    kubectl --kubeconfig=${KUBECONFIG} rollout status deployment/springboot-deployment
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
