CREATE SCHEMA `hehe` DEFAULT CHARACTER SET utf8mb4 ;

create user hehe_user IDENTIFIED by 'xxx';
grant select,update,delete ,insert,create,alter on hehe.* to hehe_user;
#show grants for hehe_user;

use hehe;
#用户信息
CREATE TABLE IF NOT EXISTS user(
id int(10) NOT NULL AUTO_INCREMENT,
phone varchar(16) NOT NULL,
password varchar(255) NOT NULL,
nickName varchar(16),
headImageUrl varchar(255),
PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS hot_video(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type INT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    `from` INT,
    state INT DEFAULT -1,
    insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    show_time TIMESTAMP DEFAULT '2000-01-01 00:00:01'
    );

CREATE TABLE IF NOT EXISTS hot_album(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type INT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    `from` INT,
    state INT DEFAULT -1,
    insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    show_time TIMESTAMP DEFAULT '2000-01-01 00:00:01'
    );

CREATE TABLE IF NOT EXISTS pic(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feed_id INT,
    thumb_url TEXT,
    big_url TEXT,
    description TEXT,
    insert_time TIMESTAMP DEFAULT  CURRENT_TIMESTAMP()
    );

CREATE TABLE IF NOT EXISTS blog(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feed_id INT,
    html_content TEXT,
    insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
    );

#收藏,这个表应该怎么设计
CREATE TABLE IF NOT EXISTS fav(
    id INT AUTO_INCREMENT PRIMARY KEY,
    uid int(10),
    feed_id int(10),
    type INT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    `from` INT,
    state INT DEFAULT -1,
    insert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS feedid(
    id int(10) AUTO_INCREMENT PRIMARY KEY,
    stub char(1) NOT NULL default '',
    UNIQUE KEY stub(stub)
);

#CREATE INDEX type_title_index ON hot_video(type,title);
#CREATE INDEX type_title_index ON hot_album(type,title);
CREATE INDEX pic_feedid_index ON pic(feed_id);
CREATE INDEX fav_userid_index ON fav(uid,feed_id);
