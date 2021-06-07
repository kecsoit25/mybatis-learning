create database mybatis;
CREATE USER mybatis@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON mybatis.* TO mybatis@localhost;
ALTER DATABASE `mybatis` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;



