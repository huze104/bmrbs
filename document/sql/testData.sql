USE bmrbs;
set names 'utf8';
create index INDEX_ROMM_AREAID on mrbs_room (area_id);
create index INDEX_SCHEDULE_ROOMID on MRBS_SCHEDULE(room_id);

insert into mrbs_authorities values(1,'AUTH_CHANGEAREANOTE','改变area公告');
insert into mrbs_roles values(1,'行政人员');
insert into mrbs_roles_authorities values(1,1);


INSERT INTO mrbs_area VALUES ('1', '灵鹫宫', '8976','','');
INSERT INTO mrbs_area VALUES ('2', '燕子坞', '5511/157032685','','');
INSERT INTO mrbs_area VALUES ('3', '镜台小筑', '5743/hi:some','','');

INSERT INTO mrbs_user
   (id, login_name, name, email, tel, hi, department, view_choice, status, password)
VALUES
   (1, 'wuji', '张无忌', 'wuji@some.com', '', '', '', '', 0, 'wiji');
INSERT INTO mrbs_user
   (id, login_name, name, email, tel, hi, department, view_choice, status, password)
VALUES
   (2, 'tiantian', '甜哥', 'tiange@some.com', '', '', '', '', 0, 'tiange');

update mrbs_user set assistant=1 where login_name like '%wuji%';
update mrbs_user set assistant=1 where login_name like '%tiantian%';

insert into mrbs_users_roles values(1,(select id from mrbs_user where login_name like '%tiantian%'));
insert into mrbs_users_roles values(1,(select id from mrbs_user where login_name like '%wuji%'));

INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('2', '1', '一枝春', '4356', '6', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('3', '1', '双声子', '2345', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('4', '1', '三台令', '3234', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('5', '1', '四季花', '2345', '8', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('6', '1', '五月天', '3234', '6', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('7', '1', '六么序', '2355', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('8', '1', '七里香', '4567', '12', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('9', '1', '八拍蛮', '4356', '8', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('10', '1', '九回肠', '3456', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('11', '1', '十爱词', '4366', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('12', '1', '一江春水', '4356', '12', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('13', '1', '二泉映月', '7654', '12', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('14', '1', '梅花三弄', '4367', '8', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('15', '1', 'yi', '3221/2356', '150', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('16', '1', '八节同欢', '5419', '16', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('85', '1', '九重春色', '5405', '10', '');

INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('54', '3', '一寸金', '7101', '2', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('55', '3', '双色莲', '7102', '10', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('56', '3', '三登乐', '7103', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('57', '3', '四和春', '7104', '6', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('58', '3', '五更转', '7105', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('59', '3', '六洲歌', '7106', '10', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('60', '3', '齐天乐', '7107', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('61', '3', '八音谐', '7108', '6', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('62', '3', '九张机', '7109', '8', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('63', '3', '十拍子', '7110', '10', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('64', '3', '万年欢', '7111', '16', null);

INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('106', '2', '将进酒', '6115', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('107', '2', '水云游', '6112', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('102', '2', '一剪梅', '6110', '20', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('103', '2', '西湖春', '6111', '20', null);
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('104', '2', '东湖月', '6118', '10', '');
INSERT INTO mrbs_room(id,area_id,room_name,room_admin_email,capacity,description) VALUES ('105', '2', '瑶池宴', '6113', '6', null);




