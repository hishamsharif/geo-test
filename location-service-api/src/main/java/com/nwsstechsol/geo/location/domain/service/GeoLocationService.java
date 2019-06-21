package com.nwsstechsol.geo.location.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwsstechsol.geo.location.domain.model.RegionBoundary;
import com.nwsstechsol.geo.location.domain.model.RegionBoundaryRepository;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

@Service
public class GeoLocationService {

	@Autowired
	private RegionBoundaryRepository regionBoundaryRepository;

	public Long saveRegionBoundry(String name, Polygon polygon) {
		
		RegionBoundary regionBoundary = new RegionBoundary();
		regionBoundary.setName(name);
		regionBoundary.setShape(polygon);
		
		List<RegionBoundary> list = regionBoundaryRepository.findByNameAndShape(regionBoundary.getName(),
				regionBoundary.getShape());
		if (list.size() > 0) {
			return 0L;
		} else {
			//return regionBoundaryRepository.save(regionBoundary).getId();
			//convertShapeWktToClob()
			return regionBoundaryRepository.saveRegionBoundary(regionBoundary.getName(), regionBoundary.convertShapeWktToClob());
			
		}
	}

	public Polygon findLocationsWithin(Point location) {

		List<RegionBoundary> regionBoundaryList = regionBoundaryRepository.findWithin(location);
		if (regionBoundaryList.size() > 0) {
			return null;
		} else {
			return regionBoundaryList.get(0).getShape();

		}

	}

}
