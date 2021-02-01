-- Note: 
-- 1. The following script cannot be executed in a real MySQL environment. It is 
--    used in the Spring Boot MySQL data initialization only.
-- 2. ^; is the separator. This property is set in the application-mysql.yml

DROP PROCEDURE IF EXISTS test_create_admin^;

CREATE PROCEDURE test_create_admin()
BEGIN
	IF EXISTS(select * from information_schema.tables where table_name = 'sys_user') = 1 THEN
		IF EXISTS(select * from sys_user where login_id = 'admin') = 0 THEN
			insert into sys_user(login_id, password, disabled, version, created_by, created_dtm, last_modified_by, last_modified_dtm)
            values('admin', '098f6bcd4621d373cade4e832627b4f6', 0, 0, 0, NOW(), 0, NOW());
		END IF;
	END IF;
END^;

CALL test_create_admin()^;

DROP PROCEDURE test_create_admin^;
