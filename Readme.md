This is servlet application

# TODO
1. Finish create controller, with duplicate/ without author/ with author
2. Make it restful
3. Error resistance
4. Pagination
5. Maybe change to webflux

# Doing

1. Write Tests

# DONE

1. Mark ISBN as unique
2. Prevent creation of books that overrides a previous book. Maybe introduce different endpoints
3. Investigate why searching by name is not working
4. Spring Authentication
5. Only Admin can create books and authors
6. Role base access
7. Mark creation of books as transactional
8. Users can borrow/return books
9. Users must borrow only 1 book. They have to return the book before borrowing a new one

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