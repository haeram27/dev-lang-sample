--
INSERT INTO tb_exam AS t (
    reg_date, product_id, usage_hour, usage_duration, modified_time
) VALUES (
    :regDate, :productId::en_product_id_type, :usageHour, :usageDuration, NOW()
) ON CONFLICT (reg_date, product_id) DO
    UPDATE SET (usage_hour, usage_duration, modified_time)
            = (EXCLUDED.usage_hour, EXCLUDED.usage_duration, EXCLUDED.modified_time)
    WHERE t.usage_duration < EXCLUDED.usage_duration;


-- selectively update each column in UPDATE SET
INSERT INTO tb_exam AS t (
    reg_date, product_id, usage_hour, usage_duration, modified_time
) VALUES (
    :regDate, :productId::en_product_id_type, :usageHour, :usageDuration, NOW()
) ON CONFLICT (reg_date, product_id) DO
    UPDATE SET
        usage_hour = (SELECT GREATEST(t.usage_hour, EXCLUDED.usage_hour)),
        usage_duration = (SELECT GREATEST(t.usage_duration, EXCLUDED.usage_duration)),
        modified_time = EXCLUDED.modified_time