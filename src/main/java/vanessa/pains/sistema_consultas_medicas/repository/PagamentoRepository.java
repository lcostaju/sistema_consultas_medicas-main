package vanessa.pains.sistema_consultas_medicas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vanessa.pains.sistema_consultas_medicas.domain.Consulta;
import vanessa.pains.sistema_consultas_medicas.domain.Pagamento;
import vanessa.pains.sistema_consultas_medicas.domain.PedidoExame;



@Repository
public class PagamentoRepository {
    
    private final JdbcTemplate conexao;

    public PagamentoRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Pagamento> listar(){
        String sql = """
                select
                    p.cod_pagamento as codPagamento,
                    p.tipo_pagamento as tipoPagamento,
                    p.vlr_pagamento as vlrPagamento,
                    p.dt_pagamento as dataPagamento,
                    c.cod_consulta as cod_consulta

                from
                    registropagamentos p, consulta c

                where
                    p.cod_consulta = c.cod_consulta
                """;

        return conexao.query(sql, (rs, rowNum) -> getPagamento(rs));
    }

    public Pagamento buscaPorCod(Integer codPagamento){
        String sql = """
                select
                    p.cod_pagamento as codPagamento,
                    p.tipo_pagamento as tipoPagamento,
                    p.vlr_pagamento as vlrPagamento,
                    p.dt_pagamento as dataPagamento,
                    c.cod_consulta as cod_consulta

                from
                    registropagamentos p, consulta c

                where
                    p.cod_consulta = c.cod_consulta and
                    p.cod_pagamento = ?
                """;

            return conexao.queryForObject(sql, (rs, rowNum) -> getPagamento(rs), codPagamento);
    }

    public void updatePagamento(Pagamento pagamento) {
        String sql = """
                update registropagamentos
                set tipo_pagamento = ?,
                vlr_pagamento = ?,
                dt_pagamento = ?
                where
                cod_pagamento = ?
                """;
        conexao.update(sql,
                pagamento.getTipoPagamento(), 
                pagamento.getVlrPagamento(),
                pagamento.getDataPagamento(),
                pagamento.getCodPagamento());
    }

    public void novoPagamento(Pagamento pagamento) {
        String sql = """
                insert into registropagamentos
                (tipo_pagamento, vlr_pagamento, dt_pagamento, cod_consulta)
                values
                (?, ?, ?, ?)
                """;
        conexao.update(sql,
                pagamento.getTipoPagamento(),
                pagamento.getVlrPagamento(),
                pagamento.getDataPagamento(),
                pagamento.getConsulta().getCod_consulta());
    }

    public void excluir(Integer codPagamento) {
        String sql = """
                delete from registropagamentos
                where cod_pagamento = ?
                """;
        conexao.update(sql, codPagamento);
    }

    public List<Pagamento> listarPorConsulta(Integer codConsulta) {
        String sql = """
                select
                    p.cod_pagamento as codPagamento,
                    p.tipo_pagamento as tipoPagamento,
                    p.vlr_pagamento as vlrPagamento,
                    p.dt_pagamento as dataPagamento,
                    c.cod_consulta as cod_consulta

                from
                    registropagamentos p, consulta c

                where
                    p.cod_consulta = c.cod_consulta and
                    c.cod_consulta = ?
                """;

        return conexao.query(sql, (rs, rowNum) -> getPagamento(rs), codConsulta);
    }

    public static Pagamento getPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setCodPagamento(rs.getInt("codPagamento"));
        pagamento.setTipoPagamento(rs.getString("tipoPagamento"));
        pagamento.setVlrPagamento(rs.getDouble("vlrPagamento"));
        pagamento.setDataPagamento(rs.getDate("dataPagamento"));

        Consulta consulta = new Consulta();
        consulta.setCod_consulta(rs.getInt("cod_consulta"));
        // consulta.setMedico().(rs.getString("nomeMedico"));
        // consulta.setDataConsulta(rs.getDate("dataConsulta"));
        // consulta.setObservacoes(rs.getString("observacoes_consulta"));
        // consulta.setTipoConsulta(rs.getString("tipoConsulta"));
        // consulta.setNomePaciente(rs.getString("nomePaciente"));

        pagamento.setConsulta(consulta);

        return pagamento;
    }
}
