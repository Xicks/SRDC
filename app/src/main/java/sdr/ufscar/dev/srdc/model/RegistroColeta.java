package sdr.ufscar.dev.srdc.model;

import java.util.Date;

/**
 * Classe responsável
 * Created by Schick on 8/23/16.
 */
public class RegistroColeta {

    /*
    Pressão arterial: Máxima (sistólica), Minima (diastólica)
    Unidade: mmHg

    Peso: kg
    Gasto calórico: kcal

    Glicemia: mg/dL
     */

    private Integer idRegistroColeta;
    private Integer idDadosClinicos;
    private Date dataColeta;
    private Integer pressaoDiastolica;
    private Integer pressaoSistolica;
    private Integer peso;
    private Integer gastoCalorico;
    private Integer glicemia;

    public Integer getIdRegistroColeta() {
        return idRegistroColeta;
    }

    public void setIdRegistroColeta(Integer idRegistroColeta) {
        this.idRegistroColeta = idRegistroColeta;
    }

    public Integer getIdDadosClinicos() {
        return idDadosClinicos;
    }

    public void setIdDadosClinicos(Integer idDadosClinicos) {
        this.idDadosClinicos = idDadosClinicos;
    }

    public Date getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(Date dataColeta) {
        this.dataColeta = dataColeta;
    }

    public Integer getPressaoDiastolica() {
        return pressaoDiastolica;
    }

    public void setPressaoDiastolica(Integer pressaoDiastolica) {
        this.pressaoDiastolica = pressaoDiastolica;
    }

    public Integer getPressaoSistolica() {
        return pressaoSistolica;
    }

    public void setPressaoSistolica(Integer pressaoSistolica) {
        this.pressaoSistolica = pressaoSistolica;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getGastoCalorico() {
        return gastoCalorico;
    }

    public void setGastoCalorico(Integer gastoCalorico) {
        this.gastoCalorico = gastoCalorico;
    }

    public Integer getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(Integer glicemia) {
        this.glicemia = glicemia;
    }
}
