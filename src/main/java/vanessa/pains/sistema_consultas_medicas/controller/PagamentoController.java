package vanessa.pains.sistema_consultas_medicas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vanessa.pains.sistema_consultas_medicas.domain.Consulta;
import vanessa.pains.sistema_consultas_medicas.domain.Pagamento;
import vanessa.pains.sistema_consultas_medicas.domain.PedidoExame;
import vanessa.pains.sistema_consultas_medicas.repository.ConsultaRepository;
import vanessa.pains.sistema_consultas_medicas.repository.PagamentoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String URL_REDIRECT_LISTA = "redirect:/pagamento";
    public static final String ATRIBUTO_OBJETO = "pagamento";
    public static final String URL_FORM = "pagamento/form";

    private PagamentoRepository pagamentoRepository;
    private ConsultaRepository consultaRepository;

    public PagamentoController(PagamentoRepository pagamentoRepository, ConsultaRepository consultaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.consultaRepository = consultaRepository;
    }

    @GetMapping
    public String listar(Model model) {

        List<Pagamento> pagamentos = pagamentoRepository.listar();
        model.addAttribute("pagamentos", pagamentos);
        return "pagamento/lista";
    }

    @GetMapping("/listaporconsulta/{codConsulta}")
    public String listarPorConsulta(@PathVariable Integer codConsulta, Model model) {
        List<Pagamento> pagamentos = pagamentoRepository.listarPorConsulta(codConsulta);
        model.addAttribute("pagamentos", pagamentos);
        return "pagamento/lista";
    }

    @GetMapping("/editar/{codPagamento}")
    public String abrirFormEditar(@PathVariable Integer codPagamento, Model model,
            RedirectAttributes redirectAttributes) {
        Pagamento pagamento = pagamentoRepository.buscaPorCod(codPagamento);

        if (pagamento == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codPagamento + " não encontrado.");
            return URL_REDIRECT_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, pagamento);
            return URL_FORM;
        }

    }

    @PostMapping("/atualizar")
    public String updatePagamento( Pagamento pagamento) {
        pagamentoRepository.updatePagamento(pagamento);
        consultaRepository.buscaPorCodigo(pagamento.getConsulta().getCod_consulta());
        return URL_REDIRECT_LISTA;
    }
    
    @GetMapping("/novo/{codConsulta}")
    public String novo(@PathVariable("codConsulta") Integer cod_consulta, Model model) {
        Consulta consulta = consultaRepository.buscaPorCodigo(cod_consulta);
        model.addAttribute(ATRIBUTO_OBJETO, new Pagamento(consulta));
        return URL_FORM;
    }

    @PostMapping("/salvar")
    public String salvarPagamento(Pagamento pagamento, RedirectAttributes redirectAttributes,Model model) {
        pagamentoRepository.novoPagamento(pagamento);
        Consulta consulta = consultaRepository.buscaPorCodigo(pagamento.getConsulta().getCod_consulta());
        model.addAttribute("consulta", consulta);
        return "redirect:/consulta/editar/"+consulta.getCod_consulta();
    }

    @PostMapping("/delete/{codPagamento}")
    public String excluir(@PathVariable Integer codPagamento, RedirectAttributes redirectAttributes) {
        pagamentoRepository.excluir(codPagamento);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Pagamento excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }
}
