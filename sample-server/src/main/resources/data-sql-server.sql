IF (OBJECT_ID('sys_user', 'U') IS NOT NULL) AND NOT EXISTS (SELECT * FROM sys_user WHERE login_id = 'admin') 
BEGIN
   insert into sys_user(login_id, password, disabled, version, created_by, created_dtm, last_modified_by, last_modified_dtm) 
   values('admin', '098f6bcd4621d373cade4e832627b4f6', 0, 0, 0, GETDATE(), 0, GETDATE())
END;