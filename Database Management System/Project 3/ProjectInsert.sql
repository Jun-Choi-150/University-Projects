USE `Proj_3`;

SET GLOBAL local_infile = 'ON';  
-- Loading user data
LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/user.csv'
INTO TABLE TwitterUser
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(screen_name,usr_name,sub_category,category,state,followers,following);

-- loading tweets data
LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/tweets.csv'
INTO TABLE tweets
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tweet_id,text_body,retweet_count,@col4,@col5,posting_user)
set day_posted= day(str_to_date(@col5, '%Y-%m-%d %H:%i:%s')),
month_posted= month(str_to_date(@col5, '%Y-%m-%d %H:%i:%s')),
year_posted= year(str_to_date(@col5, '%Y-%m-%d %H:%i:%s'));

-- loading hashtag data
LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/tagged.csv'
INTO TABLE hashtags
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(@col1,tag_name);

LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/tagged.csv'
INTO TABLE hashtag_used
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tweet_id,tag_name);

-- loading url data
LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/urlused.csv'
INTO TABLE urls
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(@col1, url);

LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/urlused.csv'
INTO TABLE url_used
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tweet_id,url);

-- loading mentioned data
LOAD DATA LOCAL INFILE 'C:/Users/cwj70/Downloads/dataCSV/dataCSV/mentioned.csv'
INTO TABLE tweet_mentions
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tweet_id,screen_name);
