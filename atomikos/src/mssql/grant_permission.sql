USE [master]
GO
CREATE USER [sa1] FOR LOGIN [sa1] WITH DEFAULT_SCHEMA=[dbo]
USE [master]
GO
EXEC sp_addrolemember [SqlJDBCXAUser], 'sa1'