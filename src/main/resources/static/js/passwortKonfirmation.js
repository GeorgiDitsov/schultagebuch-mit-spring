$('#passwort, #konfirm_passwort').on('keyup', function () {
	if ($('#passwort').val() == $('#konfirm_passwort').val()) {
		this.setCustomValidity('');
	} else {
		this.setCustomValidity('Not matched');
	}
});