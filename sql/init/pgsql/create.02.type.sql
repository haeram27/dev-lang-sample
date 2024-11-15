SET client_encoding = 'UTF8';

------------------------------------------------------------------------------------------
-- CREATE TYPE
------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------
select '## en_fruit_type';
DO $$
BEGIN
    IF NOT EXISTS ( select 1 from pg_type where typname = 'en_fruit_type' ) 
    THEN
        CREATE TYPE en_fruit_type AS ENUM ();
        COMMENT ON TYPE en_application_service_type IS 'FRUIT TYPES';
    END IF;
END $$ LANGUAGE 'plpgsql';
ALTER TYPE en_fruit_type ADD VALUE IF NOT EXISTS 'APPLE';
ALTER TYPE en_fruit_type ADD VALUE IF NOT EXISTS 'BANANA';
ALTER TYPE en_fruit_type ADD VALUE IF NOT EXISTS 'GRAPE';
    