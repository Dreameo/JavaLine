/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.5.28 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS jdbc_db DEFAULT CHARACTER SET utf8

USE `jdbc_db`;

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `photo` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT = 19 DEFAULT CHARSET=utf8;

/*Data for the table `customers` */

insert  into `customers`(`id`,`name`,`email`,`birth`,`photo`) values (1,'汪峰','wf@126.com','2010-02-02',NULL),
(2,'王菲','wangf@163.com','1988-12-26',NULL),
(3,'林志�?,'linzl@gmail.com','1984-06-12',NULL),
(4,'汤唯','tangw@sina.com','1986-06-13',NULL),
(5,'成龙','Jackey@gmai.com','1955-07-14',NULL),
(6,'迪丽热巴','reba@163.com','1983-05-17',NULL),
(7,'刘亦�?,'liuyifei@qq.com','1991-11-14',NULL),
(8,'陈道�?,'bdf@126.com','2014-01-17',NULL),
(10,'周杰伦','zhoujl@sina.com','1979-11-15',NULL),
(12,'黎明','LiM@126.com','1998-09-08',NULL),
(13,'张学友','zhangxy@126.com','1998-12-21',NULL),
(16,'朱茵','zhuyin@126.com','2014-01-16',NULL)
/*Table structure for table `examstudent` */

DROP TABLE IF EXISTS `examstudent`;

CREATE TABLE `examstudent` (
  `FlowID` int(20) NOT NULL AUTO_INCREMENT,
  `Type` int(20) DEFAULT NULL,
  `IDCard` varchar(18) DEFAULT NULL,
  `ExamCard` varchar(15) DEFAULT NULL,
  `StudentName` varchar(20) DEFAULT NULL,
  `Location` varchar(20) DEFAULT NULL,
  `Grade` int(10) DEFAULT NULL,
  PRIMARY KEY (`FlowID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;

/*Data for the table `examstudent` */

insert  into `examstudent`(`FlowID`,`Type`,`IDCard`,`ExamCard`,`StudentName`,`Location`,`Grade`) values (1,4,'412824195263214584','200523164754000','张锋','郑州',85),(2,4,'222224195263214584','200523164754001','孙朋','大连',56),(3,6,'342824195263214584','200523164754002','刘明','沈阳',72),(4,6,'100824195263214584','200523164754003','赵虎','哈尔滨\r\n',95),(5,4,'454524195263214584','200523164754004','杨丽','北京',64),(6,4,'854524195263214584','200523164754005','王小�?,'太原',60);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gb2312;

/*Data for the table `order` */

insert  into `order`(`order_id`,`order_name`,`order_date`) values (1,'AA','2010-03-04'),(2,'BB','2000-02-01'),(4,'GG','1994-06-28');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL DEFAULT '123456',
  `address` varchar(25) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gb2312;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`address`,`phone`) values (1,'章子�?,'qwerty','Beijing','13788658672'),(2,'郭富�?,'abc123','HongKong','15678909898'),(3,'林志�?,'654321','Taiwan','18612124565'),(4,'梁静�?,'987654367','malaixiya','18912340998'),(5,'LadyGaGa','123456','America','13012386565');

/*Table structure for table `user_table` */

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
  `user` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `balance` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `user_table` */

insert  into `user_table`(`user`,`password`,`balance`) values ('AA','123456',1000),('BB','654321',1000),('CC','abcd',2000),('DD','abcder',3000);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
