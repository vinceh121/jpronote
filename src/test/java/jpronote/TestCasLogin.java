package jpronote;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.http.auth.AuthenticationException;
import org.junit.Test;

import me.vinceh121.jpronote.JPronote;
import me.vinceh121.jpronote.SessionType;

public class TestCasLogin {

	@Test
	public void test() throws HeadlessException, IOException, AuthenticationException {
		JPronote api = new JPronote("https://0310024h.index-education.net/pronote", SessionType.STUDENT);
		api.loginCas("https://cas.entmip.fr/login", JOptionPane.showInputDialog("Username"),
				JOptionPane.showInputDialog("Password"), "TOULO-ENT_parent_eleve");
	}

}
