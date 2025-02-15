package vanessa.pains.sistema_consultas_medicas.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vanessa.pains.sistema_consultas_medicas.domain.Login;
import vanessa.pains.sistema_consultas_medicas.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    public boolean verificaLogin(Login loginDigitado) {
        try {
            Login loginBanco = repository.validarLogin(loginDigitado);
            return encoder.matches(loginDigitado.getSenha(), loginBanco.getSenha());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void salvar(Login login) {
        if (login.getSenha() == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
        login.setSenha(encoder.encode(login.getSenha()));
        repository.salvar(login);
    }
}
