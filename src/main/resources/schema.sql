drop table if exists calendar;
create table calendar
(
    id             bigint auto_increment
        primary key,
    date_cal       date not null,
    total_visitors int  not null,
    version        int  not null
);
drop table if exists reservation;
create table reservation
(
    id           bigint auto_increment
        primary key,
    check_in     date         null,
    check_out    date         null,
    email        varchar(255) null,
    name_visitor varchar(255) null,
    total_group  int          not null
);

