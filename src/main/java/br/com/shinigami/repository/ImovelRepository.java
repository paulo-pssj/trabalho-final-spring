package br.com.shinigami.repository;


import br.com.shinigami.config.ConexaoBancoDeDados;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImovelRepository implements Repositorio<Imovel>{

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_imovel.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Imovel create(Imovel imovel) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            imovel.setAtivo(Tipo.S);

            Integer imovelProxId = this.getProximoId(con);
            imovel.setIdImovel(imovelProxId);
            imovel.setAlugado(Tipo.N);

            String sql = "INSERT INTO IMOVEL\n" +
                    "(id_imovel, valor_mensal, condominio, alugado, qntd_quartos, qntd_banheiros, tipo_imovel," +
                    " id_endereco, id_cliente, permite_animais, salao_de_festas,area_de_lazer,garagem, ativo, numero_de_vagas)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);


            stmt.setInt(1, imovel.getIdImovel());
            stmt.setDouble(2, imovel.getValorMensal());
            stmt.setDouble(3, imovel.getCondominio());
            stmt.setString(4, imovel.getAlugado().toString());
            stmt.setInt(5, imovel.getQntdQuartos());
            stmt.setInt(6, imovel.getQntdBanheiros());
            stmt.setInt(7, imovel.getTipoImovel().ordinal());
            stmt.setInt(8, imovel.getIdEndereco());
            stmt.setInt(9, imovel.getIdDono());
            stmt.setString(10, ("N"));
            stmt.setString(11, ("N"));
            stmt.setString(12, imovel.getAreaDeLazer().toString());
            stmt.setString(13, imovel.getGaragem().toString());
            stmt.setString(14, imovel.getAtivo().toString());
            stmt.setInt(15, 0);


            stmt.executeUpdate();
            return imovel;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE IMOVEL SET ativo = ? WHERE id_imovel = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "N");
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Imovel update(Integer id, Imovel imovel) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();


            sql.append("UPDATE IMOVEL SET");
            sql.append(" valor_mensal = ?,");
            sql.append(" condominio = ?,");
            sql.append(" qntd_quartos = ?,");
            sql.append(" qntd_banheiros = ?,");
            sql.append(" alugado = ?,");
            sql.append(" area_de_lazer = ?,");
            sql.append(" id_cliente = ?,");
            sql.append(" garagem = ?,");
            sql.append(" permite_animais = ?,");
            sql.append(" salao_de_festas = ?,");
            sql.append(" numero_de_vagas = ?,");
            sql.append(" WHERE id_imovel = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setDouble(1, imovel.getValorMensal());
            stmt.setDouble(2, imovel.getCondominio());
            stmt.setInt(3, imovel.getQntdQuartos());
            stmt.setInt(4, imovel.getQntdBanheiros());
            stmt.setString(5, imovel.getAlugado().toString());
            stmt.setString(6, imovel.getAreaDeLazer().toString());
            stmt.setInt(7, imovel.getIdDono());
            stmt.setString(8, imovel.getGaragem().toString());
            stmt.setString(9, imovel.getPermiteAnimais().toString());
            stmt.setString(10, imovel.getSalaoDeFesta().toString());
            stmt.setInt(11, imovel.getNumeroDeVagas());

            stmt.setInt(12, id);

            stmt.executeUpdate();
            return imovel;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public List<Imovel> list() throws BancoDeDadosException {
        List<Imovel> imoveis = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM IMOVEL WHERE ATIVO LIKE 'S'";
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Imovel imovel = new Imovel();

                imovel.setIdImovel(res.getInt("id_imovel"));
                imovel.setIdEndereco(res.getInt("id_endereco"));
                imovel.setValorMensal(res.getDouble("valor_mensal"));
                imovel.setCondominio(res.getDouble("condominio"));
                imovel.setAlugado(Tipo.valueOf(res.getString("alugado")));
                imovel.setQntdQuartos(res.getInt("qntd_quartos"));
                imovel.setQntdBanheiros(res.getInt("qntd_banheiros"));
                imovel.setPermiteAnimais(Tipo.valueOf(res.getString("permite_animais")));
                imovel.setSalaoDeFesta(Tipo.valueOf(res.getString("salao_de_festas")));
                imovel.setNumeroDeVagas(res.getInt("numero_de_vagas"));
                imovel.setAreaDeLazer(Tipo.valueOf(res.getString("area_de_lazer")));
                imovel.setGaragem(Tipo.valueOf(res.getString("garagem")));
                imovel.setIdDono(res.getInt("id_cliente"));
                imovel.setTipoImovel(TipoImovel.values()[(res.getInt("tipo_imovel"))]);

                imoveis.add(imovel);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getCause());
            }
        }
        return imoveis;
    }

    public List<Imovel> listarImoveisDisponiveis() throws BancoDeDadosException {
        List<Imovel> imoveis = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM IMOVEL WHERE ATIVO LIKE 'S' and ALUGADO LIKE 'N'";
            Statement stmt = con.createStatement();
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Imovel imovel = new Imovel();
                imovel.setIdImovel(res.getInt("id_imovel"));
                imovel.setIdEndereco(res.getInt("id_endereco"));
                imovel.setValorMensal(res.getDouble("valor_mensal"));
                imovel.setCondominio(res.getDouble("condominio"));
                imovel.setAlugado(Tipo.valueOf(res.getString("alugado")));
                imovel.setQntdQuartos(res.getInt("qntd_quartos"));
                imovel.setQntdBanheiros(res.getInt("qntd_banheiros"));
                imovel.setPermiteAnimais(Tipo.valueOf(res.getString("permite_animais")));
                imovel.setSalaoDeFesta(Tipo.valueOf(res.getString("salao_de_festas")));
                imovel.setNumeroDeVagas(res.getInt("numero_de_vagas"));
                imovel.setAreaDeLazer(Tipo.valueOf(res.getString("area_de_lazer")));
                imovel.setGaragem(Tipo.valueOf(res.getString("garagem")));
                imovel.setIdDono(res.getInt("id_cliente"));
                imovel.setTipoImovel(TipoImovel.values()[(res.getInt("tipo_imovel"))]);
                imoveis.add(imovel);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getCause());
            }
        }
        return imoveis;
    }

    public Imovel buscarImovel(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM IMOVEL WHERE id_imovel = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();


            if (res.next()) {
                Imovel imovel = new Imovel();

                imovel.setIdImovel(res.getInt("id_imovel"));
                imovel.setIdEndereco(res.getInt("id_endereco"));
                imovel.setValorMensal(res.getDouble("valor_mensal"));
                imovel.setCondominio(res.getDouble("condominio"));
                imovel.setAlugado(Tipo.valueOf(res.getString("alugado")));
                imovel.setQntdQuartos(res.getInt("qntd_quartos"));
                imovel.setQntdBanheiros(res.getInt("qntd_banheiros"));
                imovel.setPermiteAnimais(Tipo.valueOf(res.getString("permite_animais")));
                imovel.setSalaoDeFesta(Tipo.valueOf(res.getString("salao_de_festas")));
                imovel.setNumeroDeVagas(res.getInt("numero_de_vagas"));
                imovel.setAreaDeLazer(Tipo.valueOf(res.getString("area_de_lazer")));
                imovel.setGaragem(Tipo.valueOf(res.getString("garagem")));
                imovel.setIdDono(res.getInt("id_cliente"));
                imovel.setTipoImovel(TipoImovel.values()[(res.getInt("tipo_imovel"))]);
                return imovel;

            } else {
                throw new BancoDeDadosException("Nenhum dado encontrado!!");
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause().getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

