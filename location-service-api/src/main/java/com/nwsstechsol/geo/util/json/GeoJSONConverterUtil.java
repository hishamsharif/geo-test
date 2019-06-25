package com.nwsstechsol.geo.util.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;

public class GeoJSONConverterUtil {
	
	private GeometryFactory getGeometryFactory() throws ParseException {

		GeometryFactory result = null;

		Integer srid = null;

		if (srid == null) {
			// The default CRS is a geographic coordinate reference
			// system, using the WGS84 datum, and with longitude and
			// latitude units of decimal degrees. SRID 4326
			srid = Integer.valueOf(4326);
		}

		result = new GeometryFactory(new PrecisionModel(), srid.intValue());
		return result;
	}
	
	public Polygon createPolygonFromGeoJson(String name, Map<String, Object> geometryMap) {

		@SuppressWarnings("unchecked")
		List<List<Double>> geometryList = (List<List<Double>>) geometryMap.get(GeoJsonConstants.NAME_COORDINATES);

		Coordinate[] coordinates = new Coordinate[geometryList.size() + 1];
		int count = 0;
		for (List<Double> coordinate : geometryList) {
			coordinates[count++] = new Coordinate(coordinate.get(0), coordinate.get(1));// X=Longitude , Y=Latitude
		}
		coordinates[count] = coordinates[0];

		Polygon polygon = null;
		try {
			polygon = getGeometryFactory().createPolygon(coordinates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("error while parsing createPolygon ::" + e);
			e.printStackTrace();
		}

		Map<String, Object> attributes = new LinkedHashMap<>();
		// attributes.put("id", id.hashCode());
		attributes.put("name", name);
		polygon.setUserData(attributes);
		
		
		return polygon;
	}
	
	public GeometryCollection createGeometryCollection(Geometry[] geometries) throws ParseException {
		
		return getGeometryFactory().createGeometryCollection(geometries);
	}
	
	public Point createPointGeometry(double lng, double lat) {

		Coordinate coordinate = new Coordinate(lng,lat);
	

		Point point = null;
		try {
			point = getGeometryFactory().createPoint(coordinate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("error while parsing createPoint ::" + e);
			e.printStackTrace();
		}

		
		return point;
	}


}
