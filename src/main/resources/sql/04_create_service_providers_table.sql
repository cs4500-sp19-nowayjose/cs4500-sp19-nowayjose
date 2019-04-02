DROP TABLE IF EXISTS service_providers;
CREATE TABLE service_providers (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(255) DEFAULT NULL,
  zip_code varchar(255) DEFAULT NULL,
  latestReview varchar(255) DEFAULT NULL,
  price varchar(255) DEFAULT NULL,
  introduction varchar(1000) DEFAULT NULL,
  yearsInBusiness int(11) DEFAULT 0,
  hires int(11) DEFAULT 0,
  employees int(11) DEFAULT 0,
  rating float default 0,
  backgroundChecked bit(1) default 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;