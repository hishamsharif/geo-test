

CREATE OR REPLACE PROCEDURE save_region_boundary(p_name IN varchar2, p_shape_wkt IN CLOB,p_id OUT NUMBER) AS
v_shape SDO_GEOMETRY;
BEGIN
v_shape := SDO_UTIL.FROM_WKTGEOMETRY(p_shape_wkt);

INSERT INTO REGION_BOUNDARY(id, name, shape) VALUES(
  1,
  p_name,
  v_shape
);


COMMIT;

END save_region_boundary;
/



CREATE OR REPLACE PROCEDURE update_region_boundary(p_id IN NUMBER, p_shape_wkt IN CLOB) AS
v_shape SDO_GEOMETRY;
BEGIN
v_shape := SDO_UTIL.FROM_WKTGEOMETRY(p_shape_wkt);
UPDATE REGION_BOUNDARY SET shape = v_shape
WHERE ID = p_id;
COMMIT;
END update_region_boundary;
/




