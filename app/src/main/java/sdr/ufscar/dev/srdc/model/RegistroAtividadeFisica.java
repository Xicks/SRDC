package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;

import sdr.ufscar.dev.srdc.enumeration.AtividadeFisicaEnum;

/**
 * Created by Schick on 8/31/16.
 */
public class RegistroAtividadeFisica {

    private Integer idRegistroAtividadeFisica;
    private Integer idDadosClinicos;
    private Integer dias;
    private Integer tempoAproximado;
    private ArrayList<AtividadeFisicaEnum> atividades;
    private Date dataRegistro;

    public Integer getIdRegistroAtividadeFisica() {
        return idRegistroAtividadeFisica;
    }

    public void setIdRegistroAtividadeFisica(Integer idRegistroAtividadeFisica) {
        this.idRegistroAtividadeFisica = idRegistroAtividadeFisica;
    }

    public Integer getIdDadosClinicos() {
        return idDadosClinicos;
    }

    public void setIdDadosClinicos(Integer idDadosClinicos) {
        this.idDadosClinicos = idDadosClinicos;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getTempoAproximado() {
        return tempoAproximado;
    }

    public void setTempoAproximado(Integer tempoAproximado) {
        this.tempoAproximado = tempoAproximado;
    }

    public ArrayList<AtividadeFisicaEnum> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<AtividadeFisicaEnum> atividades) {
        this.atividades = atividades;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
