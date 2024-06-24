# Todo List API

This application supply Restful APIs to create/update/delete/get task from Database.

## Technical used:
- `Database: Postgres`
- `Spring data JPA`
- `Spring Security: JWT`
- `Mapstruct`

# Run application

1. Build project: `./mvnw clean install`
2. Run application: `./mvnw spring-boot:run`


## Base URL

`http://localhost:8080/api/v0`

## Rule
- API contains keyword `public` can call without authorization

## AUTHENTICATION Endpoints

### 1. Register user

**URL:** `http://localhost:8080/api/v0/register`

**Method:** `POST`

**Request Body:**

```json
{
  "name": "test",
  "email": "nghia148052@gmail.com",
  "password": "123123",
  "phone": "123123"
}
```

**Response:** `200 OK`
```json
{
  "id": 21,
  "email": "nghia1480533@gmail.com",
  "password": "$2a$10$I8zCJnU.rBpbcbyBI80jJeTCPicWZ0Q52c5cKArM.VDyrO4BzulHG",
  "name": "test",
  "phone": "123123"
}
```

### 2. Authenticate

**URL:** `http://localhost:8080/api/v0/authenticate`

**Method:** `POST`

**Request Body:**

```json
{
  "email": "nghia1480533@gmail.com",
  "password": "123123",
  "rememberMe": false
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsImlhdCI6MTcxOTIyOTU4MSwiZXhwIjoxNzE5MjY1NTgxLCJBVVRIT1JJVFkiOiJVU0VSIn0.6T6CF3WnqX8s1koChkRSlU-9Wh1aJVuqRkVNPnV5Obc"
}
```

`Note: Use token response after login for Task endpoint`

## TASK Endpoints

### 1. Create a new task

**URL:** `http://localhost:8080/api/v0/todos`

**Method:** `POST`

**Headers:**

- `Authorization: Bearer <token>`

**Request Body:**

```json
{
  "title": "string", (requried)
  "description": "string", (requried)
  "completed": "boolean" (must be boolean value)
}
```

**Response:**
`201 Created` `Void`

### 2. Update a task by id

**URL:** `http://localhost:8080/api/v0/todos/{id}`

**Method:** `PUT`

**Headers:**

- `Authorization: Bearer <token>`

**Path Parameters:**

- `id` Id of task need to update

**Request Body:**

```json
{
  "id": "string", (optional)
  "title": "string",
  "description": "string",
  "completed": "boolean", (must be boolean value)
  "createdAt": "string"
}
```

**Response:** `200 OK`
```json
{
  "id": "668caad3-cec5-4780-a1d5-3437a5b7a0b9",
  "title": "1232",
  "description": "test2222",
  "completed": true,
  "createdAt": "2024-06-24T08:44:00.376Z"
}
```

### 3. Delete task by ID

**URL:** `http://localhost:8080/api/v0/todos/{id}`

**Method:** `DELETE`

**Headers:**

- `Authorization: Bearer <token>`

**Path Parameters:**

- `id` Id of task need to delete

**Response:** `204 No content`

### 4. Get task by task id

**URL:** `http://localhost:8080/api/v0/todos/{id}`

**Method:** `GET`

**Headers:**

- `Authorization: Bearer <token>`

**Path Parameters:**

- `id` Id of task need to get

**Response:** `200 OK`
```json
{
    "id": "668caad3-cec5-4780-a1d5-3437a5b7a0b9",
    "title": "43",
    "description": "23",
    "completed": true,
    "createdAt": "2024-06-24T08:44:00.376Z"
}
```

### 4. Get all tasks

**URL:** `http://localhost:8080/api/v0/todos`

**PUBLIC URL:** `http://localhost:8080/api/v0/public/todos`

**Method:** `GET`

**Query Parameters:** 
- `size` `(Optional)` - total task per page `(default is 20)`
- `page` `(Optional)` - the page number `(start at 0)`

**Response:** `200 OK`
```json
{
  "data": [
    {
      "id": "28ee9385-ec94-4c1b-b80a-e69fd21b043f",
      "title": "43",
      "description": "23",
      "completed": true,
      "createdAt": "2024-06-24T08:34:59.410Z"
    },
    {
      "id": "668caad3-cec5-4780-a1d5-3437a5b7a0b9",
      "title": "1232",
      "description": "test2222",
      "completed": true,
      "createdAt": "2024-06-24T08:44:00.376Z"
    },
    {
      "id": "ea76aabd-d430-49f9-b992-f9a07c6be30d",
      "title": "test delete",
      "description": "delete",
      "completed": true,
      "createdAt": "2024-06-24T11:24:44.299Z"
    }
  ],
  "pageSize": 20,
  "pageNumber": 0,
  "totalElements": 3,
  "totalPages": 1
}
```
### Errors handling

## 400 Bad Request

- When body is invalid

## 401 Unauthorized

- When request did not include token or token is invalid

## 404 Not Found

- Not found target by id
- Call an API was not handled

## 500 Internal Server Error

- Error from server