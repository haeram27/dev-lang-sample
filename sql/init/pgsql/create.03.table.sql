SET client_encoding = 'UTF8';

------------------------------------------------------------------------------------------
-- CREATE TABLE
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
select '## tb_product_status_hourly_timeline';
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS tb_product_status_hourly_timeline () TABLESPACE cpp_olap;
    COMMENT ON TABLE tb_product_status_hourly_timeline IS 'product status hourly timeline';

    ALTER TABLE IF EXISTS tb_product_status_hourly_timeline ADD COLUMN IF NOT EXISTS product_id bigint;
    ALTER TABLE IF EXISTS tb_product_status_hourly_timeline ADD COLUMN IF NOT EXISTS reg_date text;
    ALTER TABLE IF EXISTS tb_product_status_hourly_timeline ADD COLUMN IF NOT EXISTS connection_status en_connection_status;
    ALTER TABLE IF EXISTS tb_product_status_hourly_timeline ADD COLUMN IF NOT EXISTS is_dangerous boolean;

    COMMENT ON COLUMN tb_product_status_hourly_timeline.product_id IS 'product id';
    COMMENT ON COLUMN tb_product_status_hourly_timeline.reg_date IS 'registered date';
    COMMENT ON COLUMN tb_product_status_hourly_timeline.connection_status IS 'product connection status';
    COMMENT ON COLUMN tb_product_status_hourly_timeline.is_dangerous IS 'is dangerous status';

    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_product_status_hourly_timeline' AND constraint_type = 'PRIMARY KEY') THEN
        ALTER TABLE tb_product_status_hourly_timeline ADD CONSTRAINT tb_product_status_hourly_timeline_pkey PRIMARY KEY (product_id, reg_date);
    END IF;


END $$ LANGUAGE 'plpgsql';

------------------------------------------------------------------------------------------
select '## tb_product_usage_daily_timeline';
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS tb_product_usage_daily_timeline ();
    COMMENT ON TABLE tb_product_usage_daily_timeline IS 'product usage daily timeline';

    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS reg_date date NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS product_id bigint NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS product_id en_product_id_type NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS duration int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS time_record text NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS usage_hour int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS usage_duration int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS is_use boolean NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS tz_offset integer NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS modified_time timestamp NOT NULL DEFAULT now();

    COMMENT ON COLUMN tb_product_usage_daily_timeline.reg_date IS 'registered date';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.product_id IS 'node ID';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.product_id IS 'product id';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.duration IS 'unit of measurement time(min)';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.time_record IS 'usage time record';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.usage_hour IS 'usage with unit hour';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.usage_duration IS 'usage with unit duration';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.is_use IS 'is use';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.tz_offset IS 'timezone offset';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.modified_time IS 'modified time';

    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_product_usage_daily_timeline' AND constraint_type = 'PRIMARY KEY') THEN
        ALTER TABLE IF EXISTS ONLY tb_product_usage_daily_timeline ADD CONSTRAINT tb_product_usage_daily_timeline_pkey PRIMARY KEY (reg_date, product_id, product_id);
    END IF;

END $$ LANGUAGE 'plpgsql';
