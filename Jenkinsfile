pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "saivamshi1432/springboot-app"
        DOCKER_TAG = "${DOCKER_IMAGE}:${BUILD_NUMBER}"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        KUBE_CONTEXT = "arn:aws:eks:us-east-1:717279734829:cluster/cluster1" // Ensure this matches the intended Kubernetes context
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
                        # Stop any running instance of the application (if exists)
                        pkill -f target/demo-0.0.1-SNAPSHOT.jar || true

                        # Build the Spring Boot application
                        mvn clean package
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(DOCKER_TAG)
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, 'dockerhub-cred') {
                        docker.image(DOCKER_TAG).push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-cred']]) {
                        sh '''
                        # Generate token for EKS and configure kubectl
                        export TOKEN=$(aws eks get-token --region us-east-1 --cluster-name ${KUBE_CONTEXT} | jq -r '.status.token')
                        kubectl config set-credentials arn:aws:iam::717279734829:role/cs645-jenkins-role --token=$TOKEN  # Use your IAM role ARN

                        # Set Kubernetes context and namespace
                        kubectl config use-context ${KUBE_CONTEXT}
                        kubectl config set-context --current --namespace=${KUBE_NAMESPACE}

                        # Replace placeholders in deployment.yaml with the current build number
                        sed -i "s|\\\${BUILD_NUMBER}|${BUILD_NUMBER}|g" deployment.yaml

                        # Apply the Kubernetes manifests
                        kubectl apply -f deployment.yaml --namespace=${KUBE_NAMESPACE} --validate=false
                        kubectl apply -f service.yaml --namespace=${KUBE_NAMESPACE}

                        # Rollout status to confirm the deployment
                        kubectl rollout status deployment/springboot-deployment -n ${KUBE_NAMESPACE}
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Deployment successful!"
        }

        failure {
            echo "Deployment failed. Check the logs for details."
        }

        always {
            // Archive the built JAR file and clean the workspace
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            cleanWs()
        }
    }
}
