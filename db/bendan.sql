-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bendan
-- ------------------------------------------------------
-- Server version	8.0.29

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

--
-- Table structure for table `add_friend_rel`
--

DROP TABLE IF EXISTS `add_friend_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `add_friend_rel` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cur_user_id` bigint NOT NULL COMMENT '当前 用户id',
  `add_user_id` bigint NOT NULL COMMENT '主动添加 的 用户id ',
  `nickName` varchar(40) NOT NULL COMMENT '主动添加 的 用户 昵称',
  `avatar` varchar(255) NOT NULL COMMENT '主动添加 的 用户 头像',
  `description` varchar(255) NOT NULL COMMENT '主动添加 的 用户 描述',
  `status` int NOT NULL DEFAULT '1' COMMENT '主动添加 的 用户 添加状态',
  `create_time` datetime NOT NULL COMMENT '主动添加 的 用户 创建时间',
  `update_time` datetime NOT NULL COMMENT '主动添加 的 用户 更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新增好友 关系列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_friend_rel`
--

LOCK TABLES `add_friend_rel` WRITE;
/*!40000 ALTER TABLE `add_friend_rel` DISABLE KEYS */;
INSERT INTO `add_friend_rel` VALUES (1,1,2,'老六','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/2/v2-583e098e17089d941eb4a3d4ab5f54fb_720w.jpg','加个好友好不好？？？',2,'2022-12-06 15:47:01','2022-12-13 11:37:42'),(2,1,3,'test1','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/3/a60787ac5867b1a068364ff3b62d84c.jpg','加个好友好不好？？？',2,'2022-12-10 16:13:41','2022-12-11 13:34:20'),(6,2,3,'test1','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/3/a60787ac5867b1a068364ff3b62d84c.jpg','加个好友好不好？？？',2,'2022-12-10 16:13:41','2022-12-11 13:34:20'),(42,1,4,'test2','http://dummyimage.com/100x100','31232131',2,'2023-02-04 17:56:46','2023-02-04 17:56:46');
/*!40000 ALTER TABLE `add_friend_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_chat_record`
--

DROP TABLE IF EXISTS `bendan_chat_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_chat_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `from_id` bigint DEFAULT NULL COMMENT '自己id',
  `to_id` bigint DEFAULT NULL COMMENT '用户id',
  `send_content` text COMMENT '发送内容',
  `send_type` tinyint DEFAULT NULL COMMENT '发送类型【0文本，1图片，2语音，3视频】',
  `length` bigint DEFAULT NULL COMMENT '发送时长、视频时长、文件长度',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '对方在线状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1474 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录表\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_chat_record`
--

LOCK TABLES `bendan_chat_record` WRITE;
/*!40000 ALTER TABLE `bendan_chat_record` DISABLE KEYS */;
INSERT INTO `bendan_chat_record` VALUES (61,2,1,'喝酒去',0,NULL,'2023-01-09 11:18:33',1),(62,1,2,'不去',0,NULL,'2023-01-09 11:18:39',1),(63,2,1,'\n去不去',0,NULL,'2023-01-09 11:18:42',1),(64,1,2,'\n不去',0,NULL,'2023-01-09 11:18:45',1),(65,2,1,'\n去不去',0,NULL,'2023-01-09 11:18:50',1),(66,1,2,'\n不去',0,NULL,'2023-01-09 11:18:53',1),(67,2,1,'\n你个老六',0,NULL,'2023-01-09 11:19:05',1),(68,1,2,'\n你个酒鬼',0,NULL,'2023-01-09 11:19:31',1),(69,2,1,'\n你清高',0,NULL,'2023-01-09 11:19:44',1),(70,1,2,'\n你不得了',0,NULL,'2023-01-09 11:19:53',1),(71,1,2,'吃橘子',0,NULL,'2023-01-09 12:58:38',1),(72,1,2,'\n吃不吃',0,NULL,'2023-01-09 12:59:26',1),(73,1,2,'吃苹果咯',0,NULL,'2023-01-09 13:00:39',1),(74,2,1,'不吃',0,NULL,'2023-01-09 13:03:04',1),(75,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/6ba58aab-c431-4226-a55c-d5199675fbb7.wav',2,13,'2023-01-09 13:03:04',1),(76,2,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/6ba58aab-c431-4226-a55c-d5199675fbb7.wav',2,13,'2023-01-09 13:03:04',1),(77,1,2,'http://175.178.183.32:9000/bendan/%E8%B5%B5%E9%9B%B7-%E7%94%BB%20%28%E4%B8%AD%E5%9B%BD%E5%A5%BD%E6%AD%8C%E6%9B%B2%2014-01-24%29%28%E6%A0%87%E6%B8%85%29.mp4',3,NULL,'2023-01-09 13:03:04',1),(78,2,1,'http://175.178.183.32:9000/bendan/%E8%B5%B5%E9%9B%B7-%E7%94%BB%20%28%E4%B8%AD%E5%9B%BD%E5%A5%BD%E6%AD%8C%E6%9B%B2%2014-01-24%29%28%E6%A0%87%E6%B8%85%29.mp4',3,NULL,'2023-01-09 13:03:04',1),(79,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/1-21.jpg',1,NULL,'2023-01-09 13:03:04',1),(80,2,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/1-21.jpg',1,NULL,'2023-01-09 13:03:04',1),(81,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/%E9%94%81.md',4,38360,'2023-01-09 13:03:04',1),(82,2,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/%E9%94%81.md',4,38360,'2023-01-09 13:03:04',1),(83,3,4,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/%E9%94%81.md',4,38360,'2023-01-09 13:03:04',1),(84,4,3,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/%E9%94%81.md',4,38360,'2023-01-09 13:03:04',1),(85,1,2,'gggg',0,NULL,'2023-01-14 14:58:04',1),(86,2,1,'为你看见',0,NULL,'2023-01-14 17:40:03',1),(87,1,2,'dasdsadada',0,NULL,'2023-01-14 17:40:52',1),(88,2,1,'\n大大大大大',0,NULL,'2023-01-14 17:40:58',1),(89,2,1,'的撒的撒按时打算',0,NULL,'2023-01-14 17:41:26',1),(90,1,2,'dasdsadasda',0,NULL,'2023-01-14 17:41:40',1),(91,2,1,'大大大大',0,NULL,'2023-01-14 17:41:47',1),(92,1,2,'dsadsad',0,NULL,'2023-01-14 22:08:43',1),(93,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/37a4d38a-97b9-469d-b063-31ea2eb809c4.wav',2,1,'2023-01-15 13:00:51',1),(94,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/558a4e8b-291c-4393-b8b4-31bfed33b3b0.wav',2,3,'2023-01-15 13:03:50',1),(96,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/2a4bd01f-9853-4c82-b0c8-8b767f07a8b4.wav',2,5,'2023-01-24 12:02:44',1),(97,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/d39b154e-fc6c-4be7-bfca-e98119839a72.wav',2,3,'2023-01-24 12:16:38',1),(98,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/44e53a63-7c15-47d5-b2c8-03037cc2cb05.wav',2,3,'2023-01-24 12:17:24',1),(99,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/f3f4c5f7-bb97-437b-bb64-d3726c72d2ec.wav',2,6,'2023-01-24 12:17:44',1),(100,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/cb98e4cc-ea0c-4ea3-8db4-3dfae35988b8.wav',2,3,'2023-01-24 13:02:20',1),(102,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/10fb49c8940a417c8b744646ac720d3b.jpg',1,887982,'2023-01-24 14:55:01',1),(103,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/35a96f63880911ebb6edd017c2d2eca2.jpg',1,398366,'2023-01-24 14:55:25',1),(104,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/Spring.md',4,94963,'2023-01-24 15:12:35',1),(105,2,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/2/17df5759880911ebb6edd017c2d2eca2.jpg',1,598914,'2023-01-24 15:14:21',1),(106,1,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/3d37c083-78b4-4e5a-8fc6-851cfd68b23f.wav',2,3,'2023-01-28 15:43:02',1),(125,2,1,'啦啦啦，啦啦啦我是卖报的小行家',0,NULL,'2023-02-03 11:19:21',1),(126,2,1,'月亮不睡，我不睡',0,NULL,'2023-02-03 11:22:17',1),(1410,3,1,'dadasda',0,NULL,'2023-02-13 14:02:30',1),(1411,4,1,'23213213',0,NULL,'2023-02-13 14:07:50',1),(1412,3,1,'13',0,NULL,'2023-02-13 14:09:49',1),(1413,3,1,'eqwewqe',0,NULL,'2023-02-13 14:16:02',1),(1414,3,1,'eqwewq',0,NULL,'2023-02-13 14:16:09',1),(1415,3,1,'dasdasda',0,NULL,'2023-02-13 15:09:21',1),(1416,3,1,'dasdasd',0,NULL,'2023-02-13 15:10:28',1),(1417,3,1,'dsadsadas',0,NULL,'2023-02-13 15:10:59',1),(1418,3,1,'dasdasda',0,NULL,'2023-02-13 15:11:01',1),(1419,3,1,'dsdasd',0,NULL,'2023-02-13 15:11:07',1),(1420,3,1,'dasdsadd',0,NULL,'2023-02-13 15:11:32',1),(1421,3,1,'dsadsada',0,NULL,'2023-02-13 15:11:35',1),(1422,3,1,'dasdasda',0,NULL,'2023-02-13 15:11:36',1),(1423,3,1,'asdsada',0,NULL,'2023-02-13 15:11:37',1),(1424,3,1,'das',0,NULL,'2023-02-13 15:11:37',1),(1425,3,1,'da',0,NULL,'2023-02-13 15:11:38',1),(1426,3,1,'dsad',0,NULL,'2023-02-13 15:11:38',1),(1427,3,1,'ada',0,NULL,'2023-02-13 15:11:38',1),(1428,3,1,'da',0,NULL,'2023-02-13 15:11:38',1),(1429,3,1,'dad',0,NULL,'2023-02-13 15:11:38',1),(1430,3,1,'ad',0,NULL,'2023-02-13 15:11:39',1),(1431,3,1,'adas',0,NULL,'2023-02-13 15:11:39',1),(1432,3,1,'da',0,NULL,'2023-02-13 15:11:39',1),(1433,3,1,'da',0,NULL,'2023-02-13 15:11:39',1),(1434,3,1,'dad',0,NULL,'2023-02-13 15:11:39',1),(1435,3,1,'asdsa',0,NULL,'2023-02-13 15:11:39',1),(1436,3,1,'da',0,NULL,'2023-02-13 15:11:40',1),(1437,3,1,'sda',0,NULL,'2023-02-13 15:11:40',1),(1438,3,1,'da',0,NULL,'2023-02-13 15:11:40',1),(1439,3,1,'d',0,NULL,'2023-02-13 15:11:42',1),(1440,3,1,'DASDSA',0,NULL,'2023-02-13 15:15:02',1),(1441,3,1,'DA',0,NULL,'2023-02-13 15:15:03',1),(1442,3,1,'ADA',0,NULL,'2023-02-13 15:15:03',1),(1443,3,1,'DA',0,NULL,'2023-02-13 15:15:03',1),(1444,3,1,'DAD',0,NULL,'2023-02-13 15:15:04',1),(1445,3,1,'ASD',0,NULL,'2023-02-13 15:15:04',1),(1446,3,1,'AD',0,NULL,'2023-02-13 15:15:04',1),(1447,3,1,'ASD',0,NULL,'2023-02-13 15:15:06',1),(1448,3,1,'dasdasd',0,NULL,'2023-02-13 15:17:30',1),(1449,3,1,'dasdas',0,NULL,'2023-02-13 15:17:47',1),(1450,3,1,'dasdasd',0,NULL,'2023-02-13 15:17:49',1),(1451,3,1,'dasdasd',0,NULL,'2023-02-13 15:17:50',1),(1452,3,1,'asddsad',0,NULL,'2023-02-13 15:17:51',1),(1453,3,1,'asd',0,NULL,'2023-02-13 15:17:52',1),(1454,3,1,'das',0,NULL,'2023-02-13 15:17:52',1),(1455,3,1,'das',0,NULL,'2023-02-13 15:17:52',1),(1456,3,1,'dsa',0,NULL,'2023-02-13 15:17:53',1),(1457,3,1,'sda',0,NULL,'2023-02-13 15:17:53',1),(1458,3,1,'sdasda',0,NULL,'2023-02-13 15:17:53',1),(1459,3,1,'dsa',0,NULL,'2023-02-13 15:17:53',1),(1460,3,1,'asd',0,NULL,'2023-02-13 15:17:54',1),(1461,3,1,'dsa',0,NULL,'2023-02-13 15:17:54',1),(1462,3,1,'ads',0,NULL,'2023-02-13 15:17:54',1),(1463,3,1,'das',0,NULL,'2023-02-13 15:17:54',1),(1464,3,1,'dsa',0,NULL,'2023-02-13 15:17:54',1),(1465,3,1,'sda',0,NULL,'2023-02-13 15:17:55',1),(1466,3,1,'asd',0,NULL,'2023-02-13 15:17:55',1),(1467,3,1,'das',0,NULL,'2023-02-13 15:17:55',1),(1468,3,1,'ads',0,NULL,'2023-02-13 15:17:55',1),(1469,3,1,'das',0,NULL,'2023-02-13 15:17:55',1),(1470,3,1,'ads',0,NULL,'2023-02-13 15:17:56',1),(1471,3,1,'ads',0,NULL,'2023-02-13 15:17:56',1),(1472,3,1,'ads',0,NULL,'2023-02-13 15:17:56',1),(1473,3,1,'asd',0,NULL,'2023-02-13 15:17:56',1);
/*!40000 ALTER TABLE `bendan_chat_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_oss`
--

DROP TABLE IF EXISTS `bendan_oss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_oss` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `url` text NOT NULL COMMENT '文件路径',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_oss`
--

LOCK TABLES `bendan_oss` WRITE;
/*!40000 ALTER TABLE `bendan_oss` DISABLE KEYS */;
INSERT INTO `bendan_oss` VALUES (1,NULL,'http://175.178.183.32:9000/bendan/1-%E6%B4%BE%E5%85%8B%E7%89%B9%20-%20%E4%BD%A0%E6%88%91%E4%BB%96.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T031937Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=8542814ff3a2ec9ab60f701cfeb0d4b5a2b640b169464cb02562ff824ef10e91','1-派克特 - 你我他.mp3','2023-01-03 11:19:37','2023-01-03 11:19:37'),(2,NULL,'http://175.178.183.32:9000/bendan/1-16k.pcm?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T055514Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=13f8e4407d04ce2b969df07e72d68c136148b8df6c6f44af729c70d7c5fafd82','1-16k.pcm','2023-01-03 13:55:14','2023-01-03 13:55:14'),(3,NULL,'http://175.178.183.32:9000/bendan/1-sdk_demo.pcm?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T063708Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=0fdc1584b298674da2a09d9ef4d71db8d62b016bd17953ac7795c70e48b80b80','1-sdk_demo.pcm','2023-01-03 14:37:08','2023-01-03 14:37:08'),(4,NULL,'http://175.178.183.32:9000/bendan/1-%E6%96%B0%E5%BB%BA%20%E6%96%87%E6%9C%AC%E6%96%87%E6%A1%A3.txt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230104%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230104T031428Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=3a57da7f7b26ae7cdd9db3fbdee50d1df018382e46de31ac6e85c4591589f2c6','1-新建 文本文档.txt','2023-01-04 11:14:28','2023-01-04 11:14:28'),(17,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/2/f396de6b-ec53-4ac4-aea7-f76bcf468595.pcm','2/f396de6b-ec53-4ac4-aea7-f76bcf468595.pcm',NULL,NULL),(18,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/2.wav','1/2.wav',NULL,NULL),(19,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/e08cf662-aeaf-40d7-85bd-89a26fe33651.wav','1/e08cf662-aeaf-40d7-85bd-89a26fe33651.wav',NULL,NULL),(20,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/da34b795-2c2b-4f51-8ca3-f27c4bd6903b.wav','1/da34b795-2c2b-4f51-8ca3-f27c4bd6903b.wav',NULL,NULL),(21,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/7a9a51bf-05e9-4632-a479-03d30b0285ed.wav','1/7a9a51bf-05e9-4632-a479-03d30b0285ed.wav',NULL,NULL),(22,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/830cd59a-b99b-42b9-b35d-627f4ede970f.wav','1/830cd59a-b99b-42b9-b35d-627f4ede970f.wav',NULL,NULL),(23,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/e884cf8d-fba1-42f8-bf42-7b0b68958d4c.wav','1/e884cf8d-fba1-42f8-bf42-7b0b68958d4c.wav',NULL,NULL),(24,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/466ebb83-20dd-4b53-814d-3cce5455d3fc.wav','1/466ebb83-20dd-4b53-814d-3cce5455d3fc.wav',NULL,NULL),(25,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/6ba58aab-c431-4226-a55c-d5199675fbb7.wav','1/6ba58aab-c431-4226-a55c-d5199675fbb7.wav',NULL,NULL),(26,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/37a4d38a-97b9-469d-b063-31ea2eb809c4.wav','1/37a4d38a-97b9-469d-b063-31ea2eb809c4.wav','2023-01-15 13:00:51','2023-01-15 13:00:51'),(27,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/558a4e8b-291c-4393-b8b4-31bfed33b3b0.wav','1/558a4e8b-291c-4393-b8b4-31bfed33b3b0.wav','2023-01-15 13:03:50','2023-01-15 13:03:50'),(28,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/2a4bd01f-9853-4c82-b0c8-8b767f07a8b4.wav','1/2a4bd01f-9853-4c82-b0c8-8b767f07a8b4.wav','2023-01-24 12:02:44','2023-01-24 12:02:44'),(29,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/d39b154e-fc6c-4be7-bfca-e98119839a72.wav','1/d39b154e-fc6c-4be7-bfca-e98119839a72.wav','2023-01-24 12:16:38','2023-01-24 12:16:38'),(30,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/44e53a63-7c15-47d5-b2c8-03037cc2cb05.wav','1/44e53a63-7c15-47d5-b2c8-03037cc2cb05.wav','2023-01-24 12:17:24','2023-01-24 12:17:24'),(31,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/f3f4c5f7-bb97-437b-bb64-d3726c72d2ec.wav','1/f3f4c5f7-bb97-437b-bb64-d3726c72d2ec.wav','2023-01-24 12:17:44','2023-01-24 12:17:44'),(32,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/cb98e4cc-ea0c-4ea3-8db4-3dfae35988b8.wav','1/cb98e4cc-ea0c-4ea3-8db4-3dfae35988b8.wav','2023-01-24 13:02:20','2023-01-24 13:02:20'),(33,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/test100.wav','1/test100.wav','2023-01-24 14:53:12','2023-01-24 14:53:12'),(34,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/10fb49c8940a417c8b744646ac720d3b.jpg','1/10fb49c8940a417c8b744646ac720d3b.jpg','2023-01-24 14:55:01','2023-01-24 14:55:01'),(35,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/35a96f63880911ebb6edd017c2d2eca2.jpg','1/35a96f63880911ebb6edd017c2d2eca2.jpg','2023-01-24 14:55:25','2023-01-24 14:55:25'),(36,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/Spring.md','1/Spring.md','2023-01-24 15:12:35','2023-01-24 15:12:35'),(37,2,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/2/17df5759880911ebb6edd017c2d2eca2.jpg','2/17df5759880911ebb6edd017c2d2eca2.jpg','2023-01-24 15:14:21','2023-01-24 15:14:21'),(38,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/3d37c083-78b4-4e5a-8fc6-851cfd68b23f.wav','1/3d37c083-78b4-4e5a-8fc6-851cfd68b23f.wav','2023-01-28 15:43:02','2023-01-28 15:43:02'),(39,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/3f878fc75ad84405bffa29a309238655.jpg','1/3f878fc75ad84405bffa29a309238655.jpg','2023-02-06 14:12:51','2023-02-06 14:12:51'),(40,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/3f878fc75ad84405bffa29a309238655.jpg','1/3f878fc75ad84405bffa29a309238655.jpg','2023-02-06 15:04:46','2023-02-06 15:04:46'),(41,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/1e6d9571645344d6b9eda31c990ec826.jpg','1/1e6d9571645344d6b9eda31c990ec826.jpg','2023-02-06 15:08:03','2023-02-06 15:08:03'),(42,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/1061445.jpg','1/1061445.jpg','2023-02-06 15:10:53','2023-02-06 15:10:53'),(43,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/312603.jpg','1/312603.jpg','2023-02-06 15:12:35','2023-02-06 15:12:35'),(44,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/957325.png','1/957325.png','2023-02-06 15:13:44','2023-02-06 15:13:44'),(45,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/965941.jpg','1/965941.jpg','2023-02-06 15:57:16','2023-02-06 15:57:16'),(46,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/965941.jpg','1/965941.jpg','2023-02-06 15:58:33','2023-02-06 15:58:33'),(47,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/965941.jpg','1/965941.jpg','2023-02-06 15:59:42','2023-02-06 15:59:42'),(48,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/957325.png','1/957325.png','2023-02-06 16:02:32','2023-02-06 16:02:32'),(49,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/949253.jpg','1/949253.jpg','2023-02-06 16:03:34','2023-02-06 16:03:34'),(50,1,'https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/965941.jpg','1/965941.jpg','2023-02-06 16:05:00','2023-02-06 16:05:00');
/*!40000 ALTER TABLE `bendan_oss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_menu`
--

DROP TABLE IF EXISTS `bendan_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单父ID',
  `path` varchar(64) DEFAULT NULL COMMENT '路径',
  `name` varchar(64) NOT NULL COMMENT '菜单名',
  `level` int NOT NULL DEFAULT '0' COMMENT '层级',
  `purview_name` varchar(48) DEFAULT NULL COMMENT '权限标识名',
  `status` int DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--关闭 2--删除)',
  `sort` int DEFAULT '0' COMMENT '排序;数字越越靠后',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bendan_sys_menu_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COMMENT='菜单(权限)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_menu`
--

LOCK TABLES `bendan_sys_menu` WRITE;
/*!40000 ALTER TABLE `bendan_sys_menu` DISABLE KEYS */;
INSERT INTO `bendan_sys_menu` VALUES (17,0,'/home/index','首页',0,NULL,0,0,'HomeOutlined',NULL,NULL,'2022-12-20 13:43:33','2022-12-20 13:43:33'),(18,0,'/auth','权限管理',0,NULL,0,1,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(19,18,'/auth/user','用户管理',0,NULL,0,1,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(20,18,'/auth/role','角色管理',0,NULL,0,2,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(21,18,'/auth/menu','菜单管理',0,NULL,0,3,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(22,0,'/chat','聊天室',0,NULL,0,3,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(23,19,NULL,'查询用户',0,'sys_user_query',0,0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bendan_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_role`
--

DROP TABLE IF EXISTS `bendan_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT '角色名字',
  `code` varchar(64) NOT NULL COMMENT '角色码',
  `role_describe` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `del` int DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bendan_sys_role_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_role`
--

LOCK TABLES `bendan_sys_role` WRITE;
/*!40000 ALTER TABLE `bendan_sys_role` DISABLE KEYS */;
INSERT INTO `bendan_sys_role` VALUES (1,'管理员','ROLE_ADMIN','管理员',0,2,2,'2022-12-01 20:12:31','2022-12-01 20:12:31'),(2,'普通用户','GENERAL_USER','普通用户',0,2,2,'2022-12-01 20:12:31','2022-12-01 20:12:31');
/*!40000 ALTER TABLE `bendan_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_role_menu`
--

DROP TABLE IF EXISTS `bendan_sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_role_menu`
--

LOCK TABLES `bendan_sys_role_menu` WRITE;
/*!40000 ALTER TABLE `bendan_sys_role_menu` DISABLE KEYS */;
INSERT INTO `bendan_sys_role_menu` VALUES (1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23);
/*!40000 ALTER TABLE `bendan_sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_user`
--

DROP TABLE IF EXISTS `bendan_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `self_description` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `phone_number` varchar(16) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `status` int DEFAULT '0' COMMENT '用户状态（0-正常  1-锁定  2-删除）',
  `gender` int DEFAULT '-1' COMMENT '性别   (-1 未知 0 女性  1 男性)',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_user`
--

LOCK TABLES `bendan_sys_user` WRITE;
/*!40000 ALTER TABLE `bendan_sys_user` DISABLE KEYS */;
INSERT INTO `bendan_sys_user` VALUES (1,'admin','{bcrypt}$2a$10$RlTvD5whI8rtR8D3ZAoa1ezXQ3FHzz01DnR6eiCmMKAOJIDZx/wGW','obeast-dragon','一条老龙','1008611','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/1/965941.jpg','3594900021@qq.com',0,-1,1,1,'2023-02-07 22:41:08','2022-12-06 15:47:01'),(2,'user','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','老六','老六的一生','10086111008','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/2/v2-583e098e17089d941eb4a3d4ab5f54fb_720w.jpg','t.wss@qq.com',0,0,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(3,'test1','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test1','IKun','10086111008','https://bendan-1305865318.cos.ap-guangzhou.myqcloud.com/3/a60787ac5867b1a068364ff3b62d84c.jpg','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(4,'test2','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test2','跳','10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(5,'test3','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test3','rap','10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(6,'test4','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test4','唱','10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(7,'test5','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test5',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(8,'test6','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test6',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(9,'test7','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test7',NULL,'10086111008','dasdsadsada','t.wss@qq.com',0,-1,1,9,'2023-02-06 15:51:07','2022-12-10 16:13:41'),(10,'test8','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test8',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(11,'test9','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test9',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(12,'test10','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test10',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,1,1,'2022-12-11 13:34:20','2022-12-10 16:13:41');
/*!40000 ALTER TABLE `bendan_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_user_role`
--

DROP TABLE IF EXISTS `bendan_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_user_role`
--

LOCK TABLES `bendan_sys_user_role` WRITE;
/*!40000 ALTER TABLE `bendan_sys_user_role` DISABLE KEYS */;
INSERT INTO `bendan_sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `bendan_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_registered_client` (
  `id` varchar(100) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) DEFAULT NULL,
  `client_secret_expires_at` timestamp NULL DEFAULT NULL,
  `client_name` varchar(200) NOT NULL,
  `client_authentication_methods` varchar(1000) NOT NULL,
  `authorization_grant_types` varchar(1000) NOT NULL,
  `redirect_uris` varchar(1000) DEFAULT NULL,
  `scopes` varchar(1000) NOT NULL,
  `client_settings` varchar(2000) NOT NULL,
  `token_settings` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--

LOCK TABLES `oauth2_registered_client` WRITE;
/*!40000 ALTER TABLE `oauth2_registered_client` DISABLE KEYS */;
INSERT INTO `oauth2_registered_client` VALUES ('81d68e38-9cc3-4534-b1d7-4dc812a38c5b','web','2022-12-07 05:13:19','{bcrypt}$2a$10$tRLdMGxOH/Mzg7Tv89lOBetliDlZgRN4ltIviv4YQrSwBs0sP6fOm',NULL,'81d68e38-9cc3-4534-b1d7-4dc812a38c5b','client_secret_basic','refresh_token,password,client_credentials,authorization_code','http://127.0.0.1:18812/authorized','all,openid,message.read,message.write','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",43200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",259200.000000000]}');
/*!40000 ALTER TABLE `oauth2_registered_client` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-15 15:07:05
