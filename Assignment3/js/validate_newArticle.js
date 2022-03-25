function validate()
{
	var title = document.getElementById("issueTitle"); // title
	var description = document.getElementById("issueDescription"); // description
	var resultStatus = true;
	var messageStr = "The following errors were detected:\n"; 

/*if no errors detected begin error checking*/
	if(resultStatus){
		if(title){
			var titleValue = title.value;
			if(titleValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no issue title  given\n";
			}
			if(isNaN(titleValue)){ // is not a number 
				
			}else{ // if it is a number then error because it must be letters only 
				resultStatus = false;
				messageStr += "- issue title must not be a number\n";
			}
			/*method for checking if the input string contains any special characters which causes an error*/
			var splChars = "*|,\":<>[]{}`\';()@&$#%"; 
			var flag = false;
			for(var i = 0; i < titleValue.length; i++){
				if(splChars.indexOf(titleValue.charAt(i)) != -1){ // loops through every character in the string and checks for matches
					flag = true;
				}
			}
			if(flag == true)
			{
				resultStatus = false;
				messageStr += "- issue title must not include any special characters\n";
			}
			if(titleValue.length > 60)
			{
				resultStatus = false;
				messageStr += "- title must not be longer than 60 characters\n";
			}
		}
		if(description)
		{
			var descValue = description.value;
			if(descValue == ""){ // is required so a null input is an error
				resultStatus = false;
				messageStr += "- no description given\n";
			}
			if(isNaN(descValue)){
				
			}else{ // if it is a number then error because it must be letters only 
				resultStatus = false;
				messageStr += "- description must not be a number\n";
			}
			/*method for checking if the input string contains any special characters which causes an error*/
			var splChars = "*|,\":<>[]{}`\';()@&$#%"; 
			var flag = false;
			for(var i = 0; i < descValue.length; i++){
				if(splChars.indexOf(descValue.charAt(i)) != -1){ // loops through every character in the string and checks for matches
					flag = true;
				}
			}
			if(flag == true)
			{
				resultStatus = false;
				messageStr += "- description must not include any special characters\n";
			}
			if(descValue.length > 200)
			{
				resultStatus = false;
				messageStr += "- description must not be longer than 200 characters\n";
			}
		}
	}
	if(!resultStatus){
		alert(messageStr); // display error messages if there are any 
	}
	return resultStatus; // will prevent submissions if there are errors 
}