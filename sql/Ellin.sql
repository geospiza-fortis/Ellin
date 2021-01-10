/*
Navicat MySQL Data Transfer

Source Server         : 56
Source Server Version : 50136
Source Host           : localhost:3306
Source Database       : leaderms2

Target Server Type    : MYSQL
Target Server Version : 50136
File Encoding         : 65001

Date: 2019-07-15 10:26:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) NOT NULL DEFAULT '',
  `password` varchar(128) NOT NULL DEFAULT '',
  `salt` varchar(32) DEFAULT NULL,
  `pin` varchar(10) DEFAULT NULL,
  `loggedin` tinyint(4) NOT NULL DEFAULT '0',
  `lastlogin` timestamp NULL DEFAULT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `birthday` date NOT NULL DEFAULT '2000-01-01',
  `banned` tinyint(1) NOT NULL DEFAULT '0',
  `banreason` text,
  `gm` tinyint(1) NOT NULL DEFAULT '0',
  `email` tinytext,
  `macs` tinytext,
  `lastpwemail` timestamp NOT NULL DEFAULT '2002-12-31 17:00:00',
  `tempban` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `greason` tinyint(4) DEFAULT NULL,
  `paypalNX` int(11) DEFAULT NULL,
  `mPoints` int(11) DEFAULT NULL,
  `cardNX` int(11) DEFAULT NULL,
  `donatorPoints` tinyint(1) DEFAULT NULL,
  `guest` tinyint(1) NOT NULL DEFAULT '0',
  `LastLoginInMilliseconds` bigint(20) unsigned NOT NULL DEFAULT '0',
  `lastknownip` tinytext NOT NULL,
  `votePoints` int(11) DEFAULT NULL,
  `sitelogged` text,
  `webadmin` int(1) DEFAULT '0',
  `ip` text,
  `SessionIP` varchar(64) DEFAULT NULL,
  `gender` tinyint(2) NOT NULL DEFAULT '10',
  `hwid` varchar(12) NOT NULL DEFAULT '',
  `tos` tinyint(1) NOT NULL DEFAULT '0',
  `characterslots` tinyint(2) NOT NULL DEFAULT '3',
  `nick` varchar(20) DEFAULT NULL,
  `mute` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `ranking1` (`id`,`banned`,`gm`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for alliances
-- ----------------------------
CREATE TABLE IF NOT EXISTS `alliances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) NOT NULL,
  `leaderid` int(11) NOT NULL,
  `guild1` int(11) NOT NULL,
  `guild2` int(11) NOT NULL,
  `guild3` int(11) NOT NULL DEFAULT '0',
  `guild4` int(11) NOT NULL DEFAULT '0',
  `guild5` int(11) NOT NULL DEFAULT '0',
  `rank1` varchar(13) NOT NULL DEFAULT 'Master',
  `rank2` varchar(13) NOT NULL DEFAULT 'Jr.Master',
  `rank3` varchar(13) NOT NULL DEFAULT 'Member',
  `rank4` varchar(13) NOT NULL DEFAULT 'Member',
  `rank5` varchar(13) NOT NULL DEFAULT 'Member',
  `capacity` int(11) NOT NULL DEFAULT '2',
  `notice` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for bbs_replies
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bbs_replies` (
  `replyid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `threadid` int(10) unsigned NOT NULL,
  `postercid` int(10) unsigned NOT NULL,
  `timestamp` bigint(20) unsigned NOT NULL,
  `content` varchar(26) NOT NULL DEFAULT '',
  `guildid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`replyid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for bbs_threads
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bbs_threads` (
  `threadid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `postercid` int(10) unsigned NOT NULL,
  `name` varchar(26) NOT NULL DEFAULT '',
  `timestamp` bigint(20) unsigned NOT NULL,
  `icon` smallint(5) unsigned NOT NULL,
  `replycount` smallint(5) unsigned NOT NULL DEFAULT '0',
  `startpost` text NOT NULL,
  `guildid` int(10) unsigned NOT NULL,
  `localthreadid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`threadid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for bosslog
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bosslog` (
  `bosslogid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned NOT NULL,
  `bossid` varchar(20) NOT NULL,
  `lastattempt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`bosslogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for buddies
-- ----------------------------
CREATE TABLE IF NOT EXISTS `buddies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `buddyid` int(11) NOT NULL,
  `pending` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for buddyentries
-- ----------------------------
CREATE TABLE IF NOT EXISTS `buddyentries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL,
  `buddyid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`owner`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for cashshopcouponitems
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cashshopcouponitems` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `type` int(11) NOT NULL,
  `itemData` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for cashshopcoupons
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cashshopcoupons` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `used` int(11) NOT NULL DEFAULT '0',
  `character` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for characters
-- ----------------------------
CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountid` int(11) NOT NULL DEFAULT '0',
  `world` int(11) NOT NULL DEFAULT '0',
  `name` varchar(13) NOT NULL DEFAULT '',
  `level` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `str` int(11) NOT NULL DEFAULT '0',
  `dex` int(11) NOT NULL DEFAULT '0',
  `luk` int(11) NOT NULL DEFAULT '0',
  `int` int(11) NOT NULL DEFAULT '0',
  `hp` int(11) NOT NULL DEFAULT '0',
  `mp` int(11) NOT NULL DEFAULT '5',
  `maxhp` int(11) NOT NULL DEFAULT '0',
  `maxmp` int(11) NOT NULL DEFAULT '5',
  `meso` int(11) NOT NULL DEFAULT '0',
  `hpApUsed` int(11) NOT NULL DEFAULT '0',
  `mpApUsed` int(11) NOT NULL DEFAULT '0',
  `job` int(11) NOT NULL DEFAULT '0',
  `skincolor` int(11) NOT NULL DEFAULT '0',
  `gender` int(11) NOT NULL DEFAULT '0',
  `fame` int(11) NOT NULL DEFAULT '0',
  `hair` int(11) NOT NULL DEFAULT '0',
  `face` int(11) NOT NULL DEFAULT '0',
  `ap` int(11) NOT NULL DEFAULT '0',
  `sp` int(11) NOT NULL DEFAULT '0',
  `map` int(11) NOT NULL DEFAULT '0',
  `spawnpoint` int(11) NOT NULL DEFAULT '0',
  `gm` int(11) NOT NULL DEFAULT '0',
  `party` int(11) NOT NULL DEFAULT '0',
  `buddyCapacity` int(11) NOT NULL DEFAULT '25',
  `autoHpPot` mediumint(7) NOT NULL DEFAULT '0',
  `autoMpPot` mediumint(7) NOT NULL DEFAULT '0',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rank` int(10) unsigned NOT NULL DEFAULT '1',
  `rankMove` int(11) NOT NULL DEFAULT '0',
  `jobRank` int(10) unsigned NOT NULL DEFAULT '1',
  `jobRankMove` int(11) NOT NULL DEFAULT '0',
  `guildid` int(10) unsigned NOT NULL DEFAULT '0',
  `guildrank` int(10) unsigned NOT NULL DEFAULT '5',
  `allianceRank` int(10) unsigned NOT NULL DEFAULT '5',
  `mountlevel` int(9) NOT NULL DEFAULT '1',
  `mountexp` int(9) NOT NULL DEFAULT '0',
  `mounttiredness` int(9) NOT NULL DEFAULT '0',
  `married` int(10) unsigned NOT NULL DEFAULT '0',
  `partnerid` int(10) unsigned NOT NULL DEFAULT '0',
  `marriagequest` int(10) unsigned NOT NULL DEFAULT '0',
  `omok` int(4) DEFAULT NULL,
  `matchcard` int(4) DEFAULT NULL,
  `omokwins` int(4) DEFAULT NULL,
  `omoklosses` int(4) DEFAULT NULL,
  `omokties` int(4) DEFAULT NULL,
  `matchcardwins` int(4) DEFAULT NULL,
  `matchcardlosses` int(4) DEFAULT NULL,
  `matchcardties` int(4) DEFAULT NULL,
  `merchantMesos` int(11) NOT NULL DEFAULT '0',
  `HasMerchant` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `equipslots` int(11) NOT NULL DEFAULT '24',
  `useslots` int(11) unsigned zerofill NOT NULL DEFAULT '00000000024',
  `setupslots` int(11) NOT NULL DEFAULT '24',
  `etcslots` int(11) NOT NULL DEFAULT '24',
  `bosspoints` int(11) NOT NULL DEFAULT '0',
  `ariantPoints` int(11) NOT NULL DEFAULT '0',
  `votePoints` int(11) NOT NULL DEFAULT '0',
  `hpMpUsed` int(11) NOT NULL,
  `pets` varchar(13) NOT NULL DEFAULT '-1,-1,-1',
  `playtime` bigint(20) unsigned NOT NULL DEFAULT '0',
  `spouseId` int(11) NOT NULL DEFAULT '0',
  `dataString` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  KEY `party` (`party`),
  KEY `ranking1` (`level`,`exp`),
  KEY `ranking2` (`gm`,`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for characterslots
-- ----------------------------
CREATE TABLE IF NOT EXISTS `characterslots` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `world` tinyint(3) unsigned NOT NULL,
  `slots` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_3` (`accountId`),
  FOREIGN KEY (`accountId`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for cheatlog
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cheatlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT '0',
  `offense` tinytext NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `lastoffensetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `param` tinytext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cid` (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for completpqs
-- ----------------------------
CREATE TABLE IF NOT EXISTS `completpqs` (
  `id` int(11) NOT NULL,
  `henesysPQ` int(11) DEFAULT '0',
  `kerningPQ` int(11) DEFAULT '0',
  `ludibriumPQ` int(11) DEFAULT '0',
  `orbisPQ` int(11) DEFAULT '0',
  `piratePQ` int(11) DEFAULT '0',
  `zakumPQ` int(11) DEFAULT '0',
  `horntailPQ` int(11) DEFAULT '0',
  `guildPQ` int(11) DEFAULT '0',
  `amoriaPQ` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for cooldowns
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cooldowns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `charid` int(11) NOT NULL,
  `skillid` int(11) NOT NULL,
  `length` bigint(20) unsigned NOT NULL,
  `starttime` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for drop_data
-- ----------------------------
CREATE TABLE IF NOT EXISTS `drop_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dropperid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL DEFAULT '0',
  `minimum_quantity` int(11) NOT NULL DEFAULT '1',
  `maximum_quantity` int(11) NOT NULL DEFAULT '1',
  `questid` int(11) NOT NULL DEFAULT '0',
  `chance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dropperid` (`dropperid`,`itemid`),
  KEY `mobid` (`dropperid`),
  KEY `dropperid_2` (`dropperid`,`itemid`)
) ENGINE=MyISAM AUTO_INCREMENT=24966 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for drop_data_global
-- ----------------------------
CREATE TABLE IF NOT EXISTS `drop_data_global` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `continent` int(11) NOT NULL,
  `dropType` tinyint(1) NOT NULL DEFAULT '0',
  `itemid` int(11) NOT NULL DEFAULT '0',
  `minimum_quantity` int(11) NOT NULL DEFAULT '1',
  `maximum_quantity` int(11) NOT NULL DEFAULT '1',
  `questid` int(11) NOT NULL DEFAULT '0',
  `chance` int(11) NOT NULL DEFAULT '0',
  `comments` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mobid` (`continent`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for dueyitems
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dueyitems` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PackageId` int(10) unsigned NOT NULL DEFAULT '0',
  `itemid` int(10) unsigned NOT NULL DEFAULT '0',
  `quantity` int(10) unsigned NOT NULL DEFAULT '0',
  `upgradeslots` int(11) DEFAULT '0',
  `level` int(11) DEFAULT '0',
  `str` int(11) DEFAULT '0',
  `dex` int(11) DEFAULT '0',
  `int` int(11) DEFAULT '0',
  `luk` int(11) DEFAULT '0',
  `hp` int(11) DEFAULT '0',
  `mp` int(11) DEFAULT '0',
  `watk` int(11) DEFAULT '0',
  `matk` int(11) DEFAULT '0',
  `wdef` int(11) DEFAULT '0',
  `mdef` int(11) DEFAULT '0',
  `acc` int(11) DEFAULT '0',
  `avoid` int(11) DEFAULT '0',
  `hands` int(11) DEFAULT '0',
  `speed` int(11) DEFAULT '0',
  `jump` int(11) DEFAULT '0',
  `flag` int(11) DEFAULT '0',
  `owner` tinytext,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`PackageId`) REFERENCES `dueypackages` (`PackageId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for dueypackages
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dueypackages` (
  `PackageId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ReceiverId` int(10) unsigned NOT NULL,
  `SenderName` varchar(13) NOT NULL,
  `Mesos` int(10) unsigned DEFAULT '0',
  `TimeStamp` varchar(10) NOT NULL,
  `Checked` tinyint(1) unsigned DEFAULT '1',
  `Type` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`PackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for famelog
-- ----------------------------
CREATE TABLE IF NOT EXISTS `famelog` (
  `famelogid` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT '0',
  `characterid_to` int(11) NOT NULL DEFAULT '0',
  `when` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`famelogid`),
  FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for gifts
-- ----------------------------
CREATE TABLE IF NOT EXISTS `gifts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `to` int(11) NOT NULL,
  `from` varchar(13) NOT NULL,
  `message` tinytext NOT NULL,
  `sn` int(10) unsigned NOT NULL,
  `ringid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gifts_1` (`to`),
  CONSTRAINT `FK_gifts_1` FOREIGN KEY (`to`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for gmlog
-- ----------------------------
CREATE TABLE IF NOT EXISTS `gmlog` (
  `gmlogid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL DEFAULT '0',
  `command` text NOT NULL,
  `mapid` int(11) NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gmlogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for guilds
-- ----------------------------
CREATE TABLE IF NOT EXISTS `guilds` (
  `guildid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `leader` int(10) unsigned NOT NULL DEFAULT '0',
  `GP` int(10) unsigned NOT NULL DEFAULT '0',
  `logo` int(10) unsigned DEFAULT NULL,
  `logoColor` smallint(5) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL,
  `rank1title` varchar(45) NOT NULL DEFAULT 'Master',
  `rank2title` varchar(45) NOT NULL DEFAULT 'Jr. Master',
  `rank3title` varchar(45) NOT NULL DEFAULT 'Member',
  `rank4title` varchar(45) NOT NULL DEFAULT 'Member',
  `rank5title` varchar(45) NOT NULL DEFAULT 'Member',
  `capacity` int(10) unsigned NOT NULL DEFAULT '10',
  `logoBG` int(10) unsigned DEFAULT NULL,
  `logoBGColor` smallint(5) unsigned NOT NULL DEFAULT '0',
  `notice` varchar(101) DEFAULT NULL,
  `signature` int(11) NOT NULL DEFAULT '0',
  `alliance` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`guildid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for hwidbans
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hwidbans` (
  `hwidbanid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `hwid` varchar(30) NOT NULL,
  PRIMARY KEY (`hwidbanid`),
  UNIQUE KEY `hwid_2` (`hwid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for inventoryequipment
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventoryequipment` (
  `inventoryequipmentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` int(10) unsigned NOT NULL DEFAULT '0',
  `upgradeslots` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `str` int(11) NOT NULL DEFAULT '0',
  `dex` int(11) NOT NULL DEFAULT '0',
  `int` int(11) NOT NULL DEFAULT '0',
  `luk` int(11) NOT NULL DEFAULT '0',
  `hp` int(11) NOT NULL DEFAULT '0',
  `mp` int(11) NOT NULL DEFAULT '0',
  `watk` int(11) NOT NULL DEFAULT '0',
  `matk` int(11) NOT NULL DEFAULT '0',
  `wdef` int(11) NOT NULL DEFAULT '0',
  `mdef` int(11) NOT NULL DEFAULT '0',
  `acc` int(11) NOT NULL DEFAULT '0',
  `avoid` int(11) NOT NULL DEFAULT '0',
  `hands` int(11) NOT NULL DEFAULT '0',
  `speed` int(11) NOT NULL DEFAULT '0',
  `jump` int(11) NOT NULL DEFAULT '0',
  `locked` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  FOREIGN KEY (`inventoryitemid`) REFERENCES `inventoryitems` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for inventoryitems
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventoryitems` (
  `inventoryitemid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL,
  `characterid` int(11) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `itemid` int(11) NOT NULL DEFAULT '0',
  `inventorytype` int(11) NOT NULL DEFAULT '0',
  `position` int(11) NOT NULL DEFAULT '0',
  `quantity` int(11) NOT NULL DEFAULT '0',
  `owner` tinytext NOT NULL,
  `uniqueid` int(11) NOT NULL DEFAULT '-1',
  `giftfrom` varchar(26) NOT NULL,
  `expiration` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventoryitems_ibfk_1` (`characterid`),
  KEY `characterid` (`characterid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `characterid_2` (`characterid`,`inventorytype`),
  CONSTRAINT `inventoryitems_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for ipbans
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ipbans` (
  `ipbanid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`ipbanid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for iplog
-- ----------------------------
CREATE TABLE IF NOT EXISTS `iplog` (
  `iplogid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accountid` int(11) NOT NULL DEFAULT '0',
  `ip` varchar(30) NOT NULL DEFAULT '',
  `login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`iplogid`),
  KEY `accountid` (`accountid`,`ip`),
  KEY `ip` (`ip`),
  CONSTRAINT `iplog_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for keymap
-- ----------------------------
CREATE TABLE IF NOT EXISTS `keymap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT '0',
  `key` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `action` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for macbans
-- ----------------------------
CREATE TABLE IF NOT EXISTS `macbans` (
  `macbanid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mac` varchar(30) NOT NULL,
  PRIMARY KEY (`macbanid`),
  UNIQUE KEY `mac_2` (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for macfilters
-- ----------------------------
CREATE TABLE IF NOT EXISTS `macfilters` (
  `macfilterid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filter` varchar(30) NOT NULL,
  PRIMARY KEY (`macfilterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for mountdata
-- ----------------------------
CREATE TABLE IF NOT EXISTS `mountdata` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned DEFAULT NULL,
  `Level` int(3) unsigned NOT NULL DEFAULT '0',
  `Exp` int(10) unsigned NOT NULL DEFAULT '0',
  `Fatigue` int(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for notes
-- ----------------------------
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to` varchar(13) NOT NULL DEFAULT '',
  `from` varchar(13) NOT NULL DEFAULT '',
  `message` text NOT NULL,
  `timestamp` bigint(20) unsigned NOT NULL,
  `fame` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for pets
-- ----------------------------
CREATE TABLE IF NOT EXISTS `pets` (
  `petid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(13) DEFAULT NULL,
  `level` int(3) unsigned NOT NULL,
  `closeness` int(6) unsigned NOT NULL,
  `fullness` int(3) unsigned NOT NULL,
  `seconds` int(11) NOT NULL DEFAULT '0',
  `excluded` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for playernpcs
-- ----------------------------
CREATE TABLE IF NOT EXISTS `playernpcs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) NOT NULL,
  `hair` int(11) NOT NULL,
  `face` int(11) NOT NULL,
  `skin` int(11) NOT NULL,
  `dir` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `cy` int(11) NOT NULL DEFAULT '0',
  `map` int(11) NOT NULL,
  `gender` int(11) NOT NULL,
  `ScriptId` int(10) unsigned NOT NULL DEFAULT '0',
  `Foothold` int(11) NOT NULL DEFAULT '0',
  `rx0` int(11) NOT NULL DEFAULT '0',
  `rx1` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for playernpcs_equip
-- ----------------------------
CREATE TABLE IF NOT EXISTS `playernpcs_equip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `npcid` int(11) NOT NULL,
  `equipid` int(11) NOT NULL,
  `equippos` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`npcid`) REFERENCES `playernpcs` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for player_variables
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_variables` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(11) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for questactions
-- ----------------------------
CREATE TABLE IF NOT EXISTS `questactions` (
  `questactionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `questid` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `data` blob NOT NULL,
  PRIMARY KEY (`questactionid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for questprogress
-- ----------------------------
CREATE TABLE IF NOT EXISTS `questprogress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queststatusid` int(10) unsigned NOT NULL DEFAULT '0',
  `progressid` int(11) NOT NULL DEFAULT '0',
  `progress` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for questrequirements
-- ----------------------------
CREATE TABLE IF NOT EXISTS `questrequirements` (
  `questrequirementid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `questid` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `data` blob NOT NULL,
  PRIMARY KEY (`questrequirementid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for queststatus
-- ----------------------------
CREATE TABLE IF NOT EXISTS `queststatus` (
  `queststatusid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT '0',
  `quest` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `time` int(11) NOT NULL DEFAULT '0',
  `forfeited` int(11) NOT NULL DEFAULT '0',
  `completed` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`queststatusid`),
  FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for queststatusmobs
-- ----------------------------
CREATE TABLE IF NOT EXISTS `queststatusmobs` (
  `queststatusmobid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queststatusid` int(10) unsigned NOT NULL DEFAULT '0',
  `mob` int(11) NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`queststatusmobid`),
  FOREIGN KEY (`queststatusid`) REFERENCES `queststatus` (`queststatusid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for reactordrops
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reactordrops` (
  `reactordropid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reactorid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `chance` int(11) NOT NULL,
  `questid` int(5) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`reactordropid`),
  KEY `reactorid` (`reactorid`)
) ENGINE=InnoDB AUTO_INCREMENT=1487 DEFAULT CHARSET=utf8 PACK_KEYS=1;

-- ----------------------------
-- Table structure for regrocklocations
-- ----------------------------
CREATE TABLE IF NOT EXISTS `regrocklocations` (
  `trockid` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) DEFAULT NULL,
  `mapid` int(11) DEFAULT NULL,
  PRIMARY KEY (`trockid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for reports
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reports` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reporterid` int(11) NOT NULL,
  `victimid` int(11) NOT NULL,
  `reason` tinyint(4) NOT NULL,
  `chatlog` text NOT NULL,
  `status` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for rings
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partnerRingId` int(11) NOT NULL DEFAULT '0',
  `partnerChrId` int(11) NOT NULL DEFAULT '0',
  `itemid` int(11) NOT NULL DEFAULT '0',
  `partnername` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for savedlocations
-- ----------------------------
CREATE TABLE IF NOT EXISTS `savedlocations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `locationtype` enum('FREE_MARKET','WORLDTOUR','FLORINA','MONSTER_CARNIVAL','ARIANT_PQ','EVENTO','BALROGPQ','RICHIE','RANDOM_EVENT') NOT NULL,
  `map` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `characterid` (`characterid`,`map`,`locationtype`),
  KEY `savedlocations_ibfk_1` (`characterid`),
  CONSTRAINT `savedlocations_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for shopitems
-- ----------------------------
CREATE TABLE IF NOT EXISTS `shopitems` (
  `shopitemid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shopid` int(10) unsigned NOT NULL DEFAULT '0',
  `itemid` int(11) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  `position` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`shopitemid`),
  KEY `shopid` (`shopid`),
  UNIQUE KEY `shopid_2` (`shopid`,`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for shops
-- ----------------------------
CREATE TABLE IF NOT EXISTS `shops` (
  `shopid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `npcid` int(11) DEFAULT '0',
  PRIMARY KEY (`shopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for skillmacros
-- ----------------------------
CREATE TABLE IF NOT EXISTS `skillmacros` (
  `entryid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `position` tinyint(1) NOT NULL,
  `name` varchar(12) NOT NULL,
  `silent` tinyint(1) NOT NULL,
  `skill1` int(11) NOT NULL,
  `skill2` int(11) NOT NULL,
  `skill3` int(11) NOT NULL,
  PRIMARY KEY (`entryid`),
  KEY `characterid` (`characterid`),
  CONSTRAINT `skillmacros_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for skills
-- ----------------------------
CREATE TABLE IF NOT EXISTS `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skillid` int(11) NOT NULL DEFAULT '0',
  `characterid` int(11) NOT NULL DEFAULT '0',
  `skilllevel` int(11) NOT NULL DEFAULT '0',
  `masterlevel` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `characterid` (`characterid`,`skillid`),
  KEY `skills_ibfk_1` (`characterid`),
  CONSTRAINT `skills_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for spawns
-- ----------------------------
CREATE TABLE IF NOT EXISTS `spawns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idd` int(11) NOT NULL,
  `f` int(11) NOT NULL,
  `fh` int(11) NOT NULL,
  `type` varchar(1) NOT NULL,
  `cy` int(11) NOT NULL,
  `rx0` int(11) NOT NULL,
  `rx1` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `mobtime` int(11) DEFAULT '1000',
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for storages
-- ----------------------------
CREATE TABLE IF NOT EXISTS `storages` (
  `storageid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accountid` int(11) NOT NULL DEFAULT '0',
  `slots` int(11) NOT NULL DEFAULT '0',
  `meso` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`storageid`),
  KEY `accountid` (`accountid`),
  CONSTRAINT `storages_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for topresults
-- ----------------------------
CREATE TABLE IF NOT EXISTS `topresults` (
  `itemid` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `world` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for trocklocations
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trocklocations` (
  `trockid` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) DEFAULT NULL,
  `mapid` int(11) DEFAULT NULL,
  PRIMARY KEY (`trockid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for wishlist
-- ----------------------------
CREATE TABLE IF NOT EXISTS `wishlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `sn` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for wz_speedquiz
-- ----------------------------
CREATE TABLE IF NOT EXISTS `wz_speedquiz` (
  `quizDataId` int(10) NOT NULL AUTO_INCREMENT,
  `type` smallint(6) NOT NULL,
  `objectid` int(11) NOT NULL,
  `answer` varchar(200) NOT NULL,
  `questionNo` int(11) NOT NULL,
  PRIMARY KEY (`quizDataId`)
) ENGINE=MyISAM AUTO_INCREMENT=948 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for levelhistory
-- ----------------------------
CREATE TABLE IF NOT EXISTS `levelhistory` (
  `accountid` int(11) NOT NULL,
  `characterid` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `date` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
