package sdr.ufscar.dev.srdc.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.facade.CidadaoFacade;
import sdr.ufscar.dev.srdc.facade.UsuarioFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.Usuario;
import sdr.ufscar.dev.srdc.util.AppUtils;

public class CadastroCidadaoActivity extends AppCompatActivity {

    private EditText mETNome;
    private EditText mETCpf;
    private EditText mETCns;
    private TextView mTVCpf;
    private TextView mTVCns;
    private Spinner mSPNEstados;
    private EditText mETCidade;
    private EditText mETDataNascimento;
    private CheckBox mCHKNaoTenhoCNS;
    private EditText mETSenha;

    private DatePickerDialog mDPDialog;

    private Calendar mDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cidadao);

        mETNome = (EditText) findViewById(R.id.activity_cadastrocidadao_et_nome);
        mETCidade = (EditText) findViewById(R.id.activity_cadastrocidadao_et_cidade);
        mETCpf = (EditText) findViewById(R.id.activity_cadastrocidadao_et_cpf);
        mTVCpf = (TextView) findViewById(R.id.activity_cadastrocidadao_tv_cpf);
        mETCns = (EditText) findViewById(R.id.activity_cadastrocidadao_et_cns);
        mTVCns = (TextView) findViewById(R.id.activity_cadastrocidadao_tv_cns);
        mSPNEstados = (Spinner) findViewById(R.id.activity_cadastrocidadao_spn_estado);
        mETDataNascimento = (EditText) findViewById(R.id.activity_cadastrocidadao_et_datanascimento);

        mDataNascimento = Calendar.getInstance();
        mDPDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDataNascimento.set(year, monthOfYear, dayOfMonth);
                mETDataNascimento.setText(new SimpleDateFormat("dd/MM/yyyy")
                        .format(mDataNascimento.getTime()));
            }

        },mDataNascimento.get(Calendar.YEAR), mDataNascimento.get(Calendar.MONTH), mDataNascimento.get(Calendar.DAY_OF_MONTH));

        mCHKNaoTenhoCNS = (CheckBox) findViewById(R.id.activity_cadastrocidadao_chk_naotenhocns);
        mETSenha = (EditText) findViewById(R.id.activity_cadastrocidadao_et_senha);
        popularEstados();
    }

    /**
     * Popula Spinner com as siglas dos estados brasileiros
     */
    public void popularEstados() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_cadastrocidadao_estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPNEstados.setAdapter(adapter);
    }

    /**
     * Mostra a caixa de diálogo para escolha da data de nascimento
     * @param v
     */
    public void escolherDataDeNascimento(View v) {
        mDPDialog.show();
    }

    /**
     * Ativa e desativa os campos CPF e CNS de acordo com o estado do checkbox
     * @param v
     */
    public void mudarCheckbox(View v) {
        if(mCHKNaoTenhoCNS.isChecked()){
            mETCns.setText("");
            mETCns.setEnabled(false);
            mTVCns.setEnabled(false);
            mETCpf.setEnabled(true);
            mTVCpf.setEnabled(true);
        } else {
            mETCpf.setText("");
            mETCns.setEnabled(true);
            mTVCns.setEnabled(true);
            mETCpf.setEnabled(false);
            mTVCpf.setEnabled(false);
        }
    }

    /**
     * Processa o formulário e cadastra
     * @param v
     */
    public void cadastrar(View v) {
        if(isFormularioValido()){
            Cidadao cidadao = new Cidadao();
            Usuario usuario = new Usuario();
            cidadao.setNome(mETNome.getText().toString());
            if(mCHKNaoTenhoCNS.isChecked()) {
                cidadao.setCpfCns(mETCpf.getText().toString());
                usuario.setUsername(mETCpf.getText().toString());
            } else {
                cidadao.setCpfCns(mETCns.getText().toString());
                usuario.setUsername(mETCns.getText().toString());
            }
            cidadao.setCidade(mETCidade.getText().toString());
            cidadao.setEstado(mSPNEstados.getSelectedItem().toString());
            cidadao.setDataNascimento(mDataNascimento.getTime());
            usuario.setSenha(mETSenha.getText().toString());
            cidadao.setUsuario(usuario);
            try {
                Boolean sucesso = new CidadaoFacade().cadastrarCidadao(cidadao);
                if (Boolean.TRUE.equals(sucesso)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Cadastro Realizado")
                            .setMessage("Seu cadastro foi realizado com sucesso!\n Utilize seu CPF/CNS" +
                                    " e senha para entrar no sistema.")
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
                gerarAlertaDeErro("CPF/CNS já cadastrado");
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
        if(mETNome.getText().toString().trim().isEmpty()) {
            mETNome.setError("Digite um nome");
            retorno = false;
        }
        if(mCHKNaoTenhoCNS.isChecked()){
            if(!AppUtils.isCPFValido(mETCpf.getText().toString())){
                mETCpf.setError("CPF Inválido");
                retorno = false;
            }
        } else {
            if(!AppUtils.isCNSValido(mETCns.getText().toString())){
                mETCns.setError("CNS Inválido");
                retorno = false;
            }
        }
        Calendar today = Calendar.getInstance();
        if(mETDataNascimento.getText().toString().isEmpty()|| mDataNascimento.after(today)) {
            mETDataNascimento.setError("Data de Nascimento Inválida");
            retorno = false;
        }
        if(mETCidade.getText().toString().isEmpty()) {
            mETCidade.setError("Digite o nome da cidade");
            retorno = false;
        }

        if(!Boolean.TRUE.equals(AppUtils.isSenhaValida(mETSenha.getText().toString()))){
            mETSenha.setError("Senha Inválida. (Senhas devem conter de 6 a 11 dígitos)");
            retorno = false;
        }
        return retorno;
    }
}
