CREATE TABLE "like"
(
    like_id  SERIAL            PRIMARY KEY,
    news_id  INT     NOT NULL  REFERENCES "news",
    user_id  INT     NOT NULL  REFERENCES "user"
);