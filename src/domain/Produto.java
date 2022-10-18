package domain;

import java.util.Objects;

public class Produto {

    private Integer id;
    private String nome;
    private Double preco;
    private Integer estoque;

    public Produto() {
    }

    public Produto(Integer id, String nome, Double preco, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{ID: ");
        stringBuffer.append(getId());
        stringBuffer.append(", PRODUTO: ");
        stringBuffer.append(getNome());
        stringBuffer.append(", PREÇO: ");
        stringBuffer.append(getPreco());
        stringBuffer.append(", QUANTIDADE: ");
        stringBuffer.append(getEstoque());
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
