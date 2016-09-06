package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.facade.RegistroColetaFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.enumeration.DoencaEnum;
import sdr.ufscar.dev.srdc.model.RegistroColeta;

public class CadastroRegistroColetaActivity extends AppCompatActivity {

    private TextView mTVPressaoSistolica;
    private Spinner mSPNPressaoSistolica;
    private TextView mTVPressaoDiastolica;
    private Spinner mSPNPressaoDiastolica;
    private TextView mTVGastoCalorico;
    private EditText mETGastoCalorico;
    private TextView mTVPeso;
    private EditText mETPeso;
    private TextView mTVGlicemia;
    private EditText mETGlicemia;
    private DadosClinicos mDadosClinicos;
    private Cidadao mCidadao;
    private ArrayList<DoencaEnum> mDoencas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_registro_coleta);

        mSPNPressaoSistolica = (Spinner)
                findViewById(R.id.activity_cadastro_registro_coleta_spn_pressao_sistolica);
        mTVPressaoSistolica = (TextView)
                findViewById(R.id.activity_cadastro_registro_coleta_tv_pressao_sistolica);
        mSPNPressaoDiastolica = (Spinner)
                findViewById(R.id.activity_cadastro_registro_coleta_spn_pressao_diastolica);
        mTVPressaoDiastolica = (TextView)
                findViewById(R.id.activity_cadastro_registro_coleta_tv_pressao_diastolica);
        mETGastoCalorico = (EditText)
                findViewById(R.id.activity_cadastro_registro_coleta_et_gasto_calorico);
        mTVGastoCalorico = (TextView)
                findViewById(R.id.activity_cadastro_registro_coleta_tv_gasto_calorico);
        mETPeso = (EditText) findViewById(R.id.activity_cadastro_registro_coleta_et_peso);
        mTVPeso = (TextView) findViewById(R.id.activity_cadastro_registro_coleta_tv_peso);
        mETGlicemia = (EditText) findViewById(R.id.activity_cadastro_registro_coleta_et_glicemia);
        mTVGlicemia = (TextView) findViewById(R.id.activity_cadastro_registro_coleta_tv_glicemia);

        SRDCApplication application = (SRDCApplication) getApplication();
        mCidadao = application.getCidadaoInstance();
        mDadosClinicos = mCidadao.getDadosClinicos();

        mDoencas = mDadosClinicos.getDoencas();

        if (mDoencas.contains(DoencaEnum.I10_15_DOENÇAS_HIPERTENSIVAS)) {
            mSPNPressaoSistolica.setVisibility(View.VISIBLE);
            mTVPressaoSistolica.setVisibility(View.VISIBLE);
            mSPNPressaoDiastolica.setVisibility(View.VISIBLE);
            mTVPressaoDiastolica.setVisibility(View.VISIBLE);
            popularSpinnersPressao();
        }
        if (mDoencas.contains(DoencaEnum.E10_E14_DIABETES_MELLITUS)) {
            mETGlicemia.setVisibility(View.VISIBLE);
            mTVGlicemia.setVisibility(View.VISIBLE);
        }
        if (mDoencas.contains(DoencaEnum.E65_E68_OBESIDADE_HIPERALIMENTAÇÃO)) {
            mETPeso.setVisibility(View.VISIBLE);
            mTVPeso.setVisibility(View.VISIBLE);
            mETGastoCalorico.setVisibility(View.VISIBLE);
            mTVGastoCalorico.setVisibility(View.VISIBLE);
        }
    }

    public void popularSpinnersPressao() {
        ArrayList<String> valores = new ArrayList<>(101);
        // PRESSAO SISTOLICA
        valores.add(" ");
        for (int i = 80; i <= 180; i++) {
            valores.add(i + "");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSPNPressaoSistolica.setAdapter(adapter);

        // PRESSAO DIASTOLICA
        valores = new ArrayList<>(100);
        valores.add(" ");
        for (int i = 60; i <= 160; i++) {
            valores.add(i + "");
        }
        adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSPNPressaoDiastolica.setAdapter(adapter);
    }


    public void registrar(View v) {
        if (isFormularioValido()) {
            new AlertDialog.Builder(this).setTitle("Registrar")
                    .setMessage("Deseja registrar os dados?")
                    .setNegativeButton(R.string.nao,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .setPositiveButton(R.string.sim,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    salvarRegistroColeta();
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    private void salvarRegistroColeta() {
        RegistroColeta registroColeta = new RegistroColeta();

        Boolean sucesso;
        try {
            if (mSPNPressaoSistolica.getSelectedItemId() != 0) {
                registroColeta.setPressaoSistolica(
                        Integer.parseInt(mSPNPressaoSistolica.getSelectedItem().toString()));
                registroColeta.setPressaoDiastolica(
                        Integer.parseInt(mSPNPressaoDiastolica.getSelectedItem().toString()));
            }
            if (!mETGlicemia.getText().toString().trim().isEmpty()) {
                registroColeta.setGlicemia(Integer.parseInt(mETGlicemia.getText().toString()));
            }
            if (!mETGastoCalorico.getText().toString().trim().isEmpty()) {
                registroColeta.setGastoCalorico(Integer.parseInt(
                        mETGastoCalorico.getText().toString()));
            }
            if (!mETPeso.getText().toString().trim().isEmpty()) {
                registroColeta.setPeso(Integer.parseInt(mETPeso.getText().toString()));
            }
            sucesso = new RegistroColetaFacade().
                    cadastrarRegistroColeta(registroColeta, mDadosClinicos);
        } catch (NumberFormatException e) {
            sucesso = Boolean.FALSE;
        }
        if (Boolean.TRUE.equals(sucesso)) {
            new AlertDialog.Builder(this).setTitle("Sucesso")
                    .setMessage("Registro salvo com sucesso!")
                    .setPositiveButton(R.string.sim,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_info).show();
        } else {
            gerarAlerta("Erro", "Erro ao processar operação. Tente novamente.");
        }
    }

    public boolean isFormularioValido(){
        // Nada preenchido
        if(mSPNPressaoSistolica.getSelectedItemId() == 0 &&
                mSPNPressaoDiastolica.getSelectedItemId() == 0 &&
                mETGastoCalorico.getText().toString().trim().isEmpty() &&
                mETPeso.getText().toString().trim().isEmpty() &&
                mETGlicemia.getText().toString().trim().isEmpty()) {
            gerarAlerta("Erro ao registrar","Algum dado deve ser informado para o cadastro.");
            return false;
        }
        // Somente um valor de pressao preenchido
        if((mSPNPressaoSistolica.getSelectedItemId() == 0 &&
                mSPNPressaoDiastolica.getSelectedItemId() != 0) ||
                (mSPNPressaoSistolica.getSelectedItemId() != 0 &&
                        mSPNPressaoDiastolica.getSelectedItemId() == 0)) {
            gerarAlerta("Cadastro de pressão","Devem ser informados os valores de Pressão " +
                    "Sistólica e Diastólica.");
            return false;
        } else if(mSPNPressaoSistolica.getSelectedItemId() != 0 &&
                mSPNPressaoSistolica.getSelectedItemId() != 0) {
            // Valor de pressao diastolica maior que a sistolica
            if(Integer.parseInt(mSPNPressaoDiastolica.getSelectedItem().toString()) >=
                    Integer.parseInt(mSPNPressaoSistolica.getSelectedItem().toString())) {
                gerarAlerta("Cadastro de pressão","Pressão Sistólica deve ser maior que a Diastólica.");
                return false;
            }
        }
        if(!mETPeso.getText().toString().trim().isEmpty()) {
            Integer peso = Integer.parseInt(mETPeso.getText().toString());
            if(peso < 30 || peso > 250 ) {
                gerarAlerta("Cadastro de peso","Os valores de Peso devem ficar entre 30 e 250");
                return false;
            }
        }

        String glicemia = mETGlicemia.getText().toString();
        if(!glicemia.trim().isEmpty()) {
            int valor_glicemia = Integer.parseInt(glicemia);
            if(valor_glicemia < 30 || valor_glicemia > 600) {
                gerarAlerta("Cadastro de glicemia","Os valores de Glicemia devem ficar entre 30 e 600");
                return false;
            }
        }
        return true;
    }

    public void gerarAlerta(String title, String msg) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_registro_coleta,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch(item.getItemId()) {
            case R.id.menu_cadastro_registro_coleta_ajuda:
                i = new Intent(getApplicationContext(),
                        AjudaCadastroRegistroColetaActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(i);
        return true;
    }

}
