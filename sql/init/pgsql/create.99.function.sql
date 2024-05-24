SET client_encoding = 'UTF8';

------------------------------------------------------------------------
-- SPECIAL FUNCTION
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION ssp_change_sequence_nextval(in_schema VARCHAR)
RETURNS void AS
$$
DECLARE
  object_types VARCHAR[];
  object_classes VARCHAR[];
  object_type record;
  r record;
  BEGIN
    FOR r IN
      EXECUTE 'SELECT se.relname, co.column_name, co.table_name FROM information_schema.columns co,
       ( select c.relname
         from pg_class c, pg_namespace n
         where n.oid = c.relnamespace
         and nspname = ''public''
         and relkind =  ''S'' ) se
         where co.column_default LIKE ''%'' || se.relname || ''%'''
    loop
      EXECUTE
        'SELECT setval('''||r.relname||''', (SELECT MAX('||r.column_name||') FROM '||r.table_name||'))';
    END loop;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ssp_exec_alter(text)
returns text language plpgsql volatile AS
$$
    BEGIN
      EXECUTE $1;
      RETURN $1;
    END;
$$;

------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION ssp_change_object_owner(in_schema VARCHAR, new_owner VARCHAR)
RETURNS void AS
$$
DECLARE
  object_types VARCHAR[];
  object_classes VARCHAR[];
  object_type record;

  r record;
BEGIN
  object_types = '{type,table,sequence,index,table,view}';
  object_classes = '{c,t,S,i,r,v}';

  FOR object_type IN
      SELECT unnest(object_types) type_name,
                unnest(object_classes) code
  loop
    FOR r IN
      EXECUTE '
          select n.nspname, c.relname
          from pg_class c, pg_namespace n
          where n.oid = c.relnamespace
            and nspname = ''' || in_schema || '''
            and relkind = ''' || object_type.code || ''''
    loop
      raise notice 'Changing ownership of % %.% to %',
                  object_type.type_name,
                  r.nspname, r.relname, new_owner;
      EXECUTE
        'alter ' || object_type.type_name || ' '
                 || r.nspname || '.' || r.relname
                 || ' owner to ' || new_owner;
    END loop;
  END loop;

  FOR r IN
    SELECT  p.proname, n.nspname,
       pg_catalog.pg_get_function_identity_arguments(p.oid) args
    FROM    pg_catalog.pg_namespace n
    JOIN    pg_catalog.pg_proc p
    ON      p.pronamespace = n.oid
    WHERE   n.nspname = in_schema
  LOOP
    raise notice 'Changing ownership of function %.%(%) to %',
                 r.nspname, r.proname, r.args, new_owner;
    EXECUTE
       'alter function ' || r.nspname || '.' || r.proname ||
       '(' || r.args || ') owner to ' || new_owner;
  END LOOP;

  FOR r IN
    SELECT *
    FROM pg_catalog.pg_namespace n
    JOIN pg_catalog.pg_ts_dict d
      ON d.dictnamespace = n.oid
    WHERE n.nspname = in_schema
  LOOP
    EXECUTE
       'alter text search dictionary ' || r.nspname || '.' || r.dictname ||
       ' owner to ' || new_owner;
  END LOOP;
END;
$$
LANGUAGE plpgsql;

------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION ssp_func_all_drop_under_10()
RETURNS VOID AS
$$
DECLARE
r RECORD;
BEGIN
  FOR r IN
   SELECT 'DROP '
    || CASE WHEN p.proisagg THEN 'AGGREGATE ' ELSE 'FUNCTION ' END
    || quote_ident(n.nspname) || '.' || quote_ident(p.proname) || '('
    || pg_catalog.pg_get_function_identity_arguments(p.oid) || ');' AS stmt
  FROM   pg_catalog.pg_proc p
  JOIN   pg_catalog.pg_namespace n ON n.oid = p.pronamespace
  WHERE  n.nspname = 'public'                     -- schema name (optional)
  AND    p.proname ILIKE 'sp_%'              -- function name
  -- AND pg_catalog.pg_function_is_visible(p.oid) -- function visible to user
  ORDER  BY 1
  LOOP
    EXECUTE r.stmt;
  END LOOP;
 
END;
$$
LANGUAGE plpgsql;

------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION ssp_func_all_drop_over_11()
RETURNS VOID AS
$$
DECLARE
r RECORD;
BEGIN
  FOR r IN
   SELECT 'DROP '
    || CASE WHEN p.prokind = 'a' THEN 'AGGREGATE ' ELSE 'FUNCTION ' END
    || quote_ident(n.nspname) || '.' || quote_ident(p.proname) || '('
    || pg_catalog.pg_get_function_identity_arguments(p.oid) || ');' AS stmt
  FROM   pg_catalog.pg_proc p
  JOIN   pg_catalog.pg_namespace n ON n.oid = p.pronamespace
  WHERE  n.nspname = 'public'                     -- schema name (optional)
  AND    p.proname ILIKE 'sp_%'              -- function name
  -- AND pg_catalog.pg_function_is_visible(p.oid) -- function visible to user
  ORDER  BY 1
  LOOP
    EXECUTE r.stmt;
  END LOOP;

END;
$$
LANGUAGE plpgsql;

------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION isodate(t timestamp with time zone) RETURNS timestamp with time zone
    LANGUAGE plpgsql
    AS $$
BEGIN
  RETURN t;
END;
$$;



------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION replace_recursive(search text, from_to text[]) RETURNS text
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF (array_length(from_to,1) > 1) THEN
        RETURN replace_recursive(
            replace(search, from_to[1][1], from_to[1][2]),
            from_to[2:array_upper(from_to,1)]
        );
    ELSE
        RETURN replace(search, from_to[1][1], from_to[1][2]);
    END IF;
END;$$;

------------------------------------------------------------------------
-- GENERAL FUNCTION
select * from ssp_func_all_drop_under_10();
select * from ssp_func_all_drop_over_11();
------------------------------------------------------------------------