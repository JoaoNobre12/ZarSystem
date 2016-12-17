package zarsystem.model.domain;

/**Classe de domínio do usuário do software
 * Created by joao on 07/11/16.
 */
public class User {
    String tipo;

    public User() {
        System.out.println("usuário criado " + this.getClass().getCanonicalName());
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
