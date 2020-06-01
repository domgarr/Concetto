# Initial set-up. The tabales are generated by Hibernate in concetto_db_create.sql

CREATE DATABASE concetto_dev;
CREATE DATABASE concetto_prod;

CREATE USER 'concetto_dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'concetto_prod_user'@'localhost' IDENTIFIED BY 'password';

#db grants. Set minimal amount of access.
GRANT SELECT ON concetto_dev.* to 'concetto_dev_user'@'localhost';
GRANT INSERT ON concetto_dev.* to 'concetto_dev_user'@'localhost';
GRANT UPDATE ON concetto_dev.* to 'concetto_dev_user'@'localhost';
GRANT DELETE ON concetto_dev.* to 'concetto_dev_user'@'localhost';

GRANT SELECT ON concetto_prod.* to 'concetto_prod_user'@'localhost';
GRANT INSERT ON concetto_prod.* to 'concetto_prod_user'@'localhost';
GRANT UPDATE ON concetto_prod.* to 'concetto_prod_user'@'localhost';
GRANT DELETE ON concetto_prod.* to 'concetto_prod_user'@'localhost';

