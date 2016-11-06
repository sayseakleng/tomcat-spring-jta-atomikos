CREATE TABLE [dbo].[account_otp] (
	[otp] [varchar](100) NULL,
	[issued_date_time] [datetime] NULL,
	[is_valid] [smallint] NULL,
	[account_id] [bigint] NULL,
	[account_number] [varchar](250) NULL,
	[opt_id] [int] IDENTITY(1,1) NOT NULL
);

CREATE TABLE [dbo].[USERS](
	[USER_NAME] [varchar](50) NULL,
	[PASSWORD] [varchar](50) NULL
);