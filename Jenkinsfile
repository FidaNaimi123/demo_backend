pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    stages {

        stage('Récupération du code') {
            steps {
                echo 'Récupération du projet depuis GitHub...'
                checkout scm
            }
        }

        stage('Tests unitaires') {
            steps {
                echo 'Exécution des tests unitaires...'
                sh 'mvn test'
            }
        }

        stage('Build - Création du livrable') {
            steps {
                echo 'Construction du livrable...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archivage du livrable') {
            steps {
                echo 'Archivage du fichier JAR...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Construction de l image Docker...'
                sh 'docker build -t fadoucha/demo-backend:${BUILD_NUMBER} .'
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Publication de l image sur Docker Hub...'

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

                        docker push "$DOCKER_USERNAME/demo-backend:${BUILD_NUMBER}"

                        docker tag "$DOCKER_USERNAME/demo-backend:${BUILD_NUMBER}" \
                                   "$DOCKER_USERNAME/demo-backend:latest"

                        docker push "$DOCKER_USERNAME/demo-backend:latest"
                    '''
                }
            }
        }
    }

    post {

        failure {
            echo 'La construction a échoué, envoi d un email...'

            mail to: 'fidanaimi3@gmail.com',
                 subject: "ÉCHEC de la build Jenkins : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "La pipeline a échoué.\n\nConsultez les logs ici : ${env.BUILD_URL}"
        }

        success {
            echo 'Pipeline exécutée avec succès.'
        }
    }
}
