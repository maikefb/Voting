ALTER TABLE voting_agenda
  ADD COLUMN was_counted BOOLEAN NOT NULL DEFAULT FALSE AFTER finalize_vote;