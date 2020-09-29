CREATE TABLE visitors (
  visitorsid INT auto_increment,
  adminid INT,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(50),
  password varchar(20),
  verificationemailsent varchar(1),
  primaryphnumber varchar(11),
  primarycountrycode varchar(5),
  secondaryphnumber varchar(11),
  secondarycountrycode varchar(5),
  nationalid varchar(30),
  companyname varchar(255),
  activated varchar(1),
  PRIMARY KEY (visitorsid)
)



