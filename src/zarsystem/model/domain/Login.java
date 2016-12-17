package zarsystem.model.domain;

public class Login {
	String[] user = { "admin", "KleberDora", "RodrigoZar" };

	String pass = "admin";

	public String getUser(int i) {
		return user[i];

	}

	public void setUser(String user, int i) {
		this.user[i] = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean autenticate(String user, String pass){
		if((user.equals(this.user[0]) || user.equals(this.user[1]) || user.equals(this.user[2])) && pass.equals(this.pass))
			return true; //temporario
		else
			return false;
	}
}
