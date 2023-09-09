drop database IF EXISTS `Proj_3`;
CREATE DATABASE IF NOT EXISTS `Proj_3`;
USE `Proj_3`;

-- Create user table first since it'll be used as a FK in tweets and mentions
DROP TABLE IF EXISTS TwitterUser;
CREATE TABLE TwitterUser (
  screen_name VARCHAR(200) PRIMARY KEY,
  usr_name TINYTEXT,
  followers INT,
  following INT,
  sub_category TINYTEXT,
  category TINYTEXT,
  state TINYTEXT);

-- Create tweets table so id can be used as a FK in all the other tables
DROP TABLE IF EXISTS tweets;
CREATE TABLE tweets (
  tweet_id BIGINT PRIMARY KEY,
  text_body TEXT,
  retweet_count INT DEFAULT NULL,
  year_posted INT DEFAULT NULL,
  month_posted INT DEFAULT NULL,
  day_posted INT DEFAULT NULL,
  posting_user VARCHAR(200),
  FOREIGN KEY(posting_user)
    REFERENCES TwitterUser(screen_name)
    ON DELETE CASCADE);

-- create table of mentions in tweets
DROP TABLE IF EXISTS tweet_mentions;
CREATE TABLE tweet_mentions (
  tweet_id BIGINT,
  screen_name VARCHAR(200),
  PRIMARY KEY(tweet_id, screen_name),
  FOREIGN KEY(tweet_id)
    REFERENCES tweets(tweet_id),
  FOREIGN KEY(screen_name)
    REFERENCES TwitterUser(screen_name));

-- create tables for hashtags
DROP TABLE IF EXISTS hashtags;
CREATE TABLE hashtags (
  tag_name VARCHAR(200) primary key
);

-- create tables for hashtag_used
DROP TABLE IF EXISTS hashtag_used;
CREATE TABLE hashtag_used (
  tweet_id BIGINT,
  tag_name VARCHAR(200),
  FOREIGN KEY(tweet_id)
    REFERENCES tweets(tweet_id)
    ON DELETE CASCADE,
  FOREIGN KEY(tag_name)
    REFERENCES hashtags(tag_name)  
    ON DELETE CASCADE,
  primary key(tweet_id, tag_name)
    );

-- create table for urls
DROP TABLE IF EXISTS urls;
CREATE TABLE urls (
  url VARCHAR(500) primary key
);
    
-- create table for url_used
DROP TABLE IF EXISTS url_used;
CREATE TABLE url_used (
  tweet_id BIGINT,
  url VARCHAR(500),
  primary key (tweet_id, url),
  FOREIGN KEY(tweet_id)
    REFERENCES tweets(tweet_id)
    ON DELETE CASCADE,
   FOREIGN KEY(url)
    REFERENCES urls(url)
    ON DELETE CASCADE   
    );
