package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.graph.Grafico;
import sdr.ufscar.dev.srdc.graph.GraficoDataset;
import sdr.ufscar.dev.srdc.graph.GraficoHelper;
import sdr.ufscar.dev.srdc.model.DadosClinicos;

public class GraficoActivity extends AppCompatActivity {

    private XYPlot mAPGrafico;
    private DadosClinicos mDadosClinicos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        SRDCApplication app = (SRDCApplication) getApplication();
        mDadosClinicos = app.getCidadaoInstance().getDadosClinicos();

        Intent i = super.getIntent();

        int tipoGrafico = i.getIntExtra("tipo_grafico", -1);
        int dadoFisiologico = i.getIntExtra("dado_fisiologico", -1);
        Date dataInicial = new Date(i.getLongExtra("data_inicial", -1));
        Date dataFinal = new Date(i.getLongExtra("data_final", -1));

        mAPGrafico = (XYPlot) findViewById(R.id.activity_grafico_ap_grafico);

        Grafico grafico = new GraficoHelper(mDadosClinicos.getIdDadosClinicos())
                .gerarGrafico(tipoGrafico, dadoFisiologico, dataInicial, dataFinal);

        // Verifica se existe mais de 3 valores
        if (grafico.getRegistroCount() <= 3) {
            new AlertDialog.Builder(this).setTitle("Poucos dados.")
                    .setMessage("Existem poucos ou nenhum registros para a data informada.\n" +
                            "Escolha a opção lista de registros para visualizar os dados de coleta.")
                    .setPositiveButton(R.string.voltar,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_info).show();
        } else {
            criarGrafico(grafico);
        }
    }
    public void criarGrafico(Grafico grafico){
        mAPGrafico.setTitle(grafico.getTitulo());
        mAPGrafico.getRangeTitle().setText(grafico.getTituloValorY());
        mAPGrafico.getDomainTitle().setText(grafico.getTituloValorX());

        // Modifica o intervalo de valores
        mAPGrafico.setRangeBoundaries(grafico.getValorMinimo(), grafico.getValorMaximo(),
                BoundaryMode.FIXED);
        mAPGrafico.setRangeStep(StepMode.INCREMENT_BY_VAL, grafico.getValorStep());
        // Mofifica o formato dos valores
        XYGraphWidget.LineLabelStyle style = mAPGrafico.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT);
        style.setFormat(new DecimalFormat("0"));

        GraficoDataset[] datasets = grafico.getDatasets();

        for(GraficoDataset dataset: datasets) {
            final List<String> datas = dataset.getValoresX();
            XYSeries series = new SimpleXYSeries(
                    dataset.getValoresY(), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, dataset.getDatasetTitle());//dataset.getDatasetTitle());
            // create formatters to use for drawing a series using LineAndPointRenderer
            // and configure them from xml:
            LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
            seriesFormat.setPointLabelFormatter(new PointLabelFormatter());
            seriesFormat.configure(getApplicationContext(),
                    dataset.getFormatter());

            // just for fun, add some smoothing to the lines:
            // see: http://androidplot.com/smooth-curves-and-androidplot/
            seriesFormat.setInterpolationParams(
                    new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));

            // add a new series' to the xyplot:
            mAPGrafico.addSeries(series,seriesFormat);

            mAPGrafico.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                @Override
                public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                    int i = Math.round(((Number) obj).floatValue());
                    return toAppendTo.append(datas.get(i));
                }

                @Override
                public Object parseObject(String source, ParsePosition pos) {
                    return null;
                }
            });
        }
        // Adiciona funções de zoom
        PanZoom.attach(mAPGrafico);
    }
}