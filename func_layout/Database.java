package unifei.edu.br.techcar;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.ArrayList;

public class Database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TechCar";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    /** Constructor */
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

    // Using the insert method

    public boolean addUsuario(String nome,String login,String senha,String nascimento,String cpf,String email,String telefone){

        String sql =("SELECT login,senha from usuarios where login ='" + login + "'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

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
                Log.e("Error writing new job", e.toString());
            }
            return true;
        }else{
            return false;
        }
    }

    // Using the update method ************************************************************
    public void editAlunos(String RA, String nome,String idade, String ano,String curso) {
        ContentValues entry = new ContentValues();
        entry.put("RA", RA);
        entry.put("Nome", nome);
        entry.put("Idade", idade);
        entry.put("Ano", ano);
        entry.put("Curso", curso);
        String[] whereArgs = new String[]{RA};
        try{
            getWritableDatabase().update("alunos", entry, "RA=?", whereArgs);
        } catch (SQLException e) {
            Log.e("Error writing new job", e.toString());
        }
    }

    public int consultaUsuario(String login, String senha){
        String sql =("SELECT login,senha from usuarios where login ='" + login + "'");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        /**
         * 1 - Usuário inválido
         * 2 - Senha inválida
         * 3 - Sucesso
         */

        if(cursor.getCount() == 0){
            return 1;
        }else{
            /*********** Fazer isto por cada coluna ***************/
            String senha_bd = cursor.getString(cursor.getColumnIndex("senha"));
            if(senha_bd.equals(senha)){
               return 3;
            }else{
                return 2;
            }
        }

    }

    // Using the delete method  ************************************************************

    public void deleteAlunos(String RA) {
        String[] whereArgs = new String[]{RA};
        try {
            getWritableDatabase().delete("Alunos", "RA=?", whereArgs);
        } catch (SQLException e) {
            Log.e("Error deleteing job", e.toString());
        }
    }

    public ArrayList<String> listarAutomoveis(){
        String sql =("SELECT * from automoveis");
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        ArrayList<String> lista = new ArrayList<>();
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                lista.add(cursor.getString(cursor.getColumnIndex("marca")) + " / " + cursor.getString(cursor.getColumnIndex("modelo")));
            } while (cursor.moveToNext());
        }
        return lista;
    }

    public void addAutomovel(String modelo,String tipo,String marca,int ano,String cor,float potencia, float consumo, float capacidade, int hardware){

        ContentValues entry = new ContentValues();
        entry.put("modelo", modelo);
        entry.put("tipo", tipo);
        entry.put("marca", marca);
        entry.put("ano", ano);
        entry.put("cor", cor);
        entry.put("potencia", potencia);
        entry.put("consumo", consumo);
        entry.put("hardware", hardware);
            try {
                getWritableDatabase().insert("automoveis", null, entry);
            } catch (SQLException e) {
                Log.e("Error writing new job", e.toString());
            }
    }

}