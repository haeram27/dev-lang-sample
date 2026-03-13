---
INSERT INTO tb_ip4_range (
    start_ip,
    end_ip
)
VALUES (
    :startIp,
    :endIp
)
ON CONFLICT (start_ip, end_ip) DO
    UPDATE SET (start_ip, end_ip) = (EXCLUDED.start_ip, EXCLUDED.end_ip)

---
INSERT INTO tb_ip4_range (
    startIp,
    endIp
)
VALUES (
    :startIp,
    :endIp
)
ON CONFLICT (start_ip, end_ip) DO NOTHING

---
INSERT INTO tb_exam AS t (
    reg_date, product_id, usage_hour, usage_duration, modified_time
)
VALUES (
    :regDate, :productId::en_product_id_type, :usageHour, :usageDuration, NOW()
)
ON CONFLICT (reg_date, product_id) DO
    UPDATE SET (usage_hour, usage_duration, modified_time)
        = (EXCLUDED.usage_hour, EXCLUDED.usage_duration, EXCLUDED.modified_time)
    WHERE t.usage_duration < EXCLUDED.usage_duration;


--- selectively update each column in UPDATE SET
INSERT INTO tb_exam AS t (
    reg_date, product_id, usage_hour, usage_duration, modified_time
)
VALUES (
    :regDate, :productId::en_product_id_type, :usageHour, :usageDuration, NOW()
)
ON CONFLICT (reg_date, product_id) DO
    UPDATE SET
        usage_hour = (SELECT GREATEST(t.usage_hour, EXCLUDED.usage_hour)),
        usage_duration = (SELECT GREATEST(t.usage_duration, EXCLUDED.usage_duration)),
        modified_time = EXCLUDED.modified_time