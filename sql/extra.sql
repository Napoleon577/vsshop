create table vss_code
(
	uuid varchar(50) not null
		primary key,
	code varchar(50) null,
	down_url varchar(255) null comment 'down_url，下载地址',
	file_path varchar(255) null comment '下载的文件本地地址',
	status varchar(255) null comment '1:未使用，2已使用，3作废',
	ip varchar(20) null,
	name varchar(20) null,
	tel varchar(20) null,
	wx varchar(20) null,
	email varchar(20) null,
	source varchar(1) null comment '1、csdn',
	insert_time datetime null,
	used_time datetime null,
	update_time datetime null
)
engine=MyISAM
;

create table vss_user
(
	uuid varchar(50) not null
		primary key,
	source varchar(1) null comment '1.csdn',
	username varchar(50) null,
	password varchar(50) null,
	cookie tinytext null,
	type int(2) null comment '0,normal,1,vip',
	login_status varchar(1) null comment '1登录成功，2登录失败',
	status varchar(1) null comment '1，正常，2禁用',
	last_login_time datetime null,
	insert_time datetime null
)
engine=MyISAM
;

