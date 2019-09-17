# Brusati, Cuesta, Fernandez, Manzetti

Feature: Basic Interaction
	Scenario Outline: A logged user takes a random test based challenge
		Given that user “Pponzio”is already logged on
		And he wants to take a test based challenge
		And the difficulty of the challenge is <difficulty>
		When the user clicks test based challenge link
		Then the system shows a random test based challenge
