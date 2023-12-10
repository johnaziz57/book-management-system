This is servlet application

# TODO

1. Mark creation of books as transactional
2. Only Admin can create books and authors
3. Users can borrow/return books
4. Users must borrow only 1 book. They have to return the book before borrowing a new one
5. Finish create controller, with duplicate/ without author/ with author
6. Make it restful
7. Error resistance
8. Spring Authentication
9. Role base access
10. Pagination
11. Maybe change to webflux

# DONE

1. Mark ISBN as unique
2. Prevent creation of books that overrides a previous book. Maybe introduce different endpoints
3. Investigate why searching by name is not working

# Questions

1. Servlet vs Web-flux?


# Curl
## Register
````bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"username": "mark", "password": "password", "name":"mark"}' \
  http://localhost:8080/auth/register
````

## Login

````bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"username": "mark", "password": "password"}' \
  http://localhost:8080/auth/login
````

## List books
````bash
curl -X GET \
  -H "Content-Type: application/json" \
  http://localhost:8080/book/all

````