PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: capman
CREATE TABLE capman (dataName STRING (100) PRIMARY KEY, dataValue STRING (200));
INSERT INTO capman (dataName, dataValue) VALUES ('youtubeVideoBaseURL', 'https://www.youtube.com/watch?v=');
INSERT INTO capman (dataName, dataValue) VALUES ('version', 0);

-- Table: accounts
CREATE TABLE accounts (id INTEGER PRIMARY KEY AUTOINCREMENT, email STRING (50) UNIQUE NOT NULL);

-- Table: sites
CREATE TABLE sites (id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING (50), videoBaseURL STRING (300), captionsBaseURL STRING (300));
INSERT INTO sites (id, name, videoBaseURL, captionsBaseURL) VALUES (1, 'ESO', 'https://www.eso.org/public/videos/', 'https://www.eso.org/public/archives/videos/srt/');

-- Table: videos
CREATE TABLE videos (id INTEGER PRIMARY KEY AUTOINCREMENT, idAccount INTEGER REFERENCES accounts (id) ON DELETE CASCADE ON UPDATE CASCADE, idVideoSite STRING (30), idSite INTEGER REFERENCES sites (id) ON DELETE SET NULL ON UPDATE CASCADE, siteCaptions STRING (3) DEFAULT ('--'), idVideoYoutube STRING (30), youtubeCaptions STRING (3) DEFAULT ('--'), UNIQUE (idVideoSite, idSite));

INSERT INTO accounts(id, "email") VALUES (0, "AUTO_INCREMENT");
DELETE FROM accounts WHERE id = 0;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

