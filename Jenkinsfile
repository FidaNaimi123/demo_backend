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
                echo 'Construction du livrable (.jar) dans le dossier target...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archivage du livrable') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Test échec email') {
            steps {
                sh 'exit 1'
            }
        }
    }

    post {
        failure {
            echo 'La construction a échoué, envoi d\'un email...'
            mail to: 'fidanaimi3@gmail.com',
                 subject: "ÉCHEC de la build Jenkins : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "La pipeline a échoué.\n\nConsultez les logs ici : ${env.BUILD_URL}"
        }

        success {
            echo 'Pipeline exécutée avec succès.'
        }
    }
}


