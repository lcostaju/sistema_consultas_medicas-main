package vanessa.pains.sistema_consultas_medicas.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Consulta;
import vanessa.pains.sistema_consultas_medicas.domain.PedidoExame;

@Repository
public class PedidoExameRepository {

    public static Date stringToDate(String dataEmTexto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Converte a string para java.util.Date
            java.util.Date utilDate = sdf.parse(dataEmTexto);

            // Converte java.util.Date para java.sql.Date
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            // Exibe mensagem de erro e retorna null caso falhe
            System.out.println("Erro ao converter a string para Date: " + e.getMessage());
            return null;
        }
    }

    private final JdbcTemplate conexao;

    // private static ConsultRepositoy consultRepositoy = new ConsultRepositoy();
    // private static Consulta consultaInicial = consultRepositoy.buscaPorCod(1);
    public static List<PedidoExame> pedidosExame2 = new ArrayList<>();

    // static{
    // pedidosExame2.add(new PedidoExame(1, "Hemograma", stringToDate("03/12/2024"),
    // consultaInicial, "Observação", "Laboratório"));
    // }

    public PedidoExameRepository(JdbcTemplate conexao) {
        this.conexao = conexao;

    }

    // public List<PedidoExame> listar() {
    // String sql = """
    // select
    // p.cod_exame as codExame,
    // p.nome_exame as nomeExame,
    // p.dt_solicitacao as dataSolicitacao,
    // p.dt_resultado as dataResultado,
    // p.resultado as resultado,
    // c.cod_consulta as cod_consulta

    // from
    // pedidoexames p, consulta c

    // where
    // p.cod_consulta = c.cod_consulta

    // """;

    // return conexao.query(sql, (rs, rowNum) -> getPedidoExame(rs));
    // }

    public List<PedidoExame> listar() {
        String sql = """
                select
                    p.cod_exame as codExame,
                    p.nome_exame as nomeExame,
                    p.dt_solicitacao as dataSolicitacao,
                    p.dt_resultado as dataResultado,
                    p.resultado as resultado,
                    c.cod_consulta as cod_consulta

                from
                    pedidoexames p, consulta c

                where
                    p.cod_consulta = c.cod_consulta
                """;

        return conexao.query(sql, (rs, rowNum) -> getPedidoExame(rs));
    }

    public List<PedidoExame> listarPorConsulta(Consulta consulta) {
        // List<PedidoExame> pedidosConsulta = new ArrayList<>();
        // for (PedidoExame pedido : pedidosExame2) {
        // if (pedido.getConsulta().getCodConsulta().equals(consulta.getCodConsulta()))
        // {
        // pedidosConsulta.add(pedido);
        // }
        // }
        // return pedidosConsulta;
        String sql = """
                select
                    p.cod_exame as codExame,
                    p.nome_exame as nomeExame,
                    p.dt_solicitacao as dataSolicitacao,
                    p.dt_resultado as dt_resultado,
                    p.resultado as resultado,
                    c.cod_consulta as codConsulta,

                from
                    consulta as c, pedidoexames as p

                where
                    p.consulta_id = c.codConsulta and
                    p.consulta_id = ?

                """;

        return conexao.query(sql, (rs, rowNum) -> getPedidoExame(rs), consulta.getCod_consulta());
    }

    public List<PedidoExame> listarPorConsulta(Integer codConsulta) {
        String sql = """
                select
                    p.cod_exame as codExame,
                    p.nome_exame as nomeExame,
                    p.dt_solicitacao as dataSolicitacao,
                    p.dt_resultado as dataResultado,
                    p.resultado as resultado,
                    c.cod_consulta as cod_consulta

                from
                    pedidoexames p, consulta c

                where
                    p.cod_consulta = c.cod_consulta and
                    c.cod_consulta = ?
                """;

        return conexao.query(sql, (rs, rowNum) -> getPedidoExame(rs), codConsulta);
    }

    public void novoPedidoExame(PedidoExame pedidoExame) {
        String sql = """
                INSERT INTO
                pedidoexames (nome_exame, dt_solicitacao, dt_resultado, resultado, cod_consulta)
                VALUES ( ?, ?, ?, ?, ?)
                """;
        conexao.update(sql,
                pedidoExame.getNomeExame(), pedidoExame.getDataSolicitacao(), pedidoExame.getDataResultado(),
                pedidoExame.getResultado(), pedidoExame.getConsulta().getCod_consulta());
    }

    public void updatePedidoExame(PedidoExame pedidoExame) {
        String sql = """
                update pedidoexames
                set nome_exame = ?,
                dt_solicitacao = ?,
                dt_resultado = ?,
                resultado = ?
                where
                cod_exame = ?
                """;
        conexao.update(sql,
                pedidoExame.getNomeExame(), 
                pedidoExame.getDataSolicitacao(),
                pedidoExame.getDataResultado(),
                pedidoExame.getResultado(),
                pedidoExame.getCodExame());
    }

    public PedidoExame buscaPorCod(Integer codPedidoExame) {
        String sql = """
                select
                    p.cod_exame as codExame,
                    p.nome_exame as nomeExame,
                    p.dt_solicitacao as dataSolicitacao,
                    p.dt_resultado as dataResultado,
                    p.resultado as resultado,
                    c.cod_consulta as cod_consulta

                from
                    pedidoexames p, consulta c

                where
                    p.cod_consulta = c.cod_consulta and
                    p.cod_exame = ?
                """;

        return conexao.queryForObject(sql, (rs, rowNum) -> getPedidoExame(rs), codPedidoExame);
    }

    public void deletePedidoExame(Integer codPedidoExame) {
        String sql = """
                delete from pedidoexames
                where cod_exame = ?

                """;

        conexao.update(sql, codPedidoExame);
    }

    public static PedidoExame getPedidoExame(ResultSet rs) throws SQLException {
        PedidoExame pedidoExame = new PedidoExame();
        pedidoExame.setCodExame(rs.getInt("codExame"));
        pedidoExame.setNomeExame(rs.getString("nomeExame"));
        pedidoExame.setDataSolicitacao(rs.getDate("dataSolicitacao"));
        pedidoExame.setDataResultado(rs.getDate("dataResultado"));
        pedidoExame.setResultado(rs.getString("resultado"));
        // pedidoExame.setLaboratorio(rs.getString("laboratorio"));

        Consulta consulta = new Consulta();
        consulta.setCod_consulta(rs.getInt("cod_consulta"));
        // consulta.setMedico().(rs.getString("nomeMedico"));
        // consulta.setDataConsulta(rs.getDate("dataConsulta"));
        // consulta.setObservacoes(rs.getString("observacoes_consulta"));
        // consulta.setTipoConsulta(rs.getString("tipoConsulta"));
        // consulta.setNomePaciente(rs.getString("nomePaciente"));

        pedidoExame.setConsulta(consulta);

        return pedidoExame;
    }
}
