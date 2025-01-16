ALTER TABLE users ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE cards ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

UPDATE users SET created_at = CURRENT_TIMESTAMP WHERE created_at = NOW();
UPDATE cards SET created_at = CURRENT_TIMESTAMP WHERE created_at = NOW();

