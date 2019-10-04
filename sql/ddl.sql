create database kakao CHARACTER SET utf8;

CREATE USER 'kakao'@'localhost' IDENTIFIED BY 'kakao';
CREATE USER 'kakao'@'%' IDENTIFIED BY 'kakao';

GRANT ALL PRIVILEGES ON kakao.* TO 'kakao'@'localhost';
GRANT ALL PRIVILEGES ON kakao.* TO 'kakao'@'%';

use kakao;

drop table if exists government;
drop table if exists region;

create table government (
                          id bigint not null auto_increment,
                          code varchar(255) unique ,
                          institute varchar(255),
                          limited bigint,
                          limited_word varchar(255),
                          mgmt varchar(255),
                          purpose varchar(255),
                          rate_avg double precision,
                          rate_max double precision,
                          rate_min double precision,
                          reception varchar(255),
                          reg_dt datetime,
                          region varchar(255),
                          target varchar(255),
                          upd_dt datetime,
                          primary key (id)
) engine=InnoDB;

create table region (
                      code bigint not null,
                      primary key (code)
) engine=InnoDB;