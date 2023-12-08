


const toggleSideBar = () => {

	if ($(".sidebar").is(":visible")) {

		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
		$(".d-flex").css("margin-left", "10%");
		$(".d-flex").css("column-gap", "2%");
		$(".card-body").css("margin-right", "0%");
		$(".search-news").css("padding-left", "5%");

	} else {

		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
		$(".d-flex").css("margin-left", "0%");
		$(".d-flex").css("column-gap", "0%");
		$(".card-body").css("margin-right", "15%");
		$(".search-news").css("padding-right", "2%");


	}


}





const search = () => {

	let query = $("#search").val();
	if (query == "") {

		$(".search-result").hide();
	} else {


		let url = `http://localhost:8080/search/${query}`;
		fetch(url)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				console.log(data);
				let text = `<div class='list-group'>`

				data.forEach((news) => {

					text += `<a href='/admin/${news.nId}/news' class='list-group-item list-group-item-action'>${news.rubrik} </a>`;
				});
				text += `</div>`;
				$(".search-result").html(text);
				$(".search-result").show();
			});
	}

}

const userSearch = () => {

	let query1 = $("#search").val();
	if (query1 == "") {

		$(".search-result").hide();
	} else {
		/*console.log(query1);*/

		let url = `http://localhost:8080/searches/${query1}`;
		fetch(url)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				/*console.log(data);*/
				let text = `<div class='list-group'>`

				data.forEach((news) => {

					text += `<a href='/user/${news.nId}/news' class='list-group-item list-group-item-action'>${news.rubrik} </a>`;
				});
				text += `</div>`;
				$(".search-result").html(text);
				$(".search-result").show();
			});
	}

}


const searchEvent = () => {

	let query = $("#search").val();
	if (query == "") {

		$(".search-result").hide();
	} else {


		let url = `http://localhost:8080/search/event/${query}`;
		fetch(url)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				/*console.log(data);*/
				let text = `<div class='list-group'>`

				data.forEach((event) => {

					text += `<a href='/admin/${event.eId}/event' class='list-group-item list-group-item-action'>${event.rubrik} </a>`;
				});
				text += `</div>`;
				$(".search-result").html(text);
				$(".search-result").show();
			});
	}

}

const userSearchEvent = () => {

	let query1 = $("#search").val();
	if (query1 == "") {

		$(".search-result").hide();
	} else {
		/*console.log(query1);*/

		let url = `http://localhost:8080/searches/event/${query1}`;
		fetch(url)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				/*console.log(data);*/
				let text = `<div class='list-group'>`

				data.forEach((event) => {

					text += `<a href='/user//${event.eId}/event' class='list-group-item list-group-item-action'>${event.rubrik} </a>`;
				});
				text += `</div>`;
				$(".search-result").html(text);
				$(".search-result").show();
			});
	}

}

			function toggleSelection(checkbox) {
				var selectedKretsInput = document.getElementById('selectedKrets');
				var value = checkbox.value;

				if (checkbox.checked) {
					// Add to the list if checked
					if (!selectedKretsInput.value.includes(value)) {
						selectedKretsInput.value += value + ',';
					}
				} else {
					// Remove from the list if unchecked
					selectedKretsInput.value = selectedKretsInput.value.replace(value + ',', '');
				}
			}
		

$(document).ready(function() {
	// Cache the filters elements for better performance
	var filtersElement = $('#filters');
	var eventFiltersElement = $('#event-filters');
	var filterSelect = $('#filterSelect');
	var eventFilterSelect = $('#eventFilterSelect');

	// Your existing isotope initialization for news-list
	var $grid = $('#news-lists').isotope({
		/*itemSelector: '.news-flex',*/
		layoutMode: 'fitRows'

	});

	filtersElement.hide();

	var $eventGrid = $('#event-list').isotope({

		layoutMode: 'fitRows'

	});

	eventFiltersElement.hide();

	// Variables to store filter values
	var newsListFilterValue = '*';
	var eventListFilterValue = '*';

	// Function for handling news-list filters

	function handleNewsListFilters() {

		filterSelect.change(function() {
			var selectedValues = filterSelect.val();
			// Apply your filtering logic using the selected value
			$grid.isotope({ filter: selectedValues });
		});

		$('#filters').on('click', 'button', function() {
			newsListFilterValue = $(this).attr('data-filter');
			$grid.isotope({ filter: newsListFilterValue });
		});
	}

	// Function for handling event-list filters
	function handleEventListFilters() {
  
		eventFilterSelect.change(function() {
			
			var selectedValues = eventFilterSelect.val();
			$eventGrid.isotope({ filter: selectedValues });
		
		});

		$('#event-filters').on('click', 'button', function() {
			eventListFilterValue = $(this).attr('data-filter');
			$eventGrid.isotope({ filter: eventListFilterValue });
			$grid.isotope({ filter: newsListFilterValue });
		});
	}

	// Call the functions to set up event listeners
	handleNewsListFilters();
	handleEventListFilters();



});





//my rating widget

// my rating widget ends


//rating widget

/*(function(d, t, e, m) {

	// Async Rating-Widget initialization.
	window.RW_Async_Init = function() {

		RW.init({
			huid: "494080",
			uid: "a166ec41c515ddb71e3f2d934b93882d",
			options: { "style": "oxygen" }
		});
		RW.render();
	};
	// Append Rating-Widget JavaScript library.
	var rw, s = d.getElementsByTagName(e)[0], id = "rw-js",
		l = d.location, ck = "Y" + t.getFullYear() +
			"M" + t.getMonth() + "D" + t.getDate(), p = l.protocol,
		f = ((l.search.indexOf("DBG=") > -1) ? "" : ".min"),
		a = ("https:" == p ? "secure." + m + "js/" : "js." + m);
	if (d.getElementById(id)) return;
	rw = d.createElement(e);
	rw.id = id; rw.async = true; rw.type = "text/javascript";
	rw.src = p + "//" + a + "external" + f + ".js?ck=" + ck;
	s.parentNode.insertBefore(rw, s);
}(document, new Date(), "script", "rating-widget.com/"));*/



// rating widget ends


window.RW_Async_Init = function() {
	RW.init({
		huid: "494080",
		uid: "a166ec41c515ddb71e3f2d934b93882d",
		options: { "style": "oxygen" }
	});
	// Create an array to store the received data objects
	var ratingArray = [];
	RW.render({
		callback: function(data) {
			// Process the widget data here
			console.log("Widget data:", data);
			ratingArray.push(data);
			console.log("ratingArray:", ratingArray);
			// Assuming ratingArray is the original array you provided
			//var transformedArray = Object.values(ratingArray[0]);

			//console.log("transformedArray:", transformedArray);
			// Extract rate and votes for each rating object
			/*transformedArray.forEach(function(rating) {
				var rate = rating.rate;
				var votes = rating.votes;
				 var individualRating = rate /votes;
				 var maxRating=5;
				 
		    

// Output the result

			    
				// Do something with rate and votes
			console.log("IndividualRating:", individualRating);
				console.log("Rate:", rate);
				console.log("Votes:", votes);
				var ratingRepresentation = generateRatingRepresentation(individualRating, maxRating);
				console.log("Rating Representation:", ratingRepresentation);

function generateRatingRepresentation(individualRating, maxRating) {
	// Calculate the number of filled stars
	var filledStars = Math.floor(individualRating);

	// Calculate the decimal part to determine whether to add a half star
	var decimalPart = individualRating - filledStars;

	// Determine whether to add a half star
	var addHalfStar = decimalPart >= 0.5;

	// Calculate the number of empty stars
	var emptyStars = maxRating - filledStars - (addHalfStar ? 1 : 0);

	// Create the rating representation
	var ratingRepresentation = "";

	for (var i = 0; i < filledStars; i++) {
		ratingRepresentation += "⭐";
	}

	if (addHalfStar) {
		ratingRepresentation += "½"; // Add a half star
	}

	for (var j = 0; j < emptyStars; j++) {
		ratingRepresentation += " ";
	}
    
    
	for (var j = 0; j < maxRating - filledStars - (addHalfStar ? 1 : 0); j++) {
		ratingRepresentation += " ";
	}

	ratingRepresentation += " " + individualRating.toFixed(1) + " out of " + maxRating;

	// Create an object with the representation and counts
	var result = {
		representation: ratingRepresentation,
		counts: {
		  totalVotes: votes,
		excellent: individualRating >= maxRating - 0.25 ? 1 : 0,
		good: individualRating >= maxRating - 1 && individualRating < maxRating - 0.25 ? 1 : 0,
		average: individualRating >= maxRating - 2 && individualRating < maxRating - 1 ? 1 : 0,
		poor: individualRating >= maxRating - 3 && individualRating < maxRating - 2 ? 1: 0,
		awful: individualRating < maxRating - 3 ? votes : 0
	    
	    
		   excellent: filledStars === maxRating ? 1 : 0,
		good: filledStars === maxRating - 1 ? 1 : 0,
		average: filledStars === maxRating - 2 ? 1 : 0,
		poor: filledStars === maxRating - 3 ? 1 : 0,
		awful: filledStars === maxRating - 4 ? 1 : 0
		}
	};
    
	// Distribute votes across categories
if (individualRating >= maxRating - 0.25) {
	result.counts.excellent += votes;
} else if (individualRating >= maxRating - 1 && individualRating < maxRating - 0.25) {
	result.counts.good += votes;
} else if (individualRating >= maxRating - 2 && individualRating < maxRating - 1) {
	result.counts.average += votes;
} else if (individualRating >= maxRating - 3 && individualRating < maxRating - 2) {
	result.counts.poor += votes;
} else if (individualRating < maxRating - 3) {
	result.counts.awful += votes;
}

// Log the counts to the console for verification
console.log(result.counts);

	return result;
}


			});*/
		}
	});


};


// Add event model open
function addEvent() {

	window.location.href = '/admin/event';
}


// Add newa model open
function addNews() {

	window.location.href = '/admin/add_news';
}

function addMeddelande() {

	window.location.href = '/admin/add_meddelande';
}



$(document).ready(function() {
	$('[data-toggle="delete"]').tooltip();
});
$(document).ready(function() {
	$('[data-toggle="update"]').tooltip();
});
//request to create donatation order

/*const paymentStart = () => {
	console.log("payment started");
	let amount = $("#payment_id").val();
	console.log(amount);
	if (amount == "" || amount == null) {
		swal("Enter some amount !!");
		return;
	}

	$.ajax(
		{
			url: '/user/create_donation',
			data: JSON.stringify({ amount: amount, info: 'order_request' }),
			contentType: 'application/json',
			type: 'POST',
			dataType: 'json',
			success: function(response) {
				console.log(response);
				if (response.status == "created") {
					let options = {

						key: 'rzp_test_CcDt1D92QvQgF4',
						amount: response.amount,
						currency: 'SEK',
						name: 'Smart Contact Manager',
						description: 'Donation',
						image: "http://localhost:8080/img/contact.png",
						order_id: response.id,
						handler: function(response) {
							console.log(response.razorpay_payment_id)
							console.log(response.razorpay_order_id)
							console.log(response.razorpay_signature)
							swal("Good job!", "payment successful!!", "success");

						},
						"prefill": {
							"name": "",
							"email": "",
							"contact": ""
						},
						"notes": {
							"address": "GM Contact Manager System"
						},
						"theme": {
							"color": "#3399cc"
						}
					};
					let rzp = new Razorpay(options);
					rzp.on('payment.failed', function(response) {
						console.log(response.error.code);
						console.log(response.error.description);
						console.log(response.error.source);
						console.log(response.error.step);
						console.log(response.error.reason);
						console.log(response.error.metadata.order_id);
						console.log(response.error.metadata.payment_id);
						swal("Oops!", "Payment failed!!", "error");
					});
					rzp.open();
				}


			},
			error: function(error) {
				console.log(error);
				swal("Oops!", "Donation is not completed!!", "error");
			}



		}


	)


};*/