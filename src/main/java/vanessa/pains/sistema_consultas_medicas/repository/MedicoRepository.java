package vanessa.pains.sistema_consultas_medicas.repository;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Medico;

@Repository
public class MedicoRepository {

    private JdbcTemplate conexaoBanco;

    public MedicoRepository(JdbcTemplate conexaoBanco){
        this.conexaoBanco = conexaoBanco;
    }

    public List<Medico> listar(){
        String sql = "SELECT cod_medico, crm, nome, endereco, telefone, email, especialidade, sexo FROM medico order by nome";
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Medico.class));
    }

    

    //busca por nome que contenha algum dos caracteres informados
    public List<Medico> buscaPorNome(String nome){
        String sql = "SELECT cod_medico, crm, nome, endereco, telefone, email, especialidade, sexo FROM medico WHERE nome LIKE ? ";
        return conexaoBanco.query(sql, 
            new BeanPropertyRowMapper<>(Medico.class), "%" + nome + "%");
    }

    //busca por login digitado
    public Medico buscaPorCodigo(Integer codMedico){
        String sql = "SELECT cod_medico, crm, nome, endereco, telefone, email, especialidade, sexo FROM medico WHERE cod_medico = ? ";
        return conexaoBanco.queryForObject(sql, 
            new BeanPropertyRowMapper<>(Medico.class), codMedico);
    }

    //adicionar novo usuario
    public void novo(Medico medico){
        // String sql = "INSERT INTO medico (cod_medico, crm, nome, endereco, telefone, email, especialidade, sexo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sql = "INSERT INTO medico ( crm, nome, endereco, telefone, email, especialidade, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        conexaoBanco.update(sql,
            // medico.getCod_medico(),
            medico.getCrm(),
            medico.getNome(),
            medico.getEndereco(),
            medico.getTelefone(),
            medico.getEmail(),
            medico.getEspecialidade(),
            medico.getSexo());
    }

    public boolean delete(Integer cod_medico){
        String  sql = "DELETE FROM medico WHERE cod_medico = ?";
        return conexaoBanco.update(sql, cod_medico) > 0;
    }

    public boolean update(Medico medico){
        String sql = "UPDATE medico SET crm = ?, nome = ?, endereco = ?, telefone = ?, email = ?, especialidade = ?, sexo = ? WHERE cod_medico = ?";    
        return conexaoBanco.update(sql,
            medico.getCrm(),
            medico.getNome(),
            medico.getEndereco(),
            medico.getTelefone(),
            medico.getEmail(),
            medico.getEspecialidade(),
            medico.getSexo(),
            medico.getCod_medico()) > 0;
    }
}
