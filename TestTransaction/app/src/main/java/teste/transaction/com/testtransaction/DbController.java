package teste.transaction.com.testtransaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by mainente on 29/06/16.
 */
public class DbController {

    private SQLiteDatabase db;
    private DbOpenHelper dbOpen;

    public DbController(Context context){
        dbOpen = new DbOpenHelper(context);
    }

    public Boolean insertTransaction(){
        ContentValues valores;
        long resultado = 0;
        try {


            db = dbOpen.getWritableDatabase();


            valores=new ContentValues();

            valores.put("cardHolder", Transaction.getInstance().getCard_holder());
            valores.put("cardNumber",Transaction.getInstance().getCard_number());
            valores.put("cardBrand",Transaction.getInstance().getCard_brand());
            valores.put("expiration_month",Transaction.getInstance().getExpiration_month());
            valores.put("expiration_year",Transaction.getInstance().getExpiration_year());
            valores.put("CVV",Transaction.getInstance().getCVV());

            try {
                resultado = db.insert("CardTransaction", null, valores);
            }catch (Exception e){

                String teste=e.toString();
            }
            if (resultado == -1) {
                db.close();

                return false;


            } else {
                SQLiteStatement scsAddItemInsert = null;

                String sql="insert into Transactions(value,idCardTransaction,status) values(?,(select MAX(id)from CardTransaction),?)";

                scsAddItemInsert=db.compileStatement(sql);


                Double value=Transaction.getInstance().getValue();
                String status=Transaction.getInstance().getStatus();


                scsAddItemInsert.bindDouble(1, value);
                scsAddItemInsert.bindString(2, status);


                if (scsAddItemInsert.executeInsert() > 0) {
                    db.close();


                    return true;





                }else {
                    db.close();

                    return false;
                }




            }



        }catch (Exception e){


        }
        return false;


    }

    public Cursor listPayment() {


        SQLiteDatabase db = dbOpen.getReadableDatabase();
        String sql = "select value,status,dateTransaction from Transactions;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

            String teste = cursor.getString(cursor.getColumnIndex("value"));
            Log.d("t", teste);


        }


        return cursor;

    }
}