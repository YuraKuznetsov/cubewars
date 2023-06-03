CREATE TABLE "user"
(
    user_id   SERIAL        NOT NULL  PRIMARY KEY,
    email     VARCHAR(50),
    username  VARCHAR(50)   NOT NULL,
    password  VARCHAR(100)  NOT NULL,
    role_id   INT           NOT NULL  REFERENCES role
);