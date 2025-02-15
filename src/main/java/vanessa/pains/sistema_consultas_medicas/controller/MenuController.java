package vanessa.pains.sistema_consultas_medicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/menu")
public class MenuController {    
    public static final String URL_FORM = "menu/menu";
    public static final String ATRIBUTO_OBJETO = "menu";
    
    @GetMapping("/medico")
    public String redirecionarMedicos() {
        return "redirect:/medico"; // Redireciona para o controlador de médicos
    }

    @GetMapping("/paciente")
    public String redirecionarPacientes() {
        return "redirect:/paciente"; // Redireciona para o controlador de pacientes
    }

    @GetMapping("/consulta")
    public String redirecionarConsultas() {
        return "redirect:/consulta"; // Redireciona para o controlador de consultas
    }
}
