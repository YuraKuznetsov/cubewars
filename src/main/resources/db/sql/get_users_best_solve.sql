SELECT u.username,
       s.time AS best_time,
       stat.status_name AS status,
       s.date
FROM "user" u
     JOIN (SELECT user_id,
                  MIN(EXTRACT(epoch FROM (time::time))) AS best_solve_time
           FROM solve
                JOIN discipline d ON solve.discipline_id = d.discipline_id
                JOIN status stat ON solve.status_id = stat.status_id
           WHERE stat.status_name != 'DNF' AND
                 d.discipline_name = :discipline
           GROUP BY user_id) bs ON u.user_id = bs.user_id
     JOIN solve s ON bs.user_id = s.user_id AND
                     bs.best_solve_time = EXTRACT(epoch FROM (s.time::time))
     JOIN status stat ON s.status_id = stat.status_id
ORDER BY s.time;