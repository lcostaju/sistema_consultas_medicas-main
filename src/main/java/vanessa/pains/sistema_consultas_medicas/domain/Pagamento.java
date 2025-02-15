package vanessa.pains.sistema_consultas_medicas.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pagamento {
    Integer codPagamento;
    String tipoPagamento;
    Double vlrPagamento;
    Date dataPagamento;
    Consulta consulta;

    public Pagamento(Consulta consulta) {
        this.consulta = consulta;
    }

}
