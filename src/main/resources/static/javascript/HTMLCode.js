

var htmlCodeCallBack = function htmlCodeCallBack() {
	var blockCode = document.getElementById("htmlResponse");
	blockCode.value = request.responseText;
	
	blockCode.style.display = "block";	
	document.getElementById("addContentBlock").style.display = "block";	
}


function getHTMLCode() {
	
	var url = {}
	url.urlString = document.getElementById("urlString").value;
	url.id = null;
	var context = document.getElementById("context").value;
	doPost(context + 'checkurl', url, htmlCodeCallBack);
}



function addCodeToTracker(){
	var responseHtml = document.getElementById("htmlResponse");
	var selectedCode = responseHtml.value.slice(responseHtml.selectionStart, responseHtml.selectionEnd);
	document.getElementById("textToTrack").value = selectedCode;
	document.getElementById("urlToTrack").value = document.getElementById("urlString").value;
}
