package vanessa.pains.sistema_consultas_medicas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vanessa.pains.sistema_consultas_medicas.domain.Paciente;
import vanessa.pains.sistema_consultas_medicas.repository.PacienteRepository;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteRepository repository;

    public static final String URL_LISTA = "paciente/listaPaciente";
    public static final String URL_FORM = "paciente/frmPaciente";
    public static final String URL_REDIRECT_LISTA = "redirect:/paciente";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "paciente";
    public static final String ATRIBUTO_LISTA = "pacientes";

    public PacienteController (PacienteRepository repository){
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "index";  // Isso corresponderá ao arquivo index.html na pasta 'static'
    }
    
    //mapping da lista de pacientes
    @GetMapping
    public String listar(Model model){
        List<Paciente> pacientes = repository.listar();
        model.addAttribute(ATRIBUTO_LISTA,pacientes);
        return URL_LISTA;
    }

    //mapping de buscar 
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model){
        List<Paciente> pacientesBusca = repository.buscaPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, pacientesBusca);
        if(pacientesBusca.isEmpty()){
            model.addAttribute(ATRIBUTO_MENSAGEM, nome+" não encontrado.");
        }
        return URL_LISTA;
    }

    //abrir novo formulario para incluir novos pacientes
    @GetMapping("/novo")    
    public String abrirFormNovo(Model model){
        Paciente paciente = new Paciente();
        model.addAttribute(ATRIBUTO_OBJETO, paciente);
        return URL_FORM;
    }

    //abrir formulario de editar os pacientes cadastrados
    @GetMapping("/editar/{cod_paciente}")
    public String abrirFormEditar(@PathVariable("cod_paciente") Integer cod_paciente, Model model, RedirectAttributes redirectAttributes){
        Paciente pacienteBusca = repository.buscaPorCodigo(cod_paciente);
        if (pacienteBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cod_paciente+" não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        model.addAttribute(ATRIBUTO_OBJETO, pacienteBusca);
        return URL_FORM;
    }

    //inserir novo paciente
    @PostMapping("/novo")
    public String salvar(@ModelAttribute("paciente") Paciente paciente, BindingResult result,  RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            return URL_FORM;
        }
        repository.novo(paciente);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, paciente.getNome()+ " salvo com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    //excluir paciente
    @PostMapping("/excluir/{cod_paciente}")
    public String excluir(@PathVariable("cod_paciente") Integer cod_paciente, RedirectAttributes redirectAttributes) {
        repository.delete(cod_paciente); 
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Cadastro do paciente excluído com sucesso.");
        return URL_REDIRECT_LISTA; 
    }

    //editar cadastro do paciente
    @PostMapping("/editar/{cod_paciente}")
    public String atualizar(@PathVariable("cod_paciente") Integer cod_paciente, @ModelAttribute("paciente") Paciente paciente, RedirectAttributes redirectAttributes){
        if (repository.update(paciente)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM +"Cadastro do paciente ", paciente.getNome()+ " foi atualizado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possivel alterar o cadastro do pacinete "+paciente.getNome()+".");
        }
        return URL_REDIRECT_LISTA;
    }
}
