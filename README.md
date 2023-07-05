# User Demo
This project demo how to register an user, edit/read/(soft) delete single or multiple user(s).

# Getting Started

## Prerequisites
- jdk 8
- maven
- Docker 20.10.14
- docker-compose 1.29.2

## Build

``` bash
# build jar file and docker images
make build
```

note: if want to rebuild the UIs, please go to the user-demo-xxx-ui directory, and run
``` bash
npm install
npm run build
```

## Run Locally
``` bash
make local-run
```

# How To Use
After running the images locally:
- for user registration, please access: http://localhost:8001
- for user admin, please access: http://localhost:8002


# REST API Definition
please refer to the swagger:
- user registation swagger: http://localhost:8099/swagger-ui.html
- User admin swagger: http://localhost:8088/swagger-ui.html

# Folder structure
* user-demo-admin - admin backend API
* user-demo-admin-ui - admin UI
* user-demo-register - user register backend API
* user-demo-register-ui  - user register UI
* user-demo-core - common module for admin and register backend
  

