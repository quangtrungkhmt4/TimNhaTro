-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 31, 2018 at 03:11 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `timnhatro`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblcomments`
--

CREATE TABLE `tblcomments` (
  `IDCOMMENT` int(11) NOT NULL,
  `TEXT` text NOT NULL,
  `IDUSER` int(11) NOT NULL,
  `IDHOUSE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tblhouses`
--

CREATE TABLE `tblhouses` (
  `IDHOUSE` int(11) NOT NULL,
  `TITLE` text NOT NULL,
  `ADDRESS` text NOT NULL,
  `OBJECT` int(11) NOT NULL,
  `IMAGE` text NOT NULL,
  `DESC` text NOT NULL,
  `CONTACT` text NOT NULL,
  `ACREAGE` float NOT NULL,
  `PRICE` float NOT NULL,
  `CREATED_AT` text NOT NULL,
  `STATE` int(11) NOT NULL,
  `IDUNIT` int(11) NOT NULL,
  `IDUSER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tblinfoimages`
--

CREATE TABLE `tblinfoimages` (
  `IDIMAGE` int(11) NOT NULL,
  `URL` text NOT NULL,
  `IDHOUSE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tblunits`
--

CREATE TABLE `tblunits` (
  `IDUNIT` int(11) NOT NULL,
  `TYPE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tblunits`
--

INSERT INTO `tblunits` (`IDUNIT`, `TYPE`) VALUES
(1, 'triệu/tháng');

-- --------------------------------------------------------

--
-- Table structure for table `tblusers`
--

CREATE TABLE `tblusers` (
  `IDUSER` int(11) NOT NULL,
  `USER` text NOT NULL,
  `PASSWORD` text NOT NULL,
  `NAME` text NOT NULL,
  `PHONE` text NOT NULL,
  `IMAGE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblcomments`
--
ALTER TABLE `tblcomments`
  ADD PRIMARY KEY (`IDCOMMENT`),
  ADD KEY `tblComments_fk0` (`IDUSER`),
  ADD KEY `tblComments_fk1` (`IDHOUSE`);

--
-- Indexes for table `tblhouses`
--
ALTER TABLE `tblhouses`
  ADD PRIMARY KEY (`IDHOUSE`),
  ADD KEY `tblHouses_fk0` (`IDUNIT`),
  ADD KEY `tblHouses_fk1` (`IDUSER`);

--
-- Indexes for table `tblinfoimages`
--
ALTER TABLE `tblinfoimages`
  ADD PRIMARY KEY (`IDIMAGE`),
  ADD KEY `tblInfoImages_fk0` (`IDHOUSE`);

--
-- Indexes for table `tblunits`
--
ALTER TABLE `tblunits`
  ADD PRIMARY KEY (`IDUNIT`);

--
-- Indexes for table `tblusers`
--
ALTER TABLE `tblusers`
  ADD PRIMARY KEY (`IDUSER`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblcomments`
--
ALTER TABLE `tblcomments`
  MODIFY `IDCOMMENT` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblhouses`
--
ALTER TABLE `tblhouses`
  MODIFY `IDHOUSE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tblinfoimages`
--
ALTER TABLE `tblinfoimages`
  MODIFY `IDIMAGE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblunits`
--
ALTER TABLE `tblunits`
  MODIFY `IDUNIT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tblusers`
--
ALTER TABLE `tblusers`
  MODIFY `IDUSER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tblcomments`
--
ALTER TABLE `tblcomments`
  ADD CONSTRAINT `tblComments_fk0` FOREIGN KEY (`IDUSER`) REFERENCES `tblusers` (`IDUSER`),
  ADD CONSTRAINT `tblComments_fk1` FOREIGN KEY (`IDHOUSE`) REFERENCES `tblhouses` (`IDHOUSE`);

--
-- Constraints for table `tblhouses`
--
ALTER TABLE `tblhouses`
  ADD CONSTRAINT `tblHouses_fk0` FOREIGN KEY (`IDUNIT`) REFERENCES `tblunits` (`IDUNIT`),
  ADD CONSTRAINT `tblHouses_fk1` FOREIGN KEY (`IDUSER`) REFERENCES `tblusers` (`IDUSER`);

--
-- Constraints for table `tblinfoimages`
--
ALTER TABLE `tblinfoimages`
  ADD CONSTRAINT `tblInfoImages_fk0` FOREIGN KEY (`IDHOUSE`) REFERENCES `tblhouses` (`IDHOUSE`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
