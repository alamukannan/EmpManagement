# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE ems_dev;

#Create database service accounts
CREATE USER 'ems_dev_user'@'localhost' IDENTIFIED BY 'emsv1';
CREATE USER 'ems_dev_user'@'%' IDENTIFIED BY 'emsv1';

#Database grants
GRANT SELECT ON ems_dev.* to 'ems_dev_user'@'localhost';
GRANT INSERT ON ems_dev.* to 'ems_dev_user'@'localhost';
GRANT DELETE ON ems_dev.* to 'ems_dev_user'@'localhost';
GRANT UPDATE ON ems_dev.* to 'ems_dev_user'@'localhost';
GRANT SELECT ON ems_dev.* to 'ems_dev_user'@'%';
GRANT INSERT ON ems_dev.* to 'ems_dev_user'@'%';
GRANT DELETE ON ems_dev.* to 'ems_dev_user'@'%';
GRANT UPDATE ON ems_dev.* to 'ems_dev_user'@'%';
