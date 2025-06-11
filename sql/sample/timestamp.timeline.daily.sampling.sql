
-- ## generate timestamp timeline for sampling test
-- create tb_exam_timestamp_timeline
DROP TABLE tb_exam_timestamp_timeline;
CREATE TABLE tb_exam_timestamp_timeline (
    timeline TIMESTAMP NOT NULL PRIMARY KEY
);

-- Create 
-- start time: '2025-05-01 00:00:00'
-- total item count: 300 (0 ~ 299)
-- interval: INTERVAL '20 minutes'
-- end time: 'start time' + (20min × 299) = 99.6 hour
INSERT INTO tb_exam_timestamp_timeline (timeline)
SELECT generate_series(
    '2025-05-01 00:00:00'::timestamp,
    '2025-05-01 00:00:00'::timestamp + INTERVAL '20 minutes' * 299,
    INTERVAL '20 minutes'
);

-- validate
SELECT COUNT(*) FROM tb_exam_timestamp_timeline;
SELECT * FROM tb_exam_timestamp_timeline ORDER BY timeline LIMIT 100;


-- ## sampling query: sampling 1 timestamp per day most closed to designated day time 
-- base_daily_points : generate criteria timstamptz timeline
WITH base_daily_points AS (
    SELECT generate_series(
        '2025-05-01 00:00:00'::timestamp AT TIME ZONE 'Asia/Seoul',
        '2025-06-01 00:00:00'::timestamp AT TIME ZONE 'Asia/Seoul',
        INTERVAL '1 day'
    ) AS target_utc_tstz
),
ranked_data AS (
    SELECT
        bdp.target_utc_tstz,
        ett.*,
        ROW_NUMBER() OVER (
            PARTITION BY bdp.target_utc_tstz
            ORDER BY ett.timeline DESC
        ) AS rn
    FROM
        base_daily_points bdp
    JOIN
        tb_exam_timestamp_timeline ett
    ON
        ett.timeline > (bdp.target_utc_tstz - make_interval(days => 1))
        AND
        ett.timeline <= bdp.target_utc_tstz
    ),
selected_daily_points AS (
    SELECT
        (target_utc_tstz AT TIME ZONE 'Asia/Seoul')::date AS "date",
        timeline
    FROM ranked_data
    WHERE
        rn = 1
)
SELECT * FROM selected_daily_points sdp;

