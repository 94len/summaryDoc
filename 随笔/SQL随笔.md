### SQL随笔

#### 分组查询同类型的最新/最高的一条记录
```
SELECT 
orv.customer_id,
orv.open_type,
orv.vipcard_id
FROM orders_vip orv 
JOIN (SELECT customer_id,MAX(gmt_create) gmt_create FROM orders_vip GROUP BY customer_id) t 
ON orv.customer_id=t.customer_id AND orv.gmt_create=t.gmt_create
```

分析：自表连接。根据需求，分组查询出最新记录的时间和用户ID，再通过和自表建立连接，定位查询出条件内最新的那条记录
