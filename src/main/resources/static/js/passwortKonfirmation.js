var password = document.getElementById("passwort")
  , confirm_password = document.getElementById("konfirm_passwort");

function validatePassword(){
  if(password.value != confirm_password.value) {
	  confirm_password.setCustomValidity([[#{message.passwords.not.matched}]]);
  } else {
    confirm_password.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;