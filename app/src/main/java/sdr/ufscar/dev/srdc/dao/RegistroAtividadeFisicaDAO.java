package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sdr.ufscar.dev.srdc.enumeration.AtividadeFisicaEnum;
import sdr.ufscar.dev.srdc.model.RegistroAtividadeFisica;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/31/16.
 */
public class RegistroAtividadeFisicaDAO implements GenericDAO<RegistroAtividadeFisica> {

    @Override
    public Boolean insert(RegistroAtividadeFisica registroAtividadeFisica) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();

        cv.put("registro_atividade_fisica_id_dados_clinicos",registroAtividadeFisica.getIdDadosClinicos());
        cv.put("dias",registroAtividadeFisica.getDias());
        StringBuilder sb = new StringBuilder();
        for(AtividadeFisicaEnum atividade: registroAtividadeFisica.getAtividades()){
            sb.append(atividade.toString());
            sb.append(" ");
        }
        cv.put("atividades",new String(sb));
        cv.put("tempo_aproximado",registroAtividadeFisica.getTempoAproximado());
        cv.put("data_registro", AppUtils.converterData(registroAtividadeFisica.getDataRegistro()));

        long id = db.insert("registro_atividade_fisica",null,cv);
        db.close();
        if(id != -1) {
            registroAtividadeFisica.setIdRegistroAtividadeFisica((int) id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Integer idRegistroAtividadeFisicoa) {
        return null;
    }

    @Override
    public Boolean update(RegistroAtividadeFisica registroAtividadeFisica) {
        return null;
    }

    @Override
    public RegistroAtividadeFisica select(Integer idRegistroAtividadeFisicoa) {
        return null;
    }
}
