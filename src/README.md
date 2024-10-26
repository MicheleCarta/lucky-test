## Users-Service CRUD




### Test and Build
from the root of the project run the script

```
./build.sh
```

### Start Containers


```
docker-compose up build
```

### Run the app
from the root of the project run

```
./gradlew bootRun
```
Tomcat listen port: 3000

### test app http://localhost:3000/api/users POST

To get error from invalid email and age < 18 use the json:
```json
{
  "email": "some_test",
  "firstName": "name",
  "lastName": "surname",
  "dob": "2020-12-03"
}
```
Response
```json
{
  "fieldErrors": {
    "dob": "User must be at least 18 years old",
    "email": "Email must be valid"
  },
  "error": "Validation failed",
  "status": 400
}
```

Ok general Response
```json
{
  "userId": 1,
  "user": {
    "email": "mix@test.com",
    "firstName": "name2",
    "lastName": "surname2",
    "dob": "1980-12-03"
  },
  "age": 43
}
```

### database 

```
jdbc:postgresql://localhost:3259/users
```



