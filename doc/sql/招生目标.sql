DELETE FROM e_r_target_decomposition WHERE target_id IN (1265167821730484226);
DELETE FROM e_r_target WHERE target_id IN (1265167821730484226)



 SELECT
        a.node_id,
        a.node_name,
        a.target_amount,
        a.`comment`,
        a.target_decomposition_id
        FROM e_r_target_decomposition a
        WHERE a.target_id=1263036130321571842 AND a.parent_id='0' AND a.type=1
        
       SELECT * FROM e_r_target_decomposition WHERE target_id IN (1265167821730484226);
 SELECT * FROM e_r_target WHERE target_id IN (1265167821730484226)