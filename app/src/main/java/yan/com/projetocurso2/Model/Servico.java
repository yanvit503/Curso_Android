package yan.com.projetocurso2.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Servico {

    @PrimaryKey(autoGenerate = true)
    private Long Id;

    @ColumnInfo
    private String cliente , tipo, rua , numero , bairro , cidade;

    public Servico(){}

    public Servico(String tipo ,String cliente ,String numero ,String bairro ,String cidade , String rua)
    {
        setTipo(tipo);
        setCliente(cliente);
        setNumero(numero);
        setBairro(bairro);
        setCidade(cidade);
        setRua(rua);
    }

    //region Get e Set

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEnderecoTotal()
    {
       return getRua() + " , numero " + getNumero() + ", "+ getBairro() + " , " + getCidade();
    }

    //endregion
}
