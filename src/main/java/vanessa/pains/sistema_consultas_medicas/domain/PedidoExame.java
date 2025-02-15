package vanessa.pains.sistema_consultas_medicas.domain;

import java.sql.Date;

public class PedidoExame {
    private Integer codExame;
    private String nomeExame;
    private Date dataSolicitacao;
    private Date dataResultado;
    private Consulta consulta;
    private String resultado;
    

    public PedidoExame() {
    }

    public PedidoExame(Integer codExame, String nomeExame, Date dataSolicitacao,Consulta consulta, String observacao, String laboratorio, Date dataResultado) {
        this.codExame = codExame;
        this.nomeExame = nomeExame;
        this.dataSolicitacao = dataSolicitacao;
        this.consulta = consulta;
        this.resultado = observacao;
        this.dataResultado = dataResultado;
    }

    public PedidoExame(Integer codExame, String nomeExame, Date dataSolicitacao,String observacao, String laboratorio) {
        this.codExame = codExame;
        this.nomeExame = nomeExame;
        this.dataSolicitacao = dataSolicitacao;
        this.resultado = observacao;
        
    }
    public PedidoExame(Consulta consulta2) {
        //TODO Auto-generated constructor stub
        consulta = consulta2;
    }

    public PedidoExame(Integer codPedidoExame) {
        //TODO Auto-generated constructor stub
        this.codExame = codPedidoExame;
    }

    public Integer getCodExame() {
        return codExame;
    }

    public void setCodExame(Integer codExame) {
        this.codExame = codExame;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codExame == null) ? 0 : codExame.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PedidoExame other = (PedidoExame) obj;
        if (codExame == null) {
            if (other.codExame != null)
                return false;
        } else if (!codExame.equals(other.codExame))
            return false;
        return true;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String observacao) {
        this.resultado = observacao;
    }

    public Date getDataResultado() {
        return dataResultado;
    }

    public void setDataResultado(Date dataResultado) {
        this.dataResultado = dataResultado;
    }

    

}
