INSERT INTO team (id, team_name, team_code)
VALUES (1, 'Test Team A', 'T001'),
       (2, 'Test Team B', 'T002'),
       (3, 'Test Team C', 'T003'),
       (4, 'Test Team D', 'T004'),
       (5, 'Test Team E', 'T005'),
       (6, 'Test Team F', 'T006'),
       (7, 'Test Team G', 'T007'),
       (8, 'Test Team H ', 'T008'),
       (9, 'Test Team I', 'T009');

INSERT INTO pro (id, pro_name, team_id, birth, birth_place, org, pro_year)
VALUES (1, 'Test Pro 1', 1, '1990-01-01', 'City A', 'Test Org 1', 2023),
       (2, 'Test Pro 2', 1, '1991-02-01', 'City B', 'Test Org 2', 2022),
       (3, 'Test Pro 3', 1, '1992-03-01', 'City C', 'Test Org 3', 2021),
       (4, 'Test Pro 4', 1, '1993-04-01', 'City D', 'Test Org 4', 2020),
       (5, 'Test Pro 5', 2, '1994-05-01', 'City E', 'Test Org 5', 2019),
       (6, 'Test Pro 6', 2, '1995-06-01', 'City F', 'Test Org 1', 2019),
       (7, 'Test Pro 7', 2, '1996-07-01', 'City G', 'Test Org 2', 2019),
       (8, 'Test Pro 8', 2, '1997-08-01', 'City H', 'Test Org 3', 2019);