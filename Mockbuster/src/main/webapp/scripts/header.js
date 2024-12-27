function toggleMenuVisibility(contextPath) {
	let menu = document.getElementById("menu");
	let hamburger = document.getElementById("hamburger");
	let visibility = menu.style.visibility;
	
	if(visibility === "visible") {
		menu.style.visibility = "hidden";
		hamburger.src = contextPath + "/images/icons/menu.png"
	}
	else {
		menu.style.visibility = "visible";
		hamburger.src = contextPath + "/images/icons/cross.png"
	}
}

function toggleSearchbarVisibility(contextPath) {
	let searchbar = document.getElementById("searchbar");
	let lens = document.getElementById("lens");
	let display = searchbar.style.display;
	
	if(display === "inline") {
		searchbar.style.display = "none";
		lens.src = contextPath + "/images/icons/search.png"
	}
	else {
		searchbar.style.display = "inline";
		searchbar.focus();
		lens.src = contextPath + "/images/icons/cross.png"
	}
}