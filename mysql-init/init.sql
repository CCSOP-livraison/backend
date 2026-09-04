-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: CCSOP_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS CCSOP_db;
USE CCSOP_db;

--
-- Table structure for table `deliver`
--

DROP TABLE IF EXISTS `deliver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliver` (
  `id_customer` bigint NOT NULL,
  `id_deliverer` bigint NOT NULL,
  `delivery_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_customer`,`id_deliverer`,`delivery_date`),
  KEY `fk_deliver_deliverer` (`id_deliverer`),
  CONSTRAINT `fk_deliver_customer` FOREIGN KEY (`id_customer`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_deliver_deliverer` FOREIGN KEY (`id_deliverer`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dishs`
--

DROP TABLE IF EXISTS `dishs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` decimal(4,2) NOT NULL,
  `description` text,
  `id_restaurant` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dish_restaurant` (`id_restaurant`),
  CONSTRAINT `fk_dish_restaurant` FOREIGN KEY (`id_restaurant`) REFERENCES `restaurants` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `own`
--

DROP TABLE IF EXISTS `own`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `own` (
  `id_owner` bigint NOT NULL,
  `id_restaurant` bigint NOT NULL,
  PRIMARY KEY (`id_owner`,`id_restaurant`),
  KEY `fk_own_restaurant` (`id_restaurant`),
  CONSTRAINT `fk_own_owner` FOREIGN KEY (`id_owner`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_own_restaurant` FOREIGN KEY (`id_restaurant`) REFERENCES `restaurants` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lastname` varchar(50) NOT NULL,
  `firstname` varchar(105) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `zipcode` varchar(4) DEFAULT NULL,
  `locate` varchar(18) DEFAULT NULL,
  `email` varchar(320) NOT NULL,
  `phone_number` char(16) DEFAULT NULL,
  `credit_card` varchar(19) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `token_expired` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `zipcode` char(4) DEFAULT NULL,
  `locate` varchar(18) DEFAULT NULL,
  `summary` varchar(100) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `privileges`
--

DROP TABLE IF EXISTS `privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privileges` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `privileges_seq`
--

DROP TABLE IF EXISTS `privileges_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privileges_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `rolesusers_roles_seq`
--

DROP TABLE IF EXISTS `roles_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_privileges`
--

DROP TABLE IF EXISTS `roles_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_privileges` (
  `role_id` bigint NOT NULL,
  `privilege_id` bigint NOT NULL,
  KEY `FK5yjwxw2gvfyu76j3rgqwo685u` (`privilege_id`),
  KEY `FK9h2vewsqh8luhfq71xokh4who` (`role_id`),
  CONSTRAINT `FK5yjwxw2gvfyu76j3rgqwo685u` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`),
  CONSTRAINT `FK9h2vewsqh8luhfq71xokh4who` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `to_order`
--

DROP TABLE IF EXISTS `to_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `to_order` (
  `id_user` bigint NOT NULL,
  `id_restaurant` bigint NOT NULL,
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`,`id_restaurant`,`order_date`),
  KEY `fk_order_restaurant` (`id_restaurant`),
  CONSTRAINT `fk_order_restaurant` FOREIGN KEY (`id_restaurant`) REFERENCES `restaurants` (`id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  KEY `FK48qhl0k3dvjwm8v1dg614tlqd` (`user_id`),
  CONSTRAINT `FK48qhl0k3dvjwm8v1dg614tlqd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-03 13:43:12
-- ==============================================================================
-- Seed Script for CCSOP_db Database
-- Generated automatically and consistent with foreign keys and data types.
-- ==============================================================================

USE CCSOP_db;

-- Desactiver temporairement les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 0;

-- Nettoyage des tables existantes
TRUNCATE TABLE `deliver`;
TRUNCATE TABLE `to_order`;
TRUNCATE TABLE `dishs`;
TRUNCATE TABLE `own`;
TRUNCATE TABLE `restaurants`;
TRUNCATE TABLE `users_roles`;
TRUNCATE TABLE `roles_privileges`;
TRUNCATE TABLE `users`;
TRUNCATE TABLE `roles`;
TRUNCATE TABLE `privileges`;
TRUNCATE TABLE `privileges_seq`;
TRUNCATE TABLE `roles_seq`;

-- Réactiver les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------------------------
-- 1. Insertion des Privilèges (Privileges)
-- ------------------------------------------------------
INSERT INTO `privileges` (`id`, `name`) VALUES
(1, 'READ_PRIVILEGE'),
(2, 'WRITE_PRIVILEGE'),
(3, 'DELETE_PRIVILEGE'),
(4, 'MANAGE_RESTAURANT'),
(5, 'DELIVER_ORDER');

INSERT INTO `privileges_seq` (`next_val`) VALUES (6);

-- ------------------------------------------------------
-- 2. Insertion des Rôles (Roles)
-- ------------------------------------------------------
INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'MODERATION'),
(3, 'DELIVER'),
(4, 'CUSTOMER');

INSERT INTO `roles_seq` (`next_val`) VALUES (5);

-- ------------------------------------------------------
-- 3. Association Rôles - Privilèges (roles_privileges)
-- ------------------------------------------------------
INSERT INTO `roles_privileges` (`role_id`, `privilege_id`) VALUES
-- ADMIN a tous les privilèges
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
-- OWNER peut lire, écrire et gérer ses restaurants
(2, 1), (2, 2), (2, 4),
-- DELIVERER peut lire, écrire et livrer
(3, 1), (3, 2), (3, 5),
-- CUSTOMER peut lire et faire des commandes
(4, 1);

-- ------------------------------------------------------
-- 4. Insertion des Utilisateurs (Users)
-- Note: zipcode (max 4 chars), locate (max 18 chars)
-- ------------------------------------------------------
INSERT INTO `users` (`id`, `lastname`, `firstname`, `address`, `zipcode`, `locate`, `email`, `phone_number`, `credit_card`, `password`, `enabled`, `token_expired`) VALUES
(1, 'Dupont', 'Jean', '10 Rue de la Paix', '7501', 'Paris', 'jean.dupont@example.com', '+33612345678', '4532111122223333', '$2a$12$0jGHvoJGgN2ocKpJjJWK6OvMAiuhA1ZT41C/6mcH3W9WyYqZUoXAy', b'1', b'0'),
(2, 'Martin', 'Sophie', '25 Avenue des Fleurs', '6902', 'Lyon', 'sophie.martin@example.com', '+33623456789', '4532222233334444', '$2a$12$5NlLBW8aMIaw26jU.ynT.OD2QQP/ozHYjbDUr.AkhZNI0e.TRnAXG', b'1', b'0'),
(3, 'Bernard', 'Lucas', '8 Rue des Mimosas', '1301', 'Marseille', 'lucas.bernard@example.com', '+33634567890', '4532333344445555', '$2a$12$1h1depTWc/hfXto3UkVKH.M7.0bWxoJ1qOCl.u.Xk7eJ5mkaYy7OG', b'1', b'0'),
(4, 'Petit', 'Camille', '12 Boulevard Victor Hugo', '3100', 'Toulouse', 'camille.petit@example.com', '+33645678901', '4532444455556666', '$2a$12$nf.SHVc0D400hsGOasguV.MMZpO5145yrX2hHyGiEM5FbPappDQMm', b'1', b'0'),
(5, 'Moreau', 'Thomas', '45 Rue Nationale', '5900', 'Lille', 'thomas.moreau@example.com', '+33656789012', '4532555566667777', '$2a$12$Nmk7wM.8sBQMukau4H4au.3qxiAixlnZnnzeFVX98Dodbrul08w3e', b'1', b'0');

-- ------------------------------------------------------
-- 5. Association Utilisateurs - Rôles (users_roles)
-- ------------------------------------------------------
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1), -- Jean (Admin)
(2, 2), -- Sophie (Restaurateur)
(3, 3), -- Lucas (Livreur)
(4, 4), -- Camille (Client)
(5, 4); -- Thomas (Client)

-- ------------------------------------------------------
-- 6. Insertion des Restaurants (Restaurants)
-- Note: summary max 20 chars
-- ------------------------------------------------------
INSERT INTO `restaurants` (`id`, `name`, `address`, `zipcode`, `locate`, `summary`, `description`) VALUES
(1, 'Le Gourmet Lyon', '25 Avenue des Fleurs', '6902', 'Lyon', 'Cuisine raffinée', 'Restaurant gastronomique spécialisé dans la cuisine traditionnelle lyonnaise revisitée.'),
(2, 'La Trattoria Bellecour', '14 Rue de la République', '6902', 'Lyon', 'Spécialités italien', 'Authentique trattoria italienne proposant des pizzas au feu de bois et pâtes fraîches maison.');

-- ------------------------------------------------------
-- 7. Table de liaison Propriétaires (Own)
-- ------------------------------------------------------
INSERT INTO `own` (`id_owner`, `id_restaurant`) VALUES
(4, 1),
(4, 2);

-- ------------------------------------------------------
-- 8. Insertion des Plats (Dishs)
-- Note: price decimal(4,2) -> max 99.99
-- ------------------------------------------------------
INSERT INTO `dishs` (`id`, `name`, `price`, `description`, `id_restaurant`) VALUES
(1, 'Quenelle de Brochet', 18.50, 'Quenelle artisanale nappée de sa sauce Nantua d exception.', 1),
(2, 'Salade Lyonnaise', 12.00, 'Salade verte, lardons grillés, croutons et œuf poché.', 1),
(3, 'Filet de Bœuf Pêcher', 24.90, 'Filet de bœuf tendre servi avec gratin dauphinois.', 1),
(4, 'Pizza Margherita', 11.50, 'Sauce tomate, mozzarella di bufala, basilic frais.', 2),
(5, 'Tiramisu Classico', 6.50, 'Dessert italien traditionnel au café et mascarpone.', 2),
(6, 'Pâtes Carbonara', 14.00, 'Pâtes fraîches avec guanciale, jaune d œuf et pecorino romano.', 2);

-- ------------------------------------------------------
-- 9. Insertion des Commandes (To_order)
-- ------------------------------------------------------
INSERT INTO `to_order` (`id_user`, `id_restaurant`, `order_date`) VALUES
(4, 1, '2026-09-01 12:30:00'),
(5, 2, '2026-09-02 19:45:00'),
(4, 2, '2026-09-03 20:15:00');

-- ------------------------------------------------------
-- 10. Insertion des Livraisons (Deliver)
-- ------------------------------------------------------
INSERT INTO `deliver` (`id_customer`, `id_deliverer`, `delivery_date`) VALUES
(4, 3, '2026-09-01 13:10:00'),
(5, 3, '2026-09-02 20:20:00'),
(4, 3, '2026-09-03 20:50:00');