package sdr.ufscar.dev.srdc.graph;

import java.util.ArrayList;
import java.util.Date;

import sdr.ufscar.dev.srdc.model.RegistroColeta;

/**
 * Created by Schick on 9/4/16.
 */
public class GraficoDataset {

    private String datasetTitle;
    private int formatter;
    private ArrayList<String> valoresX;
    private ArrayList<Integer> valoresY;

    public GraficoDataset(String datasetTitle, ArrayList<String> valoresX, ArrayList<Integer> valoresY){
        this.datasetTitle = datasetTitle;
        this.valoresX = valoresX;
        this.valoresY = valoresY;
    }

    public ArrayList<String> getValoresX() {
        return valoresX;
    }

    public ArrayList<Integer> getValoresY() {
        return valoresY;
    }

    public String getDatasetTitle() {
        return datasetTitle;
    }

    public int size() {
        return valoresX.size();
    }

    public int getFormatter() {
        return formatter;
    }
    public void setFormatter(int formatter){
        this.formatter = formatter;
    }
}
