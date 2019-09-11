$(document).ready(function modalZeigen(){
	/*$('#adminNavbar').load('html/adminNavbar.html');*/
	if('${add}'){
		$('#addModal').modal('show');
	}
	if('${edit}'){
		$('#editModal').modal('show');
	}
});
