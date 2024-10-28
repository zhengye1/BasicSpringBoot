pipeline {
    agent any

    environment {
        SONAR_TOKEN = '7fc6ca324844d8cc65b6a051e70ca26c629703d6' // 在 Jenkins 中配置的 SonarCloud Token
        SONAR_HOST_URL = 'https://sonarcloud.io'
        SONAR_PROJECT_KEY = 'vincentteam_basicspring'
        SONAR_ORG = 'vincentteam'
    }

 stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // 发布 JUnit 测试报告
                }
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                withSonarQubeEnv('SonarCloud') {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.organization=${SONAR_ORG} \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        success {
            echo 'Build and SonarCloud analysis completed successfully.'
        }
        failure {
            echo 'Build or SonarCloud analysis failed. Please check the logs.'
        }
    }
}
