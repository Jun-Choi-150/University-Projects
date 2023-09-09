-- Q1
SELECT t.retweet_count, t.text_body, t.posting_user, u.category, u.sub_category
FROM tweets t
JOIN TwitterUser u ON t.posting_user = u.screen_name
WHERE year_posted = 2016
ORDER BY t.retweet_count DESC
LIMIT 10;

-- Q2 //Wrong
SELECT COUNT(DISTINCT u.state) AS num_states, GROUP_CONCAT(DISTINCT u.state) AS states, UPPER(h.tag_name) AS hashtag_name
FROM hashtag_used h
JOIN tweets t ON h.tweet_id = t.tweet_id
JOIN TwitterUser u ON t.posting_user = u.screen_name
WHERE t.year_posted = 2016
GROUP BY h.tag_name
HAVING num_states >= 1
ORDER BY num_states DESC, h.tag_name
LIMIT 10;


-- Q3
SELECT u.screen_name, u.state
FROM TwitterUser u
JOIN tweets t ON u.screen_name = t.posting_user
JOIN hashtag_used h ON t.tweet_id = h.tweet_id
WHERE h.tag_name IN ('iowacaucus', 'iacaucus')
GROUP BY u.screen_name, u.state
ORDER BY u.followers DESC, u.screen_name;



-- Q4
SELECT u.screen_name, u.category, u.followers
FROM TwitterUser u
WHERE u.sub_category = 'GOP'
ORDER BY u.followers DESC, u.screen_name
LIMIT 5;

SELECT u.screen_name, u.category, u.followers
FROM TwitterUser u
WHERE u.sub_category = 'Democrat'
ORDER BY u.followers DESC, u.screen_name
LIMIT 5;

-- Q5 
SELECT h.tag_name, COUNT(t.tweet_id) AS num_tweets
FROM hashtags h
JOIN hashtag_used hu ON h.tag_name = hu.tag_name
JOIN tweets t ON hu.tweet_id = t.tweet_id
JOIN TwitterUser u ON t.posting_user = u.screen_name
WHERE u.state = 'Iowa' AND t.year_posted = 2016 AND t.month_posted = 1
GROUP BY h.tag_name;

-- Q6
SELECT t.text_body, h.tag_name, u.screen_name, u.sub_category
FROM tweets t
JOIN hashtag_used h ON t.tweet_id = h.tweet_id
JOIN TwitterUser u ON t.posting_user = u.screen_name
WHERE h.tag_name = 'Iowa' AND u.sub_category IN ('GOP', 'Democrat') AND t.year_posted = 2016 AND t.month_posted = 2;

-- Q7 //Wrong
SELECT u.screen_name, COUNT(t.tweet_id) AS num_tweets, GROUP_CONCAT(DISTINCT uu.url) AS urls
FROM TwitterUser u
JOIN tweets t ON u.screen_name = t.posting_user
JOIN url_used uu ON t.tweet_id = uu.tweet_id
WHERE u.sub_category = 'GOP' AND t.year_posted = 2016 AND t.month_posted = 1
GROUP BY u.screen_name
ORDER BY num_tweets DESC, u.screen_name;



-- Q8 //Wrong
SELECT tm.screen_name, u.followers, COUNT(DISTINCT t.posting_user) AS num_posting_users
FROM tweet_mentions tm
JOIN tweets t ON tm.tweet_id = t.tweet_id
JOIN TwitterUser u ON tm.screen_name = u.screen_name
GROUP BY tm.screen_name, u.followers
ORDER BY num_posting_users DESC, tm.screen_name
LIMIT 10;


-- Q9 
SELECT uu.url, COUNT(t.tweet_id) AS num_tweets
FROM url_used uu
JOIN tweets t ON uu.tweet_id = t.tweet_id
JOIN TwitterUser u ON t.posting_user = u.screen_name
WHERE u.sub_category = 'GOP' AND t.year_posted = 2016 AND t.month_posted IN (1, 2, 3)
GROUP BY uu.url
ORDER BY num_tweets DESC, uu.url
LIMIT 10;
