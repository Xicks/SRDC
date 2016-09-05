package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.enumeration.AtividadeFisicaEnum;
import sdr.ufscar.dev.srdc.facade.RegistroAtividadeFisicaFacade;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.RegistroAtividadeFisica;

public class CadastroRegistroAtividadeFisica extends AppCompatActivity {

    private Spinner mSPNDias;
    private EditText mETTempo;
    private ArrayList<AtividadeFisicaEnum> mAtividades;
    private DadosClinicos dadosClinicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SRDCApplication app = (SRDCApplication) getApplication();
        dadosClinicos = app.getCidadaoInstance().getDadosClinicos();

        setContentView(R.layout.activity_cadastro_registro_atividade_fisica);

        mSPNDias = (Spinner) findViewById(R.id.activity_cadastro_registro_atividade_fisica_spn_dias);
        mETTempo = (EditText) findViewById(R.id.activity_cadastro_registro_atividade_fisica_et_tempo);

        mAtividades = new ArrayList<>(4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_cadastro_registro_atividade_fisica_dias,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPNDias.setAdapter(adapter);
    }

    public void adicionarAtividade(View v) {
        AtividadeFisicaEnum atividade;
        switch(v.getId()) {
            case R.id.activity_cadastro_registro_atividade_fisica_chk_atividades_alongamento:
                atividade = AtividadeFisicaEnum.ATIVIDADE_ALONGAMENTO;
                break;
            case R.id.activity_cadastro_registro_atividade_fisica_chk_aerobico:
                atividade = AtividadeFisicaEnum.AEROBICO;
                break;
            case R.id.activity_cadastro_registro_atividade_fisica_chk_musculacao:
                atividade = AtividadeFisicaEnum.MUSCULACAO;
                break;
            case R.id.activity_cadastro_registro_atividade_fisica_chk_esportes:
                atividade = AtividadeFisicaEnum.ESPORTES;
                break;
            default:
                atividade = null;
        }

        CheckBox chk = (CheckBox) v;
        if(chk.isChecked()) {
            mAtividades.add(atividade);
        } else {
            mAtividades.remove(atividade);
        }
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
                                    salvarRegistroAtividadeFisica();
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }
    private void salvarRegistroAtividadeFisica(){
        RegistroAtividadeFisica registroAtividadeFisica = new RegistroAtividadeFisica();
        Integer tempo = Integer.parseInt(mETTempo.getText().toString());
        registroAtividadeFisica.setTempoAproximado(tempo);
        registroAtividadeFisica.setAtividades(mAtividades);
        String dia = mSPNDias.getSelectedItem().toString().substring(0,1);
        registroAtividadeFisica.setDias(Integer.parseInt(dia));

        Boolean sucesso = new RegistroAtividadeFisicaFacade()
                .cadastrarRegistroAtividadeFisica(registroAtividadeFisica,dadosClinicos);
        if(Boolean.TRUE.equals(sucesso)){
            new AlertDialog.Builder(this).setTitle("Cadastro Realizado")
                    .setMessage("Seu registro de atividade física for registrado com sucesso!")
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_info).show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Houve um erro ao realizar o cadastro. Tente novamente.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    public boolean isFormularioValido(){
        boolean retorno = true;
        if(mETTempo.getText().toString().isEmpty()) {
            mETTempo.setError("Digite o tempo aproximado na semana");
            retorno = false;
        } else {
            int tempo = Integer.parseInt(mETTempo.getText().toString());
            if(tempo > 84) {
                mETTempo.setError("Tempo aproximado na semana inválido");
                retorno = false;
            }
        }
        if(mAtividades.isEmpty()) {
            Toast.makeText(getBaseContext(),
                    R.string.activity_cadastro_registro_atividade_fisica_atividade_invalida,
                    Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }
}
