$().ready(function() {
	var name = $("#name");
	var yob = $("#yob");
	var make = $("#make");
	var model = $("#model");
	var yop = $("#yop");
	var message = $("#message");
	
	var textPatt = /^[A-Z][a-z]+/;
	
	var fillTables = function() {
		var personList;
		var carList;
		
		var showRecords = function() {
			if(typeof personList === 'object' && typeof carList === 'object') {
				console.log(personList);
				console.log(_.size(personList)- 1);

				console.log(carList)
				$('.added').remove();
				for(var i = 0; i <_.size(personList); i++) {
					$('#person-table tbody').append('<tr class="added"><td>' + personList[i].id +'</td><td>'
						+ personList[i].firstName + '</td><td>' + personList[i].yob + '</td></tr>');
				}
				
				for(var i = 0; i < _.size(carList); i++) {
					var owner;
					if (carList[i].owner == null) {
						owner = "none";
					} else {
						owner = carList[i].owner.firstName; 
					}
					$('#car-table tbody').append('<tr class="added"><td>' + carList[i].id +'</td><td>'
						+ carList[i].make + '</td><td>' + carList[i].model + '</td><td>' + carList[i].yop + '</td><td>'
						+ owner + '</td></tr>');
				}
			}
		};
		
		var getCars = function() {
			$.ajax({
				url: "http://localhost:8080/restservicedemo/api/car/",
	            type: "GET",
	            contentType: "application/json",
	            success: function(data) {
	                carList = data;
	                showRecords();
	            },
	            error: function() {
	            	console.log("puacz");
	            }
			});
		};
		
		$.ajax({
			url: "http://localhost:8080/restservicedemo/api/person/",
            type: "GET",
            contentType: "application/json",
            success: function(data) {
                personList = data;
                getCars();
            },
            error: function() {
            	console.log("puacz");
            }
		});
	};
	fillTables();
	
	$("#add-person").click(function() {
		$(".m").remove();
		
		if (textPatt.test(name.val())) {
			if (parseInt(yob.val()) > 1800 && parseInt(yob.val()) <= 2016) {
				var person = {
					"firstName": name.val(),
					"yob": parseInt(yob.val())
				};
				
				$.ajax({
		            url: "http://localhost:8080/restservicedemo/api/person/",
		            type: "POST",
		            data: JSON.stringify(person),
		            contentType: "application/json",
		            success: function() {
		                $("#message").append('<p class="m">Person added</p>');
		                fillTables();
		            },
		            error: function() {
		            	$("#message").append('<p class="m">Person NOT added</p>');
		            }
		        });
			} else {
				message.append('<p class="m">Type correct year of birth, for example 1995</p>');
			}
		} else {
			message.append('<p class="m">Type correct name, for example Jasiu</p>');
		}
	});
	
	$("#add-car").click(function() {
		$(".m").remove();
		
		if (textPatt.test(make.val())) {
			if (parseInt(yop.val()) > 1800 && parseInt(yop.val()) <= 2016) {
				if (textPatt.test(model.val())) {
					var car = {
						"make": make.val(),
						"model": model.val(),
						"yop": parseInt(yop.val())
					};
					
					$.ajax({
			            url: "http://localhost:8080/restservicedemo/api/car/",
			            type: "POST",
			            data: JSON.stringify(car),
			            contentType: "application/json",
			            success: function() {
			                $("#message").append('<p class="m">Car added</p>');
			                fillTables();
			            },
			            error: function() {
			            	$("#message").append('<p class="m">Car NOT added</p>');
			            }
			        });
				} else {
					message.append('<p class="m">Type correct model, for example Panda</p>');
				}
			} else {
				message.append('<p class="m">Type correct year of production, for example 1995</p>');
			}
		} else {
			message.append('<p class="m">Type correct make, for example Fiat</p>');
		}
	});

});