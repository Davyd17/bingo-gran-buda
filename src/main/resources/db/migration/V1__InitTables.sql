CREATE TYPE "game_status" AS ENUM (
  'waiting',
  'in_progress',
  'completed'
);

CREATE TYPE "card_status" AS ENUM (
  'win',
  'lost',
  'disqualified',
  'undefined'
);

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "username" VARHCAR(50) UNIQUE NOT NULL,
  "password" VARCHAR(100) NOT NULL,
  "created_at" TIMESTAMP DEFAULT NOW()
);

CREATE TABLE "games" (
  "id" SERIAL PRIMARY KEY,
  "status" game_status NOT NULL,
  "generated_ballots" INT[] DEFAULT '{}'
);

CREATE TABLE "cards" (
  "id" SERIAL PRIMARY KEY,
  "numbers" INT[] NOT NULL,
  "status" card_status NOT NULL,
  "user_id" INTEGER UNIQUE NOT NULL REFERENCES users (id),
  "game_id" INTEGER NOT NULL REFERENCES games (id) ON DELETE NULL
);

CREATE TABLE "selections" (
  "id" SERIAL PRIMARY KEY,
  "number" INT NOT NULL,
  "card_id" INTEGER NOT NULL REFERENCES cards (id)
);
