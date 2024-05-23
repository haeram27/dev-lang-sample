

------------------------------------------------------------------------------------------
-- delete rows has server_date(date) values less than current - interval days 
------------------------------------------------------------------------------------------
DELETE FROM tb_node_alive_daily_history WHERE server_date < (now() - make_interval(days => ${days}))