Bricks Breaking
===============

[![Build Status](https://travis-ci.org/ptrstovka/bricksbreaking.svg?branch=master)](https://travis-ci.org/ptrstovka/bricksbreaking)

### How to setup?

- install postgreSQL (v10 will be good)
- create `local.properties` file
- set your local variables in this file
```
postgreUser=root
postgrePass=secret
postgreUrl=jdbc:postgresql://localhost:15432/root

postgreTestUser=root
postgreTestPass=secret
postgreTestUrl=jdbc:postgresql://localhost:15432/root
```
- execute `tables.sql` to create tables
- make the binary via `gradle install`
- run it `build/install/bricksbreaking/bin/bricksbreaking`

### Universe

This project is part of my University journey. I will be graded for it. If you are on same University as I am, do not
use this project and create your own. You can inspire, but not steal. Otherwise, do whatever you want. 🍻  


### License

MIT
