package utilities;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

public final class FormValidation {
	
	
	private JTextField fName,lName,fNameFather,lNameFather;
	private JTextField number,email,remail;
	private JTextField country,state,city,zipcode;
	private JTextField password,repassword;
	private JComboBox<String> title,gender,nation;
	private JDatePickerImpl datePicker;
	private JCheckBox check;
	private String error;
	public FormValidation(){
		error = "";
	}
	
	// This function intializes the textfields
	public void intializeTextFields(JTextField ...textfields) {
		
		fName = textfields[0];
		lName = textfields[1];
		fNameFather = textfields[2];
		lNameFather = textfields[3];
		number = textfields[4];
		email = textfields[5];
		remail = textfields[6];
		country = textfields[7];
		state  = textfields[8];
		city = textfields[9];
		zipcode = textfields[10];
		password = textfields[11];
		repassword = textfields[12];
	}
	
	// This function intializes the combobox
	public void initializeJComboBox(JComboBox<String> ...comboboxes) {
		
		title = comboboxes[0];
		gender = comboboxes[1];
		nation = comboboxes[2];
		
	}
	
	// This function intializes the Datepicker
    public void initializeDatePicker(JDatePickerImpl datePicker) {
    	this.datePicker = datePicker;
	}
    
    // This function intializes the Checkbox
    public void initializeCheckbox(JCheckBox check) {
    	this.check = check;
	}
    
    
    // Function for validation
	public int startValidation() {
		
		//Checking if Title is not selected
		if(title.getSelectedItem().equals("Please select..")) {
			error += "Title must be selected !\n";
		}
		
		//Checking if Title is not selected
		if(gender.getSelectedItem().equals("Please select..")) {
			error += "Gender must be selected !\n";
		}
	
		//Checking if the firtsname is empty
		if(fName.getText().trim().isEmpty()) {
			error += "First name must be entered !\n";
		}
		
		//Checking if firstsname is only in alphabet
		else if(!fName.getText().trim().matches("^[a-zA-Z]*$")) {
			error  += "First name must be in alphabets only !\n";
		}
		
		//Checking if the Lastname is empty
		if(lName.getText().trim().isEmpty()) {
			error += "Last name must be entered !\n";
		}
		//Checking if lastname is alphabetic only
		else if(!lName.getText().trim().matches("^[a-zA-Z]*$")) {
			error  += "Last name must be in alphabets only !\n";
		}
		
		//Checking if DOB is not selected
		if(datePicker.getJFormattedTextField().getText().isEmpty()) {
			error += "Date must be selected !\n";
		}
		
		//Checking if the Father firtsname is empty
		if(fNameFather.getText().trim().isEmpty()) {
			error += "Father's First name must be entered !\n";
		}
		//Checking if father first name is alphabetic only
		else if(!fNameFather.getText().trim().matches("^[a-zA-Z]*$")) {
			error  += "Fathers's First name must be in alphabets only !\n";
		}
		
		//Checking if the Father Lastname is empty
		if(lNameFather.getText().trim().isEmpty()) {
			error += "Father's Last name must be entered !\n";
		}
		//Checking if father last name is alphabetic only
		else if(!lNameFather.getText().trim().matches("^[a-zA-Z]*$")) {
			error  += "Fathers's Last name must be in alphabets only !\n";
		}
		
		//Checking if Title is not selected
		if(nation.getSelectedItem().equals("Please select..")) {
			error += "Nationlity must be selected !\n";
		}
		
		//Checking if the Lastname is empty
		if(number.getText().trim().isEmpty()) {
			error += "Phone number must be entered !\n";
		}
		//Checking if number is numeric only
		else if(!number.getText().trim().matches("^[0-9]*$")) {
			error  += "Number must be numeric digits only !\n";
		}
		
		//Checking if the email is empty
		if(email.getText().trim().isEmpty()) {
			error += "E-mail address must be entered !\n";
		}
		
		//Checking if the re - email is empty
		if(remail.getText().trim().isEmpty()) {
			error += "Re-enter email must be entered !\n";
		}
		//Checking if email and remail is not identical
		else if(!email.getText().trim().equals(remail.getText().trim())) {
			error += "Email and Re-enter email must be same !\n";
		}
		
		//Checking if password is empty
		if(password.getText().trim().isEmpty()) {
			error += "Password must be entered !\n";
		}
		//Checking if password is less than 8 character
		else if(password.getText().trim().length() < 8) {
			error += "Password must be 8 or more character long !\n";
		}
		
		//Checking if re-password is empty
		if(repassword.getText().trim().isEmpty()) {
			error += "Re-Password must be entered !\n";
		}
		//Checking if password and re-password is not same
		else if(!password.getText().trim().equals(repassword.getText().trim())) {
			error += "Password and re-password must be same !\n";
		}
		
		//Checking if the Country name is empty
		if(country.getText().trim().isEmpty()) {
			error += "Country name must be entered !\n";
		}
				
		//Checking if Country name is only in alphabet
		else if(!country.getText().trim().matches("^[a-zA-Z ]*$")) {
			error  += "Country name must be in alphabets only !\n";
		}
		
		//Checking if the City name is empty
		if(city.getText().trim().isEmpty()) {
			error += "City name must be entered !\n";
		}
						
		//Checking if City is only in alphabet
		else if(!city.getText().trim().matches("^[a-zA-Z ]*$")) {
			error  += "City name must be in alphabets only !\n";
		}
		
		//Checking if the state name is empty
		if(state.getText().trim().isEmpty()) {
			error += "State name must be entered !\n";
		}
								
		//Checking if state is only in alphabet
		else if(!state.getText().trim().matches("^[a-zA-Z ]*$")) {
			error  += "State name must be in alphabets only !\n";
		}
		
		//Checking if the Zipcode is empty
		if(zipcode.getText().trim().isEmpty()) {
			error += "Zipcode must be entered !\n";
			}
		//Checking if zipcode is numerical only
		else if(!zipcode.getText().trim().matches("^[0-9]*$")) {
			error  += "Zipcode must be numeric digits only !\n";
		}
		
		//Checking if declration checkbox is checked or not
		if(!check.isSelected()) {
			error += "Checkbox must be selected !";
		}
		
		
		if(error.isEmpty()) {
			return 1;
		}
		else {
			JOptionPane.showMessageDialog(null, error);
			return 0;
		}
	}
}
