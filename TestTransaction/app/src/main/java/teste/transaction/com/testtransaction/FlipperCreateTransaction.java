package teste.transaction.com.testtransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class FlipperCreateTransaction extends FlipperPane {




	EditText card_holder;
	EditText card_number;
	EditText card_brand;
	EditText expiration_month;
	EditText expiration_year;
	EditText CVV;
	View v;





	public int getLayoutResourceId() {
		return R.layout.create_transaction_pane;

	}
	public void setView(View v){
		this.v=v;

	}

	@Override
	public void onFlip() {

		card_holder=(EditText)v.findViewById(R.id.card_holder);
		card_number=(EditText)v.findViewById(R.id.card_number);
		card_brand=(EditText)v.findViewById(R.id.card_brand);
		expiration_month=(EditText)v.findViewById(R.id.expiration_month);
		expiration_year=(EditText)v.findViewById(R.id.expiration_year);
		CVV=(EditText)v.findViewById(R.id.CVV);



		try {

			card_brand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {

						String cardBrand = Extras.getInstance().getCardBrand(card_number.getText().toString());
						card_brand.setText(cardBrand);


					}
				}
			});
		}catch (Exception e){

		}





	}

	public void setInfoAccount(){

		Transaction.getInstance().setCard_holder(card_holder.getText().toString());
		Transaction.getInstance().setCard_number(card_number.getText().toString());
		Transaction.getInstance().setCard_brand(card_brand.getText().toString());
		if(!(TextUtils.isEmpty(expiration_month.getText().toString()))){
			Transaction.getInstance().setExpiration_month(Integer.parseInt(expiration_month.getText().toString()));
		}
		if(!(TextUtils.isEmpty(expiration_year.getText().toString()))){
			Transaction.getInstance().setExpiration_year(Integer.parseInt(expiration_year.getText().toString()));
		}

		Transaction.getInstance().setCVV(CVV.getText().toString());


	}


}
