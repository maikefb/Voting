ALTER TABLE voting_agenda
  ADD COLUMN voting_time INTEGER NOT NULL DEFAULT 1 AFTER description;