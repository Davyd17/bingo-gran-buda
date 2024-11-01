-- Seeding Users
INSERT INTO users (username, password, created_at) VALUES
('player1', 'hashedpassword1', NOW()),
('player2', 'hashedpassword2', NOW());

-- Seeding Games
INSERT INTO games (status, generated_ballots) VALUES
('waiting', '{12, 34, 56, 78}'),
('in_progress', '{11, 22, 33, 44, 55}');

-- Seeding Cards
-- Assuming player1 is associated with game 1, and player2 with game 2
INSERT INTO cards (numbers, status, user_id, game_id) VALUES
('{1, 2, 3, 4, 5}', 'win', 1, 1),
('{6, 7, 8, 9, 10}', 'lost', 2, 2);

-- Seeding Selections
-- Assuming these are numbers selected on each card
INSERT INTO selections (card_id, number) VALUES
(1, 3),
(1, 4),
(2, 8),
(2, 10);