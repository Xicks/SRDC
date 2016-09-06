package sdr.ufscar.dev.srdc.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sdr.ufscar.dev.srdc.R;
import sdr.ufscar.dev.srdc.database.DatabaseHelper;
import sdr.ufscar.dev.srdc.facade.CidadaoFacade;
import sdr.ufscar.dev.srdc.facade.DadosClinicosFacade;
import sdr.ufscar.dev.srdc.facade.RegistroColetaFacade;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.enumeration.DoencaEnum;
import sdr.ufscar.dev.srdc.model.RegistroColeta;
import sdr.ufscar.dev.srdc.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteDatabase("srdc.db");
        DatabaseHelper.initDatabase(this);

        Cidadao cidadao = new Cidadao();
        cidadao.setNome("Leonardo Schick");
        cidadao.setCpfCns("123");
        cidadao.setEstado("SP");
        cidadao.setDataNascimento(new Date());
        cidadao.setCidade("Araraquara");
        Usuario usuario = new Usuario();
        usuario.setUsername("123");
        usuario.setEmail("lschicks@mail.ccsf.edu");
        usuario.setSenha("123");
        cidadao.setUsuario(usuario);

        DadosClinicos dadosClinicos = new DadosClinicos();
        dadosClinicos.setCnes("12345678");
        dadosClinicos.setCnsProfissional("121261419980006");
        ArrayList<Integer> horas = new ArrayList<>();
        horas.add(1);horas.add(2);horas.add(3);horas.add(4);horas.add(5);horas.add(6);horas.add(7);horas.add(8);
        dadosClinicos.setHorasColeta(horas);
        dadosClinicos.setDiasColeta("SE TE QA QI SX SA DO");
        dadosClinicos.setAltura(165);
        dadosClinicos.setDataRegistro(new Date());
        ArrayList<DoencaEnum> doencas = new ArrayList<>();
        doencas.add(DoencaEnum.I10_15_DOENÇAS_HIPERTENSIVAS);
        doencas.add(DoencaEnum.E65_E68_OBESIDADE_HIPERALIMENTAÇÃO);
        doencas.add(DoencaEnum.E10_E14_DIABETES_MELLITUS);
        dadosClinicos.setObservacoes("Monitorar logo após as refeições");
        dadosClinicos.setDoencas(doencas);

        new CidadaoFacade().cadastrarCidadao(cidadao);
        new DadosClinicosFacade().cadastrarDadosClinicos(dadosClinicos,cidadao);

        int glicemia = 100;
        int pressao_sistolica = 120;
        int pressao_diastolica = 80;
        int peso = 110;
        int gasto_calorico = 100;
        Calendar c = Calendar.getInstance();
        RegistroColetaFacade facade = new RegistroColetaFacade();
        for(int i = 1; i < 180; i++) {
            c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR) - 1);
            RegistroColeta registroColeta = new RegistroColeta();
            glicemia = (glicemia + (i * 5)) % 400;
            pressao_sistolica = ((pressao_sistolica * 2) % 100) + 100;
            pressao_diastolica = ((pressao_diastolica * 2) % 80) + 80;
            peso = 110 + ((peso + 1) % 110);
            gasto_calorico = 100 * (peso % 4);
            if(glicemia < 40) glicemia = 40;
            registroColeta.setGlicemia(glicemia);
            registroColeta.setPressaoSistolica(pressao_sistolica);
            registroColeta.setPressaoDiastolica(pressao_diastolica);
            registroColeta.setGastoCalorico(gasto_calorico);
            registroColeta.setDataColeta(c.getTime());
            registroColeta.setPeso(peso);
            facade.cadastrarRegistroColeta(registroColeta,dadosClinicos);
            registroColeta = new RegistroColeta();
            glicemia = (glicemia + (i * 2)) % 400;
            if(glicemia < 40) glicemia = 40;
            pressao_sistolica = ((pressao_sistolica * 2) % 100) + 100;
            pressao_diastolica = ((pressao_diastolica * 2) % 80) + 80;
            peso = 110 + ((peso + 1) % 110);
            gasto_calorico = 100 * (peso % 4);
            registroColeta.setGlicemia(glicemia);
            registroColeta.setPressaoSistolica(pressao_sistolica);
            registroColeta.setPressaoDiastolica(pressao_diastolica);
            registroColeta.setGastoCalorico(gasto_calorico);
            registroColeta.setDataColeta(c.getTime());
            registroColeta.setPeso(peso);
            facade.cadastrarRegistroColeta(registroColeta,dadosClinicos);
        }

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
