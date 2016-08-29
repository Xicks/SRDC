package sdr.ufscar.dev.srdc.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Schick on 8/25/16.
 */
public class DatabaseHelper {

    private static DatabaseHelper helper;
    private final static int VERSION = 1;
    private String DBPATH;
    private static final String DBNAME = "srdc.db";

    private DatabaseHelper(){}

    /**
     * Inicializa o banco de dados
     * @param ctx
     */
    public static void initDatabase(Context ctx) {
        if(!ctx.getDatabasePath(DBNAME).exists()) {
            SQLiteDatabase db = ctx.openOrCreateDatabase(DBNAME,SQLiteDatabase.CREATE_IF_NECESSARY,null);
            db.setVersion(VERSION);
            db.execSQL("CREATE TABLE dados_clinicos(dados_clinicos_id_dados_clinicos INTEGER " +
                    "PRIMARY KEY AUTOINCREMENT, cns_profissional TEXT, cnes TEXT, " +
                    "data_registro TEXT, altura INTEGER, i10_15 INTEGER DEFAULT 0, e10_e14 " +
                    "INTEGER DEFAULT 0, e65_68 INTEGER DEFAULT 0, observacoes TEXT, " +
                    "enviar_notificacao INTEGER DEFAULT 0);");
            db.execSQL("CREATE TABLE usuario (usuario_id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE, senha TEXT);");
            db.execSQL("CREATE TABLE cidadao (cidadao_id_cidadao INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cidadao_id_usuario INTEGER, cidadao_id_dados_clinicos INTEGER, nome TEXT, " +
                    "cpf_cns TEXT, data_nascimento TEXT, cidade TEXT, estado TEXT, " +
                    "FOREIGN KEY(cidadao_id_usuario) REFERENCES usuario(usuario_id_usuario) " +
                    ", FOREIGN KEY(cidadao_id_dados_clinicos) REFERENCES dados_clinicos" +
                    "(dados_clinicos_id_dados_clinicos));");
            db.execSQL("CREATE TABLE horarios_coleta(horarios_coleta_id_horarios_coleta INTEGER " +
                    "PRIMARY KEY AUTOINCREMENT, horarios_coleta_id_dados_clinicos INTEGER, " +
                    "dia_semana INTEGER, hora TEXT, FOREIGN KEY(horarios_coleta_id_dados_clinicos) " +
                    "REFERENCES dados_clinicos(dados_clinicos_id_dados_clinicos));");
            db.close();
        }
        helper = new DatabaseHelper();
        helper.DBPATH = ctx.getDatabasePath("srdc.db").getAbsolutePath();
        Log.d("DATABASE FACTORY","Database created!");
    }

    /**
     * Remove o banco de dados
     * @param ctx
     */
    public static void deleteDatabase(Context ctx) {
        ctx.deleteDatabase(DBNAME);
    }

    /**
     * Retorna instância do objeto DatabaseHelper
     * @return helper
     */
    public static DatabaseHelper getInstance() {
        return helper;
    }

    /**
     * Conecta com o banco de dados no modo READ ONLY
     * @return instância do banco de dados
     */
    public SQLiteDatabase openReadOnly(){
        return SQLiteDatabase.openDatabase(DBPATH,null,SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * Conecta com o banco de dados no modo READ WRITE
     * @return instância do banco de dados
     */
    public SQLiteDatabase openReadWrite(){
        return SQLiteDatabase.openDatabase(DBPATH,null,SQLiteDatabase.OPEN_READWRITE);
    }
}
