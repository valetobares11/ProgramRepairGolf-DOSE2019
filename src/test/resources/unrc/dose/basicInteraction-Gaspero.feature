#Creadores de los escenarios: Gaspero, Maximiliano. Tobares, Valentin. Barrios, Alejandro.

Feature: user “Pponzio” is already logged on

	Scenario: A logged user want to change the password
		Given he wants to change his password from his account
		When press the button change password
		Then the system redirect to page change password 

	Scenario: A logged user want to change the email
		Given that he wants change his email
		When press the button change email
		Then the system redirect to page change email

