This is servlet application

# TODO
1. Make it restful
2. Error resistance
3. Maybe change to webflux

# Doing

- Add database versioning (flyway or liquidbase)
    - Task: Implement Flyway or Liquibase in the project and create migration scripts to handle schema changes in the database.

# DONE

1. Finish create controller, with duplicate/ without author/ with author
2. Mark ISBN as unique
3. Prevent creation of books that overrides a previous book. Maybe introduce different endpoints
4. Investigate why searching by name is not working
5. Spring Authentication
6. Only Admin can create books and authors
7. Role base access
8. Mark creation of books as transactional
9. Users can borrow/return books
10. Users must borrow only 1 book. They have to return the book before borrowing a new one
11. Pagination
12. User -> Book relationship
13. Task: Write a complex SQL query to generate a report, such as a list of the most popular books by genre. Include
    database indexing to optimize the query
    1. Most borrowed book
    2. Average time of borrow
    3. Book not returned
14. Statistics Table
15. Add environments variables (Dev, Staging, Production)


# Questions

1. Servlet vs Web-flux?
2. Track how all this security is working with debugging

# Notes

## Remote debugging

Don't forget to uncomment the following section in `pom.xml`

```
    <jvmArguments>
        -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
    </jvmArguments>
```

The project won't start until you attach a debugger to it

# Curl

## User

### Register

````bash
curl -iX POST \
  -H "Content-Type: application/json" \
  -d '{"username": "mark", "password": "password", "name":"mark"}' \
  http://localhost:8080/auth/register
````

### Login

````bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"username": "mark", "password": "password"}' \
  http://localhost:8080/auth/login
````

## Book

### List books (authenticated)

````bash
curl --location 'localhost:8080/book/all' \
--header 'Authorization: Bearer <token>'
````

## Author

### Create Author

```bash
curl -iX POST\
  -H "Content-Type: application/json" \
  -d '{"name": "Joan Kathlin Rowlings"}'\
  http://localhost:8080/author/create
```