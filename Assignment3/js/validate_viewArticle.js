function validate()
{
	var comment = document.getElementById("commentArea");

	var resultStatus = true;
	var messageStr = "The following errors were detected:\n"; 

/*if no errors detected begin error checking*/
	if(resultStatus){
		if(comment){
			var commentValue = comment.value;
			if(commentValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no comment given\n";
			}
			if(isNaN(commentValue)){ // is not a number 
				
			}else{ // if it is a number then error because it must be letters only 
				resultStatus = false;
				messageStr += "- comment must not be a number\n";
			}
			/*method for checking if the input string contains any special characters which causes an error*/
			var splChars = "*|,\":<>[]{}`\';()@&$#%"; 
			var flag = false;
			for(var i = 0; i < commentValue.length; i++){
				if(splChars.indexOf(commentValue.charAt(i)) != -1){ // loops through every character in the string and checks for matches
					flag = true;
				}
			}
			if(flag == true)
			{
				resultStatus = false;
				messageStr += "- comment must not include any special characters\n";
			}
			if(commentValue.length > 100)
			{
				resultStatus = false;
				messageStr += "- comments must not be longer than 100 characters\n";
			}
		}
	}
	if(!resultStatus){
		alert(messageStr); // display error messages if there are any 
	}
	return resultStatus; // will prevent submissions if there are errors 
}