$(document).ready(function() {
	$("#locales").change(function () {
	    var selectedOption = $('#locales').val();
	    if (selectedOption != ''){
	        window.location.replace('home?lang=' + selectedOption);
	    }
	});
});