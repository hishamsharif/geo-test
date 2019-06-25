package com.nwsstechsol.geo.location.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nwsstechsol.geo.location.domain.service.GeoLocationService;
import com.nwsstechsol.geo.util.json.GeoJSONConverterUtil;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/geo/location")
@CrossOrigin
@Slf4j
public class GeoLocationController {

	@Autowired
	private GeoLocationService geoLocationService;

	// Receives GeoJSON of polygon selected on web map view(google) and store it in
	// the database (if found none existing with the same)
	@PostMapping(value = "polygon", consumes = "application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addPolygon(@RequestBody Map<String, Object> geometryMap, UriComponentsBuilder builder) {

		// loog GeoJSON request
		log.info("addPolygon GeoJSON request:: " + geometryMap.get("coordinates"));

		String name = "Test-Polygon"; // TODO may need to name this polygon in the web map view
		Polygon polygon = new GeoJSONConverterUtil().createPolygonFromGeoJson(name, geometryMap);

		Long polygonId = geoLocationService.saveRegionBoundry(name, polygon);

		// update the header with the resource url for newly created (polygon)
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/polygon/{id}").buildAndExpand(polygonId).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "polygon", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final ResponseEntity<org.wololo.geojson.Geometry> findPolygonOfGivenPoint(
			@RequestParam("lng") String longitude, @RequestParam("lat") String latitude) {
		Point p = new GeoJSONConverterUtil().createPointGeometry(Double.valueOf(longitude), Double.valueOf(latitude));
		org.wololo.geojson.Geometry polyOfGivenPoint = geoLocationService.findLocationsWithin(p);

		log.info("findPolygonOfGivenPoint GeoJSON response::" + polyOfGivenPoint);

		return new ResponseEntity<org.wololo.geojson.Geometry>(polyOfGivenPoint, HttpStatus.OK);

	}

}
