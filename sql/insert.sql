
-------------------------------------------------------
--- insert using loop with increasing date and count
-------------------------------------------------------

-------------------------------------------------------
-- create test table
CREATE TABLE my_table (
    id SERIAL PRIMARY KEY,
    my_date DATE,
    data TEXT
);

TRUNCATE my_table;
SELECT * FROM my_table;

-------------------------------------------------------
-- one loop to increase date
DO $$
DECLARE
    start_date DATE := '2023-01-01';
    end_date DATE := '2024-01-10';
    cur_date DATE := start_date;
BEGIN
    WHILE cur_date <= end_date LOOP
        INSERT INTO my_table (my_date, data)
		VALUES (cur_date, 'Data for ' || cur_date);
        cur_date := cur_date + INTERVAL '1 day';
    END LOOP;
END $$;

-------------------------------------------------------
-- two loop to increase two variable
-- first loop(WHILE) increases date
-- second loop(FOR) increases count
DO $$
DECLARE
    start_date DATE := '2024-01-01';
    end_date DATE := '2024-01-10';
    cur_date DATE := start_date;
    counter INT;
BEGIN
    WHILE cur_date <= end_date LOOP
        -- insert 5 count row per day
        FOR counter IN 1..5 LOOP
            INSERT INTO my_table (my_date, data)
            VALUES (cur_date, 'Data ' || counter || ' for ' || cur_date);
        END LOOP;
        -- move next day
        cur_date := cur_date + INTERVAL '1 day';
    END LOOP;
END $$;