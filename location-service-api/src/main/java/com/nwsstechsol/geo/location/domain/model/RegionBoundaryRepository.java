package com.nwsstechsol.geo.location.domain.model;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Geometry;

@Repository
public interface RegionBoundaryRepository extends CrudRepository<RegionBoundary, Long> {
	
   
	
	// Initially had issues with serializing jpa geometry objects into sdo_geometry
	// Later the issue resolved by changing DB and hibernate spatial version 
	// along with a compatible connection finder implementation for hikari and SRID in DB.	
    @Procedure(name = RegionBoundary.NamedQuery_Save)
    Long saveRegionBoundary(@Param("p_name") String name,@Param("p_shape") Geometry shape);
    


    
    //@Query("select c from tbl_region_boundary c where within(c.shape, ?1) = true")
    @Query("select c from tbl_region_boundary c where contains(c.shape, ?1) = true")
    List<RegionBoundary> findWithin(Geometry filter);  //TODO: still did get this working against 11g xe version, need to try with 12c

    //@Query("select c from tbl_region_boundary c where c.name = ?1 or equals(c.shape, ?2) = true ") 
	//List<RegionBoundary> findByNameAndShape(String name, Geometry shape);  // and equals(c.shape, ?2) = true
    
    @Query("select c from tbl_region_boundary c where equals(c.shape, ?1) = true ") 
    List<RegionBoundary> findByShape(Geometry shape);
	
	
}