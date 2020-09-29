CREATE TABLE invitation (
  invitationid INT auto_increment,
  purpose varchar(255),
  poc varchar(255),
  visitorresponse varchar(1),
  visitorlocation varchar(255),
  epamlocation varchar(255),
  visitdatetime varchar(25),
  estimatedduration varchar(25),
  visitorid INT,
  createdbyadmin INT,
  createdate DATE,
  updatedbyadmin INT,
  updatedate DATE,
  intime varchar(25),
  outtime varchar(25),
  PRIMARY KEY (invitationid)
)