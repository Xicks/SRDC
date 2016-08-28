package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicos {

    private Integer idDadosClinicos;
    private String cnsProfissional;
    private String cnes;
    private Date dataRegistro;
    private float altura;
    private ArrayList<DoencaEnum> doencas;
    private ArrayList<Registro> registros;
    private ArrayList<Date> horariosColeta;
    private String observacoes;
    private Boolean enviarNotificacao;

    public Integer getIdDadosClinicos() {
        return idDadosClinicos;
    }

    public void setIdDadosClinicos(Integer idDadosClinicos) {
        this.idDadosClinicos = idDadosClinicos;
    }

    public String getCnsProfissional() {
        return cnsProfissional;
    }

    public void setCnsProfissional(String cnsProfissional) {
        this.cnsProfissional = cnsProfissional;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public ArrayList<DoencaEnum> getDoencas() {
        return doencas;
    }

    public void setDoencas(ArrayList<DoencaEnum> doencas) {
        this.doencas = doencas;
    }

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
    }

    public ArrayList<Date> getHorariosColeta() {
        return horariosColeta;
    }

    public void setHorariosColeta(ArrayList<Date> horariosColeta) {
        this.horariosColeta = horariosColeta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getEnviarNotificacao() {
        return enviarNotificacao;
    }

    public void setEnviarNotificacao(Boolean enviarNotificacao) {
        this.enviarNotificacao = enviarNotificacao;
    }
}
