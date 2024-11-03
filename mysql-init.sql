CREATE DATABASE all_in;
CREATE USER 'appuser'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON all_in.* TO 'appuser'@'%';
