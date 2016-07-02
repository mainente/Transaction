package teste.transaction.com.testtransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mainente on 29/06/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "transaction.db";

    private static final int VERSAO = 1;

    public DbOpenHelper(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            String sql = "CREATE TABLE CardTransaction(id integer primary key autoincrement, cardHolder text, cardNumber text, cardBrand text, expiration_month integer, expiration_year integer, CVV String);";

            db.execSQL(sql);


            String sql2 = "CREATE TABLE Transactions(id integer primary key autoincrement, value decimal(15,3),status text, dateTransaction datetime DEFAULT CURRENT_TIMESTAMP, idCardTransaction integer, FOREIGN KEY (idCardTransaction) REFERENCES CardTransaction (id));";

            db.execSQL(sql2);


        }catch (Exception e){

            Log.d("error DB",e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS" + TABELA);
      //  onCreate(db);
    }
}
