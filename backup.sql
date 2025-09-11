-- MySQL dump 10.13  Distrib 9.3.0, for macos15 (arm64)
--
-- Host: localhost    Database: dg
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_description` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `UK41g4n0emuvcm3qyf1f6cn43c0` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Products for washing and cleaning vehicles including soaps, shampoos, and cleaners','Car Wash Products'),(2,'Tools and equipment for professional car detailing and maintenance','Detailing Tools'),(3,'Wax, sealants, and protective coatings for vehicle surfaces','Protection Products'),(4,'Products for cleaning and maintaining vehicle interiors','Interior Care');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `prod_id` int NOT NULL AUTO_INCREMENT,
  `img_url` varchar(255) DEFAULT NULL,
  `prod_description` text,
  `prod_name` varchar(255) DEFAULT NULL,
  `prod_price` decimal(38,0) DEFAULT NULL,
  `prod_quantity` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`prod_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'/uploads/ceramiccarwash.png','Premium pH-balanced ceramic soap for a gentle, effective clean with long-lasting protection.','Ceramic Car Wash Soap',750,100,1),(2,'/uploads/bugtar.png','Safely dissolves stubborn bug splatter and tar spots without damaging paint.','Bug & Tar Remover',950,60,1),(3,'/uploads/carshampoo.png','Rich foaming shampoo that safely lifts dirt and grime while protecting your paint finish.','Premium Car Shampoo',850,75,1),(4,'/uploads/waterlesswash.png','Eco-friendly waterless formula that cleans and waxes in one step.','Waterless Wash & Wax',1200,45,1),(5,'/uploads/ironremover.png','Professional-grade iron contamination remover that changes color as it works.','Iron Fallout Remover',1150,35,1),(6,'/uploads/brushkit.png','Professional set of various brushes for intricate cleaning of vents, emblems, and tight spaces.','Detailing Brushes Set',2599,45,2),(7,'/uploads/microfiber.png','Ultra-soft microfiber towels in various sizes for drying and detailing.','Microfiber Towel Pack',1599,80,2),(8,'/uploads/foamcannon.png','Professional foam cannon for thick, clinging wash foam application.','Foam Cannon',3250,25,2),(9,'/uploads/claybar.png','Complete clay bar system for removing bonded contaminants.','Detailing Clay Bar Kit',1750,55,2),(10,'/uploads/polisher.png','Professional dual-action polisher for safe paint correction.','Dual Action Polisher',12500,15,2),(11,'/uploads/ceramic9h.png','Professional-grade ceramic coating providing 2+ years of protection.','Ceramic Coating 9H',4500,20,3),(12,'/uploads/carnaubawax.png','Premium carnauba wax for deep gloss and protection.','Carnauba Wax Paste',2250,35,3),(13,'/uploads/paintsealant.png','Synthetic sealant offering 6 months of durable protection.','Paint Sealant',1850,50,3),(14,'/uploads/quickdetailer.png','Convenient spray for quick touch-ups and added gloss between washes.','Quick Detailer Spray',950,70,3),(15,'/uploads/leatherconditioner.png','Nourishing conditioner that keeps leather soft and prevents cracking.','Leather Conditioner',1450,60,4),(16,'/uploads/fabricprotector.png','Advanced protection against stains and spills for fabric surfaces.','Fabric Protector',1250,45,4),(17,'/uploads/dashboard.png','UV protection for dashboard and interior plastic surfaces.','Dashboard Protectant',850,85,4),(18,'/uploads/interiorkit.png','Complete kit with cleaners and protectants for all interior surfaces.','Interior Detailing Kit',2850,30,4),(19,'/uploads/odoreliminator.png','Professional-strength odor neutralizer for lasting freshness.','Odor Eliminator',1150,65,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ch.rohanvarma4444@gmail.com','rohan','varma','$2a$12$QJ28Cgx39y/U8QGungxVjOAgefaQe6XOPoJzGC/vemZXoIniw7lZO','2025-08-29 11:10:15.678831');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-29 20:48:37
