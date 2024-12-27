function updateRentButton(contextPath, movieID, dailyRentalPrice) {
	let button = document.getElementById("rentButton");
	let slider = document.getElementById("rentDays");
	
	let days = slider.value;
	
	button.innerHTML = "Noleggia: " + dailyRentalPrice.toFixed(1) + "€/gg x " + days + "gg";
	button.href = contextPath + '/browse/UpdateCartServlet?action=add&type=rent&movieid=' + movieID + '&days=' + days;
}