package sdr.ufscar.dev.srdc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;

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
        mETObservacoes = (EditText) findViewById(R.id.activity_cadastrodadosclinicos_et_observacoes);
        mSPNAltura = (Spinner) findViewById(R.id.activity_cadastrodadosclinicos_spn_altura);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_cadastrodadosclinicos_alturas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPNAltura.setAdapter(adapter);

        mCHKI1015 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_i1015);
        mCHKE1014 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e1014);
        mCHKE6568 = (CheckBox) findViewById(R.id.activity_cadastrodadosclinicos_chk_e6568);
        mCHKEnviarNotificao = (CheckBox) findViewById(
                R.id.activity_cadastrodadosclinicas_chk_enviarnotificacao);
        mLLHorarios = (LinearLayout) findViewById(R.id.activity_cadastrodadosclinicas_ll_horarios);
        mHorariosSpinner = new ArrayList<>();
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
}
