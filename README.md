# CCSOP - BACKEND INFRASTRUCTURE

**CCSOP**, une plateforme de livraison à domicile après commande auprès de restaurant dévelopé avec Spring Boot.

---

## 📌 Table des Matières

1. [Descriptif du Projet](#descriptif-du-projet)
2. [Choix Technologiques](#choix-technologiques--choix-de-lapproche)
3. [Outils de Développement & Prérequis](#outils-de-développement--prérequis)
4. [Quick Start](#quick-start)
5. [Schéma d'Infrastructure Réseau (Docker Compose)](#schéma-dinfrastructure-réseau-docker-compose)
6. [Conception & Modélisation](#conception--modélisation)
7. [Pratiques Git & Commits](#Pratiques-Git--Commits)
8. [Guide utilisateur](#Guide-utilisateur)
9. [Problèmes Connus & Limitations](#problèmes-connus--limitations)

---

## Descriptif du Projet

**CCSOP livraison** est une plateforme web pour commander des repas auprès des restaurants partenaires avec livraison.

Un client peut choisir le restaurant dans lequel il souhaite commander et accéder à sa carte dédiée. Une fois sa commande passée, une livraison est automatiquement créée et proposée aux livreurs. La livraison est clôturée dès lors qu'elle est validée à la fois par le livreur et par le client.

### Composants techniques
Le projet est composé de trois briques principales fonctionnant dans des conteneurs isolés :
1. **ms-api-gateway** : Un proxy inverse Nginx servant de passerelle unique, redirigeant les flux HTTP vers les bons microservices et gérant les erreurs d'indisponibilité.
2. **ms-account** : Le service Spring Boot gérant l'ensemble du traitement fonctionnel des requêtes auprès du backend. 
3. **ms-CCSOP-database** : Une base de donnée mysql qui stocke l'ensemble des données du site web. 

---

## Choix Technologiques 
| Domaine | Technologie | Justification |
|---|---|---|
| Langage & Framework | Java 21 (LTS) + Spring Boot 4 | Écosystème mature et largement documenté pour des API REST ; support long terme de Java 21. |
| Persistance | Spring Data JPA + MySQL 8.0 | Mapping objet-relationnel standard ; MySQL pour sa fiabilité et sa simplicité d'exploitation. |
| Passerelle (Gateway) | Nginx | Reverse proxy léger et performant servant de point d'entrée unique et gérant les indisponibilités. |
| Conteneurisation | Docker & Docker Compose | Environnements reproductibles et isolation réseau des services et des bases de données. |

---

## Outils de Développement & Prérequis
Pour compiler, exécuter et tester ce projet en local, assurez-vous d'avoir installé les outils suivants :
- **IDE** : [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (recommandé avec le plugin Lombok)
- **Java** : [JDK 21 - Temurin](https://adoptium.net/temurin/releases/?version=21) (de préférence)
- **Moteur de base de données** : [MySQL 8.0](https://dev.mysql.com/downloads/mysql/) (géré par Docker en local)
- **Conteneurs** : [Docker 24+ & Docker Compose v2+](https://docs.docker.com/get-docker/) (Docker Desktop inclut les deux)

---

## Quick Start

Une seule commande lance toute l'infrastructure en local (bases de données, microservices et Gateway). Prérequis : Docker et Docker Compose (voir [Outils de Développement](#outils-de-développement--prérequis)).

```bash
# 1. Créer le .env à partir de l'exemple, puis y renseigner mots de passe et ports
cp sample.env .env

# 2. Construire et démarrer tous les services
docker compose up -d --build
```

L'application est accessible via la Gateway sur `http://localhost:NGINX_PORT` (le port défini dans votre `.env`).

> [!NOTE]
> Pas de Maven à installer : les commandes de build et de test sont installés dans le **Dockerfile** de ms-account.

---

## Schéma d'Infrastructure Réseau (Docker Compose)

📎 **Schéma d'infrastructure réseau** : [voir le diagramme](https://github.com/CCSOP-livraison/backend/blob/develop/doc/diagramm%20of%20micro-services.png)

### Ports & protocoles
| Flux | Source → Destination | Port | Protocole |
|---|---|---|---|
| Accès externe | Client → `ms-api-gateway` (DMZ) | `NGINX_PORT` (hôte) → `80` | HTTP |
| Routage Gateway | `ms-api-gateway` → `ms-account` | `8080` | HTTP |
| Accès données | `ms-account` → `ms-CCSOP-database` | `3306` | TCP (MySQL) |

---

## Conception & Modélisation

L'ensemble des diagrammes du projet généraux du projet - **modèle conceptuel des données (MCD)**, **planification gantt**, **uses cases** et **diagrammes de séquences** - sont disponibles dans le dossier [`.github/doc`](https://github.com/CCSOP-livraison/.github/tree/main/doc).
L'ensemble des diagrammes backend du projet - **diagramme de classe** et **diagramme de microservice**- sont disponibles dans le dossier [`.doc`](https://github.com/CCSOP-livraison/backend/tree/develop/doc).

---

## Pratiques Git & Commits 

### Stratégie de branches (Git Flow)
Le projet s'est basé sur le modèle **Git Flow**. Pour la description complète du fonctionnement des branches, des règles de fusion et du cycle de vie des releases, référez-vous à la spécification officielle : [A successful Git branching model - nvie.com](https://nvie.com/posts/a-successful-git-branching-model/).

> [!IMPORTANT]
> Une fois qu'une branche `feature/` ou `bugfix/` est testée et fusionnée avec succès sur `develop`, elle doit être supprimée du dépôt distant pour maintenir l'historique propre.

### Conventions de commit (Conventional Commits)
L'équipe applique la spécification [Conventional Commits 1.0.0](https://www.conventionalcommits.org/fr/v1.0.0/). Chaque message de commit doit être préfixé par son type :
- `feat:` : Ajout d'une nouvelle fonctionnalité
- `fix:` : Résolution d'un bug
- `docs:` : Modification de la documentation
- `refactor:` : Modification du code sans changement de comportement (restructuration, renommage)

---

## Guide utilisateur  
### créer une commande
1. Se connecter avec le compte client de **test** suivant ou créer un nouveau compte utilisateur: 
email : camille.petit@example.com
mot de passe : customer123

2. Choisir un restaurant 

3. Aller sur voir la carte 

4. Choisir des plats et valider la commande 

5. Se déconnecter du compte utilisateur 

### s'assigner une livraison
6. Se connecter avec le compte livreur de **test** suivant : 
email : lucas.bernard@example.com
mot de passe : deliver123

7. Cliquer sur la commande créer précédement et se l'assigner
 1. Scénario alternatif (pour simuler une vérification utilisateur de sa commande au moment où la commande est en cours de traitement): 
 2. Se déconnecter 
 3. Se connecter avec le compte de l'utilisateur test camille.petit@example.com  
 4. Aller sur "mes commande"
 5. Voir la commande, et là on peut voir "lucas bernard" comme livreur. 

### Clôturé la livraison
8. Clique sur la commande créer précédement et la valider 

9. Se déconnecter 

10. Se connecter avec le compte de l'utilisateur test camille.petit@example.com  

11. Aller sur "mes commandes"

12. La commande est en état livré et si l'utilisateur la valide elle passe en état terminé.  

---

## Problèmes connus & Limitations

### Sécurité
* **Récupération des données :** Lors des requêtes `GET`, l'intégralité des données d'une entité est actuellement renvoyée (hors authentification). Un filtrage des données sensibles doit être mis en place.
* **Authentification :** Le système d'authentification est temporaire et non sécurisé. Les sessions ne sont pas conservées, ce qui rend le contrôle d'accès inexistant pour le moment.

### Gestion des utilisateurs
* **Profil utilisateur :** Les fonctionnalités de consultation, de modification et de suppression des données personnelles ne sont pas encore implémentées.
* **Création de comptes :** Seuls les comptes de type "Client" peuvent être créés pour l'instant via l'application.
* **Paiement :** Le module de paiement n'est pas encore intégré.
