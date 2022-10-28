package br.com.shinigami.repository;

import br.com.shinigami.config.ConexaoBancoDeDados;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.model.Contrato;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.service.ImovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContratoRepository implements Repositorio<Integer, Contrato> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final ImovelService imovelService;


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_contrato.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Contrato create(Contrato contrato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer idProxContrato = getProximoId(con);
            contrato.setIdContrato(idProxContrato);

            String sql = "INSERT INTO CONTRATO\n" +
                    " (ID_CONTRATO, VALOR_ALUGUEL, DATA_ENTRADA, DATA_VENCIMENTO, ID_LOCATARIO, ID_LOCADOR, ID_IMOVEL, ATIVO)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, contrato.getIdContrato());
            stmt.setDouble(2, contrato.getValorAluguel());
            stmt.setDate(3, Date.valueOf(contrato.getDataEntrada()));
            stmt.setDate(4, Date.valueOf(contrato.getDataVencimento()));
            stmt.setInt(5, contrato.getLocatario().getIdCliente());
            stmt.setInt(6, contrato.getLocador().getIdCliente());
            stmt.setInt(7, contrato.getImovel().getIdImovel());
            stmt.setString(8, contrato.getAtivo().toString());

            int res = stmt.executeUpdate();
            contrato.getImovel().setAlugado(Tipo.S);
            imovelService.update(contrato.getImovel().getIdImovel(), contrato.getImovel());
            return contrato;

        } catch (BancoDeDadosException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contrato;
    }

    @Override
    public boolean delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE CONTRATO SET ativo = ? WHERE ID_CONTRATO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "F");
            stmt.setInt(2, id);

            int res = stmt.executeUpdate();
            Imovel imovel = buscarContrato(id).getImovel();
            imovel.setAlugado(false);
            imovelRepository.update(buscarContrato(id).getImovel().getIdImovel(), imovel);
            return res > 0;

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
    public Contrato update(Integer id, Contrato contrato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE CONTRATO SET " +
                    "DATA_ENTRADA = ?, " +
                    "DATA_VENCIMENTO = ?, " +
                    "ID_LOCATARIO = ?, " +
                    "ID_LOCADOR = ? " +
                    "WHERE ID_CONTRATO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(contrato.getDataEntrada()));
            stmt.setDate(2, Date.valueOf(contrato.getDataVencimento()));
            stmt.setInt(3, contrato.getLocatario().getIdCliente());
            stmt.setInt(4, contrato.getLocador().getIdCliente());
            stmt.setInt(5, id);

            int res = stmt.executeUpdate();
            return contrato;

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
    public List<Contrato> list() throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            List<Contrato> contratos = new ArrayList<>();

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CONTRATO WHERE ativo LIKE 'T'";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contrato contrato = new Contrato();

                contrato.setIdContrato(res.getInt("id_contrato"));
                contrato.setValorAluguel(res.getDouble("valor_aluguel"));
                contrato.setDataEntrada(res.getDate("data_entrada").toLocalDate());
                contrato.setDataVencimento(res.getDate("data_vencimento").toLocalDate());
                contrato.setLocatario(clienteRepository.buscarCliente(res.getInt("id_locatario")));
                contrato.setLocador(clienteRepository.buscarCliente(res.getInt("id_locador")));
                contrato.setImovel(imovelRepository.buscarImovel(res.getInt("id_imovel")));
                contrato.setAtivo(converteCharPraBoolean(res.getString("ativo")));
                contratos.add(contrato);
            }
            return contratos;

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

    public Contrato buscarContrato(int id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CONTRATO WHERE ID_CONTRATO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();
            res.next();
            Contrato contrato = new Contrato();
            contrato.setIdContrato(res.getInt("id_contrato"));
            contrato.setValorAluguel(res.getDouble("valor_aluguel"));
            contrato.setDataEntrada(res.getDate("data_entrada").toLocalDate());
            contrato.setDataVencimento(res.getDate("data_vencimento").toLocalDate());
            contrato.setLocatario(clienteRepository.buscarCliente(res.getInt("id_locatario")));
            contrato.setLocador(clienteRepository.buscarCliente(res.getInt("id_locador")));
            contrato.setImovel(imovelRepository.buscarImovel(res.getInt("id_imovel")));
            contrato.setAtivo(converteCharPraBoolean(res.getString("ativo")));

            return contrato;

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

    private boolean converteCharPraBoolean(String valor) {
        return valor.equalsIgnoreCase("T");
    }

}
