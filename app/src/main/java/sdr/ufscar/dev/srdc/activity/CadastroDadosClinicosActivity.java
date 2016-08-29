package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.facade.DadosClinicosFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.DiasEnum;
import sdr.ufscar.dev.srdc.model.DoencaEnum;
import sdr.ufscar.dev.srdc.util.AppUtils;

public class CadastroDadosClinicosActivity extends AppCompatActivity {

    private Cidadao cidadao;

    private EditText mETCnsProfissional;
    private EditText mETCnes;
    private EditText mETObservacoes;
    private TextView mTVDoencas;
    private Spinner mSPNAltura;
    private CheckBox mCHKI1015;
    private CheckBox mCHKE1014;
    private CheckBox mCHKE6568;
    private CheckBox mCHKEnviarNotificao;
    private Button mBTRemoverHorario;
    private LinearLayout mLLHorarios;
    private ArrayList<Spinner> mHorariosSpinner;

    private ArrayList<DiasEnum> mDias;

    // Resposta dos alertas de erro
    private boolean mResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SRDCApplication app = (SRDCApplication) super.getApplication();
        cidadao = app.getCidadaoInstance();
        mDias = new ArrayList<>(7);

        setContentView(R.layout.activity_registro_dados_clinicos);

        mETCnsProfissional = (EditText) findViewById(
                R.id.activity_cadastrodadosclinicos_et_cnsprofissional);
        mETCnes = (EditText) findViewById(R.id.activity_cadastrodadosclinicos_et_cnes);
        mTVDoencas = (TextView) findViewById(R.id.activity_cadastrodadosclinicos_tv_doencas);
        mSPNAltura = (Spinner) findViewById(R.id.activity_cadastrodadosclinicos_spn_altura);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_cadastrodadosclinicos_alturas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPNAltura.setAdapter(adapter);

        mCHKI1015 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_i1015);
        mCHKE1014 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e1014);
        mCHKE6568 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e6568);
        mBTRemoverHorario =
                (Button) findViewById(R.id.activity_cadastrodadosclinicos_bt_removerhorario);

        mLLHorarios = (LinearLayout) findViewById(R.id.activity_cadastrodadosclinicas_ll_horarios);
        mHorariosSpinner = new ArrayList<>();

        mETObservacoes = (EditText) findViewById(R.id.activity_cadastrodadosclinicos_et_observacoes);

        mCHKEnviarNotificao = (CheckBox) findViewById(
                R.id.activity_cadastrodadosclinicas_chk_enviarnotificacao);

        gerarAlerta("Alerta","Os dados a seguir devem ser preenchidos somente por um profissional " +
                "do SUS.");
    }

    public void removerHorario(View v) {
        //Remove o ultimo Spinner de horario
        if(!mHorariosSpinner.isEmpty()) {
            int idSpinner = mHorariosSpinner.size() - 1;
            mHorariosSpinner.remove(idSpinner);
            mLLHorarios.removeViewAt(idSpinner);
            if(mHorariosSpinner.isEmpty()) mBTRemoverHorario.setEnabled(false);
        }
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
        mBTRemoverHorario.setEnabled(true);

    }

    // Muda o dia de coleta de acordo com a seleção do CheckBox
    public void mudarDia(View v){
        CheckBox chk = (CheckBox) v;
        if(chk.isChecked()) {
            mDias.add(DiasEnum.valueOf(chk.getText().toString().toUpperCase()));
        } else {
            mDias.remove(DiasEnum.valueOf(chk.getText().toString().toUpperCase()));
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

            ArrayList<DoencaEnum> doencas = new ArrayList<>();
            if(mCHKI1015.isChecked())
                doencas.add(DoencaEnum.I10_15_DOENÇAS_HIPERTENSIVAS);
            if(mCHKE1014.isChecked())
                doencas.add(DoencaEnum.E10_E14_DIABETES_MELLITUS);
            if(mCHKE6568.isChecked())
                doencas.add(DoencaEnum.E65_E68_OBESIDADE_HIPERALIMENTAÇÃO);

            dadosClinicos.setDoencas(doencas);

            dadosClinicos.setObservacoes(mETObservacoes.getText().toString());
            dadosClinicos.setEnviarNotificacao(mCHKEnviarNotificao.isChecked());

            if(!mDias.isEmpty()) {
                dadosClinicos.setDiasColeta(mDias);
                ArrayList<Integer> horas = new ArrayList<>(8);
                for (Spinner spn : mHorariosSpinner) {
                    String hora = spn.getSelectedItem().toString().split(":")[0];
                    horas.add(Integer.valueOf(hora));
                }
                dadosClinicos.setHorasColeta(horas);
            }

            Boolean sucesso = new DadosClinicosFacade().cadastrarDadosClinicos(dadosClinicos,cidadao);
            if (Boolean.TRUE.equals(sucesso)) {
                new AlertDialog.Builder(this).setTitle("Cadastro Realizado")
                        .setMessage("Seus dados clínicos foram registrados com sucesso!")
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_info).show();
            } else {
                gerarAlerta("Erro","Houve um erro ao realizar o cadastro. Tente novamente.");
            }
        }
    }

    /**
     * Mostra uma notificação de erro
     * @param msg
     */
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

    /**
     * Válida o formulário
     * @return se o formulário é válido
     */
    public boolean isFormularioValido(){
        boolean retorno = true;
        if(!AppUtils.isCNSValido(mETCnsProfissional.getText().toString())){
            mETCnsProfissional.setError("CNS Inválido");
            retorno = false;
        }
        if(mETCnes.getText().toString().trim().isEmpty()) {
            mETCnes.setError("CNES Inválido");
            retorno = false;
        }
        if(!mCHKE1014.isChecked() && !mCHKE6568.isChecked() && !mCHKI1015.isChecked()) {
            mTVDoencas.setError("Escolha uma doença");
            retorno = false;
        }
        if(!mHorariosSpinner.isEmpty() && mDias.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Dias de Coleta não definidos")
                    .setMessage("Você adicionou horários de coleta sem definir os dias da semana.\n" +
                            "Se continuar os horários de coleta não serão salvos.\nDeseja continuar?")
                    .setNegativeButton(R.string.nao,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mResposta = false;
                        }
                    })
                    .setPositiveButton(R.string.sim,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mResposta = true;
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
            if(!mResposta) retorno = false;
        }
        return retorno;
    }


}
