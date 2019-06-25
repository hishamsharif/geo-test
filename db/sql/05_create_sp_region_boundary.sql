
CONNECT HISHAM/123@//localhost:1521/GEOPDB;


create or replace PROCEDURE sp_save_region_boundary(p_name IN varchar2, p_shape IN SDO_GEOMETRY,p_id OUT NUMBER) AS
v_shape varchar2(4000); --SDO_GEOMETRY;
v_err_num NUMBER;
v_err_msg VARCHAR2(4000);

BEGIN

    
    p_id := SQ_REGION_BOUNDARY.nextval;
    v_shape := sdo_util.to_wktgeometry_varchar(p_shape);

    INSERT INTO TBL_LOGS VALUES('sp_save_region_boundary::p_shape_wkt::after::'||p_id,NULL,v_shape);
    COMMIT;

    INSERT INTO TBL_REGION_BOUNDARY(id, name, shape) VALUES(
      p_id,
      p_name,
      p_shape
    );

    COMMIT;

    -- POLYGON ((79.87717757517703 6.868444708563957, 79.87224231058963 6.865462184288502, 79.8792804270447 6.863502229585933, 79.87717757517703 6.868444708563957))

EXCEPTION
  WHEN OTHERS THEN
      v_err_num := SQLCODE;
      v_err_msg := SUBSTR(SQLERRM, 1, 2000);
      ROLLBACK;
      INSERT INTO TBL_LOGS VALUES('REGION_BOUNDARY_'||p_id,v_err_num,v_err_msg);
      COMMIT;
      RAISE;


END sp_save_region_boundary;
/



CREATE OR REPLACE PROCEDURE sp_update_region_boundary(p_id IN NUMBER, p_shape_wkt IN CLOB) AS
v_shape SDO_GEOMETRY;
BEGIN
v_shape := SDO_UTIL.FROM_WKTGEOMETRY(p_shape_wkt);
UPDATE TBL_REGION_BOUNDARY SET shape = v_shape
WHERE ID = p_id;
COMMIT;
END sp_update_region_boundary;
/




