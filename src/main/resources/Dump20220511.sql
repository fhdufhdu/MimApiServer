-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: fhdufhdu.iptime.org    Database: mim
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `board`
--
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint NOT NULL,
  `is_removed` tinyint(1) NOT NULL DEFAULT '0',
  `last_posting_number` bigint NOT NULL,
  `movie_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmeso5c69xhjv3rspd343rbn1o` (`movie_id`),
  CONSTRAINT `FKmeso5c69xhjv3rspd343rbn1o` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,0,0,3);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL,
  `comment_group` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `depth` int NOT NULL,
  `is_removed` tinyint(1) NOT NULL DEFAULT '0',
  `time` datetime(6) NOT NULL,
  `posting_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4uvy94ui17hs3qcch04plyltp` (`posting_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK4uvy94ui17hs3qcch04plyltp` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'dsdaf',1,0,'2022-05-11 05:13:47.000000',1,'fhdufhdu');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_report`
--

DROP TABLE IF EXISTS `comment_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_report` (
  `id` bigint NOT NULL,
  `is_confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `repor_timestamp` datetime(6) NOT NULL,
  `report_reason` varchar(255) NOT NULL,
  `comment_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ugevhla12t9n0uw4o0rkvnth` (`comment_id`),
  CONSTRAINT `FK8ugevhla12t9n0uw4o0rkvnth` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_report`
--

LOCK TABLES `comment_report` WRITE;
/*!40000 ALTER TABLE `comment_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `id` bigint NOT NULL,
  `feature_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature`
--

LOCK TABLES `feature` WRITE;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` VALUES (1,'fname1'),(2,'fname2'),(3,'fname3'),(4,'fname4'),(5,'fname5'),(6,'fname6'),(7,'fname7'),(8,'fname8'),(9,'fname9'),(10,'fname10');
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` bigint NOT NULL,
  `genre_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'gname1'),(2,'gname2'),(3,'gname3'),(4,'gname4'),(5,'gname5'),(6,'gname6'),(7,'gname7'),(8,'gname8'),(9,'gname9'),(10,'gname10');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (100);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` bigint NOT NULL,
  `dir_name` varchar(255) DEFAULT NULL,
  `eng_title` varchar(255) DEFAULT NULL,
  `running_time` varchar(255) DEFAULT NULL,
  `synopsis` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `year` int DEFAULT NULL,
  `movie_rating_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbd8p64dx4qew9k5ryo9fd9rf6` (`movie_rating_id`),
  CONSTRAINT `FKbd8p64dx4qew9k5ryo9fd9rf6` FOREIGN KEY (`movie_rating_id`) REFERENCES `movie_rating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,NULL,NULL,NULL,NULL,'movie1',NULL,1),(2,NULL,NULL,NULL,NULL,'movie2',NULL,2),(3,NULL,NULL,NULL,NULL,'movie3',NULL,3);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_feature`
--

DROP TABLE IF EXISTS `movie_feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_feature` (
  `id` bigint NOT NULL,
  `feature_id` bigint NOT NULL,
  `movie_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9gdp3fp14olyah6m09n07srn4` (`feature_id`),
  KEY `FK4pgavu2wdxclm6oyj1ygm1hlc` (`movie_id`),
  CONSTRAINT `FK4pgavu2wdxclm6oyj1ygm1hlc` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FK9gdp3fp14olyah6m09n07srn4` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_feature`
--

LOCK TABLES `movie_feature` WRITE;
/*!40000 ALTER TABLE `movie_feature` DISABLE KEYS */;
INSERT INTO `movie_feature` VALUES (1,1,1),(2,2,1),(3,3,2),(4,4,2),(5,5,3),(6,6,3);
/*!40000 ALTER TABLE `movie_feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_genre` (
  `id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  `movie_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86p3roa187k12avqfl28klp1q` (`genre_id`),
  KEY `FKp6vjabv2e2435at1hnuxg64yv` (`movie_id`),
  CONSTRAINT `FK86p3roa187k12avqfl28klp1q` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
  CONSTRAINT `FKp6vjabv2e2435at1hnuxg64yv` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (1,1,1),(2,2,1),(3,3,2),(4,4,2),(5,5,3),(6,6,3);
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_rating`
--

DROP TABLE IF EXISTS `movie_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_rating` (
  `id` bigint NOT NULL,
  `rating` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_rating`
--

LOCK TABLES `movie_rating` WRITE;
/*!40000 ALTER TABLE `movie_rating` DISABLE KEYS */;
INSERT INTO `movie_rating` VALUES (1,'all'),(2,'15'),(3,'19');
/*!40000 ALTER TABLE `movie_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_worker`
--

DROP TABLE IF EXISTS `movie_worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_worker` (
  `id` bigint NOT NULL,
  `movie_id` bigint NOT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdthj98lp6599cvyi6n73map8w` (`movie_id`),
  KEY `FK22hqoar1vahgid3astja985fk` (`worker_id`),
  CONSTRAINT `FK22hqoar1vahgid3astja985fk` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `FKdthj98lp6599cvyi6n73map8w` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_worker`
--

LOCK TABLES `movie_worker` WRITE;
/*!40000 ALTER TABLE `movie_worker` DISABLE KEYS */;
INSERT INTO `movie_worker` VALUES (1,1,1),(2,2,2),(3,3,3),(4,1,4),(5,1,5),(6,2,5),(7,2,6),(8,3,6),(9,3,7),(10,1,9),(11,2,10),(12,3,11),(13,3,9);
/*!40000 ALTER TABLE `movie_worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posting`
--

DROP TABLE IF EXISTS `posting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posting` (
  `id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `is_removed` tinyint(1) NOT NULL DEFAULT '0',
  `comment_cnt` bigint DEFAULT NULL,
  `posting_number` bigint NOT NULL,
  `time` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `board_id` bigint DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpg6syulatj8o8lhvj16wfro4c` (`board_id`),
  KEY `FK9jcjpid91kqcndm4o267k1too` (`user_id`),
  CONSTRAINT `FK9jcjpid91kqcndm4o267k1too` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpg6syulatj8o8lhvj16wfro4c` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posting`
--

LOCK TABLES `posting` WRITE;
/*!40000 ALTER TABLE `posting` DISABLE KEYS */;
INSERT INTO `posting` VALUES (1,'dasf',0,1,1,'2022-05-11 05:13:47.000000','sdf',1,'fhdufhdu'),(2,'adsfa',0,1,2,'2022-05-11 05:13:47.000000','asd',1,'fhdufhdu'),(3,'asdf',0,1,3,'2022-05-11 05:13:47.000000','asdf',1,'fhdufhdu'),(4,'asdf',1,1,4,'2022-05-11 05:13:47.000000','asdf',1,'fhdufhdu');
/*!40000 ALTER TABLE `posting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posting_report`
--

DROP TABLE IF EXISTS `posting_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posting_report` (
  `id` bigint NOT NULL,
  `is_confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `repor_timestamp` datetime(6) NOT NULL,
  `report_reason` varchar(255) NOT NULL,
  `posting_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7mjvpyg36kviif356byvrbw` (`posting_id`),
  CONSTRAINT `FKi7mjvpyg36kviif356byvrbw` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posting_report`
--

LOCK TABLES `posting_report` WRITE;
/*!40000 ALTER TABLE `posting_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `posting_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_board`
--

DROP TABLE IF EXISTS `request_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_board` (
  `id` bigint NOT NULL,
  `is_confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `request_cnt` int NOT NULL,
  `movie_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe36m986s85l9lk6k061ht3gnq` (`movie_id`),
  CONSTRAINT `FKe36m986s85l9lk6k061ht3gnq` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_board`
--

LOCK TABLES `request_board` WRITE;
/*!40000 ALTER TABLE `request_board` DISABLE KEYS */;
INSERT INTO `request_board` VALUES (2,0,20,2),(3,1,30,3);
/*!40000 ALTER TABLE `request_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `is_removed` tinyint(1) NOT NULL DEFAULT '0',
  `nick_name` varchar(255) DEFAULT NULL,
  `profile_path` varchar(255) DEFAULT NULL,
  `pw` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin',0,NULL,NULL,'{bcrypt}$2a$10$sggNmufpEjubpmJoxq6ZWuC4Es4KifbRPL9q9gUw6GOgK/pwF/vtW','ADMIN'),('fhdufhdu',0,'nick',NULL,'{bcrypt}$2a$10$slVCDm5R7N0PqlphInYqb.gXdFJ9FhuLvEGkpPlpAuxk2su24ZkbO','USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES ('director',1,'director1'),('director',2,'director2'),('director',3,'director3'),('actor',4,'actor1'),('actor',5,'actor2'),('actor',6,'actor3'),('actor',7,'actor4'),('actor',8,'actor5'),('writer',9,'writer1'),('writer',10,'writer2'),('writer',11,'writer3');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-11 14:22:49
SET foreign_key_checks = 1;