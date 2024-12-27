/**
 * 
 */
function createXMLHttpRequest() {
	var request;
	try {
		// Firefox 1+, Chrome 1+, Opera 8+, Safari 1.2+, Edge 12+, Internet Explorer 7+
		request = new XMLHttpRequest();
	} catch (e) {
		// past versions of Internet Explorer 
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");  
		} catch (e) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Il browser non supporta AJAX");
				return null;
			}
		}
	}
	return request;
}

function retrieveMovies(contextPath) {
	var request = createXMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
		    	response = JSON.parse(this.responseText);
				printMovies(response.movies, contextPath);
			} else {				
				if(this.status == 0){ // When aborting the request
					alert("Problemi nell'esecuzione della richiesta: nessuna risposta ricevuta nel tempo limite");
				} else { // Any other situation
					alert("Problemi nell'esecuzione della richiesta:\n" + this.statusText);
				}
			}
		}
	};
	setTimeout(function() {
		if(request.readyState < 4)
			request.abort();
	}, 15000);
	
	var url = contextPath + "/browse/SearchMovieTitleServlet"; 
	
	var title = document.getElementById('searchbar').value;
	var params = 'title=' + title;
	
	request.open("GET", url + "?" + params, true);
	request.setRequestHeader("Connection", "close");
	request.setRequestHeader("Cache-Control", "no-cache");
	request.send(null);
}

function printMovies(movies, contextPath) {
	let containers = document.getElementById('containers');
	containers.innerHTML = '';
	movies.forEach(function(movie){
		containers.innerHTML += 
			'<div class = "container">' +
				'<a href = "' + contextPath + '/browse/MoviePageServlet?id=' + movie.id + '">' +
					'<img src = "' + contextPath + '/images/posters/' + movie.posterpath + '">' +
				'</a>' +
			'</div>';
	});
}
