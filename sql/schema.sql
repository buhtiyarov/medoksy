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

DROP TABLE IF EXISTS image;
CREATE TABLE image (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       name VARCHAR,
       size INTEGER,
       content BLOB
);

DROP TABLE IF EXISTS visitImage;
CREATE TABLE visitImage (
       visitId INTEGER,
       imageId INTEGER,
       FOREIGN KEY (visitId) REFERENCES visit(id),
       FOREIGN KEY (imageId) REFERENCES image(id),
       PRIMARY KEY (visitId, imageId)
);

DROP TABLE IF EXISTS document;
CREATE TABLE document (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       name VARCHAR,
       content TEXT
);

DROP TABLE IF EXISTS clientDocument;
CREATE TABLE clientDocument (
       clientId INTEGER,
       documentId INTEGER,
       FOREIGN KEY (clientId) REFERENCES client(id),
       FOREIGN KEY (documentId) REFERENCES document(id),
       PRIMARY KEY (clientId, documentId)
);

DROP TABLE IF EXISTS clientImage;
CREATE TABLE clientImage (
       clientId INTEGER,
       imageId INTEGER,
       FOREIGN KEY (clientId) REFERENCES client(id),
       FOREIGN KEY (imageId) REFERENCES image(id),
       PRIMARY KEY (clientId, imageId)
);
