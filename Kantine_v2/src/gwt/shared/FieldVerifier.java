package gwt.shared;

import java.util.List;
import com.google.gwt.user.client.Window;

/**
 * Fieldverifier for validating user input
 * 
 */
public class FieldVerifier {


	/**
	 * Validate name 
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name) {
		//Validate lenght 
		if (name.length() < 2) {
			Window.alert("Skal mindst bestå af 2 tegn");
			return false;
		}

		// Validate if name only consist of numbers
		if (name.matches("[0-9]+")){
			Window.alert("Må ikke kun indeholde tal");
			return false;
		}

		else {
			return true;
		}
	}

	/**
	 * Validate email 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		// Validate if email is valid. 
		if (!email.contains("@") && (email.length() < 3)) {
			Window.alert("Indtast venligst en emailadresse");
			return false;
		}

		else {
			String parts[] = email.split("@");

			if (!(parts[0].length() > 1) && !(parts[1].length() > 1)) {

				return false;
			} else {
				if (!parts[1].contains(".")) {
					return false;
				}

				else {
					return true;
				}
			}

		}

	}

	/**
	 * Validate password. A password, must contain special characters, and ateleast 1 number. 
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password) {

		char[] specialCh = { '!', '@', ']', '#', '$', '%', '^', '&', '*' };
		boolean hasSpecialChar = false;


		if (password == null) {
			Window.alert("Du mangler at indtaste et kodeord");
			return false;
		} else if (!password.matches(".*\\d.*")) {
			Window.alert("Koden skal bestå at mindst et tal");
			return false;
		} else if (password.length() < 6) {
			Window.alert("Koden skal mindst bestå af 6 tegn");
			return false;
		} else if (password.equals(password.toLowerCase())) {
			Window.alert("Koden skal bestå af mindst et stort bogstav");
			return false;
		} else if (password.equals(password.toUpperCase())) {
			Window.alert("Koden skal bestå af mindst et lille bogstav");
			return false;
		} else {
			for (int i = 0; i < password.length(); i++) {
				for (Character c : specialCh) {
					if (password.charAt(i) == c)
						hasSpecialChar = true;
				}
			}

			if (hasSpecialChar) {
				return true;
			} else {
				Window.alert("Koden skal bestå at mindst et special tegn");
				return false;
			}

		}

	}

	/**
	 * Checks if user already exists. 
	 * @param result
	 * @param name
	 * @return
	 */
	public static boolean isUserAlreadyThere(List<PersonDTO> result, String name) {

		for (PersonDTO person : result) {
			if ((name.equals(person.getName()))) {
				Window.alert(person.getName() + " findes allerede i systemet");
				return false;
			}
		}
		return true;
	}


	/**
	 * Checks if saldo is valid. 
	 * @param number
	 * @return
	 */

	public static boolean isValidSaldo(String number) {
		double numberConvert;
		// check if saldo field is empty (not allowed)
		if (number.isEmpty()) {
			return false;
		} 

		// check if age field contains a number
		try {
			// try to convert to a number
			numberConvert = Double.parseDouble(number);
		} catch (NumberFormatException e) {
			Window.alert("Saldo må ikke indholde tal");
			return false;
		}

		if(numberConvert < 0)
		{
			Window.alert("Må ikke være under nul");
			return false;
		}
		else
		{
			return true;
		}


	}

	/**
	 * Checks if price is valid. 
	 * @param number
	 * @return
	 */
	public static boolean isValidPrice(String number) {
		double numberConvert;
		// check if saldo field is empty (not allowed)
		if (number.isEmpty()) {
			return false;
		} 

		// check if age field contains a number
		try {
			// try to convert to a number
			numberConvert = Double.parseDouble(number);
		} catch (NumberFormatException e) {
			Window.alert("Prisen må kun indeholde tal");
			return false;
		}

		if(numberConvert < 0)
		{
			Window.alert("Prisen må ikke være under nul");
			return false;
		}
		else
		{
			return true;
		}

	}

}
