CREATE DATABASE  IF NOT EXISTS `fit` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fit`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: fit
-- ------------------------------------------------------
-- Server version	5.7.14

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
INSERT INTO `alunos` VALUES (12991,'Leonidas','1999-03-13','M','13.443.234-4','','Sertanejo',5,'2016-11-05',0,0),(24056,'Eli Soares','2016-11-16','M','45.234.523-4','345.634.563-45','Musculação + Sertanejo',25,'2016-11-05',1,NULL),(27148,'Natália Lima Vieira','1989-04-08','F','34.523.454-3','765.456.543-45','Muay Thai',15,'2016-11-05',0,0),(27682,'João Carlos','1934-12-23','M','65.435.654-3','','Musculação + Sertanejo',5,'2016-11-05',0,0),(37240,'João Vitor Nobre','1999-01-20','M','34.563.456-3','475.674.567-45','Muay Thai',5,'2016-11-05',0,1),(38912,'Carlos Bolsonaro','1989-02-13','M','23.452.345-3','','Musculação + Sertanejo',5,'2016-11-05',0,1),(75879,'Thalles Roberto','1977-11-08','M','23.452.345-2','','Musculação + Sertanejo + Muay Thai',10,'2016-11-05',0,1),(90183,'Maria Madalena','1970-06-09','F','42.345.424-3','022.085.434-32','Musculação + Sertanejo + Muay Thai',10,'2016-11-05',0,NULL),(91049,'Anderson Freire','1999-02-03','M','23.452.345-5','','Muay Thai',20,'2016-11-05',1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `av_fisica`
--

LOCK TABLES `av_fisica` WRITE;
/*!40000 ALTER TABLE `av_fisica` DISABLE KEYS */;
INSERT INTO `av_fisica` VALUES (9,38912,1.65,6,6,100.00,23.00,53.00,4.80,4.84,6.80,6.80,6.00,5.00,5.00,6.00,5.00,5.50,3.60,6.00,5.00,88.88),(12,91049,3.00,3,3,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,3.00,34.00),(13,75879,1.55,1,4,1.00,3.00,14.00,1.00,3.00,1.00,2.00,3.00,2.00,4.00,1.00,4.00,4.00,1.00,1.00,4.00,50.00),(14,37240,1.75,4,2,23.00,3.00,4.00,5.00,3.00,2.00,4.00,3.00,5.00,5.00,3.00,2.00,4.00,3.00,4.00,4.00,55.00);
/*!40000 ALTER TABLE `av_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duvidas_codeking`
--

DROP TABLE IF EXISTS `duvidas_codeking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `duvidas_codeking` (
  `cod_duvida` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `duvida` text,
  PRIMARY KEY (`cod_duvida`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duvidas_codeking`
--

LOCK TABLES `duvidas_codeking` WRITE;
/*!40000 ALTER TABLE `duvidas_codeking` DISABLE KEYS */;
INSERT INTO `duvidas_codeking` VALUES (1,'André Silva','andre@silva.com','Com perfeição?'),(2,'André Silva','andre@silva.com','Com perfeição?'),(3,'André Silva','andre@silva.com','Com perfeição?'),(4,'André Silva','andre@silva.com','Com perfeição?'),(5,'André Silva','andre@silva.com','Com perfeição?'),(6,'André Silva','andre@silva.com','Com perfeição?'),(7,'André Silva','andre@silva.com','Com perfeição?'),(8,'André Silva','andre@silva.com','Com perfeição?'),(9,'André Silva','andre@silva.com','Com perfeição?'),(10,'André Silva','andre@silva.com','Com perfeição?'),(11,'André Silva','andre@silva.com','Com perfeição?'),(12,'André Silva','andre@silva.com','Com perfeição?'),(13,'André Silva','andre@silva.com','Com perfeição?'),(14,'joao','joaovitornobre12@hotmail.com','asdfadsf'),(15,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(16,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(17,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(18,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(19,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(20,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(21,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd'),(22,'João Vitor Nobre Clarindo','joaovitornobre12@hotmail.com','asdasd');
/*!40000 ALTER TABLE `duvidas_codeking` ENABLE KEYS */;
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
  PRIMARY KEY (`cod_funcionario`),
  UNIQUE KEY `rg` (`rg`),
  UNIQUE KEY `rg_2` (`rg`),
  UNIQUE KEY `rg_3` (`rg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES (19088,'Kim Walker Smith','M','23.455.634-5','','Cantar S','Música',1500.00),(40579,'Remido Santos Lopos','M','23.455.434-5','565.845.745-5','Aula','Ensino Médio',600.00),(61053,'Naldo Beni Santos','M','24.532.452-3','234.523.455-42','Dançar','Ensino Médio',1500.00),(89398,'Lauren Diagle','F','45.235.454-5','234.523.455-42','Cantar e Dançar','Música',4000.00);
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_user`
--

DROP TABLE IF EXISTS `login_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_user` (
  `usuario` varchar(15) NOT NULL,
  `senha` varchar(15) NOT NULL DEFAULT '123456',
  `system_key` varchar(20) NOT NULL DEFAULT 'QWERTY987123BJ',
  PRIMARY KEY (`usuario`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_user`
--

LOCK TABLES `login_user` WRITE;
/*!40000 ALTER TABLE `login_user` DISABLE KEYS */;
INSERT INTO `login_user` VALUES ('zar','123zar','QWERTY987123BJ'),('dora','dora456','QWERTY987123BJ'),('atendente','123atendente','QWERTY987123BJ');
/*!40000 ALTER TABLE `login_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produtos` (
  `cod_produto` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `descricao` varchar(150) DEFAULT NULL,
  `vendas` int(11) DEFAULT '0',
  `valor` decimal(10,2) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `valor_revenda` decimal(10,2) NOT NULL,
  PRIMARY KEY (`cod_produto`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (95041,'Whey','Consumível','Suplemento',1,40.00,49,50.00);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_clientes`
--

DROP TABLE IF EXISTS `site_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_clientes` (
  `cod_cliente_site` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `email` varchar(50) NOT NULL,
  `plano` varchar(45) NOT NULL,
  `duvida` text NOT NULL,
  PRIMARY KEY (`cod_cliente_site`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_clientes`
--

LOCK TABLES `site_clientes` WRITE;
/*!40000 ALTER TABLE `site_clientes` DISABLE KEYS */;
INSERT INTO `site_clientes` VALUES (1,'João Vitor Nobre','joao@joao','Sertanejo','qual a dois de 1?\r\n        '),(19,'João Vitor Nobre Clarindo','pela@joao.com','Musculação + Aeróbico','??? Oi?'),(17,'Maria Madalena','marianobre@maria.com','Musculação + Aeróbico','???dedede?????'),(16,'joao','joao@joo.com','Sertanejo','??°???°???°?'),(11,'JOão','joaovitornobre12@hotmail.com','Muay Thai',''),(18,'Marlene Silva','marlene.silva@etec.sp.gov.br','Musculação + Aeróbico','O que é java?'),(20,'João','joao@joo.com','Musculação + Aeróbico','Nada?');
/*!40000 ALTER TABLE `site_clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendas` (
  `cod_produto` int(11) NOT NULL,
  `nome` varchar(30) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `descricao` varchar(150) DEFAULT NULL,
  `valor` decimal(10,2) NOT NULL,
  `data_venda` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
INSERT INTO `vendas` VALUES (91792,'Ademantol','Consumível','caddsfdsfadf',67.00,'2016-12-09'),(68556,'Calemoro','Vestível','Cakça de nadar',800.00,'2016-12-09'),(91792,'Ademantol','Consumível','caddsfdsfadf',67.00,'2016-12-09'),(68556,'Calemoro','Vestível','Cakça de nadar',800.00,'2016-12-09'),(91792,'Ademantol','Consumível','caddsfdsfadf',67.00,'2016-12-10'),(94488,'Calça23','Vestível','Calça Legging',30.00,'2016-12-10'),(95041,'Whey','Consumível','Suplemento',50.00,'2016-12-13');
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-23 20:10:14
