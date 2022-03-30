DROP TABLE IF EXISTS user_sector;
CREATE TABLE user_sector
(
    id                    INT          NOT NULL AUTO_INCREMENT,
    name                  VARCHAR(255) NOT NULL,
    is_agreement_accepted boolean      NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sector;
CREATE TABLE sector
(
    id    INT          NOT NULL AUTO_INCREMENT,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sector_to_sector;
CREATE TABLE sector_to_sector
(
    id        INT NOT NULL AUTO_INCREMENT,
    parent_id INT NOT NULL,
    child_id  INT NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_to_sector;
CREATE TABLE user_to_sector
(
    id        INT NOT NULL AUTO_INCREMENT,
    user_id   INT NOT NULL,
    sector_id INT NOT NULL,
    PRIMARY KEY (id)
);