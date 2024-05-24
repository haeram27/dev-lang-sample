SET client_encoding = 'UTF8';


------------------------------------------------------------------------
-- SPECIAL FUNCTION
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION ssp_view_all_drop()
RETURNS VOID AS
$$
DECLARE
r RECORD;
BEGIN
  FOR r IN
   select viewname from pg_views where viewname LIKE 'vw_%'
  LOOP
    EXECUTE 'DROP VIEW ' || r.viewname;
  END LOOP;

END;
$$
LANGUAGE plpgsql;

------------------------------------------------------------------------
-- GENERAL FUNCTION
select * from ssp_view_all_drop();
------------------------------------------------------------------------

------------------------------------------------------------------------------------------
-- CREATE VIEWS
------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
select '## vw_config';
------------------------------------------------------------------------------------------
DO $$
BEGIN
    CREATE OR REPLACE VIEW vw_config AS
    SELECT *
    FROM tb_config;

    COMMENT ON VIEW vw_config IS 'configuration view';
    COMMENT ON COLUMN vw_config.config_group IS 'configuration group';
    COMMENT ON COLUMN vw_config.key IS 'configuration key';
    COMMENT ON COLUMN vw_config.value IS 'configuration value';
    COMMENT ON COLUMN vw_config.modified_time IS 'modified time';

END $$ LANGUAGE 'plpgsql';



    