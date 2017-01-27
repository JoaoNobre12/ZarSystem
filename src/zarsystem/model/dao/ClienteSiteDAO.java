package zarsystem.model.dao;

import zarsystem.model.domain.ClienteSite;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de DAO dos alunos cadastrados pelo site
 * Created by joaov on 10/12/2016.
 */
public class ClienteSiteDAO extends Dao{
    /**
     * Consulta geral à tabela de clientes cadastrados pelo site
     * @return List<ClienteSite> com todos os clientes da tabela
     * */
    public List<ClienteSite> consultDb(){
        String query = "SELECT * FROM site_clientes";
        List<ClienteSite> listAluno = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                ClienteSite clienteSite = new ClienteSite();
                clienteSite.setCodCliente(result.getInt("cod_cliente_site"));
                clienteSite.setNome(result.getString("nome"));
                clienteSite.setEmail(result.getString("email"));
                clienteSite.setPlano(result.getString("plano"));
                clienteSite.setDuvida(result.getString("duvida"));

                listAluno.add(clienteSite);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAluno;
    }

    public void deleteEmail(int codSiteClientes) throws SQLException{
        String queryAvFisica = "DELETE FROM site_clientes WHERE cod_cliente_site = " + codSiteClientes;

        PreparedStatement statement = connection.prepareStatement(queryAvFisica);
        statement.execute();
    }
}
