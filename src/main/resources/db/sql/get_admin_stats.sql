SELECT u.username as admin,
       COUNT(DISTINCT n.news_id) as news_count,
       COUNT(DISTINCT c.comment_id) as comments_count,
       COUNT(DISTINCT l.like_id) as likes_count
FROM (SELECT user_id, username
      FROM public.user u
          JOIN role r on r.role_id = u.role_id
      WHERE role_name = 'ROLE_ADMIN') u
    LEFT JOIN news n ON u.user_id = n.user_id
    LEFT JOIN comment c ON n.news_id = c.news_id
    LEFT JOIN public.like l ON n.news_id = l.news_id
GROUP BY u.username
ORDER BY news_count DESC, comments_count DESC, likes_count DESC;