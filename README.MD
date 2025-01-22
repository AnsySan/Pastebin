# Pastebin

<img width="945" alt="Screenshot 2024-10-01 at 07 27 19" src="https://github.com/user-attachments/assets/5bb2d649-3501-4043-b0c6-037539b22e53">

Here are the Postman requests corresponding to the `PostController` API you've shared:

### 1. **Get Post by ID**

**GET** `/posts/{postId}`

#### Request:
```plaintext
GET http://localhost:8080/posts/{postId}
```

#### Example:
```plaintext
GET http://localhost:8080/posts/1
```

#### Response:
```json
{
  "id": 1,
  "content": "This is the content of the post.",
  "authorId": 101,
  "projectId": 202
}
```

### 2. **Get All Posts by User**

**GET** `/posts/user/{userId}`

#### Request:
```plaintext
GET http://localhost:8080/posts/user/{userId}
```

#### Example:
```plaintext
GET http://localhost:8080/posts/user/1
```

#### Response:
```json
[
  {
    "id": 1,
    "content": "First post by the user.",
    "authorId": 1,
    "projectId": 101
  },
  {
    "id": 2,
    "content": "Second post by the user.",
    "authorId": 1,
    "projectId": 102
  }
]
```

### 3. **Create a New Post**

**POST** `/posts`

#### Request:
```plaintext
POST http://localhost:8080/posts
Content-Type: application/json
```

#### Body (JSON):
```json
{
  "content": "This is a new post",
  "authorId": 1,
  "projectId": 101
}
```

#### Response:
```json
{
  "id": 3,
  "content": "This is a new post",
  "authorId": 1,
  "projectId": 101
}
```

### 4. **Update an Existing Post**

**PATCH** `/posts/{postId}`

#### Request:
```plaintext
PATCH http://localhost:8080/posts/{postId}
Content-Type: application/json
```

#### Example:
```plaintext
PATCH http://localhost:8080/posts/1
```

#### Body (JSON):
```json
{
  "content": "This is the updated content"
}
```

#### Response:
```json
{
  "id": 1,
  "content": "This is the updated content",
  "authorId": 1,
  "projectId": 101
}
```

### 5. **Delete a Post by ID**

**DELETE** `/posts/{postId}`

#### Request:
```plaintext
DELETE http://localhost:8080/posts/{postId}
```

#### Example:
```plaintext
DELETE http://localhost:8080/posts/1
```

#### Response:
```plaintext
HTTP 200 OK (No content)
```

You can directly use these queries in Postman by replacing `localhost:8080` with the actual domain or IP where your service is running, and adjust the paths and bodies as needed for your requests.