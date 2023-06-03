CREATE TABLE news
(
    news_id  SERIAL                   PRIMARY KEY,
    user_id  INT            NOT NULL  REFERENCES "user",
    title    VARCHAR(50)    NOT NULL,
    content  VARCHAR(1000)  NOT NULL,
    date     TIMESTAMP      NOT NULL
);