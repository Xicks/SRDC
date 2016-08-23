package sdr.ufscar.dev.srdc.model;

/**
 * Created by Schick on 8/23/16.
 */
public class Sensor {

    private Integer idSensor;
    private Integer numero;
    private SensorType tipo;

    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public SensorType getTipo() {
        return tipo;
    }

    public void setTipo(SensorType tipo) {
        this.tipo = tipo;
    }
}
