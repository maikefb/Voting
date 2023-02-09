ALTER TABLE voting_agenda
  ADD COLUMN finalize_vote DATETIME AFTER start_vote;