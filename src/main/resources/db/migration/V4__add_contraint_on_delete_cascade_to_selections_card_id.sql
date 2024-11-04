-- Drop the existing foreign key constraint
ALTER TABLE selections
DROP CONSTRAINT  selections_card_id_fkey;

-- Add a new foreign key with the constraint ON DELETE CASCADE
ALTER TABLE selections
ADD CONSTRAINT  selections_card_id_fkey FOREIGN KEY (card_id)
REFERENCES cards (id)
ON DELETE CASCADE;