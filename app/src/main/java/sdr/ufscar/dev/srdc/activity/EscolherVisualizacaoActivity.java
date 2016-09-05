package sdr.ufscar.dev.srdc.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.graph.GraficoHelper;

public class EscolherVisualizacaoActivity extends AppCompatActivity {

    private TextView mTVTipoVisualizacao;
    private TextView mTVDadosFisiologicos;
    private EditText mETDataInicial;
    private EditText mETDataFinal;
    private RadioGroup mRGDadoFisiologico;

    private Integer mTipoVisualizacao;
    private Integer mDadoFisiologico;
    private Calendar mDataInicial;
    private Calendar mDataFinal;
    private DatePickerDialog mDPDialogInicio;
    private DatePickerDialog mDPDialogFinal;
    private boolean mRadioButtonDesabilitados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_visualizacao);

        mRGDadoFisiologico = (RadioGroup) findViewById(R.id.activity_escolher_visualizacao_rg_dado_fisiologico);
        mETDataInicial = (EditText) findViewById(R.id.activity_escolher_visualizacao_et_data_inicial);
        mETDataFinal = (EditText) findViewById(R.id.activity_escolher_visualizacao_et_data_final);
        mTVTipoVisualizacao = (TextView) findViewById(R.id.activity_escolher_visualizacao_tv_tipo_visualizacao);
        mTVDadosFisiologicos = (TextView) findViewById(R.id.activity_escolher_visualizacao_tv_dados_fisiologicos);

        mDataInicial = Calendar.getInstance();
        mDPDialogInicio = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDataInicial.set(year, monthOfYear, dayOfMonth);
                mETDataInicial.setText(new SimpleDateFormat("dd/MM/yyyy")
                        .format(mDataInicial.getTime()));
            }

        }, mDataInicial.get(Calendar.YEAR), mDataInicial.get(Calendar.MONTH), mDataInicial.get(Calendar.DAY_OF_MONTH));

        mDataFinal = Calendar.getInstance();
        mDPDialogFinal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDataFinal.set(year, monthOfYear, dayOfMonth);
                mETDataFinal.setText(new SimpleDateFormat("dd/MM/yyyy")
                        .format(mDataFinal.getTime()));
            }

        },mDataFinal.get(Calendar.YEAR), mDataFinal.get(Calendar.MONTH), mDataFinal.get(Calendar.DAY_OF_MONTH));

        mETDataInicial.setText(new SimpleDateFormat("dd/MM/yyyy")
                .format(mDataInicial.getTime()));
        mETDataFinal.setText(new SimpleDateFormat("dd/MM/yyyy")
                .format(mDataFinal.getTime()));
        mTipoVisualizacao = GraficoHelper.GRAFICO_MAXIMO;
        mDadoFisiologico = GraficoHelper.PRESSAO_ARTERIAL;
    }

    public void escolherData(View v) {
        switch (v.getId()){
            case R.id.activity_escolher_visualizacao_et_data_inicial:
                mDPDialogInicio.show();
                break;
            case R.id.activity_escolher_visualizacao_et_data_final:
                mDPDialogFinal.show();
                break;
        }
    }
    public void visualizar(View v){
        if(isFormularioValido()) {
            Intent i;
            if (mTipoVisualizacao != -1) {
                i = new Intent(getApplicationContext(),GraficoActivity.class);
                i.putExtra("tipo_grafico",mTipoVisualizacao);
                i.putExtra("dado_fisiologico",mDadoFisiologico);
            } else {
                i = new Intent(getApplicationContext(),ListaRegistros.class);
            }
            i.putExtra("data_inicial", mDataInicial.getTime().getTime());
            i.putExtra("data_final",mDataFinal.getTime().getTime());
            startActivity(i);
        }
    }

    public boolean isFormularioValido() {
        boolean retorno = true;
        if(mTipoVisualizacao == null) {
            mTVTipoVisualizacao.setError("Escolha um tipo de visualização");
            retorno = false;
        } else if(mTipoVisualizacao != -1 && mDadoFisiologico == null){
            mTVDadosFisiologicos.setError("Escolha um dado fisiológico");
            retorno = false;
        }
        if(mDataInicial.after(mDataFinal)) {
            mETDataInicial.setError("Data de inicial posterior a data final");
            retorno = false;
        } else if (mDataFinal.after(Calendar.getInstance())){
            mETDataFinal.setError("Data de final posterior a data atual");
            retorno = false;
        }
        return retorno;
    }

    public void escolherDadoFisiologico(View v) {
        switch(v.getId()) {
            case R.id.activity_escolher_visualizacao_rb_glicemia:
                mDadoFisiologico = GraficoHelper.GLICEMIA;
                break;
            case R.id.activity_escolher_visualizacao_rb_pressao:
                mDadoFisiologico = GraficoHelper.PRESSAO_ARTERIAL;
                break;
            case R.id.activity_escolher_visualizacao_rb_peso:
                mDadoFisiologico = GraficoHelper.PESO;
                break;
            case R.id.activity_escolher_visualizacao_rb_gasto_calorico:
                mDadoFisiologico = GraficoHelper.GASTO_CALORICO;
        }
    }
    public void escolherTipoVisualizacao(View v){
        switch(v.getId()) {
            case R.id.activity_escolher_visualizacao_rb_grafico_maximo:
                mTipoVisualizacao = GraficoHelper.GRAFICO_MAXIMO;
                habilitarRadioButtons();
                break;
            case R.id.activity_escolher_visualizacao_rb_grafico_minimo:
                mTipoVisualizacao = GraficoHelper.GRAFICO_MINIMO;
                habilitarRadioButtons();
                break;
            case R.id.activity_escolher_visualizacao_rb_grafico_periodo:
                mTipoVisualizacao = GraficoHelper.GRAFICO_POR_PERIODO;
                habilitarRadioButtons();
                break;
            case R.id.activity_escolher_visualizacao_rb_lista_registros:
                mTipoVisualizacao = -1;
                desabilitarRadioButtons();
        }
    }

    private void desabilitarRadioButtons() {
        for(int i = 0; i < mRGDadoFisiologico.getChildCount(); i++) {
            mRGDadoFisiologico.getChildAt(i).setEnabled(false);
        }
        mRadioButtonDesabilitados = true;
    }

    private void habilitarRadioButtons() {
        if(mRadioButtonDesabilitados) {
            for(int i = 0; i < mRGDadoFisiologico.getChildCount(); i++) {
                mRGDadoFisiologico.getChildAt(i).setEnabled(true);
            }
        }
    }
}
