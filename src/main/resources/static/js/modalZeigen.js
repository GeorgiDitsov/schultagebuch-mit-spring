$(document).ready(function modalZeigen(){
	if('${add}'){
		$('#addModal').modal('show');
	}
	if('${edit}'){
		$('#editModal').modal('show');
	}
	if('${showSchulerfolg}'){
		$('#kindSchulerfolgModal').modal('show');
	}
});