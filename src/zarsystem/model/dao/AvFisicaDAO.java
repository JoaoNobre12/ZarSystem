package zarsystem.model.dao;

import zarsystem.model.domain.Aluno;
import zarsystem.model.domain.AvaliacaoFisica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by joao on 04/10/16.
 * Classe Data Access Object da Avaliação Física
 */
public class AvFisicaDAO extends Dao{

    public boolean insertDB(AvaliacaoFisica avaliacaoFisica, Aluno aluno){

        String query = "INSERT INTO av_fisica (altura, flexao, abdominal, gordura, pescoco, quadril, " + //6
                "braco_direito, braco_esquerdo, antebraco_direito, antebraco_esquerdo, coxa_direita, coxa_esquerda, " + //6
                "panturrilha_direita, panturrilha_esquerda, abdomen, torax, cintura, metabolismo_treino, metabolismo_basal, peso, cod_matricula)" + //9
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //21

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDouble(1, avaliacaoFisica.getAltura()); //MUDAR
            statement.setInt(2, avaliacaoFisica.getFlexao());
            statement.setInt(3, avaliacaoFisica.getAbdominal());
            statement.setDouble(4, avaliacaoFisica.getGordura());
            statement.setDouble(5, avaliacaoFisica.getPescoco());
            statement.setDouble(6, avaliacaoFisica.getQuadril());

            statement.setDouble(7, avaliacaoFisica.getBracoDireito());
            statement.setDouble(8, avaliacaoFisica.getBracoEsquerdo());
            statement.setDouble(9, avaliacaoFisica.getAntebracoDireito());
            statement.setDouble(10, avaliacaoFisica.getAntebracoEsquerdo());
            statement.setDouble(11, avaliacaoFisica.getCoxaDireita());
            statement.setDouble(12, avaliacaoFisica.getCoxaEsquerda());

            statement.setDouble(13, avaliacaoFisica.getPanturrilhaDireita());
            statement.setDouble(14, avaliacaoFisica.getPanturrilhaEsquerda());
            statement.setDouble(15, avaliacaoFisica.getAbdomen());
            statement.setDouble(16, avaliacaoFisica.getTorax());
            statement.setDouble(17, avaliacaoFisica.getCintura());
            statement.setDouble(18, avaliacaoFisica.getMetabolismoTreino());
            statement.setDouble(19, avaliacaoFisica.getMetabolismoBasal());
            statement.setDouble(20, avaliacaoFisica.getPeso());
            statement.setInt(21, aluno.getCodMatricula());

            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AvaliacaoFisica consultAvFisica(int codAluno){
        String query = "SELECT * FROM av_fisica WHERE cod_matricula = '" + codAluno + "' ";

        try {
            AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                avaliacaoFisica.setCodAvFisica(result.getInt("codavfisica"));
                avaliacaoFisica.setAltura(result.getDouble("altura"));
                avaliacaoFisica.setFlexao(result.getInt("flexao"));
                avaliacaoFisica.setAbdominal(result.getInt("abdominal"));
                avaliacaoFisica.setGordura(result.getDouble("gordura"));
                avaliacaoFisica.setPescoco(result.getDouble("pescoco"));
                avaliacaoFisica.setQuadril(result.getDouble("quadril"));
                avaliacaoFisica.setBracoDireito(result.getDouble("braco_direito"));
                avaliacaoFisica.setBracoEsquerdo(result.getDouble("braco_esquerdo"));
                avaliacaoFisica.setAntebracoDireito(result.getDouble("antebraco_direito"));
                avaliacaoFisica.setAntebracoEsquerdo(result.getDouble("antebraco_esquerdo"));
                avaliacaoFisica.setCoxaDireita(result.getDouble("coxa_direita"));
                avaliacaoFisica.setCoxaEsquerda(result.getDouble("coxa_esquerda"));
                avaliacaoFisica.setPanturrilhaDireita(result.getDouble("panturrilha_direita"));
                avaliacaoFisica.setPanturrilhaEsquerda(result.getDouble("panturrilha_esquerda"));
                avaliacaoFisica.setAbdomen(result.getDouble("abdomen"));
                avaliacaoFisica.setTorax(result.getDouble("torax"));
                avaliacaoFisica.setCintura(result.getDouble("cintura"));
                avaliacaoFisica.setMetabolismoTreino(result.getDouble("metabolismo_treino"));
                avaliacaoFisica.setMetabolismoBasal(result.getDouble("metabolismo_basal"));
                avaliacaoFisica.setPeso(result.getDouble("peso"));

            }

            return avaliacaoFisica;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update na tabela de avaliação física
     * */
    public void updateAvaliacaoFisica(int codAluno, AvaliacaoFisica avaliacaoFisica) throws SQLException{
        String query =
                "UPDATE av_fisica SET altura = ?, flexao = ?, abdominal = ?, gordura = ?, pescoco = ?, " +
                "quadril = ?, braco_direito = ?, braco_esquerdo = ?, antebraco_direito = ?, antebraco_esquerdo = ?, " +
                "coxa_direita = ?, coxa_esquerda = ?, panturrilha_direita = ?, panturrilha_esquerda = ?, abdomen = ?, " +
                "torax = ?, cintura = ?, metabolismo_treino = ?, metabolismo_basal = ?, peso = ? " +
                        "WHERE cod_matricula = '"+codAluno+"';";
        System.out.println(query);

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setDouble(1, avaliacaoFisica.getAltura()); //MUDAR
        statement.setInt(2, avaliacaoFisica.getFlexao());
        statement.setInt(3, avaliacaoFisica.getAbdominal());
        statement.setDouble(4, avaliacaoFisica.getGordura());
        statement.setDouble(5, avaliacaoFisica.getPescoco());
        statement.setDouble(6, avaliacaoFisica.getQuadril());

        statement.setDouble(7, avaliacaoFisica.getBracoDireito());
        statement.setDouble(8, avaliacaoFisica.getBracoEsquerdo());
        statement.setDouble(9, avaliacaoFisica.getAntebracoDireito());
        statement.setDouble(10, avaliacaoFisica.getAntebracoEsquerdo());
        statement.setDouble(11, avaliacaoFisica.getCoxaDireita());
        statement.setDouble(12, avaliacaoFisica.getCoxaEsquerda());

        statement.setDouble(13, avaliacaoFisica.getPanturrilhaDireita());
        statement.setDouble(14, avaliacaoFisica.getPanturrilhaEsquerda());
        statement.setDouble(15, avaliacaoFisica.getAbdomen());
        statement.setDouble(16, avaliacaoFisica.getTorax());
        statement.setDouble(17, avaliacaoFisica.getCintura());
        statement.setDouble(18, avaliacaoFisica.getMetabolismoTreino());
        statement.setDouble(19, avaliacaoFisica.getMetabolismoBasal());
        statement.setDouble(20, avaliacaoFisica.getPeso());

        statement.execute();
    }

}
