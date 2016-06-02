CREATE DATABASE  IF NOT EXISTS `acetaia` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `acetaia`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: acetaia
-- ------------------------------------------------------
-- Server version	5.6.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('chiara','ferrari'),('xhensila','doda');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aggiunta`
--

DROP TABLE IF EXISTS `aggiunta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aggiunta` (
  `idOperazione` int(11) NOT NULL,
  `barile` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `quantita` int(11) NOT NULL,
  PRIMARY KEY (`idOperazione`),
  CONSTRAINT `fk_a` FOREIGN KEY (`idOperazione`) REFERENCES `storico` (`idOp`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aggiunta`
--

LOCK TABLES `aggiunta` WRITE;
/*!40000 ALTER TABLE `aggiunta` DISABLE KEYS */;
INSERT INTO `aggiunta` VALUES (1,1,'mosto',1),(4,6,'aceto',1),(11,1,'aceto',1),(15,1,'mosto',1),(18,7,'aceto',1),(19,6,'aceto',2),(20,6,'aceto',2);
/*!40000 ALTER TABLE `aggiunta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `aggiunta_view`
--

DROP TABLE IF EXISTS `aggiunta_view`;
/*!50001 DROP VIEW IF EXISTS `aggiunta_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `aggiunta_view` (
  `barile` tinyint NOT NULL,
  `batteria` tinyint NOT NULL,
  `data` tinyint NOT NULL,
  `operazione` tinyint NOT NULL,
  `idOp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `barile`
--

DROP TABLE IF EXISTS `barile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barile` (
  `idbarile` int(11) NOT NULL,
  `capacita` int(11) NOT NULL DEFAULT '0',
  `maxLivello` int(11) NOT NULL DEFAULT '0',
  `livello` int(11) NOT NULL DEFAULT '0',
  `legno` varchar(45) NOT NULL DEFAULT '0',
  `idbatteria` int(11) NOT NULL DEFAULT '0',
  `acidita` int(11) NOT NULL DEFAULT '0',
  `densita` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idbarile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barile`
--

LOCK TABLES `barile` WRITE;
/*!40000 ALTER TABLE `barile` DISABLE KEYS */;
INSERT INTO `barile` VALUES (1,70,53,43,'frassino',5,5,3),(2,25,19,16,'robinia',1,6,0),(3,60,45,34,'castagno',1,4,0),(4,15,11,9,'ciliegio',1,8,0),(5,55,41,34,'rovere',1,0,0),(6,70,53,51,'ginepro',2,0,0),(7,55,41,38,'gelso',2,0,0),(8,70,53,42,'frassino',8,0,0),(9,25,19,15,'gelso',2,0,0),(10,75,56,45,'rovere',1,0,0),(11,25,19,15,'rovere',3,0,0),(12,50,38,30,'ginepro',5,0,0),(13,15,11,9,'rovere',2,0,0),(14,40,30,24,'castagno',6,0,0),(15,15,11,9,'robinia',3,0,0),(16,45,34,27,'gelso',6,0,0),(17,65,49,39,'ginepro',1,0,0),(18,75,56,45,'castagno',2,0,0),(19,45,34,27,'castagno',2,0,0),(20,25,19,15,'frassino',4,0,0),(21,25,19,15,'ginepro',5,0,0),(22,55,41,33,'ciliegio',3,0,0),(23,35,26,21,'robinia',1,0,0),(24,25,19,15,'ciliegio',6,0,0),(25,35,26,21,'ginepro',2,0,0),(26,35,26,21,'frassino',3,0,0),(27,60,45,36,'ginepro',7,0,0),(28,20,15,12,'rovere',1,0,0),(29,50,38,30,'robinia',8,0,0),(30,50,38,30,'rovere',3,0,0),(31,35,26,21,'ciliegio',4,0,0),(32,50,38,30,'frassino',4,0,0),(33,65,49,39,'ginepro',7,0,0),(34,65,49,39,'rovere',3,0,0),(35,40,30,24,'castagno',2,0,0),(36,15,11,9,'castagno',4,0,0),(37,20,15,12,'robinia',2,0,0),(38,45,34,27,'robinia',5,0,0),(39,40,30,24,'ginepro',8,0,0),(40,20,15,12,'rovere',3,0,0),(41,50,38,30,'rovere',6,0,0),(42,25,19,15,'gelso',7,0,0),(43,50,38,30,'castagno',7,0,0),(44,15,11,9,'robinia',5,0,0),(45,35,26,21,'robinia',5,0,0),(46,80,60,48,'castagno',5,0,0),(47,25,19,15,'frassino',8,0,0),(48,55,41,33,'ginepro',4,0,0),(49,70,53,42,'ciliegio',4,0,0),(50,35,26,21,'castagno',6,0,0),(51,40,30,24,'ciliegio',4,0,0),(52,35,26,21,'robinia',7,0,0),(53,75,56,45,'castagno',8,0,0),(54,80,60,44,'gelso',1,0,0);
/*!40000 ALTER TABLE `barile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degustazione`
--

DROP TABLE IF EXISTS `degustazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degustazione` (
  `idOperazione` int(11) NOT NULL,
  `barile` int(11) NOT NULL,
  `file` varchar(300) NOT NULL,
  PRIMARY KEY (`idOperazione`),
  CONSTRAINT `fk_d` FOREIGN KEY (`idOperazione`) REFERENCES `storico` (`idOp`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degustazione`
--

LOCK TABLES `degustazione` WRITE;
/*!40000 ALTER TABLE `degustazione` DISABLE KEYS */;
INSERT INTO `degustazione` VALUES (6,1,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_1_03_12_2013.jpg'),(7,3,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_3_03_12_2013.jpg'),(8,4,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_4_03_12_2013.jpg'),(9,2,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_2_03_12_2013.jpg'),(10,5,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_5_03_12_2013.jpg'),(14,6,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_6_04_12_2013.jpg'),(24,8,'C:/Users/xhensila/workspace/Acetaia/WebContent/degustazione/Degustazione_8_11_12_2013.jpg');
/*!40000 ALTER TABLE `degustazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `degustazione_view`
--

DROP TABLE IF EXISTS `degustazione_view`;
/*!50001 DROP VIEW IF EXISTS `degustazione_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `degustazione_view` (
  `barile` tinyint NOT NULL,
  `batteria` tinyint NOT NULL,
  `data` tinyint NOT NULL,
  `operazione` tinyint NOT NULL,
  `idOp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `misurazione`
--

DROP TABLE IF EXISTS `misurazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `misurazione` (
  `idOperazione` int(11) NOT NULL,
  `barile` int(11) NOT NULL,
  `parametro` varchar(45) NOT NULL,
  `valore` float NOT NULL,
  PRIMARY KEY (`idOperazione`),
  CONSTRAINT `fk_m` FOREIGN KEY (`idOperazione`) REFERENCES `storico` (`idOp`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `misurazione`
--

LOCK TABLES `misurazione` WRITE;
/*!40000 ALTER TABLE `misurazione` DISABLE KEYS */;
INSERT INTO `misurazione` VALUES (5,3,'acidita',4),(23,4,'livello',9);
/*!40000 ALTER TABLE `misurazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `misurazione_view`
--

DROP TABLE IF EXISTS `misurazione_view`;
/*!50001 DROP VIEW IF EXISTS `misurazione_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `misurazione_view` (
  `barile` tinyint NOT NULL,
  `batteria` tinyint NOT NULL,
  `data` tinyint NOT NULL,
  `operazione` tinyint NOT NULL,
  `idOp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `prelievo`
--

DROP TABLE IF EXISTS `prelievo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prelievo` (
  `idOperazione` int(11) NOT NULL,
  `barile` int(11) NOT NULL,
  `quantita` varchar(45) NOT NULL,
  PRIMARY KEY (`idOperazione`),
  CONSTRAINT `fk_p` FOREIGN KEY (`idOperazione`) REFERENCES `storico` (`idOp`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prelievo`
--

LOCK TABLES `prelievo` WRITE;
/*!40000 ALTER TABLE `prelievo` DISABLE KEYS */;
INSERT INTO `prelievo` VALUES (2,3,'2'),(13,2,'2'),(16,1,'2'),(17,1,'2'),(21,5,'4');
/*!40000 ALTER TABLE `prelievo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `prelievo_view`
--

DROP TABLE IF EXISTS `prelievo_view`;
/*!50001 DROP VIEW IF EXISTS `prelievo_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `prelievo_view` (
  `barile` tinyint NOT NULL,
  `batteria` tinyint NOT NULL,
  `data` tinyint NOT NULL,
  `operazione` tinyint NOT NULL,
  `idOp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `rabbocco`
--

DROP TABLE IF EXISTS `rabbocco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rabbocco` (
  `idOperazione` int(11) NOT NULL,
  `src` int(11) NOT NULL,
  `dest` int(11) NOT NULL,
  `quantita` int(11) NOT NULL,
  PRIMARY KEY (`idOperazione`),
  CONSTRAINT `fk_r` FOREIGN KEY (`idOperazione`) REFERENCES `storico` (`idOp`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rabbocco`
--

LOCK TABLES `rabbocco` WRITE;
/*!40000 ALTER TABLE `rabbocco` DISABLE KEYS */;
INSERT INTO `rabbocco` VALUES (3,4,5,4),(12,2,4,1),(22,3,2,1);
/*!40000 ALTER TABLE `rabbocco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `rabbocco_view`
--

DROP TABLE IF EXISTS `rabbocco_view`;
/*!50001 DROP VIEW IF EXISTS `rabbocco_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `rabbocco_view` (
  `barile` tinyint NOT NULL,
  `batteria` tinyint NOT NULL,
  `data` tinyint NOT NULL,
  `operazione` tinyint NOT NULL,
  `idOp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `storico`
--

DROP TABLE IF EXISTS `storico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storico` (
  `barile` int(11) NOT NULL,
  `batteria` int(11) NOT NULL,
  `data` date NOT NULL,
  `operazione` varchar(45) NOT NULL,
  `idOp` int(11) NOT NULL,
  PRIMARY KEY (`idOp`,`barile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storico`
--

LOCK TABLES `storico` WRITE;
/*!40000 ALTER TABLE `storico` DISABLE KEYS */;
INSERT INTO `storico` VALUES (1,5,'2013-12-03','aggiunta',1),(3,1,'2013-12-03','prelievo',2),(4,1,'2013-12-03','rabbocco',3),(5,1,'2013-12-03','rabbocco',3),(6,2,'2013-12-03','aggiunta',4),(3,1,'2013-12-03','misurazione',5),(1,5,'2013-12-03','degustazione',6),(3,1,'2013-12-03','degustazione',7),(4,1,'2013-12-03','degustazione',8),(2,1,'2013-12-03','degustazione',9),(5,1,'2013-12-03','degustazione',10),(1,5,'2013-12-03','aggiunta',11),(2,1,'2013-12-03','rabbocco',12),(4,1,'2013-12-03','rabbocco',12),(2,1,'2013-12-03','prelievo',13),(6,2,'2013-12-04','degustazione',14),(1,5,'2013-12-04','aggiunta',15),(1,5,'2013-12-04','prelievo',16),(1,5,'2013-12-06','prelievo',17),(7,2,'2013-12-06','aggiunta',18),(6,2,'2013-12-11','aggiunta',19),(6,2,'2013-12-11','aggiunta',20),(5,1,'2013-12-11','prelievo',21),(2,1,'2013-12-11','rabbocco',22),(3,1,'2013-12-11','rabbocco',22),(4,1,'2013-12-11','misurazione',23),(8,8,'2013-12-11','degustazione',24);
/*!40000 ALTER TABLE `storico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `aggiunta_view`
--

/*!50001 DROP TABLE IF EXISTS `aggiunta_view`*/;
/*!50001 DROP VIEW IF EXISTS `aggiunta_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `aggiunta_view` AS select `s`.`barile` AS `barile`,`s`.`batteria` AS `batteria`,`s`.`data` AS `data`,`s`.`operazione` AS `operazione`,`s`.`idOp` AS `idOp` from `storico` `s` where (`s`.`operazione` = 'aggiunta') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `degustazione_view`
--

/*!50001 DROP TABLE IF EXISTS `degustazione_view`*/;
/*!50001 DROP VIEW IF EXISTS `degustazione_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `degustazione_view` AS select `s`.`barile` AS `barile`,`s`.`batteria` AS `batteria`,`s`.`data` AS `data`,`s`.`operazione` AS `operazione`,`s`.`idOp` AS `idOp` from `storico` `s` where (`s`.`operazione` = 'degustazione') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `misurazione_view`
--

/*!50001 DROP TABLE IF EXISTS `misurazione_view`*/;
/*!50001 DROP VIEW IF EXISTS `misurazione_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `misurazione_view` AS select `s`.`barile` AS `barile`,`s`.`batteria` AS `batteria`,`s`.`data` AS `data`,`s`.`operazione` AS `operazione`,`s`.`idOp` AS `idOp` from `storico` `s` where (`s`.`operazione` = 'misurazione') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `prelievo_view`
--

/*!50001 DROP TABLE IF EXISTS `prelievo_view`*/;
/*!50001 DROP VIEW IF EXISTS `prelievo_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `prelievo_view` AS select `s`.`barile` AS `barile`,`s`.`batteria` AS `batteria`,`s`.`data` AS `data`,`s`.`operazione` AS `operazione`,`s`.`idOp` AS `idOp` from `storico` `s` where (`s`.`operazione` = 'prelievo') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `rabbocco_view`
--

/*!50001 DROP TABLE IF EXISTS `rabbocco_view`*/;
/*!50001 DROP VIEW IF EXISTS `rabbocco_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `rabbocco_view` AS select `s`.`barile` AS `barile`,`s`.`batteria` AS `batteria`,`s`.`data` AS `data`,`s`.`operazione` AS `operazione`,`s`.`idOp` AS `idOp` from `storico` `s` where (`s`.`operazione` = 'rabbocco') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-11 14:49:55
