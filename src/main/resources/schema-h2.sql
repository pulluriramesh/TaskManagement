DROP TABLE IF EXISTS Tasks;

CREATE TABLE Tasks(
id bigint NOT NULL,
title varchar(100),
description varchar(500),
date date NOT NULL,
completed_date date NULL,
status varchar(100),
userid int,
progress int,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
id bigint NOT NULL,
firstName varchar(100),
lastName varchar(100),
PRIMARY KEY (id)
);

INSERT INTO Users (id,firstName,lastName ) VALUES (1,'Ramesh', 'Pulluri');
INSERT INTO Users (id,firstName,lastName ) VALUES (2,'Sree', 'Vanaparthi');
INSERT INTO Users (id,firstName,lastName ) VALUES (3,'Jyostna', 'Jadala');

