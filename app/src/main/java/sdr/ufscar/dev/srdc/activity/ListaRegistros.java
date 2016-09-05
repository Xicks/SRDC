package sdr.ufscar.dev.srdc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.facade.RegistroColetaFacade;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.RegistroColeta;
import sdr.ufscar.dev.srdc.util.AppUtils;

public class ListaRegistros extends AppCompatActivity {

    private RecyclerView mRVListaRegistros;
    private TextView mTVRegistros;

    private DadosClinicos mDadosClinicos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros);

        SRDCApplication app = (SRDCApplication) getApplication();
        mDadosClinicos = app.getCidadaoInstance().getDadosClinicos();

        Intent i = super.getIntent();

        Date dataInicial = new Date(i.getLongExtra("data_inicial", -1));
        Date dataFinal = new Date(i.getLongExtra("data_final", -1));

        mTVRegistros = (TextView) findViewById(R.id.activity_lista_registros_tv_registros);
        mRVListaRegistros = (RecyclerView) findViewById(R.id.activity_lista_registros_rv_lista_registros);
        mRVListaRegistros.setLayoutManager(new LinearLayoutManager(this));

        List<RegistroColeta> registros =
                new RegistroColetaFacade().pegarRegistroColetaNoIntervalo(
                        mDadosClinicos.getIdDadosClinicos(),dataInicial,dataFinal);

        mTVRegistros.setText("Quantidade: " + registros.size() + " registros.");
        mRVListaRegistros.setAdapter(new MyRecyclerAdapter(registros));
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
        private List<RegistroColeta> feedItemList;
        private SimpleDateFormat sdf;

        public MyRecyclerAdapter(List<RegistroColeta> feedItemList) {
            this.feedItemList = feedItemList;
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.registro_row, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            RegistroColeta rc = feedItemList.get(i);
            customViewHolder.mTVDataRegistro.setText(sdf.format(rc.getDataColeta()));

            if(rc.getPressaoSistolica() != null) {
                customViewHolder.mTVPressaoSistolica.setText(rc.getPressaoSistolica() + " mmHg");
                customViewHolder.mTVPressaoDiastolica.setText(rc.getPressaoDiastolica() + " mmHg");
                customViewHolder.mLLPressaoArterial.setVisibility(View.VISIBLE);
            }
            if(rc.getGlicemia() != null) {
                customViewHolder.mTVGlicemia.setText(rc.getGlicemia() + " mg/dl");
                customViewHolder.mLLGlicemia.setVisibility(View.VISIBLE);
            }
            if(rc.getPeso() != null || rc.getGastoCalorico() != null) {
                customViewHolder.mTVGastoCalorico.setText(rc.getGastoCalorico() + " Kcal");
                customViewHolder.mTVPeso.setText(rc.getPeso() + " kg");
                customViewHolder.mLLPesoGastoCalorico.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected TextView mTVDataRegistro;
            protected TextView mTVPressaoSistolica;
            protected TextView mTVPressaoDiastolica;
            protected TextView mTVGlicemia;
            protected TextView mTVPeso;
            protected TextView mTVGastoCalorico;
            protected LinearLayout mLLPressaoArterial;
            protected LinearLayout mLLGlicemia;
            protected LinearLayout mLLPesoGastoCalorico;

            public CustomViewHolder(View view) {
                super(view);
                mTVDataRegistro = (TextView) view.findViewById(R.id.registro_row_tv_data_registro);
                mTVPressaoSistolica = (TextView) view.findViewById(R.id.registro_row_tv_pressao_sistolica);
                mTVPressaoDiastolica = (TextView) view.findViewById(R.id.registro_row_tv_pressao_diastolica);
                mTVGlicemia = (TextView) view.findViewById(R.id.registro_row_tv_glicemia);
                mTVPeso = (TextView) view.findViewById(R.id.registro_row_tv_peso);
                mTVGastoCalorico = (TextView) view.findViewById(R.id.registro_row_tv_gasto_calorico);
                mLLPressaoArterial = (LinearLayout) view.findViewById(R.id.registro_row_ll_pressao_arterial);
                mLLGlicemia = (LinearLayout) view.findViewById(R.id.registro_row_ll_glicemia);
                mLLPesoGastoCalorico = (LinearLayout) view.findViewById(R.id.registro_row_ll_peso_gasto_calorico);
            }
        }
    }
}
