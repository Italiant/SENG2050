/*  Name: Thomas Miller
 *  Student Number: C3279309
 *  Date: 31/03/2019
 *  Course: SENG2050, Assignment 1
 *  JS: validate
*/

function validate(){
	/*get parameters by name(arrays) and ID (single parameter)*/
	 var name = document.getElementById("username");
	 
	var resultStatus = true;
	var messageStr = "The following errors were detected:\n"; // errors will be added to this string and displayed if there are errors
	
	/*if no errors detected begin error checking*/
	if(resultStatus){
		if(name){
			var nameValue = name.value;
			if(nameValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no username given\n";
			}
			if(isNaN(nameValue)){
				
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
		}
	}
	if(!resultStatus){
		alert(messageStr); // display error messages if there are any 
	}
	return resultStatus; // will prevent submissions if there are errors 
}