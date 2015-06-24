package unifei.edu.br.techcar;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import java.util.ArrayList;

/*
Classe para manipulação do banco de dados SQL Lite
 */

public class Database extends SQLiteOpenHelper{

    // BD informações
    private static final String DATABASE_NAME = "TechCar";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    // Construtor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    /**
     * Execute all of the SQL statements in the String[] array
     * @param db The database on which to execute the statements
     * @param sql An array of SQL statements to execute
     */
    private void execMultipleSQL(SQLiteDatabase db, String[] sql){
        for( String s : sql )
            if (s.trim().length()>0)
                db.execSQL(s);
    }

    /** Called when it is time to create the database */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] sql = mContext.getString(R.string.Database_onCreate).split("\n");
        db.beginTransaction();
        try {
            // Create tables & test data
            execMultipleSQL(db, sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error creating tables", e.toString());
        } finally {
            db.endTransaction();
        }
    }

    /** Called when the database must be upgraded */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LoginActivity.LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ", which will destroy all old data");
        String[] sql = mContext.getString(R.string.Database_onUpgrade).split("\n");
        db.beginTransaction();
        try {
            // Create tables & test data
            execMultipleSQL(db, sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error creating tables", e.toString());
        } finally {
            db.endTransaction();
        }
        // This is cheating.  In the real world, you'll need to add columns, not rebuild from scratch
        onCreate(db);
    }

    /*
    USUÁRIOS
     */

    // Método para adicionar novo usuário no BD, retorna um valor booleano
    public boolean adicionarUsuario(String nome, String login, String senha, String nascimento, String cpf, String email, String telefone){

        String sql =("SELECT login,senha from usuarios where login ='" + login + "'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        // Verificar se usuáio já existe
        if(cursor.getCount() == 0) {
            ContentValues entry = new ContentValues();
            entry.put("login", login);
            entry.put("nome", nome);
            entry.put("nascimento", nascimento);
            entry.put("cpf", cpf);
            entry.put("senha", senha);
            entry.put("email", email);
            entry.put("telefone", telefone);

            try {
                getWritableDatabase().insert("usuarios", null, entry);
            } catch (SQLException e) {
                Log.e("Erro ao escrever no BD", e.toString());
                return false;
            }

            return true;
        }else{
            return false;
        }
    }

    // consultar usuário específico no BD
    public int consultarUsuario(String login, String senha){

        String sql =("SELECT login,senha from usuarios where login ='" + login + "'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        /*
         * 1 - Usuário inválido
         * 2 - Senha inválida
         * 3 - Sucesso
         */

        if(cursor.getCount() == 0){
            return 1;
        }else{
            String senha_bd = cursor.getString(cursor.getColumnIndex("senha"));
            if(senha_bd.equals(senha)){
               return 3;
            }else{
                return 2;
            }
        }
    }

    // Editar usuário existente
    public void editarUsuario(String senha, String email, String telefone, String login) {
        ContentValues entry = new ContentValues();

        if(!senha.isEmpty()){
            entry.put("senha", senha);
        }

        if(!email.isEmpty()){
            entry.put("email", email);
        }

        if(!telefone.isEmpty()){
            entry.put("telefone", telefone);
        }

        String[] whereArgs = new String[]{login};

        try {
            getWritableDatabase().update("usuarios", entry, "login=?", whereArgs);
        } catch (SQLException e) {
            Log.e("Erro ao escrever no BD", e.toString());
        }
    }

    // Deletar usuário
    public void deletarUsuario(String login) {
        String[] whereArgs = new String[]{login};
        try {
            getWritableDatabase().delete("usuarios", "login=?", whereArgs);
        } catch (SQLException e) {
            Log.e("Erro ao deletar no BD", e.toString());
        }
        // Deleta todos os automóveis do usuário
        deletarAllAutomovel(login);
    }

    /*
    AUTOMÓVEIS
     */

    // Adicionar automóvel
    public void adicionarAutomovel(String modelo, String tipo, String marca, int ano, String cor, float potencia, float consumo, float capacidade, int hardware, String login){

        ContentValues entry = new ContentValues();
        entry.put("modelo", modelo);
        entry.put("tipo", tipo);
        entry.put("marca", marca);
        entry.put("ano", ano);
        entry.put("cor", cor);
        entry.put("potencia", potencia);
        entry.put("capacidade", capacidade);
        entry.put("consumo", consumo);
        entry.put("hardware", hardware);
        entry.put("login", login);

        try {
            getWritableDatabase().insert("automoveis", null, entry);
        } catch (SQLException e) {
            Log.e("Erro ao escrever no BD", e.toString());
        }
    }

    // Listar automóveis
    public ArrayList<String> listarAutomoveis(String login){

        String sql =("SELECT * from automoveis where login = '"+login+"'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // lista de automóveis
        ArrayList<String> lista = new ArrayList<>();

        // Preencher lista
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            String status;
            do {
                if(cursor.getString(cursor.getColumnIndex("hardware")).equals("1")){
                    status="Ativo";
                }else{
                    status="Inativo";
                }
                lista.add(cursor.getString(cursor.getColumnIndex("marca")) + " / " + cursor.getString(cursor.getColumnIndex("modelo"))+" - "+status);
            } while (cursor.moveToNext());
        }
        return lista;
    }

    // Buscar automóvel em uma posiçã específica
    public ArrayList<String> automovelPos(int pos,String login){

        String sql =("SELECT * from automoveis where login = '"+login+"' limit "+pos+",1");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // info do automóvel
        ArrayList<String> info = new ArrayList<>();

        // Preencher informações
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            info.add(cursor.getString(cursor.getColumnIndex("modelo")));
            info.add(cursor.getString(cursor.getColumnIndex("tipo")));
            info.add(cursor.getString(cursor.getColumnIndex("marca")));
            info.add(cursor.getString(cursor.getColumnIndex("ano")));
            info.add(cursor.getString(cursor.getColumnIndex("cor")));
            info.add(cursor.getString(cursor.getColumnIndex("potencia")));
            info.add(cursor.getString(cursor.getColumnIndex("consumo")));
            info.add(cursor.getString(cursor.getColumnIndex("capacidade")));
            info.add(cursor.getString(cursor.getColumnIndex("hardware")));
        }
        return info;
    }

    // Editar autmóvel
    public void editarAutomovel(String cor, String potencia, String consumo, String login, String modelo) {
        ContentValues entry = new ContentValues();

        if(!cor.isEmpty()){
            entry.put("cor", cor);
        }

        if(!potencia.isEmpty()){
            entry.put("potencia", Float.parseFloat(potencia));
        }

        if(!consumo.isEmpty()){
            entry.put("consumo", Float.parseFloat(consumo));
        }

        String[] whereArgs = new String[]{modelo};

        try {
            getWritableDatabase().update("automoveis", entry, "modelo = ?", whereArgs);
        } catch (SQLException e) {
            Log.e("Erro ao escrever no BD", e.toString());
        }
    }

    // Deletar automóvel
    public void deletarAutomovel(String login, String modelo) {
        String[] whereArgs = new String[]{login,modelo};
        try {
            getWritableDatabase().delete("automoveis", "login = ? AND modelo = ?", whereArgs);
        } catch (SQLException e) {
            Log.e("Erro ao deletar no BD", e.toString());
        }
        deletarAllCustos(modelo,login);
    }

    // Deletar todos os automóveis de um usuário específico
    public void deletarAllAutomovel(String login) {
        String[] whereArgs = new String[]{login};
        try {
            getWritableDatabase().delete("automoveis", "login=?", whereArgs);
        } catch (SQLException e) {
            Log.e("Error deleteing job", e.toString());
        }
    }

    /*
    CUSTOS
     */

    // Adicionar custo
    public void adicionarCusto(String data, float custo, String modelo, String login){

        String sql =("SELECT * from custos where modelo = '"+modelo+"' AND login = '"+login+"'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        int id = cursor.getCount()+1;

        ContentValues entry = new ContentValues();
        entry.put("id", id);
        entry.put("data", data);
        entry.put("custo", custo);
        entry.put("modelo", modelo);
        entry.put("login", login);

        try {
            getWritableDatabase().insert("custos", null, entry);
        } catch (SQLException e) {
            Log.e("Erro ao escrever no BD", e.toString());
        }
    }

    // Listar custos
    public ArrayList<String> listarCustos(String modelo, String login){

        String sql =("SELECT * from custos where modelo = '"+modelo+"' AND login = '"+login+"'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // lista de custos
        ArrayList<String> lista = new ArrayList<>();

        // Preencher lista
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                lista.add(cursor.getInt(cursor.getColumnIndex("id")) + "@" + cursor.getString(cursor.getColumnIndex("data"))+"@" + cursor.getFloat(cursor.getColumnIndex("custo")));
            } while (cursor.moveToNext());
        }
        return lista;
    }

    // Deletar todos os custos de um automóvel
    public void deletarAllCustos(String modelo,String login) {
        String[] whereArgs = new String[]{modelo,login};
        try {
            getWritableDatabase().delete("custos", "modelo = ? AND login = ?", whereArgs);
        } catch (SQLException e) {
            Log.e("Error deleteing job", e.toString());
        }
    }
}