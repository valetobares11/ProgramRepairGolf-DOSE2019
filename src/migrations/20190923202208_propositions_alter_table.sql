ALTER TABLE propositions
ADD FOREIGN KEY(challenge_id)
REFERENCES challenges(id)