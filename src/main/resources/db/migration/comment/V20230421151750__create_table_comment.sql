CREATE TABLE comment
(
    comment_id  SERIAL                   PRIMARY KEY,
    news_id     INT            NOT NULL  REFERENCES "news",
    user_id     INT            NOT NULL  REFERENCES "user",
    content     VARCHAR(200)   NOT NULL,
    date        TIMESTAMP      NOT NULL
);