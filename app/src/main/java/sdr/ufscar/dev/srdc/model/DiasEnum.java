package sdr.ufscar.dev.srdc.model;

/**
 * Created by Schick on 8/29/16.
 */
public enum DiasEnum {
    SE("SEGUNDA"),
    TE("TERCA"),
    QA("QUARTA"),
    QI("QUINTA"),
    SX("SEXTA"),
    SA("SABADO"),
    DO("DOMINGO");

    private String dia;

    DiasEnum(String dia){
        this.dia = dia;
    }

    public String getDia(){
        return dia;
    }

}
