$().ready(function() {
	$("#add-person").click(function() {
		var person = {
				"firstName": $("#name").val(),
				"yob": parseInt($("#yob").val())
		};
		
		$.ajax({
            url: "http://localhost:8080/restservicedemo/api/person/",
            type: "POST",
            data: JSON.stringify(person),
            contentType: "application/json",
            success: function() {
                $("#message").text("Person added");
            },
            error: function() {
            	$("#message").text("Person NOT added");
            }
        });
	});
	
	$("#add-car").click(function() {
		var car = {
				"make": $("#make").val(),
				"model": $("#model").val(),
				"yop": parseInt($("#yop").val())
		};
		
		$.ajax({
            url: "http://localhost:8080/restservicedemo/api/car/",
            type: "POST",
            data: JSON.stringify(car),
            contentType: "application/json",
            success: function() {
                $("#message").text("Car added");
            },
            error: function() {
            	$("#message").text("Car NOT added");
            }
        });
	});

});