package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Schick on 8/23/16.
 */
public class Cidadao {

    private Integer idCidadao;

    private String nome;
    private String cpfCns;
    private Date dataNascimento;
    private String cidade;
    private String estado;

    private Usuario usuario;
    private DadosClinicos dadosClinicos;

    public Integer getIdCidadao() {
        return idCidadao;
    }

    public void setIdCidadao(Integer idCidadao) {
        this.idCidadao = idCidadao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCns() {
        return cpfCns;
    }

    public void setCpfCns(String cpfCns) {
        this.cpfCns = cpfCns;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DadosClinicos getDadosClinicos() {
        return dadosClinicos;
    }

    public void setDadosClinicos(DadosClinicos dadosClinicos) {
        this.dadosClinicos = dadosClinicos;
    }
}
