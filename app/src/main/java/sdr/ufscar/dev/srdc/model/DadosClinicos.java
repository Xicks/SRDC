package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicos {

    private Integer idDadosClinicos;
    private Integer altura;
    private ArrayList<DoencaEnum> doencas;
    private ArrayList<Registro> registros;
    private ArrayList<Date> horariosColeta;
    private Boolean receberNotificacao;

    public Integer getIdDadosClinicos() {
        return idDadosClinicos;
    }

    public void setIdDadosClinicos(Integer idDadosClinicos) {
        this.idDadosClinicos = idDadosClinicos;
    }

    public Boolean getReceberNotificacao() {
        return receberNotificacao;
    }

    public void setReceberNotificacao(Boolean receberNotificacao) {
        this.receberNotificacao = receberNotificacao;
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

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }
}
