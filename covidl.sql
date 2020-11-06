/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.11-MariaDB : Database - covid
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`covid` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `covid`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `ime` varchar(30) DEFAULT NULL,
  `prezime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `administrator` */

insert  into `administrator`(`id`,`username`,`password`,`ime`,`prezime`) values 
(2,'marko','marko123','Marko','Markovic'),
(5,'milica','milica123','Milica','Jevremovic');

/*Table structure for table `laborant` */

DROP TABLE IF EXISTS `laborant`;

CREATE TABLE `laborant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(30) DEFAULT NULL,
  `prezime` varchar(20) NOT NULL,
  `brojDosijea` varbinary(5) NOT NULL,
  `brojOrdinacije` double NOT NULL,
  `testID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `profesor_fk` (`testID`),
  CONSTRAINT `test_fk` FOREIGN KEY (`testID`) REFERENCES `test` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `laborant` */

insert  into `laborant`(`id`,`ime`,`prezime`,`brojDosijea`,`brojOrdinacije`,`testID`) values 
(1,'Jovan','Jovancic','D231',1,1),
(2,'Milena','Hadzic','D554',13,2),
(5,'Katarina','Radisavljevic','D821',2,2),
(7,'Petar','Kutlesic','D234',3,10),
(8,'Srdjan','Aleksic','D111',4,2),
(9,'Dimitrije','Sreckovic','D764',6,13),
(12,'Milos','Tabakovic','D920',5,10),
(13,'LINS','Ucenje o linearnim s','D123',7,13),
(15,'Zoran','Rakic','D744',12,10);

/*Table structure for table `pacijent` */

DROP TABLE IF EXISTS `pacijent`;

CREATE TABLE `pacijent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(30) NOT NULL,
  `prezime` varchar(30) NOT NULL,
  `datumRodjenja` date NOT NULL,
  `telefon` varchar(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `laborantID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `kurs_fk` (`laborantID`),
  CONSTRAINT `laborantI_fk` FOREIGN KEY (`laborantID`) REFERENCES `laborant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `pacijent` */

insert  into `pacijent`(`id`,`ime`,`prezime`,`datumRodjenja`,`telefon`,`email`,`laborantID`) values 
(2,'Marko','Jovanovic','1997-01-01','0645687364','mjovanovic@gmail.com',2),
(5,'Jovana','Markovic','1995-07-15','0643675897','jkaradzic@gmail.com',2),
(7,'Milica','Ruzic','1997-08-01','0653568725','mruzic@gmail.com',2),
(8,'Petar','Petrovic','2001-10-28','0623747926','ppetrovic@gmail.com',5),
(11,'Nikola','Jankovic','1992-03-15','213123','njankovic@gmail.com',1),
(12,'Janko','Stojanovic','1995-07-06','0654224189','jstojanovic@gmail.com',2);

/*Table structure for table `rezultat` */

DROP TABLE IF EXISTS `rezultat`;

CREATE TABLE `rezultat` (
  `pacijentID` bigint(20) NOT NULL,
  `terminTestID` bigint(20) NOT NULL,
  `vrednost` int(11) NOT NULL,
  PRIMARY KEY (`pacijentID`,`terminTestID`),
  KEY `ispit_fk` (`terminTestID`),
  CONSTRAINT `pacijent_fk` FOREIGN KEY (`pacijentID`) REFERENCES `pacijent` (`id`),
  CONSTRAINT `termintestiranja_fk` FOREIGN KEY (`terminTestID`) REFERENCES `termintestiranja` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rezultat` */

insert  into `rezultat`(`pacijentID`,`terminTestID`,`vrednost`) values 
(2,4,17),
(5,4,40),
(12,4,50);

/*Table structure for table `termintestiranja` */

DROP TABLE IF EXISTS `termintestiranja`;

CREATE TABLE `termintestiranja` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `datum` date NOT NULL,
  `laborantID` bigint(20) NOT NULL,
  `testID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kursI_fk` (`laborantID`),
  KEY `profesorI_fk` (`testID`),
  CONSTRAINT `laborant_fk` FOREIGN KEY (`laborantID`) REFERENCES `laborant` (`id`),
  CONSTRAINT `testI_fk` FOREIGN KEY (`testID`) REFERENCES `test` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `termintestiranja` */

insert  into `termintestiranja`(`id`,`datum`,`laborantID`,`testID`) values 
(1,'2019-11-28',1,1),
(2,'2019-01-03',2,2),
(3,'2020-02-13',1,2),
(4,'2020-02-06',2,2),
(5,'2019-03-22',1,1),
(6,'2020-07-03',12,10),
(7,'2020-03-09',1,1),
(8,'2021-02-13',1,1),
(9,'2020-08-05',7,2);

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(30) NOT NULL,
  `opis` varchar(30) NOT NULL,
  `uputstvoZaPrimenu` varchar(20) NOT NULL,
  `vrsta` varchar(30) NOT NULL,
  `oznaka` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `test` */

insert  into `test`(`id`,`naziv`,`opis`,`uputstvoZaPrimenu`,`vrsta`,`oznaka`) values 
(1,'Antitela1','Kratkotrajna antitela','uputstvo1','ANTITELA','A1'),
(2,'Antitela2','Dugotrajna anititela','uputstvo2','ELIZA','A2'),
(10,'Covid1','Bris','uputstvo3','PCR','C1'),
(13,'Covid2','Krv','uputstvo4','ELIZA','902738473829');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
