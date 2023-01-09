-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: simplebankapidb
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blocked_tokens`
--

DROP TABLE IF EXISTS `blocked_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blocked_tokens` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `token` text NOT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blocked_tokens`
--

LOCK TABLES `blocked_tokens` WRITE;
/*!40000 ALTER TABLE `blocked_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `blocked_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(100) NOT NULL,
                         `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN','2022-11-11 19:19:03','2022-11-11 19:19:03'),(2,'USER','2022-11-11 19:19:03','2022-11-11 19:19:03');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
                            `session_id` varchar(60) NOT NULL,
                            `email` varchar(100) NOT NULL,
                            `last_activity` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`session_id`,`email`),
                            UNIQUE KEY `session_id` (`session_id`),
                            UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `user_id` (`user_id`,`role_id`),
                             KEY `user_role_ibfk_2` (`role_id`),
                             CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (4,6,2,'2022-11-21 17:58:23','2022-11-21 17:58:23'),(8,26,2,'2022-11-22 02:30:28','2022-11-22 02:30:28'),(9,29,2,'2022-11-22 16:34:00','2022-11-22 16:34:00'),(12,1,2,'2022-11-23 04:15:33','2022-11-23 04:15:33'),(13,1,1,'2022-11-23 04:16:58','2022-11-23 04:16:58'),(15,33,2,'2022-11-24 23:13:13','2022-11-24 23:13:13'),(17,35,2,'2022-11-27 16:33:20','2022-11-27 16:33:20'),(28,49,2,'2022-11-28 18:27:45','2022-11-28 18:27:45'),(33,66,2,'2022-11-29 05:09:47','2022-11-29 05:09:47'),(34,68,2,'2023-01-02 19:43:55','2023-01-02 19:43:55');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `email` varchar(100) NOT NULL,
                         `password` varchar(60) NOT NULL,
                         `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'jbq2@njit.edu','$2a$10$eus2X3btbsPIA6tIt/gMzeqnRwLQqWoWMhXyWp6yb6r1g0I0EnIbK','2022-11-23 04:15:33','2022-11-23 04:16:29'),(6,'jbquizon20@pthsd.net','$2a$10$L/yt9dVWR/h0gReu2VuKAukT19ZnjNQZj1i/EWGmTk6oaCsUSqDsa','2022-11-21 17:58:23','2022-11-21 17:58:23'),(26,'test.email@gmail.com','$2a$10$f.aUdfI7CczYK3T10Q8sP.gFX27h7j0zi9/qxU.g70HOEB2fkhlH.','2022-11-22 02:30:28','2022-11-22 02:30:28'),(29,'rs@njit.edu','$2a$10$vaU7YlLX.cFE69dJ08sA9O8ohuyhmIpBGB.4DvR7td/lgH5jZwTty','2022-11-22 16:34:00','2022-11-22 16:34:00'),(33,'cilantroninja@gmail.com','$2a$10$FUlY8HpNRfORBp/nXaXageYU4X1TaMebYk6xEleP1lo44Q9GdwMJu','2022-11-24 23:13:01','2022-11-24 23:13:01'),(35,'joshquizon@gmail.com','$2a$10$jECbfYgOe9cU78Nri4UStO6zRq7au7ikfRNWt9PGNVMa/0AwF09tu','2022-11-27 16:33:20','2022-11-27 16:33:20'),(49,'shagun2021@gmail.com','$2a$10$aArDXlVYw2tdktqAkgAzCu6p3Xz1z8C3Nuo761MP1z8R./Ppcir6W','2022-11-28 18:27:45','2022-11-28 18:27:45'),(66,'joe.biden@gmail.com','$2a$10$aLaeozZZMJcDpNiczJ6N0u4IOUt/2vNaNZr2b6wIm2P/1TQO.i/8y','2022-11-29 05:09:47','2022-11-29 05:09:47'),(68,'angular@gmail.com','$2a$10$ZNS9VYjdSXbvNDIgvSJei.DmQTIZ5WAsh5hX62y3uI6Gq8FFavb2G','2023-01-02 19:43:55','2023-01-02 19:43:55');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'simplebankapidb'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `remove_blocked_token` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `remove_blocked_token` ON SCHEDULE EVERY 1 MINUTE STARTS '2022-12-28 22:33:27' ON COMPLETION NOT PRESERVE ENABLE DO DELETE FROM `BLOCKED_TOKENS` WHERE UNIX_TIMESTAMP(CURRENT_TIMESTAMP) - UNIX_TIMESTAMP(`created`) > 900 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'simplebankapidb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-09 13:04:38