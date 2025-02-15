package vanessa.pains.sistema_consultas_medicas.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Paciente;

@Repository
public class PacienteRepository {
    private JdbcTemplate conexaoBanco;

    public PacienteRepository(JdbcTemplate conexaoBanco){
        this.conexaoBanco = conexaoBanco;
    }

    ///lista todos os pacientes
    public List<Paciente> listar(){
        String sql = "SELECT cod_paciente, cpf, rg, dt_nascimento, nome, endereco, telefone, sexo FROM paciente order by cod_paciente";
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Paciente.class));
    }

    ///busca por nome que contenha algum dos caracteres informados
    public List<Paciente> buscaPorNome(String nome){
        String sql = "SELECT cod_paciente, cpf, rg, dt_nascimento, nome, endereco, telefone, sexo FROM paciente WHERE nome LIKE ? ";
        return conexaoBanco.query(sql, 
            new BeanPropertyRowMapper<>(Paciente.class), "%" + nome + "%");
    }

    ///busca por codigo do paciente digitado
    public Paciente buscaPorCodigo(Integer cod_paciente){
        String sql = "SELECT cod_paciente, cpf, rg, dt_nascimento, nome, endereco, telefone, sexo FROM paciente WHERE cod_paciente = ?";
        return conexaoBanco.queryForObject(sql, 
            new BeanPropertyRowMapper<>(Paciente.class), cod_paciente);
    }

    ///adicionar novo paciente
    public void novo(Paciente paciente){
        // String sql = "INSERT INTO paciente (cod_paciente, cpf, rg, dt_nascimento, nome, endereco, telefone, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sql = "INSERT INTO paciente ( cpf, rg, dt_nascimento, nome, endereco, telefone, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        conexaoBanco.update(sql,
            // paciente.getCod_paciente(),
            paciente.getCpf(),
            paciente.getRg(),
            paciente.getDt_nascimento(),
            paciente.getNome(),
            paciente.getEndereco(),
            paciente.getTelefone(),
            paciente.getSexo());
    }

    ///deletar paciente
    public boolean delete(Integer cod_paciente){
        String  sql = "DELETE FROM paciente WHERE cod_paciente = ?";
        return conexaoBanco.update(sql, cod_paciente) > 0;
    }

    ///atualizar paciente
    public boolean update(Paciente paciente){
        String sql = "UPDATE paciente SET "+
                        "cpf = ?, "+
                        "rg = ?, "+
                        "dt_nascimento = ?, "+
                        "nome = ?, "+
                        "endereco = ?, "+
                        "telefone = ?, "+
                        "sexo = ? "+
                        "WHERE cod_paciente = ?";
        return conexaoBanco.update(sql,
            paciente.getCpf(),
            paciente.getRg(),
            paciente.getDt_nascimento(),
            paciente.getNome(),
            paciente.getEndereco(),
            paciente.getTelefone(),
            paciente.getSexo(),
            paciente.getCod_paciente()) > 0;
    }
}
