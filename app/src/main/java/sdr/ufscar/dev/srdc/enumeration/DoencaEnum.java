package sdr.ufscar.dev.srdc.enumeration;

/**
 * Created by Schick on 8/24/16.
 */
public enum DoencaEnum {
    I10_15_DOENÇAS_HIPERTENSIVAS("i10_15"),
    E10_E14_DIABETES_MELLITUS("e10_e14"),
    E65_E68_OBESIDADE_HIPERALIMENTAÇÃO("e65_68");

    private String doenca;

    DoencaEnum(String doenca){
        this.doenca = doenca;
    }

    public String getDoenca(){
        return doenca;
    }
}
