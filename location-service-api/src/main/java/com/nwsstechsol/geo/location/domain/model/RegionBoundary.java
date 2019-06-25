package com.nwsstechsol.geo.location.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;

import com.vividsolutions.jts.geom.Geometry;

import lombok.Data;

@NamedStoredProcedureQueries({
		/*
		 * @NamedStoredProcedureQuery( name = Person.NamedQuery_FetchFromHistory,
		 * procedureName = "FETCH_PERSON_HISTORY", resultClasses = Person.class,
		 * parameters = {
		 * 
		 * @StoredProcedureParameter(type = void.class, mode = ParameterMode.REF_CURSOR)
		 * } )}),
		 * 
		 * @NamedStoredProcedureQuery( name = RegionBoundary.NamedQuery_Save,
		 * procedureName = "SP_SAVE_REGION_BOUNDARY", parameters = {
		 * 
		 * @StoredProcedureParameter(name = "p_name",type = String.class, mode =
		 * ParameterMode.IN),
		 * 
		 * @StoredProcedureParameter(name = "p_shape_wkt",type = String.class, mode =
		 * ParameterMode.IN),
		 * 
		 * @StoredProcedureParameter(name = "p_id",type = Long.class, mode =
		 * ParameterMode.OUT), } )
		 */

		@NamedStoredProcedureQuery(name = RegionBoundary.NamedQuery_Save, procedureName = "SP_SAVE_REGION_BOUNDARY", parameters = {
				@StoredProcedureParameter(name = "p_name", type = String.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "p_shape", type = Geometry.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(name = "p_id", type = Long.class, mode = ParameterMode.OUT), }) })

@Entity(name = "tbl_region_boundary")
@Data
public class RegionBoundary {

	public static final String NamedQuery_Save = "saveRegionBoundary";

	@Id
	// @GeneratedValue(strategy= GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REGION_BOUNDARY")
	@SequenceGenerator(sequenceName = "SQ_REGION_BOUNDARY", allocationSize = 1, name = "SQ_REGION_BOUNDARY")
	private Long id;
	private String name;

	private Geometry shape;

	/*
	 * public Clob convertShapeWktToClob() { return
	 * ClobProxy.generateProxy(shape.toText()); }
	 */

	public String toShapeWkt() {
		return shape.toText();
	}

}