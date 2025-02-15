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

import vanessa.pains.sistema_consultas_medicas.domain.Medico;
import vanessa.pains.sistema_consultas_medicas.repository.MedicoRepository;

@Controller
@RequestMapping("/medico")
public class MedicoController {
    private MedicoRepository repository;

    public static final String URL_LISTA = "medico/listaMedico";
    public static final String URL_FORM = "medico/frmMedico";
    public static final String URL_REDIRECT_LISTA = "redirect:/medico";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "medico";
    public static final String ATRIBUTO_LISTA = "medicos";


    public MedicoController(MedicoRepository repository){
        this.repository = repository;
    }

    //mapping da lista de medicos
    @GetMapping
    public String listar(Model model){
        List<Medico> medicos = repository.listar();
        model.addAttribute(ATRIBUTO_LISTA,medicos);
        return URL_LISTA;
    }

    //mapping de buscar 
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model){
        List<Medico> medicosBusca = repository.buscaPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, medicosBusca);
        if(medicosBusca.isEmpty()){
            model.addAttribute(ATRIBUTO_MENSAGEM, nome+" não encontrado.");
        }
        return URL_LISTA;
    }

    //abrir novo formulario para incluir novos pacientes
    @GetMapping("/novo")
    public String abrirFormNovo(Model model){
        Medico medico = new Medico();
        model.addAttribute(ATRIBUTO_OBJETO, medico);
        return URL_FORM;
    }

    //abrir formulario de editar os pacientes cadastrados
    @GetMapping("/editar/{cod_medico}")
    public String abrirFormEditar(@PathVariable("cod_medico") Integer cod_medico, Model model, RedirectAttributes redirectAttributes){
        Medico medicosBusca = repository.buscaPorCodigo(cod_medico);
        if (medicosBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, cod_medico+" não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        model.addAttribute(ATRIBUTO_OBJETO, medicosBusca);
        return URL_FORM;
    }

    //inserir novo paciente
    @PostMapping("/novo")
    public String salvar(@ModelAttribute("medico") Medico medico, BindingResult result,  RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            return URL_FORM;
        }
        repository.novo(medico);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, medico.getNome()+ " salvo com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    //excluir paciente
    @PostMapping("/excluir/{cod_medico}")
    public String excluir(@PathVariable("cod_medico") Integer cod_medico, RedirectAttributes redirectAttributes) {
        repository.delete(cod_medico); 
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Cadastro do médico excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    //editar cadastro do medico
    @PostMapping("/editar/{cod_medico}")
    public String atualizar(@PathVariable("cod_medico") Integer cod_medico, @ModelAttribute("medico") Medico medico, RedirectAttributes redirectAttributes) {
        try {
            medico.setCod_medico(cod_medico);
            repository.update(medico);
            redirectAttributes.addFlashAttribute("mensagem", "Médico atualizado com sucesso.");
            return "redirect:/medico";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar médico.");
            return "medico/frmMedico";
        }
    }
}
