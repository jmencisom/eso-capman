ALTER TABLE videos RENAME TO videos_temp_copy;

CREATE TABLE videos (id INTEGER PRIMARY KEY AUTOINCREMENT, idAccount INTEGER REFERENCES accounts (id) ON DELETE CASCADE ON UPDATE CASCADE, idVideoSite STRING (30), idSite INTEGER REFERENCES sites (id) ON DELETE SET NULL ON UPDATE CASCADE, siteCaptions STRING (3) DEFAULT ('--'), idVideoYoutube STRING (30), youtubeCaptions STRING (3) DEFAULT ('--'), UNIQUE (idAccount, idVideoSite, idSite));

INSERT INTO videos (id, idAccount, idVideoSite, idSite, siteCaptions, idVideoYoutube, youtubeCaptions) SELECT id, idAccount, idVideoSite, idSite, siteCaptions, idVideoYoutube, youtubeCaptions FROM videos_temp_copy;

DROP TABLE videos_temp_copy;