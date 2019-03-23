CREATE DATABASE IF NOT EXISTS Blog
  CHARACTER SET UTF8;

USE Blog;

DROP TABLE IF EXISTS `attach`;
CREATE TABLE `attach` (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  fname     VARCHAR(100) NOT NULL,
  ftype     VARCHAR(50),
  fkey      VARCHAR(100) NOT NULL,
  author_id INTEGER(10)  NOT NULL,
  created   DATETIME     NOT NULL
);

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  cid       INTEGER DEFAULT 0 NOT NULL,
  created   DATETIME          NOT NULL,
  author    VARCHAR(200)      NOT NULL,
  author_id INTEGER(10)         DEFAULT 0,
  owner_id  INTEGER(10)         DEFAULT 0,
  mail      VARCHAR(200)      NOT NULL,
  url       VARCHAR(200),
  ip        VARCHAR(64),
  agent     VARCHAR(200),
  content   TEXT              NOT NULL,
  type      VARCHAR(16),
  status    VARCHAR(16),
  parent    INTEGER(10)         DEFAULT 0
);

DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  id            INTEGER PRIMARY KEY AUTO_INCREMENT,
  title         VARCHAR(255) NOT NULL,
  slug          VARCHAR(255),
  thumb_img     VARCHAR(255),
  created       DATETIME     NOT NULL,
  modified      DATETIME,
  content       TEXT,
  author_id     INTEGER(10)  NOT NULL,
  type          VARCHAR(16)  NOT NULL,
  status        VARCHAR(16)  NOT NULL,
  fmt_type      VARCHAR(16)         DEFAULT 'MARKDOWN',
  tags          VARCHAR(200),
  categories    VARCHAR(200),
  hits          INTEGER(10)         DEFAULT 0,
  comments_num  INTEGER(1)          DEFAULT 0,
  allow_comment INTEGER(1)          DEFAULT 1,
  allow_ping    INTEGER(1),
  allow_feed    INTEGER(1)
);

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  action    VARCHAR(100) NOT NULL,
  data      VARCHAR(2000),
  author_id INTEGER(10),
  ip        VARCHAR(20),
  created   DATETIME     NOT NULL
);

-- 文章分类
DROP TABLE IF EXISTS `meta`;
CREATE TABLE `meta` (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(200) NOT NULL,
  slug        VARCHAR(200),
  type        VARCHAR(32)  NOT NULL,
  description VARCHAR(255),
  sort        INTEGER(4)          DEFAULT 0,
  parent      INTEGER(10)         DEFAULT 0
);

DROP TABLE IF EXISTS `option`;
CREATE TABLE `option` (
  name        VARCHAR(100) PRIMARY KEY,
  value       VARCHAR(255),
  description VARCHAR(255)
);

-- content 与 mate的联系
DROP TABLE IF EXISTS `relationship`;
CREATE TABLE `relationship` (
  cid INTEGER(10) NOT NULL,
  mid INTEGER(10) NOT NULL,
  PRIMARY KEY (cid, mid)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  username    VARCHAR(64) UNIQUE         NOT NULL,
  password    VARCHAR(64)                NOT NULL,
  email       VARCHAR(100),
  home_url    VARCHAR(255),
  screen_name VARCHAR(100),
  created     DATETIME                   NOT NULL,
  activated   INTEGER(10),
  logged      INTEGER(10),
  group_name  VARCHAR(16)
);

ALTER DATABASE blog CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE `content` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `comment` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
