package vanessa.pains.sistema_consultas_medicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import vanessa.pains.sistema_consultas_medicas.domain.Login;
import vanessa.pains.sistema_consultas_medicas.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    
    //link para validar o login do usuario
    @PostMapping("/login/entrar")
    public String validarLogin (Login login, Model model ) {
        if(loginService.verificaLogin(login)){
            model.addAttribute("mensagem","Login efetuado com sucesso");
            return "redirect:/index";
        }else{
            model.addAttribute("mensagem","Login ou senha inválidos");
            return "login/login";
        }
    }

    //link para entrar na página de login
    @GetMapping("/")
    public String login(Model model) {
        return "login/login";
    }

    //link para redireciona para a página de index onde tem o menu e as opções
    @GetMapping("/login")
    public String index(Model model) {
        return "redirect:/index";
    }

    //link para entra na página de cadastro de novo usuário  
    @GetMapping("/login/cadastrarNovoUsuario")
    public String novo(Model model) {
        return "login/cadastro";
    }

    //link do botão cadastra novo usuário na pagina de cadastro de novo usuário
    @PostMapping("/login/cadastro")
    public String novoUsuario(Login loginDigitado,Model model) {
        loginService.salvar(loginDigitado);
        model.addAttribute("mensagem","Usuário "+loginDigitado.getCrm()+" cadastrado com sucesso"); 
        return "login/login";
    }

    //entra na página de index onde tem o menu e as opções
    @GetMapping("/index")
    public String home() {
        return "fragments/index";
    }
    //Teste git
    @GetMapping("/login/cadastro")
    public String cadastro(Model model){
        //model.addAttribute("IFClin", "Bem vindo ao sistema de consultas médicas");
        return "fragments/index";
    }
}
