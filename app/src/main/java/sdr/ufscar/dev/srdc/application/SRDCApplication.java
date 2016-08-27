package sdr.ufscar.dev.srdc.application;

import android.app.Application;

import sdr.ufscar.dev.srdc.model.Cidadao;

/**
 * Created by Schick on 8/26/16.
 */
public class SRDCApplication extends Application {

    private Cidadao cidadao;

    /**
     * Retorna a instancia do objeto singletron cidadao
     * @return cidadao
     */
    public Cidadao getCidadaoInstance() {
        return cidadao;
    }

    /**
     * Modifica a instancia do objeto singletron cidadao
     */
    public void setCidadaoInstance(Cidadao cidadao){
        this.cidadao = cidadao;
    }
}
