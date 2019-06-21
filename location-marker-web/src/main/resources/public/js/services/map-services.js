// load google map and enable drawing a polygon 

var map, infoWindow, drawingManager;
var selectedPolygon, selectedPolygonCoordinates, polygonGeoJson = {
	    type: 'Feature',
	    geometry: {
	        type: 'Polygon',
	        coordinates: []
	    },
	    properties: {}
	};

function showMap() {

	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 6.8663995510754265,
			lng : 79.87099776560672
		},
		zoom : 15,
	// mapTypeId: 'terrain',
	});

	enablePolygonDrawing(map);
	
}

function enablePolygonDrawing(map) {

	drawingManager = new google.maps.drawing.DrawingManager({
	      
		drawingMode : google.maps.drawing.OverlayType.POLYGON,
		drawingControl : true,
		drawingControlOptions : {
			position : google.maps.ControlPosition.TOP_CENTER,
			drawingModes : [ google.maps.drawing.OverlayType.POLYGON ]
		},
		// markerOptions: {icon:
		// 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'},
		polygonOptions : {
			
			// clickable: false,
			editable : true,
			zIndex : 1,
			strokeWeight: 0,
	        fillOpacity: 0.45,
	        draggable: true,
	        
	      
		}
	});

	drawingManager.setMap(map);
	
	var onPolygonComplete = google.maps.event.addListener(drawingManager,
			'polygoncomplete', function(polygon) {
			selectedPolygonCoordinates = polygon.getPath().getArray();
		    createGeoJSON(selectedPolygonCoordinates);
		        
		        // let polygonEncoded =
				// google.maps.geometry.encoding.encodePath(polygon.getPath())
		        // let polygonDecoded =
				// google.maps.geometry.encoding.decodePath(polygonEncoded)
				
		        
		});
	
		google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
			
			// Switch back to non-drawing mode after drawing a shape.
			//drawingManager.setDrawingMode(null);
			drawingManager.setOptions({
               // drawingControl: false
            });
			
	
			//selectedPolygon = null;
			  if (e.type == 'polygon') {
				selectedPolygon = e.overlay;
				selectedPolygon.type = e.type;
				
			  }
		});
	
	return drawingManager;
}

function clearPolygonSelection(){
	
	if (selectedPolygon) {
		selectedPolygon.setEditable(false);
		selectedPolygon.setMap(null);
		selectedPolygon = null;
		 //map.data.remove(map.data.getFeatureById(selectedPolygon.feature.getId()));
		 
		$( "#geometry" ).empty();
		}
	
	
	
}

function createGeoJSON(polygonCoordinates){
	let disp='',lng, lat;
	polygonGeoJson.geometry.coordinates = [];
	for (let point of polygonCoordinates) {
		
		
		lng=parseFloat(point.lng());
		lat=parseFloat(point.lat());
		
		
		//polygonGeoJson.geometry.coordinates.push([point.lng(), point.lat()]);
		polygonGeoJson.geometry.coordinates.push([lng, lat]);
		disp = disp.concat('<li> Longitude: '+point.lng()+' <br> Lattitude: '+point.lat()+' </li>');
    }
	
	$('#geometry').append('<ol>'+disp+'</ol>');
	
	
	// return polygonGeoJson
}

function setCurrentPosition() {

	// default position
	var defaultPos = {
		lat : 6.8663995510754265,
		lng : 79.87099776560672
	};

	// Try HTML5 geolocation.
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			};

			map.setCenter(pos);
		}, function() {
			map.setCenter(defaultPos);
			// handleLocationError(true, infoWindow, map.getCenter());
		});
	} else {
		// Browser doesn't support Geolocation
		// handleLocationError(false, infoWindow, map.getCenter());
		map.setCenter(defaultPos);
	}

}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	infoWindow.setPosition(pos);
	infoWindow
			.setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.'
					: 'Error: Your browser doesn\'t support geolocation.');
	infoWindow.open(map);
}
