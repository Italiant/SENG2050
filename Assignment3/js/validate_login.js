function validate()
{
	var name = document.getElementById("userId");
	var pass = document.getElementById("password");
	var resultStatus = true;
	var messageStr = "The following errors were detected:\n"; 

/*if no errors detected begin error checking*/
	if(resultStatus){
		if(name){
			var nameValue = name.value;
			if(nameValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no username given\n";
			}
			if(isNaN(nameValue)){ // is not a number 
				
			}else{ // if it is a number then error because it must be letters only 
				resultStatus = false;
				messageStr += "- username must not be a number\n";
			}
			/*method for checking if the input string contains any special characters which causes an error*/
			var splChars = "*|,\":<>[]{}`\';()@&$#%"; 
			var flag = false;
			for(var i = 0; i < nameValue.length; i++){
				if(splChars.indexOf(nameValue.charAt(i)) != -1){ // loops through every character in the string and checks for matches
					flag = true;
				}
			}
			if(flag == true)
			{
				resultStatus = false;
				messageStr += "- username must not include any special characters\n";
			}
			if(nameValue.length > 20)
			{
				resultStatus = false;
				messageStr += "- username must not be longer than 20 characters\n";
			}
		}
		if(pass)
		{
			var passValue = pass.value;
			if(passValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no password given\n";
			}
			if(isNaN(passValue)){
				
			}else{ // if it is a number then error because it must be letters only 
				resultStatus = false;
				messageStr += "- password must not be a number\n";
			}
			/*method for checking if the input string contains any special characters which causes an error*/
			var splChars = "*|,\":<>[]{}`\';()@&$#%"; 
			var flag = false;
			for(var i = 0; i < passValue.length; i++){
				if(splChars.indexOf(passValue.charAt(i)) != -1){ // loops through every character in the string and checks for matches
					flag = true;
				}
			}
			if(flag == true)
			{
				resultStatus = false;
				messageStr += "- password must not include any special characters\n";
			}
			if(passValue.length > 20)
			{
				resultStatus = false;
				messageStr += "- password must not be longer than 20 characters\n";
			}
		}
	}
	if(!resultStatus){
		alert(messageStr); // display error messages if there are any 
	}
	return resultStatus; // will prevent submissions if there are errors 
}