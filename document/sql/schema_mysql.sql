drop database if exists bmrbs;
create database bmrbs DEFAULT CHARACTER SET utf8;
use bmrbs;
set names 'utf8';



/*==============================================================*/
/* Table: mrbs_area                                             */
/*==============================================================*/
create table mrbs_area
(
   id                   int(11) not null auto_increment comment '主键',
   area_name            varchar(30) default '' comment '名称',
   linkman              varchar(255) comment '管理员联系方式',
   descn                text,
   shortdescn           text,
   primary key (id)
)
comment = "区域"
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_authorities                                      */
/*==============================================================*/
create table mrbs_authorities
(
   id                   integer not null auto_increment,
   name                 varchar(64) not null,
   display_name         varchar(64) not null,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_pod                                              */
/*==============================================================*/
create table mrbs_pod
(
   id                   integer not null auto_increment,
   name                 varchar(255) not null,
   component_class      varchar(255) not null,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_roles                                            */
/*==============================================================*/
create table mrbs_roles
(
   id                   integer not null auto_increment,
   name                 varchar(64) not null,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_roles_authorities                                */
/*==============================================================*/
create table mrbs_roles_authorities
(
   role_id              integer not null,
   authority_id         integer not null,
   constraint FK_Reference_4 foreign key (role_id)
      references mrbs_roles (id),
   constraint FK_Reference_5 foreign key (authority_id)
      references mrbs_authorities (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_room                                             */
/*==============================================================*/
create table mrbs_room
(
   id                   int(11) not null auto_increment,
   area_id              int(11) default NULL comment '区域',
   room_name            varchar(255) default NULL comment '名称',
   description          varchar(255) default NULL comment '简介',
   capacity             int(11) default 0 comment '容纳人数',
   room_admin_email     varchar(255) comment '联系方式',
   status               varchar(32),
   sortIndex            varchar(32),
   virtualMap           varchar(255),
   primary key (id),
   constraint FK_Reference_1 foreign key (area_id)
      references mrbs_area (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_user                                             */
/*==============================================================*/
create table mrbs_user
(
   id                   int not null auto_increment comment '主键',
   login_name           varchar(32) default ' ' comment '登陆名',
   name                 varchar(32) comment '名字',
   email                varchar(32) comment 'email',
   tel                  varchar(32) comment '分机',
   hi                   varchar(32) comment '百度hi',
   department           varchar(64) comment '部门',
   view_choice          varchar(32),
   status               int default 0,
   password             varchar(32),
   assistant            int default 0,
   primary key (id)
)
comment = "用户信息";

/*==============================================================*/
/* Table: mrbs_user_pod                                         */
/*==============================================================*/
create table mrbs_user_pod
(
   pod_id               integer not null,
   id                   int comment '主键',
   constraint FK_Reference_6 foreign key (id)
      references mrbs_user (id),
   constraint FK_Reference_7 foreign key (pod_id)
      references mrbs_pod (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_users_roles                                      */
/*==============================================================*/
create table mrbs_users_roles
(
   role_id              integer not null,
   id                   int comment '主键',
   constraint FK_Reference_2 foreign key (role_id)
      references mrbs_roles (id),
   constraint FK_Reference_3 foreign key (id)
      references mrbs_user (id)
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_repeat                                           */
/*==============================================================*/
create table mrbs_repeat
(
   id                   int(11) not null auto_increment,
   start_hour           varchar(2) not null comment '开始时间,以小时计',
   end_hour             varchar(2) not null comment '结束时间',
   start_mini           varchar(2) default '00' comment '开始分钟',
   end_mini             varchar(2) not null default '00' comment '结束分钟',
   room_id              int(11) not null comment '会议室',
   updatetime           timestamp not null,
   create_by            varchar(80) not null default '' comment '添加者',
   orderman             varchar(32) not null default '' comment '预定者,存个名字而已',
   rep_opt              int not null default 0 comment '重复情况',
   description          text comment '会议室描述',
   start_date           datetime default NULL comment '开始日期',
   end_date             datetime comment '结束日期',
   allday               int default 0 comment '是否整天',
   repeat_week_day      int default 1 comment '重复星期',
   week_span            int default 0 comment '隔开几周',
   repeat_day           int default 1 comment '重复日',
   primary key (id),
   constraint FK_fk_repeat_roo foreign key (room_id)
      references mrbs_room (id)
)
comment = "会议预定规则"
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_schedule                                         */
/*==============================================================*/
create table mrbs_schedule
(
   id                   int(11) not null auto_increment,
   start_time           datetime not null comment '开始时间',
   end_time             datetime not null comment '结束时间',
   room_id              int(11) not null comment '会议室',
   create_by            varchar(80) not null default '' comment '添加者',
   preside              varchar(80) not null default '' comment '预定者，预定者主持会议',
   repeat_id            int not null comment '对应规则',
   description          text not null comment '会议室描述',
   num                  int not null default 0 comment '与会人数',
   title                varchar(255),
   primary key (id),
   constraint FK_fk_schedule_repeat foreign key (repeat_id)
      references mrbs_repeat (id) on delete restrict on update restrict,
   constraint FK_fk_schedule_room foreign key (room_id)
      references mrbs_room (id) on delete restrict on update restrict
)
ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* Table: mrbs_schedule_user                                    */
/*==============================================================*/
create table mrbs_schedule_user
(
   userid               int,
   scheduleid           int,
   constraint FK_shedule_key foreign key (scheduleid)
      references mrbs_schedule (id) on delete restrict on update restrict,
   constraint FK_user_key foreign key (userid)
      references mrbs_user (id) on delete restrict on update restrict
)
comment = "参加会议的用户";

