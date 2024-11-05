-- Drop the existing foreign key constraint
ALTER TABLE cards
DROP CONSTRAINT cards_user_id_fkey;

-- Add a new foreign key with the constraint ON DELETE CASCADE
ALTER TABLE cards
ADD CONSTRAINT cards_user_id_fkey FOREIGN KEY (user_id)
REFERENCES users (id)
ON DELETE CASCADE;

