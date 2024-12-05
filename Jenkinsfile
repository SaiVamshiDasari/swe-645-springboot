/*Assignment-3
 *    Team Members
 * Sai Vamshi Dasari-G01464718
 * Aryan Sudhagoni-G01454180
 * Lahari Ummadisetty-G01454186
 */

pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "saivamshi1432/springboot-app"
        DOCKER_TAG = "${DOCKER_IMAGE}:${BUILD_NUMBER}"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
       KUBE_CONTEXT = "cluster1"

        KUBE_NAMESPACE = "default"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
    }
    stages {
        stage('Set Kubernetes Context') {
            steps {
                script {
                    // Retrieve the current context
                    def currentContext = sh(
                        script: "kubectl config current-context",
                        returnStdout: true
                    ).trim()
                    
                    // Set the context for the deployment
                    sh "kubectl config use-context ${currentContext}"
                }
            }
        }
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
                    sh '''
                    # Use the correct kubeconfig file and switch context
                    export KUBECONFIG=/var/lib/jenkins/.kube/config
                    kubectl config use-context ${KUBE_CONTEXT}
                    
                    # Perform the deployment
                    kubectl apply -f deployment.yaml --validate=false
                    kubectl apply -f service.yaml
                    kubectl rollout status deployment/springboot-deployment -n default
                    '''
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
