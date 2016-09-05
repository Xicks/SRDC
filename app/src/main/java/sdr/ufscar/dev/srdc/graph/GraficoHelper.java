package sdr.ufscar.dev.srdc.graph;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.facade.RegistroColetaFacade;
import sdr.ufscar.dev.srdc.model.RegistroColeta;

/**
 * Created by Schick on 9/4/16.
 */
public class GraficoHelper {

    public static final int GRAFICO_MAXIMO = 0;
    public static final int GRAFICO_MINIMO = 1;
    public static final int GRAFICO_POR_PERIODO = 2;
    public static final int GLICEMIA = 3;
    public static final int PRESSAO_ARTERIAL = 4;
    public static final int PESO = 5;
    public static final int GASTO_CALORICO = 6;

    private int idDadosClinicos;


    public GraficoHelper(int idDadosClinicos){
        this.idDadosClinicos = idDadosClinicos;
    }

    /***
     *
     * Gera gráfico a partir do Tipo de Gráfico e Tipo de Dado e datas passadas por parâmetro.
     * @param tipoGrafico
     * @param tipoDado
     * @param dataInicial
     * @param dataFinal
     * @return objeto do tipo Grafico
     */
    public Grafico gerarGrafico(int tipoGrafico, int tipoDado, Date dataInicial, Date dataFinal) {
        GraficoDataset[] datasets = null;
        Grafico g = new Grafico();
        switch (tipoDado) {
            case GLICEMIA:
                datasets = gerarGraficoDatasetsGlicemia(tipoGrafico,dataInicial,dataFinal);
                g.setTitulo("Glicemia");
                g.setTituloValorY("Glicemia (mg/dl)");
                g.setValorMinimo(30);
                g.setValorMaximo(500);
                g.setValorStep(30);
                break;
            case PRESSAO_ARTERIAL:
                datasets = gerarGraficoDatasetsPressao(tipoGrafico,dataInicial,dataFinal);
                g.setTitulo("Pressão Arterial");
                g.setTituloValorY("Pressão (mmHg)");
                g.setValorMinimo(60);
                g.setValorMaximo(180);
                g.setValorStep(10);
                break;
            case PESO:
                datasets = gerarGraficoDatasetsPeso(tipoGrafico,dataInicial,dataFinal);
                g.setTitulo("Peso");
                g.setTituloValorY("Peso (kg)");
                g.setValorMinimo(30);
                g.setValorMaximo(250);
                g.setValorStep(10);
                break;
            case GASTO_CALORICO:
                datasets = gerarGraficoDatasetsGastoCalorico(tipoGrafico,dataInicial,dataFinal);
                g.setTitulo("Gasto Calórico");
                g.setTituloValorY("Gasto Calórico (Kcal)");
                g.setValorMinimo(0);
                g.setValorMaximo(1000);
                g.setValorStep(100);
            default:
        }
        g.setTituloValorX("Mês");
        g.setRegistroCount(datasets[0].size());
        g.setDatasets(datasets);
        return g;
    }

    private GraficoDataset[] gerarGraficoDatasetsGlicemia(int tipoGrafico, Date dataInicial, Date dataFinal) {
        RegistroColetaFacade facade = new RegistroColetaFacade();
        GraficoDataset[] graficoDatasets;
        if(tipoGrafico == GRAFICO_POR_PERIODO) {
            throw new UnsupportedOperationException();
        } else {
            graficoDatasets = new GraficoDataset[1];
            List<RegistroColeta> registroColetas;
            switch (tipoGrafico) {
                case GRAFICO_MAXIMO:
                    registroColetas =
                            facade.pegarGlicemiaMaximaNoIntervalo(idDadosClinicos,dataInicial,dataFinal);
                    break;
                case GRAFICO_MINIMO:
                    registroColetas =
                            facade.pegarGlicemiaMinimaNoIntervalo(idDadosClinicos,dataInicial,dataFinal);
                    break;
                default:
                    return graficoDatasets;
            }
            ArrayList<Integer> glicemias = new ArrayList<>(registroColetas.size());
            ArrayList<String> datas = new ArrayList<>(registroColetas.size());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

            for(RegistroColeta rc : registroColetas) {
                glicemias.add(rc.getGlicemia());
                datas.add(sdf.format(rc.getDataColeta()));
            }
            GraficoDataset g0 = new GraficoDataset("Glicemia",datas,glicemias);
            g0.setFormatter(R.xml.glicemia_formatter);
            graficoDatasets[0] = g0;

        }
        return graficoDatasets;
    }

    private GraficoDataset[] gerarGraficoDatasetsPressao(int tipoGrafico, Date dataInicial, Date dataFinal) {
        RegistroColetaFacade facade = new RegistroColetaFacade();
        GraficoDataset[] graficoDatasets;
        if(tipoGrafico == GRAFICO_POR_PERIODO) {
            throw new UnsupportedOperationException();
        } else {
            graficoDatasets = new GraficoDataset[2];
            List<RegistroColeta> registroColetas;
            switch (tipoGrafico) {
                case GRAFICO_MAXIMO:
                    registroColetas =
                            facade.pegarPressaoMaximaNoIntervalo(idDadosClinicos,dataInicial,dataFinal);
                    break;
                case GRAFICO_MINIMO:
                    registroColetas =
                            facade.pegarPressaoMinimaNoIntervalo(idDadosClinicos,dataInicial,dataFinal);
                    break;
                default:
                    return graficoDatasets;
            }
            ArrayList<Integer> pressoesSistolicas = new ArrayList<>(registroColetas.size());
            ArrayList<Integer> pressoesDiastolicas = new ArrayList<>(registroColetas.size());
            ArrayList<String> datas = new ArrayList<>(registroColetas.size());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

            for(RegistroColeta rc : registroColetas) {
                pressoesSistolicas.add(rc.getPressaoSistolica());
                pressoesDiastolicas.add(rc.getPressaoDiastolica());
                datas.add(sdf.format(rc.getDataColeta()));
            }
            GraficoDataset g0 = new GraficoDataset("Sistólica",datas,pressoesSistolicas);
            g0.setFormatter(R.xml.pressao_sistolica_formatter);
            GraficoDataset g1 = new GraficoDataset("Diastólica",datas,pressoesDiastolicas);
            g1.setFormatter(R.xml.pressao_diastolica_formatter);
            graficoDatasets[0] = g0;
            graficoDatasets[1] = g1;

        }
        return graficoDatasets;
    }

    private GraficoDataset[] gerarGraficoDatasetsPeso(int tipoGrafico, Date dataInicial, Date dataFinal) {
        RegistroColetaFacade facade = new RegistroColetaFacade();
        GraficoDataset[] graficoDatasets;
        if (tipoGrafico == GRAFICO_POR_PERIODO) {
            throw new UnsupportedOperationException();
        } else {
            graficoDatasets = new GraficoDataset[1];
            List<RegistroColeta> registroColetas;
            switch (tipoGrafico) {
                case GRAFICO_MAXIMO:
                    registroColetas =
                            facade.pegarPesoMaximoNoIntervalo(idDadosClinicos, dataInicial, dataFinal);
                    break;
                case GRAFICO_MINIMO:
                    registroColetas =
                            facade.pegarPesoMinimoNoIntervalo(idDadosClinicos, dataInicial, dataFinal);
                    break;
                default:
                    return graficoDatasets;
            }
            ArrayList<Integer> pesos = new ArrayList<>(registroColetas.size());
            ArrayList<String> datas = new ArrayList<>(registroColetas.size());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

            for (RegistroColeta rc : registroColetas) {
                pesos.add(rc.getPeso());
                datas.add(sdf.format(rc.getDataColeta()));
            }
            GraficoDataset g0 = new GraficoDataset("Peso",datas, pesos);
            g0.setFormatter(R.xml.peso_formatter);
            graficoDatasets[0] = g0;
        }
        return graficoDatasets;
    }

    private GraficoDataset[] gerarGraficoDatasetsGastoCalorico(int tipoGrafico, Date dataInicial, Date dataFinal) {
        RegistroColetaFacade facade = new RegistroColetaFacade();
        GraficoDataset[] graficoDatasets;
        if (tipoGrafico == GRAFICO_POR_PERIODO) {
            throw new UnsupportedOperationException();
        } else {
            graficoDatasets = new GraficoDataset[1];
            List<RegistroColeta> registroColetas;
            switch (tipoGrafico) {
                case GRAFICO_MAXIMO:
                    registroColetas =
                            facade.pegarGastoCaloricoMaximoNoIntervalo(idDadosClinicos, dataInicial, dataFinal);
                    break;
                case GRAFICO_MINIMO:
                    registroColetas =
                            facade.pegarGastoCaloricoMinimoNoIntervalo(idDadosClinicos, dataInicial, dataFinal);
                    break;
                default:
                    return graficoDatasets;
            }
            ArrayList<Integer> pesos = new ArrayList<>(registroColetas.size());
            ArrayList<String> datas = new ArrayList<>(registroColetas.size());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

            for (RegistroColeta rc : registroColetas) {
                pesos.add(rc.getGastoCalorico());
                datas.add(sdf.format(rc.getDataColeta()));
            }
            GraficoDataset g0 = new GraficoDataset("Peso",datas, pesos);
            g0.setFormatter(R.xml.gasto_calorico_formatter);
            graficoDatasets[0] = g0;
        }
        return graficoDatasets;
    }
}
