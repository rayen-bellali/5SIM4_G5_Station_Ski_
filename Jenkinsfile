pipeline {
    agent any 

    tools {
        maven 'M2_HOME' //Maven version
        jdk 'JAVA_HOME' 
    }

    environment {
        PROJECT_NAME = "RegPipline"
        GIT_REPO = "https://github.com/rayen-bellali/5SIM4_G5_Station_Ski_"
        BRANCH_NAME = "JebabliMonia_sim4_G5"
        
    }

    stages {
        stage('GIT') {
            steps {
                echo 'Getting project from git...'
                git branch: "${BRANCH_NAME}", url: "${GIT_REPO}"
            }
        }
      
        
        stage('MVN CLEAN') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean package -DskipTests'
                
            }
        }

        stage('MVN COMPILE') {
            steps {
                echo 'Running Maven compile...'
                sh 'mvn compile'
            }
        }
        
  

    }
    

    post {
        always {
            echo 'Pipeline completed.'
        }
        success {
            echo 'SUCCESS.'
        }
        failure {
            echo 'FAIL.'
        }
    }
}
