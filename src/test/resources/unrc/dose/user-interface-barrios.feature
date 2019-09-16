#Creadores de los escenarios: Gaspero, Maximiliano. Tobares, Valentin. Barrios, Alejandro.

Feature: user “Pponzio” is already logged on


	Scenario: A logged user want to watch the score
    	Given user wants to look his score
    	When user press show score
    	Then the system show the score on a message

    Scenario: A logged user select a type the level education,
		Given he wants to select the type of level education for play
		When the user select the type of level
		Then the system should be show a challenge depending on level selected for the user

	Scenario: A logged user select a type of challenge
		Given wants select a type of challenge for play
		When the user select a type of challenge 
		Then the system should be show one challenge based in the selection
