//Verhoeff Aadhar Alogrithm
//multiplication table
const d = [
	[0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
	[1, 2, 3, 4, 0, 6, 7, 8, 9, 5], 
	[2, 3, 4, 0, 1, 7, 8, 9, 5, 6], 
	[3, 4, 0, 1, 2, 8, 9, 5, 6, 7], 
	[4, 0, 1, 2, 3, 9, 5, 6, 7, 8], 
	[5, 9, 8, 7, 6, 0, 4, 3, 2, 1], 
	[6, 5, 9, 8, 7, 1, 0, 4, 3, 2], 
	[7, 6, 5, 9, 8, 2, 1, 0, 4, 3], 
	[8, 7, 6, 5, 9, 3, 2, 1, 0, 4], 
	[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
	]

//permutation table
const p = [
	[0, 1, 2, 3, 4, 5, 6, 7, 8, 9], 
	[1, 5, 7, 6, 2, 8, 3, 0, 9, 4], 
	[5, 8, 0, 3, 7, 9, 6, 1, 4, 2], 
	[8, 9, 1, 6, 0, 4, 3, 5, 2, 7], 
	[9, 4, 5, 3, 1, 2, 6, 8, 7, 0], 
	[4, 2, 8, 6, 5, 7, 3, 9, 0, 1], 
	[2, 7, 9, 3, 8, 0, 6, 4, 1, 5], 
	[7, 0, 4, 6, 9, 1, 3, 2, 5, 8]
	]

//validates Aadhar number received as string
function AadharValidate() {
	var errorLabel = document.getElementById("appaadharno");
	var aadharNumber = document.getElementById("appAadharNo").value;
	if(aadharNumber.length == 0 || aadharNumber.length != 12){
		errorLabel.innerHTML = "Aadhaar number is required.";
		errorLabel.style.cssText = "color: red !important";
		return false;
	}else{
		let c = 0;
		let invertedArray = aadharNumber.split('').map(Number).reverse();

		invertedArray.forEach((val, i) => {
			c = d[c][p[(i % 8)][val]]
		})

		if(c === 0){
			errorLabel.innerHTML = "Valid Aadhaar number.";
			errorLabel.style.cssText = "color: green !important";
			return true;
		}else{
			errorLabel.innerHTML = "Please enter valid Aadhaar number.";
			errorLabel.style.cssText = "color: red !important";
			document.getElementById('appAadharNo').focus();
			return false;
		}
	}

}