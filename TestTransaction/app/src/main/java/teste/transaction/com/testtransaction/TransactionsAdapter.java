package teste.transaction.com.testtransaction;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.math.BigDecimal;


public class TransactionsAdapter extends BaseAdapter {

	Activity parentActivity;
	
	Cursor c;
	
	public TransactionsAdapter(Activity parentActivity, Cursor c) {
		super();
		this.parentActivity = parentActivity;
		this.c = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {			
			convertView = parentActivity.getLayoutInflater().inflate(R.layout.list_item_payment, null);



		}
		
		try {
			c.moveToPosition(position);

			TextView value=(TextView)convertView.findViewById(R.id.tvalue);
			TextView status=(TextView)convertView.findViewById(R.id.tstatus);
			TextView date=(TextView)convertView.findViewById(R.id.tdate);
			value.setText(c.getString(c.getColumnIndex("value")));
			status.setText(c.getString(c.getColumnIndex("status")));
			date.setText(c.getString(c.getColumnIndex("dateTransaction")));
			String teste=value.getText().toString();
			Log.d("aa",teste);




		} catch (Exception e) {
		}
		return convertView;

	}


	@Override
	public int getCount() {
		return c.getCount();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	
}
