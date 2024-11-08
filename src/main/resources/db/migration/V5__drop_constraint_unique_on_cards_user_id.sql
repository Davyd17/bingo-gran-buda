-- Drop the unique constraint to change the one-to-one relationship to a one-to-many relationship on cards.user_id
ALTER TABLE cards
DROP CONSTRAINT cards_user_id_key;

