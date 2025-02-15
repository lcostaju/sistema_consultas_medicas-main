package vanessa.pains.sistema_consultas_medicas.domain;

import java.sql.Date;

public class Paciente {
    private  Integer cod_paciente;
    private String cpf;
    private String rg;
    private Date dt_nascimento;
    private String nome;
    private String endereco;
    private String telefone;
    private String sexo;

    public Paciente() {
        
    }

    public Paciente(Integer cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public Integer getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(Integer cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(Date dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Paciente(Integer cod_paciente, String cpf, String rg, Date dt_nascimento, String nome, String endereco,
            String telefone, String sexo) {
        this.cod_paciente = cod_paciente;
        this.cpf = cpf;
        this.rg = rg;
        this.dt_nascimento = dt_nascimento;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Paciente other = (Paciente) obj;
        if (cod_paciente == null) {
            if (other.cod_paciente != null)
                return false;
        } else if (!cod_paciente.equals(other.cod_paciente))
            return false;
        return true;
    }
}
