CREATE TABLE solve
(
    solve_id       SERIAL                  PRIMARY KEY,
    user_id        INT           NOT NULL  REFERENCES "user",
    time           VARCHAR(15)   NOT NULL,
    status_id      INT           NOT NULL  REFERENCES status,
    discipline_id  INT           NOT NULL  REFERENCES discipline,
    scramble       VARCHAR(500)  NOT NULL,
    date           TIMESTAMP     NOT NULL
);