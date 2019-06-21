package com.nwsstechsol.geo.location.domain.model;



import java.sql.Clob;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

@Repository
public interface RegionBoundaryRepository extends CrudRepository<RegionBoundary, Long> {
	
    @Autowired
    //EntityManager entityManager;
    
    public default int saveRegionBoundaryThroughNamedProcQuery(String firstName, String lastName, String email) {

       /* StoredProcedureQuery proc = entityManager.createNamedStoredProcedureQuery(
                "addEmployeeThroughNamedStoredProcedureQuery");
        
        proc.setParameter("FIRST_NAME", firstName);
        proc.setParameter("LAST_NAME", lastName);
        proc.setParameter("EMAIL", email);

        proc.execute();*/

    /*    return SavedRegionBoundaryResult.builder()
                .email((String) proc.getOutputParameterValue("EMAIL"))
                .id((Integer) proc.getOutputParameterValue("ID"))
                .createdAt((Date) proc.getOutputParameterValue("CREATED_AT"))
                .build();*/
        
        return 1;
    }

    
    @Procedure(name = RegionBoundary.NamedQuery_Save)
    Long saveRegionBoundary(@Param("p_name") String name,@Param("p_shape_wkt") Clob shape_wkt);
    
    

    //List<Customer> findByLastName(String lastName);

    @Query("select c from region_boundary c where within(c.shape, ?1) = true")
    List<RegionBoundary> findWithin(Geometry filter);

    @Query("select c from region_boundary c where c.name = ?1 ") 
	List<RegionBoundary> findByNameAndShape(String name, Polygon shape);  // and equals(c.shape, ?2) = true
	
	
}