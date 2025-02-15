package vanessa.pains.sistema_consultas_medicas.domain;

public class Medico {
    private Integer cod_medico;
    private String crm;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String especialidade;
    private String sexo;

    public Medico() {
    }

    public Medico(Integer cod_medico) {
        this.cod_medico = cod_medico;
    }
    
    
    public Integer getCod_medico() {
        return cod_medico;
    }

    public void setCod_medico(Integer cod_medico) {
        this.cod_medico = cod_medico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Medico(Integer cod_medico, String crm, String nome, String endereco, String telefone, String email,
            String especialidade, String sexo) {
        this.cod_medico = cod_medico;
        this.crm = crm;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.especialidade = especialidade;
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
        Medico other = (Medico) obj;
        if (cod_medico == null) {
            if (other.cod_medico != null)
                return false;
        } else if (!cod_medico.equals(other.cod_medico))
            return false;
        return true;
    }
}
