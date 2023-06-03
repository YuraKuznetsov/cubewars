SELECT u.username,
       COUNT(DISTINCT s.solve_id) as solves_count,
       COUNT(DISTINCT c.comment_id) as comments_count,
       COUNT(DISTINCT l.like_id) as likes_count,
       GREATEST(COALESCE(MAX(s.date), '1900-01-01'),
                COALESCE(MAX(c.date), '1900-01-01'),
                COALESCE(MAX(l.date), '1900-01-01')) as last_activity
FROM public.user u
     LEFT JOIN solve s ON u.user_id = s.user_id
     LEFT JOIN comment c ON u.user_id = c.user_id
     LEFT JOIN public.like l ON u.user_id = l.user_id
WHERE COALESCE(s.date, c.date, l.date) IS NOT NULL
GROUP BY u.username
ORDER BY solves_count DESC, comments_count DESC, likes_count DESC;