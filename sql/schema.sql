DROP TABLE IF EXISTS client;
CREATE TABLE client (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       name VARCHAR,
       address VARCHAR,
       birthday VARCHAR,
       phone VARCHAR,
       notes TEXT,
       created DATETIME
);

DROP TABLE IF EXISTS visit;
CREATE TABLE visit (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       clientId INTEGER,
       diagnosisId INTEGER,
       description TEXT,
       created DATETIME,
       FOREIGN KEY (clientId) REFERENCES client(id),
       FOREIGN KEY (diagnosisId) REFERENCES diagnosis(id)
);
CREATE INDEX clientId ON visit (clientId);

DROP TABLE IF EXISTS diagnosis;
CREATE TABLE diagnosis (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       name VARCHAR,
       template TEXT
);
