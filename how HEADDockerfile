# --- Étape 1 : Build de l'application avec Maven ---
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copie des fichiers de configuration Maven pour optimiser le cache des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie des sources et compilation du JAR
COPY src ./src
RUN mvn package -DskipTests

# --- Étape 2 : Image d'exécution minimale ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Variable pour le port (Render fournit la variable PORT dynamiquement)
ENV PORT=8080
EXPOSE ${PORT}

# Copie du fichier JAR généré depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Lancement de l'application Spring Boot
# Forme shell (sh -c) obligatoire ici : la forme exec ["java", ...] ne
# substitue JAMAIS les variables d'environnement comme ${PORT}.
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]