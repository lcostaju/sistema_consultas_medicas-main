package vanessa.pains.sistema_consultas_medicas.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Login;

@Repository
public class LoginRepository {
    private JdbcTemplate conexaoBanco;

    public LoginRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;

    }

    public Login validarLogin(Login loginDigitado) {
        String sql = "select crm, senha from login where crm = ?";
        return conexaoBanco.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Login.class),
                            loginDigitado.getCrm());
    }

    public void salvar(Login login) {
        String sql = "insert into login(crm, senha) values(?,?)";
        conexaoBanco.update(sql,login.getCrm(),login.getSenha());
    }
}
