pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "saivamshi1432/springboot-app"
        DOCKER_TAG = "${DOCKER_IMAGE}:${BUILD_NUMBER}"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        KUBE_NAMESPACE = "default"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
        GIT_REPO = "https://github.com/SaiVamshiDasari/swe-645-springboot.git" // Replace with your GitHub repository URL
        GIT_BRANCH = "main" // Branch to fetch
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: "${GIT_BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Build and Test Spring Boot Application') {
            steps {
                sh '''
                # Ensure Maven is installed and available
                mvn clean package

                # Optional: Run the Spring Boot app to verify it starts correctly
                java -jar target/*.jar &
                sleep 10
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
                    sh '''
                    # Ensure kubectl is configured correctly
                    kubectl --kubeconfig=${KUBECONFIG} config set-context --current --namespace=${KUBE_NAMESPACE}

                    # Apply deployment and service configurations
                    kubectl --kubeconfig=${KUBECONFIG} apply -f deployment.yaml
                    kubectl --kubeconfig=${KUBECONFIG} apply -f service.yaml

                    # Update deployment with new Docker image
                    kubectl --kubeconfig=${KUBECONFIG} set image deployment/springboot-deployment springboot-container=${DOCKER_TAG}

                    # Wait for rollout to complete
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
