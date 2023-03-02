# Squad Management API

## Tech Stack

- Java 17
- Spring Boot 3.0.3
- PostgreSQL

## Running the project locally

Run this project locally with the following command (make sure you are running the Docker agent first):
```
docker-compose up -d --build
```

## Postman Collection for testing
You can find Postman Collection for testing this application on the root folder of this project with the name: </br>
<b>[Management API.postman_collection.json](https://github.com/eliasnepo/squad-management-api/blob/main/Management%20API.postman_collection.json)</b>

## Design structure
I divided each module from this system into a specific package (e.g., teams, users, membership, etc.). We'll find different services per responsibility for each module, giving us a more maintainable code. See an example down below:

![Project structure](https://github.com/eliasnepo/squad-management-api/blob/main/docs/project-structure.png)

## Database diagram and decisions
Regarding database modeling, we have a user entity that can be part of 0-N teams. In addition, we have a role entity associated with a membership (i.e., a user on a project), which means that a user can have different roles on different projects but only one role per project. We also have a Team entity that contains a foreign key to User entity (team_lead_id). I opted to use team lead as a foreign key instead of a role because anyone can be a leader from a project, and that doesn't necessarily means that the leader can not be a developer, product owner, etc. See the diagram down below:

![Database diagram](https://github.com/eliasnepo/squad-management-api/blob/main/docs/db-diagram.png)

## Testing
This repository follows the 'test slice' approach, where our services are tested until the database without loading the entire Spring Container through the annotation @DataJpaTest. Our Rest APIs test will use @WebMvcTest so we don't need to load the persistence layer. We mock the services in our API testing cause we just want to ensure that everything is working correctly at infrastructure layer.
Rest API tests:
  - Testing if we're mapping url/verb correctly
  - Testing if we're properly converting objects to/from the services and doing basic validations with Jakarta
  - Testing if we're cordinating to the right services

Service tests:
  - Testing the whole behavior until hitting the database
  - Testing persistence

## Suggestions and points of improvements:
  - Stop using 'Developer' as the default role, it doesn't make sense to use a default role and we should pass when assigning a user to a team;
  - Make custom annotation that already make the constraint validations at DTO, so we don't need to do a insert operation and be blocked by a constraint database.
