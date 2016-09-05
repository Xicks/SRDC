package sdr.ufscar.dev.srdc.graph;

/**
 * Created by Schick on 9/5/16.
 */
public class Grafico {

    private String titulo;
    private String tituloValorX;
    private String tituloValorY;
    private int valorMinimo;
    private int valorMaximo;
    private int valorStep;
    private int registroCount;
    private GraficoDataset[] datasets;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public GraficoDataset[] getDatasets() {
        return datasets;
    }

    public void setDatasets(GraficoDataset[] datasets) {
        this.datasets = datasets;
    }

    public int getRegistroCount() {
        return registroCount;
    }

    public void setRegistroCount(int registroCount) {
        this.registroCount = registroCount;
    }

    public int getValorStep() {
        return valorStep;
    }

    public void setValorStep(int valorStep) {
        this.valorStep = valorStep;
    }

    public String getTituloValorX() {
        return tituloValorX;
    }

    public void setTituloValorX(String tituloValorX) {
        this.tituloValorX = tituloValorX;
    }

    public String getTituloValorY() {
        return tituloValorY;
    }

    public void setTituloValorY(String getTituloValorY) {
        this.tituloValorY = getTituloValorY;
    }
}
