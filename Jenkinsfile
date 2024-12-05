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
        KUBE_CONTEXT = "cluster1" // Ensure this matches the intended Kubernetes context
        KUBE_NAMESPACE = "default"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
    }
    stages {
        stage('Set Kubernetes Context') {
            steps {
                script {
                    // Check if the context exists, create it if necessary
                    def contextExists = sh(
                        script: "kubectl config get-contexts | grep -w ${KUBE_CONTEXT} || true",
                        returnStdout: true
                    ).trim()

                    if (!contextExists) {
                        echo "Context '${KUBE_CONTEXT}' not found. Creating it."
                        sh '''
                        kubectl config set-context ${KUBE_CONTEXT} \
                            --cluster=swe645cluster2 \
                            --user=eks-user \
                            --namespace=${KUBE_NAMESPACE}
                        '''
                    }

                    // Set the context for the deployment
                    sh "kubectl config use-context ${KUBE_CONTEXT}"
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
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-cred']]) {
                        sh '''
                        # Generate token for EKS and configure kubectl
                        export TOKEN=$(aws eks get-token --region us-east-1 --cluster-name swe645cluster2 | jq -r '.status.token')
                        sudo kubectl config set-credentials eks-user --token=$TOKEN
                        sudo kubectl config use-context ${KUBE_CONTEXT}

                        # Set the namespace
                        sudo kubectl config set-context --current --namespace=${KUBE_NAMESPACE}

                        # Replace placeholders in deployment.yaml with the current build number
                        sed -i "s|\\\${BUILD_NUMBER}|${BUILD_NUMBER}|g" deployment.yaml

                        # Apply the Kubernetes manifests
                        sudo kubectl apply -f deployment.yaml --validate=false

                        sudo kubectl apply -f service.yaml

                        # Rollout status to confirm the deployment
                        sudo kubectl rollout status deployment/springboot-deployment -n ${KUBE_NAMESPACE}
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


