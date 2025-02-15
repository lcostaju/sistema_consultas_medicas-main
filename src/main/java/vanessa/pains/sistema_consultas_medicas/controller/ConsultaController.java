package vanessa.pains.sistema_consultas_medicas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vanessa.pains.sistema_consultas_medicas.domain.Consulta;
import vanessa.pains.sistema_consultas_medicas.domain.Medico;
import vanessa.pains.sistema_consultas_medicas.domain.Paciente;
import vanessa.pains.sistema_consultas_medicas.repository.ConsultaRepository;
import vanessa.pains.sistema_consultas_medicas.repository.MedicoRepository;
import vanessa.pains.sistema_consultas_medicas.repository.PacienteRepository;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {
    private ConsultaRepository repository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;


    public static final String URL_LISTA = "consulta/listaConsulta";
    public static final String URL_FORM = "consulta/frmConsulta";
    public static final String URL_REDIRECT_LISTA = "redirect:/consulta";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "consulta";
    public static final String ATRIBUTO_LISTA = "consultas";

    public ConsultaController(ConsultaRepository repository,MedicoRepository medicoRepository, PacienteRepository pacienteRepository){
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    //mapping da lista de consulta
    @GetMapping
    public String listar(Model model){
        List<Consulta> consultas = repository.listar();
        model.addAttribute(ATRIBUTO_LISTA,consultas);
        return URL_LISTA;
    }

    //abrir novo formulario para incluir consulta98
    @GetMapping("/novo")    
    public String abrirFormNovo(Model model){
        List<Medico> medicos = medicoRepository.listar();
        model.addAttribute("medicos",medicos);

        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute("pacientes",pacientes);
        Consulta consulta = new Consulta();
        model.addAttribute(ATRIBUTO_OBJETO, consulta);
        return URL_FORM;
    }

    //inserir nova consulta
    @PostMapping("/novo")
    public String inserir(@ModelAttribute("consulta") Consulta consulta, RedirectAttributes redirectAttributes) {
        try {
            Medico medico = new Medico();
            medico.setCod_medico(null);
            Paciente paciente = new Paciente();
            paciente.setCod_paciente(null);
            repository.novo(consulta);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta inserida com sucesso.");
            return URL_REDIRECT_LISTA;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao inserir consulta.");
            return URL_FORM;
        }
    }

    //abrir formulário de editar consulta
    @GetMapping("/editar/{cod_consulta}")
    public String editar(@PathVariable("cod_consulta") Integer cod_consulta, Model model, RedirectAttributes redirectAttributes){
        Consulta consultaBusca = repository.buscaPorCodigo(cod_consulta);
        List<Medico> medicos = medicoRepository.listar();
        model.addAttribute("medicos",medicos);

        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute("pacientes",pacientes);
        if (consultaBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cod_consulta+" não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        model.addAttribute(ATRIBUTO_OBJETO, consultaBusca);
        return URL_FORM;
    }

    //atualizar consulta
    @PostMapping("/editar/{cod_consulta}")
    public String atualizar(@PathVariable("cod_consulta") Integer cod_consulta, @ModelAttribute("consulta") Consulta consulta, RedirectAttributes redirectAttributes) {
        try {
            consulta.setCod_consulta(cod_consulta);
            repository.update(consulta);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta atualizada com sucesso.");
            return "redirect:/consulta";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar consulta.");
            return "consulta/formConsulta";
        }
    }

    //excluir consulta
    @PostMapping("/excluir/{cod_consulta}")
    public String excluir(@PathVariable("cod_consulta") Integer cod_consulta, RedirectAttributes redirectAttributes) {
        repository.delete(cod_consulta); 
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Consulta excluído com sucesso.");
        return URL_REDIRECT_LISTA; 
    }

    //buscar consulta por nome
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        model.addAttribute("consultas", repository.buscaPorNome(nome));
        return "consulta/listaConsulta";
    }

    //buscar consulta por código
    @GetMapping("/buscar/{cod_consulta}")
    public String buscarPorCodigo(@PathVariable("cod_consulta") Integer cod_consulta, Model model) {
        Consulta consulta = repository.buscaPorCodigo(cod_consulta);
        model.addAttribute("consulta", consulta);
        return "consulta/detalheConsulta";
    }
}
