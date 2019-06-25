
CONNECT HISHAM/123@//localhost:1521/GEOPDB;

-- sqlplus > @models.sql

DROP SEQUENCE SQ_REGION_BOUNDARY;
DROP INDEX SIDX_REGION_BOUNDARY;
DROP TABLE TBL_REGION_BOUNDARY ;


CREATE SEQUENCE SQ_REGION_BOUNDARY MINVALUE 1 START WITH 5 INCREMENT BY 1 CACHE 10;


CREATE TABLE TBL_REGION_BOUNDARY
(
ID NUMBER PRIMARY KEY,
NAME VARCHAR2(100),
SHAPE SDO_GEOMETRY 
);

-- one polygon (exterior polygon ring)

INSERT INTO TBL_REGION_BOUNDARY(id, name, shape) VALUES(
  1,
  'test-city1',
  SDO_GEOMETRY(
    2003,  
    NULL,
    NULL,
    SDO_ELEM_INFO_ARRAY(1,1003,1),     
    SDO_ORDINATE_ARRAY(79.87614760691531, 6.869637713038295, 79.8734439402283, 6.867336916015708, 79.87704882914431, 6.86691084127179, 79.87614760691531, 6.869637713038295)
  )
);









---------------------------------------------------------------------------
-- UPDATE METADATA VIEW --
---------------------------------------------------------------------------
-- Update the USER_SDO_GEOM_METADATA view. This is required
-- before the Spatial index can be created. Do this only once for each
-- layer (table-column combination; here: TBL_REGION_BOUNDARY and SHAPE).
-- 8307 - SRID for 'X / Y (WGS 84)' coordinate system

DELETE FROM user_sdo_geom_metadata WHERE table_name = 'TBL_REGION_BOUNDARY';
INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'TBL_REGION_BOUNDARY',
  'SHAPE',
  SDO_DIM_ARRAY(   
    SDO_DIM_ELEMENT('X', 0, 20, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 20, 0.005)
     ),
  4326   
);

-------------------------------------------------------------------
-- CREATE THE SPATIAL INDEX --
-------------------------------------------------------------------

CREATE INDEX SIDX_REGION_BOUNDARY
ON TBL_REGION_BOUNDARY(SHAPE)
INDEXTYPE IS MDSYS.SPATIAL_INDEX;

--SELECT sdo_nl_index_table
--FROM user_sdo_index_metadata WHERE sdo_index_name='SIDX_REGION_BOUNDARY';






COMMIT;



