ALTER TABLE comment
DROP CONSTRAINT comment_news_id_fkey,
ADD CONSTRAINT comment_news_id_fkey
FOREIGN KEY (news_id)
REFERENCES news
ON DELETE CASCADE;