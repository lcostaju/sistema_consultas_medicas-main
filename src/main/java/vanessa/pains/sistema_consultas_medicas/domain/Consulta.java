package vanessa.pains.sistema_consultas_medicas.domain;

import java.sql.Date;

public class Consulta {
    private Integer cod_consulta;
    private Date dt_consulta;
    private String observacoes;
    private String tipo_consulta;
    //private Integer codMedico;
    //private Integer cod_paciente;
    private Paciente paciente;
    private Medico medico;

    public Consulta() {
        
    }

    public Consulta(Integer cod_consulta) {
        this.cod_consulta = cod_consulta;
    }

    public Consulta(Paciente paciente) {
        this.paciente = paciente;
    }

    public Consulta(Medico medico) {
        this.medico = medico;
    }

    public Consulta(Integer cod_consulta, Date dt_consulta, String observacoes, String tipo_consulta, Paciente paciente, Medico medico) {
        this.cod_consulta = cod_consulta;
        this.dt_consulta = dt_consulta;
        this.observacoes = observacoes;
        this.tipo_consulta = tipo_consulta;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Integer getCod_consulta() {
        return cod_consulta;
    }

    public void setCod_consulta(Integer cod_consulta) {
        this.cod_consulta = cod_consulta;
    }

    public Date getDt_consulta() {
        return dt_consulta;
    }

    public void setDt_consulta(Date dt_consulta) {
        this.dt_consulta = dt_consulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getTipo_consulta() {
        return tipo_consulta;
    }

    public void setTipo_consulta(String tipo_consulta) {
        this.tipo_consulta = tipo_consulta;
    }

    /*public Integer getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(Integer codMedico) {
        this.codMedico = codMedico;
    }

    public Integer getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(Integer cod_paciente) {
        this.cod_paciente = cod_paciente;
    }*/

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cod_consulta == null) ? 0 : cod_consulta.hashCode());
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
        Consulta other = (Consulta) obj;
        if (cod_consulta == null) {
            if (other.cod_consulta != null)
                return false;
        } else if (!cod_consulta.equals(other.cod_consulta))
            return false;
        return true;
    }
}
