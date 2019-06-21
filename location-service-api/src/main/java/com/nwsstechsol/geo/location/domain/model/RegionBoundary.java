package com.nwsstechsol.geo.location.domain.model;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;

import org.hibernate.engine.jdbc.ClobProxy;

import com.vividsolutions.jts.geom.Polygon;

import lombok.Data;

/*@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(
      name = Person.NamedQuery_MoveToHistory,
      procedureName = "MOVE_TO_HISTORY",
      parameters = {
              @StoredProcedureParameter(type = Long.class, mode = ParameterMode.IN),
              @StoredProcedureParameter(type = String.class, mode = ParameterMode.OUT),
      }
),
@NamedStoredProcedureQuery(
      name = Person.NamedQuery_FetchFromHistory,
      procedureName = "FETCH_PERSON_HISTORY",
      resultClasses = Person.class,
      parameters = {
              @StoredProcedureParameter(type = void.class, mode = ParameterMode.REF_CURSOR)
      }
)})*/
// p_name IN varchar2, p_shape_wkt IN CLOB,p_id OUT NUMBER)

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		      name = RegionBoundary.NamedQuery_Save,
		      procedureName = "SAVE_REGION_BOUNDARY",
		      parameters = {
		              @StoredProcedureParameter(name = "p_name",type = String.class, mode = ParameterMode.IN),
		              @StoredProcedureParameter(name = "p_shape_wkt",type = Clob.class, mode = ParameterMode.IN),
		              @StoredProcedureParameter(name = "p_id",type = Long.class, mode = ParameterMode.OUT),
		      }
		)

})

@Entity(name = "region_boundary")
@Data
public class RegionBoundary {
	
	public static final String NamedQuery_Save = "saveRegionBoundary";
	  
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REGION_BOUNDARY")
	@SequenceGenerator(sequenceName = "SQ_REGION_BOUNDARY", allocationSize = 1, name = "SQ_REGION_BOUNDARY")
    private Long id;
    private String name;
    private Polygon shape;
    
    //@JsonSerialize(using = GeometrySerializer.class)
    //@JsonDeserialize(using = GeometryDeserializer.class)
    //@Column(name = "shape", columnDefinition = "Geometry")
    public Polygon getShape() {
    	//System.out.println("Geometry must be a polygon. Got a " + shape.getGeometryType() );
        return shape;
    }
    
    public Clob convertShapeWktToClob() {
        return ClobProxy.generateProxy(shape.toText());
    }
    
}