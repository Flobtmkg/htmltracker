var request = new XMLHttpRequest();


function doRequest(method, body, url, callback) {
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	    	callback();
		}
	};
	request.open(method, url, true);
	addHeaders();
	console.log(JSON.stringify(body))
	request.send(JSON.stringify(body));
}


function doGet(url, callback) {
	doRequest("GET", null, url, callback);
}

function doPost(url, body, callback) {
	doRequest("POST", body, url, callback);
}

function addHeaders() {
	//request.setRequestHeader("Access-Control-Allow-Origin", "*");
	request.setRequestHeader('Content-type','application/json');
}
