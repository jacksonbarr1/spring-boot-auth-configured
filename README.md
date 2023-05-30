
# Spring Boot Authentication Starter

Reduces time spent writing boilerplate code when starting a new Spring Boot project. 



## Features

- JWT-based authentication
- In-memory H2 database for testing
- Global exception handling
- Performs validation on registration fields


## Authors

- [@jacksonbarr2021](https://www.github.com/jacksonbarr2021)


## API Reference

#### Register user

```http
  POST /api/auth/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `registration-body` | `json` | Creates and persists new user. Request includes email, password, username[optional]. |

#### Authenticate user

```http
  POST /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `authentication-body`      | `json` | Authenticates user and returns JSON Web Token. Request includes email, password. |

#### Access H2 Console

```http
  GET /h2-console
```

