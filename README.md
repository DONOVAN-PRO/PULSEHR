# PulseHR — Backend & Application RH

Application Spring Boot 3 de gestion RH développée dans le cadre du projet d'évaluation PulseHR.

## Déploiement et Démarrage

### Prérequis
- Java 17+
- Maven 3.8+

### Profil Développeur (Par défaut pour la correction)
L'application s'exécute sur le port `8080` avec une base H2 en mémoire et la console H2 disponible.

Exécutez la commande suivante :
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev