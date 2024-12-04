/*Assignment-3
 *    Team Members
 * Sai Vamshi Dasari-G01464718
 * Aryan Sudhagoni-G01454180
 * Lahari ummadisetty-G01454186
 */

pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "saivamshi1432/springboot-app"
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
                        # Stop any running application
                        pkill -f target/demo-0.0.1-SNAPSHOT.jar || true
                        mvn clean package
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, 'dockerhub-cred') {
                        docker.image("${DOCKER_IMAGE}:${BUILD_NUMBER}").push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh '''
                        sudo kubectl --kubeconfig=${KUBECONFIG} config set-context --current --namespace=${KUBE_NAMESPACE}
                        sed -i "s|\\\${BUILD_NUMBER}|${BUILD_NUMBER}|g" deployment.yaml
                        sudo kubectl --kubeconfig=${KUBECONFIG} apply -f deployment.yaml
                        sudo kubectl --kubeconfig=${KUBECONFIG} apply -f service.yaml
                        sudo timeout 5m kubectl --kubeconfig=${KUBECONFIG} rollout status deployment/springboot-deployment
                    '''
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            cleanWs() // Clean workspace after build
        }
    }
}
