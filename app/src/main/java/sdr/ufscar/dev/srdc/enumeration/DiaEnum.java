package sdr.ufscar.dev.srdc.enumeration;

/**
 * Created by Schick on 8/29/16.
 */
public enum DiaEnum {
    SE("SEGUNDA"),
    TE("TERCA"),
    QA("QUARTA"),
    QI("QUINTA"),
    SX("SEXTA"),
    SA("SABADO"),
    DO("DOMINGO");

    private String dia;

    DiaEnum(String dia){
        this.dia = dia;
    }

    public String getDia(){
        return dia;
    }

}
