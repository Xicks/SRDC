package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.enumeration.DiaEnum;

public class InicioActivity extends AppCompatActivity {

    private TextView mTVBemvindo;
    private TextView mTVInformacoesColeta;
    private TextView mTVObservacoes;

    private Cidadao cidadao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        SRDCApplication application = (SRDCApplication) getApplication();
        cidadao = application.getCidadaoInstance();

        mTVBemvindo = (TextView) findViewById(R.id.activity_inicio_tv_bemvindo);
        mTVInformacoesColeta = (TextView) findViewById(R.id.activity_inicio_tv_informacoes_coleta);
        mTVObservacoes = (TextView) findViewById(R.id.activity_inicio_tv_observacoes);

        DadosClinicos dadosClinicos = cidadao.getDadosClinicos();

        if(dadosClinicos == null) throw new RuntimeException("Dados clinicos não cadastrados");

        mTVBemvindo.setText("Bem vindo " + cidadao.getNome());
        StringBuilder sb = new StringBuilder();
        sb.append("Coletas devem ser realizadas:\n");
        for(DiaEnum dias: dadosClinicos.getDiasColetaEnum()){
            sb.append(dias.getDia() + " ");
        }
        sb.append("\nàs\n");
        for(Integer hora: dadosClinicos.getHorasColeta()){
            sb.append(hora + ":00\n");
        }
        mTVInformacoesColeta.setText(new String(sb));
        mTVObservacoes.setText(dadosClinicos.getObservacoes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch(item.getItemId()) {
            case R.id.menu_inicio_logout:
                logout();
                return true;
            case R.id.menu_inicio_cadastro_registro_atividade_fisica:
                i = new Intent(getApplicationContext(),CadastroRegistroAtividadeFisicaActivity.class);
                break;
            case R.id.menu_inicio_graficos:
                i = new Intent(getApplicationContext(),EscolherVisualizacaoActivity.class);
                break;
            case R.id.menu_inicio_ajuda:
                i = new Intent(getApplicationContext(),AjudaInicioActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(i);
        return true;
    }

    public void novoRegistro(View v){
        Intent i = new Intent(getApplicationContext(),CadastroRegistroColetaActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        logout();
    }

    public void logout(){
        new AlertDialog.Builder(this).setTitle("Sair")
                .setMessage("Deseja sair do sistema?")
                .setNegativeButton(R.string.nao,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton(R.string.sim,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SRDCApplication app = (SRDCApplication) getApplication();
                                app.setCidadaoInstance(null);
                                finish();
                            }
                        }
                ).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
