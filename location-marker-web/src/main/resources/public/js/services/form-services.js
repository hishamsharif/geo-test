
function addFormSubmitHandler(){
	
	$(function(){
	    $("#submitBtn").click(function(){       
	    	
	    	// Stop form from submitting normally
	    	  event.preventDefault();
	    	  
	    	if(polygonGeoJson && savePolygon){
	    		console.log('polygonGeoJson::',typeof polygon, polygonGeoJson.geometry);
	    		
	    		savePolygon(polygonGeoJson.geometry);
	    	}
	        //$("#myForm").submit(); // Submit the form
	    });
	});

}

function addClearPolygonHandler(){
	
	$(function(){
	    $("#clearBtn").click(function(){     
	    	
	    	clearPolygonSelection()
	    });
	});

}


addFormSubmitHandler();
addClearPolygonHandler();