CREATE DATABASE  IF NOT EXISTS `fit` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fit`;
-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: localhost    Database: fit
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.10.1

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
-- Table structure for table `alunos`
--

DROP TABLE IF EXISTS `alunos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alunos` (
  `cod_matricula` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `nascimento` date DEFAULT NULL,
  `sexo` enum('M','F') DEFAULT NULL,
  `rg` varchar(15) DEFAULT NULL,
  `cpf` varchar(15) DEFAULT NULL,
  `plano` varchar(40) DEFAULT NULL,
  `dia_pagamento` int(11) DEFAULT NULL,
  `data_ultimo_pagamento` date DEFAULT NULL,
  `pagamento` tinyint(1) DEFAULT NULL,
  `av_fisica_cadastrada` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`cod_matricula`),
  UNIQUE KEY `rg` (`rg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alunos`
--

LOCK TABLES `alunos` WRITE;
/*!40000 ALTER TABLE `alunos` DISABLE KEYS */;
INSERT INTO `alunos` VALUES (24056,'Eli Soares','2016-11-16','M','45.234.523-4','345.634.563-45','Musculação + Sertanejo',NULL,NULL,1,NULL),(31799,'Gabriela Rocha','1999-02-23','M','23.454.523-4','234.523.452-34','Musculação + Sertanejo + Muay Thai',5,'2016-11-15',1,0),(37240,'João Vitor','2016-11-09','M','34.563.456-3','475.674.567-45','Muay Thai',5,NULL,0,NULL),(38912,'Carlos Bolsonaro','1989-02-13','M','23.452.345-3','','Musculação + Sertanejo',5,NULL,0,1),(75879,'Thalles Roberto','1977-11-08','M','23.452.345-2','','Musculação + Sertanejo + Muay Thai',10,NULL,0,0),(82077,'João Vitor Clarindo','1999-01-20','M','45.548.448-4','469.341.288-00','Musculação + Sertanejo + Muay Thai',5,NULL,0,1),(90183,'Maria Madalena','1970-06-09','F','42.345.424-3','022.085.434-32','Musculação + Sertanejo + Muay Thai',10,NULL,0,NULL),(91049,'Anderson Freire','1999-02-03','M','23.452.345-5','','Muay Thai',20,NULL,1,1);
/*!40000 ALTER TABLE `alunos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `av_fisica`
--

DROP TABLE IF EXISTS `av_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `av_fisica` (
  `codavfisica` int(11) NOT NULL AUTO_INCREMENT,
  `cod_matricula` int(11) DEFAULT NULL,
  `altura` decimal(10,2) DEFAULT NULL,
  `flexao` int(11) DEFAULT NULL,
  `abdominal` int(11) DEFAULT NULL,
  `gordura` decimal(10,2) DEFAULT NULL,
  `pescoco` decimal(10,2) DEFAULT NULL,
  `quadril` decimal(10,2) DEFAULT NULL,
  `braco_direito` decimal(10,2) DEFAULT NULL,
  `braco_esquerdo` decimal(10,2) DEFAULT NULL,
  `antebraco_direito` decimal(10,2) DEFAULT NULL,
  `antebraco_esquerdo` decimal(10,2) DEFAULT NULL,
  `coxa_direita` decimal(10,2) DEFAULT NULL,
  `coxa_esquerda` decimal(10,2) DEFAULT NULL,
  `panturrilha_direita` decimal(10,2) DEFAULT NULL,
  `panturrilha_esquerda` decimal(10,2) DEFAULT NULL,
  `abdomen` decimal(10,2) DEFAULT NULL,
  `torax` decimal(10,2) DEFAULT NULL,
  `cintura` decimal(10,2) DEFAULT NULL,
  `metabolismo_treino` decimal(10,2) DEFAULT NULL,
  `metabolismo_basal` decimal(10,2) DEFAULT NULL,
  `peso` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`codavfisica`),
  UNIQUE KEY `cod_matricula` (`cod_matricula`),
  CONSTRAINT `av_fisica_ibfk_1` FOREIGN KEY (`cod_matricula`) REFERENCES `alunos` (`cod_matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `av_fisica`
--

LOCK TABLES `av_fisica` WRITE;
/*!40000 ALTER TABLE `av_fisica` DISABLE KEYS */;
INSERT INTO `av_fisica` VALUES (9,38912,1.65,6,6,100.00,23.00,53.00,4.80,4.84,6.80,6.80,6.00,5.00,5.00,6.00,5.00,5.50,3.60,6.00,5.00,88.88),(10,82077,1.75,1520,565,1.00,8.00,46.00,7.00,4.00,9.00,9.00,6.00,4.00,4.00,6.00,366.00,6.00,5.00,5.00,4.00,50.00),(12,91049,3.00,3,3,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,34.00);
/*!40000 ALTER TABLE `av_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionarios` (
  `cod_funcionario` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `sexo` enum('M','F') DEFAULT NULL,
  `rg` varchar(16) DEFAULT NULL,
  `cpf` varchar(16) DEFAULT NULL,
  `objetivo` varchar(50) DEFAULT NULL,
  `formacao` varchar(50) DEFAULT NULL,
  `salario` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`cod_funcionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES (40579,'Remido Santos','M','23.455.434-5','','Aula','Ensino Médio',600.00),(50368,'Thalles de Milletto','M','23.452.345-2','345.345.432-43','Presença','Filosofia',10000000.00),(61053,'Naldo Beni','M','24.532.452-3','234.523.455-42','Querer','Ensino Médio',1000.00),(82627,'Ea Ado RA','F','23.455.432-4','','Marcar História','Vai e Vai',355.00);
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-15 23:43:14
