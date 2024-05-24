--------------------------------------------------------------
-- 1. kill all connection excepted by me
SELECT pg_terminate_backend(pid) FROM pg_stat_activity
WHERE
-- don't kill my own connection!
pid <> pg_backend_pid()
-- don't kill the connections to other databases
AND datname = 'mydatabase';
--------------------------------------------------------------

--------------------------------------------------------------
-- DROP USER <username>
-- -> to explicit command
-- REASSIGN OWNED BY <olduser> TO <newuser>
reassign owned by testuser to postgres;
DROP OWNED BY testuser;
--------------------------------------------------------------

--------------------------------------------------------------
-- 2. user and database drop
--------------------------------------------------------------
REVOKE ALL PRIVILEGES ON DATABASE mydatabase FROM testuser;
drop user testuser;
drop database mydatabase;
--------------------------------------------------------------

--------------------------------------------------------------
-- 3. create user and database 
-- postgresql's default password ecryption method is SCRAM-SHA-256
-- so change it to md5 before create user
--------------------------------------------------------------
ALTER SYSTEM SET password_encryption = 'md5';
SELECT pg_reload_conf();
CREATE USER testuser;
ALTER USER testuser with password 'testpassword';
ALTER USER testuser WITH SUPERUSER;
CREATE DATABASE mydatabase ENCODING='UTF-8' LC_COLLATE='C' LC_CTYPE='C' template=template0; 
ALTER DATABASE mydatabase OWNER TO testuser;
GRANT ALL PRIVILEGES ON DATABASE mydatabase TO testuser;
--------------------------------------------------------------

--------------------------------------------------------------
-- 4. remove all public privilege
--------------------------------------------------------------
REVOKE ALL ON SCHEMA public FROM PUBLIC;
--------------------------------------------------------------