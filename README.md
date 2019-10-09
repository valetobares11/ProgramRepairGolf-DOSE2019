Welcome to ProgramRepairGolf
============================

This project ...

Getting Started
---------------

1. Fork
2. Cloning
3. DB Setup
4. Correr el servidor web

#### Fork
press the button in GitHub page

#### Clon
use the following command
```
git clone git@github.com:<YOUR-USER>/triviavet.git
```

#### DB Setup

To create table
```
mvn db-migrator:create
```

```
cp {basedir}/src/main/resources/database.properties.example {basedir}/src/main/resources/database.properties
```
and use your own secret credentials

#### Correr el servidor web
Use `./run.sh` script

#### Run linter before push any code
```
mvn checkstyle:check
```

#### Curl How to 

* List Users
```
curl http://localhost:4567/users
```

* List User
```
curl http://localhost:4567/users/1
```

* Create User
```
curl -X POST http://localhost:4567/users \
  -H 'content-type: application/json' \
  -d '{"firstName":"John", "lastName": "Doe", email: "john@doe.com"}'
```

* Update User
```
curl -X PUT http://localhost:4567/users/1 \
  -H 'content-type: application/json' \
  -d '{"firstName":"Michael"}'
```

# Licence

This project is licensed under the MIT License - see the LICENSE.md file for details.
