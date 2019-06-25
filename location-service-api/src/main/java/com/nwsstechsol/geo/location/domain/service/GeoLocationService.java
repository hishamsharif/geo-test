package com.nwsstechsol.geo.location.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.nwsstechsol.geo.location.domain.model.RegionBoundary;
import com.nwsstechsol.geo.location.domain.model.RegionBoundaryRepository;
import com.nwsstechsol.geo.util.json.GeoJSONConverterUtil;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

@Service
public class GeoLocationService {

	@Autowired
	private RegionBoundaryRepository regionBoundaryRepository;

	/**
	 * @param name    - TODO may need to name this polygon in the web map view
	 * @param polygon - a geometry type to store the selected area on the web mpa
	 *                view (Google Map)
	 * @return the primary key of the created item
	 */
	public Long saveRegionBoundry(String name, Polygon polygon) {

		RegionBoundary regionBoundary = new RegionBoundary();
		regionBoundary.setName(name);
		regionBoundary.setShape(polygon);

		// List<RegionBoundary> list =
		// regionBoundaryRepository.findByNameAndShape(regionBoundary.getName(),
		// regionBoundary.getShape());

		List<RegionBoundary> list = regionBoundaryRepository.findByShape(regionBoundary.getShape());

		if (list.size() > 0) {
			System.out.println("Already polygon exists- id::" + list.get(0).getId() + "  name::" + list.get(0).getName()
					+ "  " + list.get(0).getShape().getGeometryType() + "::"
					+ Arrays.toString(list.get(0).getShape().getCoordinates()));
			return list.get(0).getId();
		} else {
			// return regionBoundaryRepository.save(regionBoundary).getId();
			// return regionBoundaryRepository.saveRegionBoundary(regionBoundary.getName(),
			// regionBoundary.toShapeWkt());
			return regionBoundaryRepository.saveRegionBoundary(regionBoundary.getName(), regionBoundary.getShape());

		}
	}

	public org.wololo.geojson.Geometry findLocationsWithin(Point location) {

		List<RegionBoundary> regionBoundaryList = regionBoundaryRepository.findWithin(location);
		if (regionBoundaryList.size() > 0) {

			Geometry[] geometries = (Geometry[]) regionBoundaryList.stream().map(RegionBoundary::getShape)
					.toArray(Geometry[]::new);

			try {

				GeometryCollection gc = new GeoJSONConverterUtil().createGeometryCollection(geometries);
				org.wololo.geojson.Geometry geometeryJSON = new GeoJSONWriter().write(gc);
				return geometeryJSON;

			} catch (Exception e) {
				System.out.println("error creating json object: " + e.toString());
			}

		} else {
			System.out.println(" NOT received results ");

		}

		return null;

	}

}
