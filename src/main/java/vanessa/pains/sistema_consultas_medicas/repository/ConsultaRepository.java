package vanessa.pains.sistema_consultas_medicas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Consulta;
import vanessa.pains.sistema_consultas_medicas.domain.Medico;
import vanessa.pains.sistema_consultas_medicas.domain.Paciente;

@Repository
public class ConsultaRepository {
    private JdbcTemplate conexaoBanco;

    public ConsultaRepository(JdbcTemplate conexaoBanco){
        this.conexaoBanco = conexaoBanco;
    }

    public List<Consulta> listar(){
        String sql = "SELECT c.cod_consulta, c.dt_consulta, c.observacoes, c.tipo_consulta, m.cod_medico, m.nome AS medico_nome, p.cod_paciente, p.nome AS paciente_nome " +
                     "FROM consulta c " +
                     "INNER JOIN medico m ON c.cod_medico = m.cod_medico " +
                     "INNER JOIN paciente p ON c.cod_paciente = p.cod_paciente order by cod_consulta";

        return conexaoBanco.query(sql, (rs, rowNum) -> getConsulta(rs));
    }

    //adicionar novo usuario
    // Inserir nova consulta
    public void novo(Consulta consulta) {
        String sql = "INSERT INTO consulta (dt_consulta, observacoes, tipo_consulta, cod_medico, cod_paciente) VALUES (?, ?, ?, ?, ?)";
        conexaoBanco.update(sql,
            consulta.getDt_consulta(),
            consulta.getObservacoes(),
            consulta.getTipo_consulta(),
            consulta.getMedico().getCod_medico(),
            consulta.getPaciente().getCod_paciente());
    }

    public void delete(Integer cod_consulta){
        // Primeiro, exclua os registros relacionados na tabela pedidoexames
        String sqlDeletePedidoExames = "DELETE FROM pedidoexames WHERE cod_consulta = ?";
        conexaoBanco.update(sqlDeletePedidoExames, cod_consulta);

        // Segundo, exclua os registros relacionados na tabela receita
        String sqlDeleteReceita = "DELETE FROM receita WHERE cod_consulta = ?";
        conexaoBanco.update(sqlDeleteReceita, cod_consulta);

        // Terceiro, exclua os registros relacionados na tabela de registro de pagamentos
        String sqlDeletePagamentos = "DELETE FROM registropagamentos WHERE cod_consulta = ?";
        conexaoBanco.update(sqlDeletePagamentos, cod_consulta);

        // Por fim, exclua o registro na tabela consulta
        String sqlDeleteConsulta = "DELETE FROM consulta WHERE cod_consulta = ?";
        conexaoBanco.update(sqlDeleteConsulta, cod_consulta);
    }

    //atualizar consulta
    public boolean update(Consulta consulta) {

        System.out.println("Atualizando consulta: " + consulta.getCod_consulta());
        System.out.println("Data: " + consulta.getDt_consulta());
        System.out.println("Observações: " + consulta.getObservacoes());
        System.out.println("Tipo: " + consulta.getTipo_consulta());
        System.out.println("Código do Médico: " + consulta.getMedico().getCod_medico());
        System.out.println("Código do Paciente: " + consulta.getPaciente().getCod_paciente());

        String sql = "UPDATE consulta SET " + 
                        "dt_consulta = ?, " +
                        "observacoes = ?, " +
                        "tipo_consulta = ?, " +
                        "cod_medico = ?, " +
                        "cod_paciente = ? " +
                        "WHERE cod_consulta = ?";
        return conexaoBanco.update(sql,
        consulta.getDt_consulta(),
        consulta.getObservacoes(),
        consulta.getTipo_consulta(),
        consulta.getMedico().getCod_medico(),
        consulta.getPaciente().getCod_paciente(),
        consulta.getCod_consulta()) > 0;

    }

    // Buscar consulta por código
    public Consulta buscaPorCodigo(Integer cod_consulta) {
        String sql = "SELECT c.cod_consulta, c.dt_consulta, c.observacoes, c.tipo_consulta, m.nome AS medico_nome, p.nome AS paciente_nome, p.cod_paciente, m.cod_medico " +
                    "FROM consulta c " +
                    "INNER JOIN medico m ON c.cod_medico = m.cod_medico " +
                    "INNER JOIN paciente p ON c.cod_paciente = p.cod_paciente " +
                    "WHERE c.cod_consulta = ?";
        return conexaoBanco.queryForObject(sql, (rs, rowNum) -> getConsulta(rs), cod_consulta);
    }


    public List<Consulta> buscaPorNome(String nome) {
        String sql = "SELECT c.cod_consulta, c.dt_consulta, c.observacoes, c.tipo_consulta, m.nome AS medico_nome, p.nome AS paciente_nome, p.cod_paciente" +
                    "FROM consulta c" +
                    "JOIN medico m ON c.cod_medico = m.cod_medico" + 
                    "JOIN paciente p ON c.cod_paciente = p.cod_paciente "+
                    "WHERE m.nome LIKE ? OR p.nome LIKE ?";
        return conexaoBanco.query(sql, (rs, rowNum) -> getConsulta(rs), "%" + nome + "%", "%" + nome + "%");
    }

    private Consulta getConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setCod_consulta(rs.getInt("cod_consulta"));
        consulta.setDt_consulta(rs.getDate("dt_consulta"));
        consulta.setObservacoes(rs.getString("observacoes"));
        consulta.setTipo_consulta(rs.getString("tipo_consulta"));

        Medico medico = new Medico();
        medico.setCod_medico(rs.getInt("cod_medico"));
        medico.setNome(rs.getString("medico_nome"));
        consulta.setMedico(medico);

        Paciente paciente = new Paciente();
        paciente.setCod_paciente(rs.getInt("cod_paciente"));
        paciente.setNome(rs.getString("paciente_nome"));
        consulta.setPaciente(paciente);

        return consulta;
    }
}
