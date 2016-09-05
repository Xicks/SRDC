package sdr.ufscar.dev.srdc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.application.SRDCApplication;
import sdr.ufscar.dev.srdc.facade.CidadaoFacade;
import sdr.ufscar.dev.srdc.facade.UsuarioFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText mCpfCns;
    private EditText mSenha;
    private UsuarioFacade facade;
    private SRDCApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCpfCns = (EditText) findViewById(R.id.activity_login_et_cpfcns);
        mSenha = (EditText) findViewById(R.id.activity_login_et_senha);
        facade = new UsuarioFacade();
    }

    /**
     * Loga o usuário
     * @param v
     */
    public void entrar(View v){
        Usuario usuario = new Usuario();
        usuario.setUsername(mCpfCns.getText() + "");
        usuario.setSenha(mSenha.getText()+ "");
        if(Boolean.TRUE.equals(facade.login(usuario))){
            Cidadao cidadao = new CidadaoFacade().procurarCidadaoPorUsuario(usuario.getIdUsuario());
            // Adiciona objeto cidadao na sessao
            cidadao.setUsuario(usuario);
            app = (SRDCApplication) getApplication();
            app.setCidadaoInstance(cidadao);

            if(cidadao.getDadosClinicos() == null) {
                Intent i = new Intent(getApplicationContext(),CadastroDadosClinicosActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(getApplicationContext(),InicioActivity.class);
                startActivity(i);
            }
        } else {
            Toast t = Toast.makeText(this.getBaseContext(),R.string.activity_login_cpf_cns_invalido,
                    Toast.LENGTH_SHORT);
            mSenha.setText("");
            t.show();
        }

    }

    public void cadastrarCidadao(View v){
        Intent i = new Intent(getBaseContext(),CadastroCidadaoActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume(){
        super.onResume();
        app = (SRDCApplication) getApplication();
        app.setCidadaoInstance(null);
    }
}
