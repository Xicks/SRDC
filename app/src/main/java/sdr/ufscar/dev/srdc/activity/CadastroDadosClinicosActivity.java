package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.facade.CidadaoFacade;
import sdr.ufscar.dev.srdc.facade.DadosClinicosFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.DoencaEnum;
import sdr.ufscar.dev.srdc.model.Usuario;
import sdr.ufscar.dev.srdc.util.AppUtils;

public class CadastroDadosClinicosActivity extends AppCompatActivity {

    private EditText mETCnsProfissional;
    private EditText mETCnes;
    private EditText mETObservacoes;
    private Spinner mSPNAltura;
    private CheckBox mCHKI1015;
    private CheckBox mCHKE1014;
    private CheckBox mCHKE6568;
    private CheckBox mCHKEnviarNotificao;
    private LinearLayout mLLHorarios;
    private ArrayList<Spinner> mHorariosSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_dados_clinicos);
        mETCnsProfissional = (EditText) findViewById(
                R.id.activity_cadastrodadosclinicos_et_cnsprofissional);
        mETCnes = (EditText) findViewById(R.id.activity_cadastrodadosclinicos_et_cnes);
        //data de registro
        mSPNAltura = (Spinner) findViewById(R.id.activity_cadastrodadosclinicos_spn_altura);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_cadastrodadosclinicos_alturas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPNAltura.setAdapter(adapter);

        mCHKI1015 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_i1015);
        mCHKE1014 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e1014);
        mCHKE6568 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e6568);

        //TODO verificar comos serão os registros
        //TODO verificar como será essa visualizacao
        mLLHorarios = (LinearLayout) findViewById(R.id.activity_cadastrodadosclinicas_ll_horarios);
        mHorariosSpinner = new ArrayList<>();

        mETObservacoes = (EditText) findViewById(R.id.activity_cadastrodadosclinicos_et_observacoes);

        mCHKEnviarNotificao = (CheckBox) findViewById(
                R.id.activity_cadastrodadosclinicas_chk_enviarnotificacao);

    }

    public void adicionarHorario(View v){
        if(mHorariosSpinner.size() < 8) {
            Spinner spn = new Spinner(this);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                    R.array.activity_cadastrodadosclinicos_horarios, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn.setAdapter(adapter);
            mHorariosSpinner.add(spn);
            mLLHorarios.addView(spn);
        } else {
            Toast.makeText(getBaseContext(),R.string.activity_cadastrodadosclinicos_horariosexcedidos,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Processa o formulário e cadastra
     * @param v
     */
    public void cadastrar(View v) {
        if(isFormularioValido()){
            DadosClinicos dadosClinicos = new DadosClinicos();
            dadosClinicos.setCnsProfissional(mETCnsProfissional.getText().toString());
            dadosClinicos.setCnes(mETCnes.getText().toString());
            dadosClinicos.setDataRegistro(new Date());
            dadosClinicos.setAltura(Integer.parseInt(mSPNAltura.getSelectedItem().toString()));

            ArrayList<DoencaEnum> doencas = new ArrayList<DoencaEnum>();
            if(mCHKI1015.isChecked())
                doencas.add(DoencaEnum.I10_15_DOENÇAS_HIPERTENSIVAS);
            if(mCHKE1014.isChecked())
                doencas.add(DoencaEnum.E10_E14_DIABETES_MELLITUS);
            if(mCHKE6568.isChecked())
                doencas.add(DoencaEnum.E65_E68_OBESIDADE_HIPERALIMENTAÇÃO);

            dadosClinicos.setDoencas(doencas);


            //TODO pensar sobre como serao feitos os registros.

            //TODO pensar sobre como serao feitos esses horarios.


            dadosClinicos.setObservacoes(mETObservacoes.getText().toString());
            dadosClinicos.setEnviarNotificacao(mCHKEnviarNotificao.isChecked());

            try {
                Boolean sucesso = new DadosClinicosFacade().cadastrarDadosClinicos(dadosClinicos);
                if (Boolean.TRUE.equals(sucesso)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Cadastro Realizado")
                            .setMessage("Seus dados clínicos foram registrados com sucesso!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                } else {
                    gerarAlertaDeErro("Houve um erro ao realizar o cadastro. Tente novamente.");
                }
            }catch(CadastroDuplicadoException e) {
                gerarAlertaDeErro("Dados clínicos já cadastrados");
            }

        }
    }

    /**
     * Mostra uma notificação de erro
     * @param msg
     */
    public void gerarAlertaDeErro(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Válida o formulário
     * @return se o formulário é válido
     */
    public boolean isFormularioValido(){
        boolean retorno = true;
        return retorno;
    }


}
