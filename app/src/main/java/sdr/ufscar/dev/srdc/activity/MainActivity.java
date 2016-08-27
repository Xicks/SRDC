package sdr.ufscar.dev.srdc.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.dao.DatabaseHelper;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.facade.UsuarioFacade;
import sdr.ufscar.dev.srdc.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //deleteDatabase("srdc.db");
        DatabaseHelper.initDatabase(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(i);
            }
        }, 2000);
    }
}
