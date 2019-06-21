package com.nwsstechsol.geo.location;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nwsstechsol.geo.json.PloygonMapReader;
import com.nwsstechsol.geo.location.domain.service.GeoLocationService;
import com.vividsolutions.jts.geom.Polygon;


@RestController
@RequestMapping("api/geo/location")
@CrossOrigin
public class GeoLocationController {
	
	
	@Autowired
	private GeoLocationService geoLocationService;

	// Creates a new article
	@PostMapping(value = "polygon", consumes = "application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addArticle(@RequestBody Map<String, Object> geometryMap, UriComponentsBuilder builder) {
		
		// received geoson of polygon selected
		
		Polygon polygon = new PloygonMapReader().createPolygonFromGeoJson("mypoly", geometryMap);

		System.out.println("parsed polygon::" + polygon.toText());

		
		Long polygonId =   geoLocationService.saveRegionBoundry("Test", polygon) ; // 7L; 
		
		System.out.println("saved polygon::polygonId=" + polygonId);
				// locationService.saveLocation(userId, feature);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/polygon/{id}").buildAndExpand(polygonId).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}



	
}
