package gwt.shared;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client-side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least four
	 * characters. In your application, you can use more complex checks to
	 * ensure that usernames, passwords, email addresses, URLs, and other fields
	 * have the proper syntax.
	 * 
	 * @param name
	 *            the name to validate
	 * @return true if valid, false if invalid
	 */

	public static boolean isValidName(String name) {
		if (name.length() < 3) {
			Window.alert("Brugernavnet skal mindst bestå af 3 tegn");
			return false;
		}

		else {

			return true;
		}
	}

	public static boolean isValidEmail(String email) {

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

	public static boolean isValidPassword(String password) {
		if (password == null) {
			Window.alert("Du mangler at indtaste et kodeord");
			return false;
		} else if (password.length() < 6) {
			Window.alert("Koden skal mindst bestå af 6 tegn");
			return false;
		} else if (password.matches("[A-Za-z0-9 ]*")) {
			Window.alert("Koden skal mindst indholde mindst et special tegn");
			return false;
		} else if (password.equals(password.toLowerCase())) {
			Window.alert("Koden skal bestå af mindst et stort bogstav");
			return false;
		} else if (password.equals(password.toUpperCase())) {
			Window.alert("Koden skal bestå af mindst et lille bogstav");
			return false;
		}

		else {
			return true;
		}

	}

	public static boolean isUserAlreadyThere(List<PersonDTO> result, String name) {

		for (PersonDTO person : result) {
			if ((name.equals(person.getName()))) {
				Window.alert(person.getName() + " findes allerede i systemet");
				return false;
			}
		}
		return true;
	}

	public static boolean isItemAlreadyThere(List<ItemDTO> result, String name) {

		for (ItemDTO item : result) {
			if ((name.equals(item.getName()))) {
				Window.alert(item.getName() + " findes allerede i systemet");
				return false;
			}
		}
		return true;
	}

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
	      Window.alert("Navnet må ikke indholde tal");
	      return false;
	    }
	    
	    if(numberConvert < 1)
	    {
	    	Window.alert("Må ikke være under nul");
	    	return false;
	    }
	    else
	    {
			return true;
	    }
		

	}

}
