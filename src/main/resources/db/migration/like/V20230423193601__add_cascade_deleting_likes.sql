ALTER TABLE public.like
DROP CONSTRAINT like_news_id_fkey,
ADD CONSTRAINT like_news_id_fkey
FOREIGN KEY (news_id)
REFERENCES news
ON DELETE CASCADE;