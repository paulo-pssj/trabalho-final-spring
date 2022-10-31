package br.com.shinigami.repository;

import br.com.shinigami.config.ConexaoBancoDeDados;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository implements Repositorio<Cliente> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_cliente.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Cliente create(Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);
            cliente.setAtivo(Tipo.S);

            String sql = "INSERT INTO CLIENTE\n" +
                    "(ID_CLIENTE, NOME, CPF, TELEFONE, EMAIL, TIPO_CLIENTE, ATIVO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setInt(6, cliente.getTipoCliente().ordinal());
            stmt.setString(7, cliente.getAtivo().toString());

            stmt.executeUpdate();

            return cliente;
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
    public void delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE CLIENTE SET ativo = ? WHERE id_cliente = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, Tipo.N.toString());
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
    public Cliente update(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            cliente.setIdCliente(id);
            String sql = "UPDATE CLIENTE SET " +
                    " cpf = ?," +
                    " nome = ?," +
                    " telefone = ?," +
                    " email = ?," +
                    " tipo_cliente = ?" +
                    " WHERE id_cliente = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getTipoCliente().ordinal());
            stmt.setInt(6, id);

            stmt.executeUpdate();

            return cliente;
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
    public List<Cliente> list() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CLIENTE WHERE ATIVO LIKE 'S'";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(res.getInt("id_cliente"));
                cliente.setNome(res.getString("nome"));
                cliente.setTelefone(res.getString("telefone"));
                cliente.setCpf(res.getString("cpf"));
                cliente.setEmail(res.getString("email"));
                cliente.setTipoCliente(TipoCliente.values()[res.getInt("tipo_cliente")]);
                clientes.add(cliente);
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
        return clientes;
    }

    public Cliente buscarCliente(Integer id) throws BancoDeDadosException {
        Cliente cliente = new Cliente();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CLIENTE WHERE id_cliente = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                cliente.setIdCliente(res.getInt("id_cliente"));
                cliente.setNome(res.getString("nome"));
                cliente.setTelefone(res.getString("telefone"));
                cliente.setCpf(res.getString("cpf"));
                cliente.setEmail(res.getString("email"));
                cliente.setTipoCliente(TipoCliente.values()[res.getInt("tipo_cliente")]);
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
        return cliente;
    }

}
