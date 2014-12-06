package testPackage;

import static org.junit.Assert.*;
import kerntaak2.*;

import org.junit.Test;

public class TestClass {

	private static SellerDAO SD = new SellerDAO();
	private static PersonDAO PD = new PersonDAO();
	private static LoginDAO LD = new LoginDAO();
	private static PasswordDAO PDA = new PasswordDAO();
	private String email;
	private String password;

	@Test
	public void testUpdateBlockedSeller() {
		System.out.println("Test 1.");
		Seller blockedSeller = SD.findSeller("LA@webmail.com");
		blockedSeller.setBlocked(0);

		if (blockedSeller.getBlocked() != 0) {
			fail("Helaas, seller niet gedeblokkeerd.");
		} else {
			System.out.println("Test geslaagd.");
		}

	}

	@Test
	public void testUpdateToBlockSeller() {
		System.out.println("Test 2.");
		Seller sellerToBlock = SD.findSeller("LA@webmail.com");
		sellerToBlock.setBlocked(1);

		if (sellerToBlock.getBlocked() != 1) {
			fail("Helaas, seller niet geblokkeerd.");
		} else {
			System.out.println("Test geslaagd.");
		}
	}

	@Test
	public void testFillLoginValues() {
		System.out.println("Test 3.");
		Login dataToFill = new Login();
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Seller seller = SD.findSeller("LA@webmail.com");

		dataToFill.setDate(sqlDate);
		dataToFill.setFirstName(seller.getFirstName());
		dataToFill.setLastName(seller.getLastName());
		dataToFill.setEmailAddress(seller.getEmailAddress());

		if (dataToFill.getDate() != sqlDate
				|| !dataToFill.getFirstName().equals("Laura")
				|| !dataToFill.getLastName().equals("van den Heuvel")
				|| !dataToFill.getEmailAddress().equals("LA@webmail.com")) {
			fail("Helaas, data niet goed opgeslagen");
		} else {
			System.out.println("Test geslaagd.");
		}

	}

	@Test
	public void testRol1() {
		System.out.println("Test 4");

		email = "LA@webmail.com";

		Seller loginSeller = SD.findSeller(email);
		Person loginPerson = PD.findPerson(email);
		Password rightPassword = PDA.findPassword(email);

		if (loginSeller != null && loginPerson == null) {
			System.out.println("Dit is een verkoper.");
		} else {
			fail("Geen gegevens persoon/verkoper");
		}
	}

	@Test
	public void testRol2() {
		System.out.println("Test 5");

		email = "EP@webmail.com";

		Seller loginSeller = SD.findSeller(email);
		Person loginPerson = PD.findPerson(email);

		if (loginPerson != null && loginSeller == null) {
			System.out.println("Dit is een persoon.");
			int admin = loginPerson.getAdmin();
			if (admin == 1) {
				System.out.println("Deze persoon is een admin.");
			} else {
				System.out.println("Deze persoon is geen admin.");
			}

		} else {
			fail("Geen gegevens persoon/verkoper");
		}
	}

	@Test
	public void testRol3() {
		System.out.println("Test 4.");

		email = "Truus@webmail.com";

		Seller loginSeller = SD.findSeller(email);
		Person loginPerson = PD.findPerson(email);

		if (loginPerson != null && loginSeller == null) {
			System.out.println("Dit is een persoon.");
			int admin = loginPerson.getAdmin();
			if (admin == 1) {
				System.out.println("Deze persoon is een admin.");
			} else {
				System.out.println("Deze persoon is geen admin.");
			}

		} else {
			fail("Geen gegevens persoon/verkoper");
		}
	}

}
