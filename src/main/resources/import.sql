CREATE TABLE IF NOT EXISTS people(
    ID          INT PRIMARY KEY,
    LAST_NAME   VARCHAR(50),
    FIRST_NAME  VARCHAR(50),
    AGE         INT,
    GENDER      CHAR(1),
    EMAIL       VARCHAR(155)
);

INSERT INTO people VALUES(1, 'Hartmann', 'Maximilian', 19, 'm', 'maximilian.hartmann@fntsoftware.com');
INSERT INTO people VALUES(2, 'Waldenmaier', 'Mike', 19, 'm', 'mike.waldenmaier@fntsoftware.com');
INSERT INTO people VALUES(3, 'Trickreich', 'Trixi', 34, 'w', '');