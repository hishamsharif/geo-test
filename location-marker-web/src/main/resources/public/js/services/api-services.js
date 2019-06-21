
// load google map and enable drawing a polygon 

var map, infoWindow;
function savePolygon(polygon){
	//let geo = {"type":"Polygon","coordinates":[[79.87,6.86],[79.87,6.86],[79.87,6.86]]}
	//polygonJson = JSON.stringify(geo)
	let polygonJson = JSON.stringify(polygon)
	
	console.log('polygonJson:: ',polygonJson);
	

	$(function() {
		$.ajax({
		    type: "POST",
		    url: "http://localhost:8083/api/geo/location/polygon",
		    data: polygonJson,
		    contentType: 'application/json',
            dataType: 'json',
		    headers: {
                'Content-Type': 'application/json'
            },
		    success: function() {
		     
		      $('#saved-results').html("<h2>Selected Polygon Saved Successfully!</h2>")
		      .append("<p>...</p>")
		      .hide()
		      .fadeIn(1500, function() {
		        $('#saved-results').append("<img id='checkmark' src='images/check.png' />");
		      });
		    }
		  });
		
	});
	
} 

/*
 
  
var path = '../services/track/get/' + lon + '/' + lat + '/' + maxdistance + '/';
$.getJSON(path, function(data) {
    $.each(data, function(idx, track) {
        addMarker(track);
    });
});
 
 */

function apiTest(){
	$(document).ready(function() {
	    $.ajax({
	        url: "http://localhost:8083/greeting"
	    }).then(function(data) {
	       $('.greeting-id').append(data.id);
	       $('.greeting-content').append(data.content);
	    });
	});
	
}

apiTest();