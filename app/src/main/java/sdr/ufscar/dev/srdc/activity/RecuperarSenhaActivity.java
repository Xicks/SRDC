package sdr.ufscar.dev.srdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.facade.UsuarioFacade;
import sdr.ufscar.dev.srdc.mail.GmailSender;
import sdr.ufscar.dev.srdc.model.Usuario;
import sdr.ufscar.dev.srdc.util.AppUtils;
import sdr.ufscar.dev.srdc.util.EmailUtils;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText mETEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        mETEmail = (EditText) findViewById(R.id.activity_recuperar_senha_et_email);
    }

    public void enviarSenha(View view) {
        String email = mETEmail.getText().toString();
        if (AppUtils.isEmailValido(email)) {
            String novaSenha = new UsuarioFacade().trocarEsquecerSenha(email);
            if (novaSenha != null) {
                EmailUtils.enviarEmailRecuperacaoSenha(RecuperarSenhaActivity.this,email,novaSenha);
            } else {
                mETEmail.setError("Email não cadastrado");
            }
        } else {
            mETEmail.setError("Email Inválido");
        }
    }

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

}
