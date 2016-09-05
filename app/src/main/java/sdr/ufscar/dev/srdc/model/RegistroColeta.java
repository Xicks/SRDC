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

    private Integer idRegistro;
    private Date dataColeta;
    private Integer pressao_diastolica;
    private Integer pressao_sistolica;
    private Integer peso;
    private Integer gasto_calorico;
    private Integer glicemia;

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(Date dataColeta) {
        this.dataColeta = dataColeta;
    }

    public Integer getPressao_diastolica() {
        return pressao_diastolica;
    }

    public void setPressao_diastolica(Integer pressao_diastolica) {
        this.pressao_diastolica = pressao_diastolica;
    }

    public Integer getPressao_sistolica() {
        return pressao_sistolica;
    }

    public void setPressao_sistolica(Integer pressao_sistolica) {
        this.pressao_sistolica = pressao_sistolica;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getGasto_calorico() {
        return gasto_calorico;
    }

    public void setGasto_calorico(Integer gasto_calorico) {
        this.gasto_calorico = gasto_calorico;
    }

    public Integer getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(Integer glicemia) {
        this.glicemia = glicemia;
    }
}
