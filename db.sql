-- 上链信息
create table invoke_info
(
    id             bigint(20) primary key auto_increment,
    create_time    timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status         tinyint(1) unsigned default 0,
    comment        varchar(128)        default '',
    operation      varchar(64)         default '' comment '操作名称',
    function_name  varchar(64)         default '' comment '方法名称',
    content        text(1024) comment '上链信息',
    transaction_id varchar(128)        default '',
    succeed        tinyint(1) unsigned default 1 comment '1:操作成功 0:操作失败'
);

-- 查询信息
create table query_info
(
    id            bigint(20) primary key auto_increment,
    create_time   timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status        tinyint(1) unsigned default 0,
    comment       varchar(128)        default '',
    operation     varchar(64)         default '' comment '操作名称',
    function_name varchar(64)         default '' comment '方法名称',
    query_param   text(1024) comment '上链信息',
    succeed       tinyint(1) unsigned default 1 comment '1:操作成功 0:操作失败'
);

create table config
(
    id            bigint(20) primary key auto_increment,
    create_time   timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status        tinyint(1) unsigned default 0,
    comment       varchar(128)        default '',
    peer_name     varchar(128)        default '',
    peer_url      varchar(128)        default '',
    channel       varchar(64)         default '',
    user_name     varchar(64)         default '',
    user_password varchar(64)         default '',
    server_host   varchar(64)         default ''
);



alter table invoke_info
    add
        (
        peer_node varchar(64) default '' comment 'peer node',
        peer_url varchar(64) default '' comment 'peer url',
        order_node varchar(64) default '' comment 'order node',
        order_url varchar(64) default '' comment 'order url',
        hash varchar(128) default '' comment 'hash值'
        )
;



alter table query_info
    add
        (
        peer_node varchar(64) default '' comment 'peer node',
        peer_url varchar(64) default '' comment 'peer url',
        order_node varchar(64) default '' comment 'order node',
        order_url varchar(64) default '' comment 'order url',
        hash varchar(128) default '' comment 'hash值'
        )
;
alter table query_info
    drop hash;


alter table invoke_info
    add
        (
        param text(1024)
        )
;


