insert into authors (name)
select 'Author ' || generate_series(1, 100);

INSERT INTO books (name, author_id)
WITH expanded AS (
  SELECT RANDOM() AS rand, gs AS seq, a.id AS author_id
  FROM generate_series(1, 1000) gs, authors a
), shuffled AS (
  SELECT e.*
  FROM expanded e
  INNER JOIN (
    SELECT ei.seq, MIN(ei.rand) AS min_rand FROM expanded ei GROUP BY ei.seq
  ) em ON (e.seq = em.seq AND e.rand = em.min_rand)
  ORDER BY e.seq
)
SELECT
  'Book ' || s.seq AS name,
  s.author_id
FROM shuffled s;




