package frontend;

import javax.swing.SwingUtilities;

import backend.Database;

public class MainApplication {

	public static void main(String[] args) {
		try {
			Database.loadEverything();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginRegisterScreen loginRegister = new LoginRegisterScreen();
		loginRegister.startGUI();

	}

}
