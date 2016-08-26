package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.facade.UsuarioFacade;
import sdr.ufscar.dev.srdc.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText mCpfCns;
    private EditText mSenha;
    private UsuarioFacade facade;

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
            Toast t = Toast.makeText(this.getBaseContext(),"Logado",
                    Toast.LENGTH_SHORT);
            mSenha.setText("");
            t.show();
        } else {
            Toast t = Toast.makeText(this.getBaseContext(),"CPF/CNS ou senha inválido!",
                    Toast.LENGTH_SHORT);
            mSenha.setText("");
            t.show();
        }

    }

    public void cadastrarCidadao(View v){
        Intent i = new Intent(getBaseContext(),CadastroCidadaoActivity.class);
        startActivity(i);
    }
}
