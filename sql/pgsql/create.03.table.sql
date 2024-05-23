SET client_encoding = 'UTF8';

------------------------------------------------------------------------------------------
-- CREATE TABLE
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
select '## tb_node_status_hourly_timeline';
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS tb_node_status_hourly_timeline () TABLESPACE cpp_olap;
    COMMENT ON TABLE tb_node_status_hourly_timeline IS '노드 현황 시간별 타임라인';

    ALTER TABLE IF EXISTS tb_node_status_hourly_timeline ADD COLUMN IF NOT EXISTS node_id bigint;
    ALTER TABLE IF EXISTS tb_node_status_hourly_timeline ADD COLUMN IF NOT EXISTS reg_date text;
    ALTER TABLE IF EXISTS tb_node_status_hourly_timeline ADD COLUMN IF NOT EXISTS connection_status en_connection_status;
    ALTER TABLE IF EXISTS tb_node_status_hourly_timeline ADD COLUMN IF NOT EXISTS is_dangerous boolean;

    COMMENT ON COLUMN tb_node_status_hourly_timeline.node_id IS '노드 ID';
    COMMENT ON COLUMN tb_node_status_hourly_timeline.reg_date IS '등록 일자';
    COMMENT ON COLUMN tb_node_status_hourly_timeline.connection_status IS '노드 연결 상태';
    COMMENT ON COLUMN tb_node_status_hourly_timeline.is_dangerous IS '위험 여부';

    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_node_status_hourly_timeline' AND constraint_type = 'PRIMARY KEY') THEN
        ALTER TABLE tb_node_status_hourly_timeline ADD CONSTRAINT tb_node_status_hourly_timeline_pkey PRIMARY KEY (node_id, reg_date);
    END IF;


END $$ LANGUAGE 'plpgsql';

-----------------------------------------------------------------------------------------
select '## tb_product_tag';
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS tb_product_tag ();
    COMMENT ON TABLE public.tb_product_tag IS '태그 정보';

    ALTER TABLE IF EXISTS tb_product_tag ADD COLUMN IF NOT EXISTS tag_id bigserial NOT NULL;
    ALTER TABLE IF EXISTS tb_product_tag ADD COLUMN IF NOT EXISTS tag_name text NULL;
    ALTER TABLE IF EXISTS tb_product_tag ADD COLUMN IF NOT EXISTS tag_color text NULL;
    ALTER TABLE IF EXISTS tb_product_tag ADD COLUMN IF NOT EXISTS created_time timestamp;
    ALTER TABLE IF EXISTS tb_product_tag ADD COLUMN IF NOT EXISTS modified_time timestamp;
    
    COMMENT ON COLUMN public.tb_product_tag.tag_id IS '태그 아이디';
    COMMENT ON COLUMN public.tb_product_tag.tag_name IS '태그 이름';
    COMMENT ON COLUMN public.tb_product_tag.tag_color IS '태그 색상';
    COMMENT ON COLUMN public.tb_product_tag.created_time IS '생성일시';
    COMMENT ON COLUMN public.tb_product_tag.modified_time IS '수정일시';
    
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_product_tag' AND constraint_type = 'PRIMARY KEY') THEN
        ALTER TABLE tb_product_tag ADD CONSTRAINT tb_product_tag_pkey PRIMARY KEY (tag_id);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_product_tag' and constraint_name = 'tb_product_tag_un' AND constraint_type = 'UNIQUE') THEN
        ALTER TABLE IF EXISTS ONLY tb_product_tag ADD CONSTRAINT tb_product_tag_un UNIQUE (tag_name);
    END IF;

END $$ LANGUAGE 'plpgsql';

------------------------------------------------------------------------------------------
select '## tb_product_usage_daily_timeline';
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS tb_product_usage_daily_timeline ();
    COMMENT ON TABLE tb_product_usage_daily_timeline IS '날짜별 제품 사용량 타임라인';

    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS reg_date date NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS node_id bigint NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS product_id en_product_id_type NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS duration int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS time_record text NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS usage_hour int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS usage_duration int4 NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS is_use boolean NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS tz_offset integer NOT NULL;
    ALTER TABLE IF EXISTS tb_product_usage_daily_timeline ADD COLUMN IF NOT EXISTS modified_time timestamp NOT NULL DEFAULT now();

    COMMENT ON COLUMN tb_product_usage_daily_timeline.reg_date IS '등록 일자';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.node_id IS '노드 ID';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.product_id IS '관리 제품 ID';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.duration IS '측정 시간 단위(분)';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.time_record IS '사용 로그';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.usage_hour IS '시간 기준 사용량';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.usage_duration IS 'duration 기준 사용량';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.is_use IS '사용 여부';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.tz_offset IS '시간대';
    COMMENT ON COLUMN tb_product_usage_daily_timeline.modified_time IS '수정 날짜';

    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE table_name = 'tb_product_usage_daily_timeline' AND constraint_type = 'PRIMARY KEY') THEN
        ALTER TABLE IF EXISTS ONLY tb_product_usage_daily_timeline ADD CONSTRAINT tb_product_usage_daily_timeline_pkey PRIMARY KEY (reg_date, node_id, product_id);
    END IF;

END $$ LANGUAGE 'plpgsql';
