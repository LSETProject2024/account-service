pipeline {
    agent any
    
    tools {
        jdk 'jdk21'
        maven 'maven3'
    }

    environment{
        SCANNER_HOME= tool 'sonar-scanner'
    }


    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'git-cred', url: 'https://github.com/LSETProject2024/account-service.git'
            }
        }
        
        stage('Compile') {
            steps {
                sh "mvn compile"
            }
        }
        
        stage('File System Scan') {
            steps {
                sh "trivy fs --severity HIGH,CRITICAL --format table -o trivy-fs-report.html ."
            }
        }
        
         stage('SonarQube Analysis'){
            steps {
              withSonarQubeEnv('sonar') {
                    sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=lsetBank -Dsonar.projectKey=lsetBank \
                            -Dsonar.java.binaries=. '''
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                script {
                  waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token' 
                }
            }
        }
        
        stage('Build') {
            steps {
               sh "mvn package"
            }
        }
        
        stage('Build & Tag Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker build -t lsetproject2024/account-service:latest ." 
                        /*sh "docker buildx build --no-cache --platform linux/amd64  -t lsetbank ."*/
                    }
               }
            }
        }
        
        stage('Docker Image Scan') {
            steps {
                sh "trivy image --format table -o trivy-image-report.html lsetproject2024/account-service:latest "
                /*sh "docker run -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image lsetproject2024/account-service:latest --no-progress --scanners vuln  --exit-code 0 --severity HIGH,CRITICAL --format table"*/
            }
        }


        stage('Push Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker push lsetproject2024/account-service:latest"
                    }
               }
            }
        }
        
        
        stage('Deploy K8s') {
            steps {
               script {
                   withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'do-lon1-k8s-cluster', contextName: '', credentialsId: 'k8', namespace: 'webapps', serverUrl: 'https://d7b821ce-6035-4a48-8d86-d2df751f2eb6.k8s.ondigitalocean.com']]){ 
                        sh "kubectl apply -f deployment.yaml"
                    }
                }
            }
        }
        
        stage('Verify the Deployment') {
            steps {
               script {
                   withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'do-lon1-k8s-cluster', contextName: '', credentialsId: 'k8', namespace: 'webapps', serverUrl: 'https://d7b821ce-6035-4a48-8d86-d2df751f2eb6.k8s.ondigitalocean.com']]){ 
                    sh "kubectl get pods -n webapps"
                    sh "kubectl get svc -n webapps"
                    }
                }
            }
        }
         


    }
      post {
    always {
          script {
            def jobName = env.JOB_NAME
            def buildNumber = env.BUILD_NUMBER
            def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
            def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'

            def body = """
                <html>
                <body>
                <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                <h2>${jobName} - Build ${buildNumber}</h2>
                <div style="background-color: ${bannerColor}; padding: 10px;">
                <h3 style="color: white;">Pipeline Status: ${pipelineStatus.toUpperCase()}</h3>
                </div>
                <p>Check the <a href="${BUILD_URL}">console output</a>.</p>
                </div>
                </body>
                </html>
            """

          emailext (
            subject: "${jobName} - Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
            body: body,
            to: 'lsetdevops2024@gmail.com',
            from: 'jenkins@example.com',
            replyTo: 'jenkins@example.com',
            mimeType: 'text/html',
            attachmentsPattern: 'trivy-image-report.html, trivy-fs-report.html'
            )
          }
        }
      }    

  
}
