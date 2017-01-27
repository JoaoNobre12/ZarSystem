package zarsystem.model.domain;

import zarsystem.model.dao.LoginDao;

import java.util.List;

public class Login {

	String user;
	String pass;
    String systemKey;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    /**
     * Faz a autenticação comparando os parâmetros com os do banco de dados
     * @param user usuário que foi passado pelo usuário
     * @param pass senha que esse usuário passou
     * @return true se ocorreu com sucesso, false se não foi feito com sucesso
     * */
    public boolean authenticate(String user, String pass){
        LoginDao loginDao = new LoginDao();
        List<Login> logins = loginDao.consultDb();
        boolean success = false;

        for (Login l : logins){
            if(l.getUser().equalsIgnoreCase(user)) {
                if (l.getPass().equals(pass)){
                    success = true;
                    break;
                }
            }
        }

        return success;
    }
}
