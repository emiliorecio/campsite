DROP TABLE IF EXISTS CALENDAR;
CREATE TABLE CALENDAR
(
    ID             BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    DATE_CAL       DATE NOT NULL,
    TOTAL_VISITORS INT  NOT NULL,
    VERSION        INT  NOT NULL,
    CHUPALA INT NOT NULL
);
DROP TABLE IF EXISTS RESERVATION;
CREATE TABLE RESERVATION
(
    ID           BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    CHECK_IN     DATE         NULL,
    CHECK_OUT    DATE         NULL,
    EMAIL        VARCHAR(255) NULL,
    NAME_VISITOR VARCHAR(255) NULL,
    TOTAL_GROUP  INT          NOT NULL
);

DROP TABLE IF EXISTS CALENDAR;
CREATE TABLE CALENDAR
(
    ID             BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    DATE_CAL       DATE NOT NULL,
    TOTAL_VISITORS INT  NOT NULL,
    VERSION        INT  NOT NULL
);
DROP TABLE IF EXISTS RESERVATION;
CREATE TABLE RESERVATION
(
    ID           BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    CHECK_IN     DATE         NULL,
    CHECK_OUT    DATE         NULL,
    EMAIL        VARCHAR(255) NULL,
    NAME_VISITOR VARCHAR(255) NULL,
    TOTAL_GROUP  INT          NOT NULL
);

