ALTER SESSION SET CONTAINER=GEOPDB;
CREATE USER HISHAM IDENTIFIED BY 123;
GRANT CONNECT, RESOURCE TO HISHAM;
ALTER USER HISHAM QUOTA UNLIMITED ON USERS;
exit;