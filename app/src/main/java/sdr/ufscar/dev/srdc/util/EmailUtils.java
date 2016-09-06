package sdr.ufscar.dev.srdc.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import sdr.ufscar.dev.srdc.mail.GmailSender;

/**
 * Created by Schick on 9/5/16.
 */
public class EmailUtils extends AsyncTask<Void,Void,Void>{

    private Boolean sucesso;
    private String subject;
    private String recipients;
    private String body;
    private Context context;
    private ProgressDialog progressDialog;
    private final String sender = "noreply@srdc.com.br";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Enviando...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private EmailUtils(Context ctx, String subject,String body,String recipients) {
        this.subject = subject;
        this.body = body;
        this.recipients = recipients;
        context = ctx;
    }

    @Override
    protected Void doInBackground(Void... v) {
        try {
            new GmailSender().sendMail(subject, body, sender, recipients);
            sucesso = Boolean.TRUE;
        } catch(Exception e) {
            sucesso = Boolean.FALSE;
        }
        return null;
    }

    protected void onPostExecute(Void v) {
        progressDialog.dismiss();
        if(Boolean.TRUE.equals(sucesso)){
            new AlertDialog.Builder(context)
                    .setTitle("Email enviado com sucesso")
                    .setMessage("O email chegará em alguns instantes")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Erro")
                    .setMessage("Erro ao eviar email. Tente novamente")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public static void enviarEmailRecuperacaoSenha(Context ctx, String email, String senha){
        String subject = "SRDC - Recuperar Senha";
        String body = "Você requisitou a recuperação de senha.\nSua nova senha é: " + senha;
        new EmailUtils(ctx, subject,body,email).execute();
    }
}
