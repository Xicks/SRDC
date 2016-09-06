package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sdr.ufscar.dev.srdc.database.DatabaseHelper;
import sdr.ufscar.dev.srdc.model.RegistroColeta;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/23/16.
 */
public class RegistroColetaDAO implements GenericDAO<RegistroColeta>{

    /**
     * Insere um novo registro no banco da entidade RegistroColeta
     * @param registroColeta
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean insert(RegistroColeta registroColeta) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("registro_coleta_id_dados_clinicos",registroColeta.getIdDadosClinicos());
        if(registroColeta.getGlicemia() != null) {
            cv.put("glicemia", registroColeta.getGlicemia());
        }
        if(registroColeta.getPressaoSistolica() != null &&
                registroColeta.getPressaoDiastolica() != null) {
            cv.put("pressao_sistolica", registroColeta.getPressaoSistolica());
            cv.put("pressao_diastolica", registroColeta.getPressaoDiastolica());
        }
        if(registroColeta.getGastoCalorico() != null) {
            cv.put("gasto_calorico", registroColeta.getGastoCalorico());
        }
        if(registroColeta.getGastoCalorico() != null) {
            cv.put("peso", registroColeta.getPeso());
        }
        cv.put("data_registro", AppUtils.converterData(registroColeta.getDataColeta()));
        long id = db.insert("registro_coleta",null,cv);
        db.close();
        if(id != -1) {
            registroColeta.setIdRegistroColeta((int)id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Integer idRegistro) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(RegistroColeta registroColeta) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public RegistroColeta select(Integer idRegistro) {
        //TODO
        return null;
    }

    public List<RegistroColeta> selectRegistroColetaNoIntervalo(Integer idDadosClinicos,
                                                               String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT * FROM registro_coleta WHERE registro_coleta_id_dados_clinicos = ?" +
                "AND data_registro BETWEEN ? AND ? ORDER BY data_registro",new String[]{
                idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setIdDadosClinicos(c.getInt(0));
                rc.setIdDadosClinicos(idDadosClinicos);
                if(!c.isNull(2)) rc.setPressaoSistolica(c.getInt(2));
                if(!c.isNull(3)) rc.setPressaoDiastolica(c.getInt(3));
                if(!c.isNull(4)) rc.setGlicemia(c.getInt(4));
                if(!c.isNull(5)) rc.setPeso(c.getInt(5));
                if(!c.isNull(6)) rc.setGastoCalorico(c.getInt(6));
                rc.setDataColeta(AppUtils.converterData(c.getString(7)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectGlicemiaMaximaNoIntervalo(Integer idDadosClinicos,
                                                               String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MAX(glicemia), data_registro FROM registro_coleta WHERE " +
                "registro_coleta_id_dados_clinicos = ?" +
                "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setGlicemia(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectGlicemiaMinimaNoIntervalo(Integer idDadosClinicos,
                                                                String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MIN(glicemia), data_registro FROM registro_coleta WHERE " +
                        "registro_coleta_id_dados_clinicos = ? AND glicemia IS NOT NULL" +
                        "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setGlicemia(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectPressaoSistolicaMaximaNoIntervalo(Integer idDadosClinicos,
                                                                        String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MAX(pressao_sistolica), pressao_diastolica, data_registro " +
                "FROM registro_coleta WHERE registro_coleta_id_dados_clinicos = ? " +
                "AND pressao_sistolica IS NOT NULL AND data_registro BETWEEN ? AND ? " +
                "GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setPressaoSistolica(c.getInt(0));
                rc.setPressaoDiastolica(c.getInt(1));
                rc.setDataColeta(AppUtils.converterData(c.getString(2)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectPressaoDiastolicaMinimaNoIntervalo(Integer idDadosClinicos,
                                                                         String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MIN(pressao_diastolica), pressao_sistolica, data_registro " +
                "FROM registro_coleta WHERE registro_coleta_id_dados_clinicos = ? " +
                "AND pressao_diastolica IS NOT NULL AND data_registro BETWEEN ? AND ? " +
                "GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setPressaoDiastolica(c.getInt(0));
                rc.setPressaoSistolica(c.getInt(1));
                rc.setDataColeta(AppUtils.converterData(c.getString(2)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectPesoMaximoNoIntervalo(Integer idDadosClinicos,
                                                            String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MAX(peso), data_registro FROM registro_coleta WHERE " +
                        "registro_coleta_id_dados_clinicos = ? AND peso IS NOT NULL " +
                        "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setPeso(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectPesoMinimoNoIntervalo(Integer idDadosClinicos,
                                                            String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MIN(peso), data_registro FROM registro_coleta WHERE " +
                        "registro_coleta_id_dados_clinicos = ? AND peso IS NOT NULL " +
                        "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setPeso(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectGastoCaloricoMaximoNoIntervalo(Integer idDadosClinicos,
                                                                     String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MAX(gasto_calorico), data_registro FROM registro_coleta WHERE " +
                        "registro_coleta_id_dados_clinicos = ? AND gasto_calorico IS NOT NULL " +
                        "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setGastoCalorico(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }

    public List<RegistroColeta> selectGastoCaloricoMinimoNoIntervalo(Integer idDadosClinicos,
                                                                     String dataInicio, String dataFinal) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();

        ArrayList<RegistroColeta> list;

        Cursor c = db.rawQuery("SELECT MIN(gasto_calorico), data_registro FROM registro_coleta WHERE " +
                        "registro_coleta_id_dados_clinicos = ? AND gasto_calorico IS NOT NULL " +
                        "AND data_registro BETWEEN ? AND ? GROUP BY data_registro ORDER BY data_registro",
                new String[]{idDadosClinicos.toString(), dataInicio, dataFinal});
        if(c != null) {
            list = new ArrayList<>(c.getCount());
            while(c.moveToNext()){
                RegistroColeta rc = new RegistroColeta();
                rc.setGastoCalorico(c.getInt(0));
                rc.setDataColeta(AppUtils.converterData(c.getString(1)));
                list.add(rc);
            }
            c.close();
        } else {
            list = new ArrayList<>();
        }
        db.close();
        return list;
    }
}
