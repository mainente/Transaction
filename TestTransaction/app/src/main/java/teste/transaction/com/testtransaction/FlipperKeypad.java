package teste.transaction.com.testtransaction;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;

public class FlipperKeypad extends FlipperPane {
			
    TextView tvValue = null;
	View v;

	public int getLayoutResourceId() {
		return R.layout.flipper_keypad;
	}

	public void setView(View v){
		this.v=v;

	}
	
	@Override
	public void onFlip() {
		tvValue = (TextView) v.findViewById(R.id.editTextValueToCharge);
		tvValue.setText(Extras.getInstance().formatBigDecimalAsLocalString(new BigDecimal(0)));
		
		Button bClear = (Button) v.findViewById(R.id.btClear);
		bClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tvValue.setText(Extras.getInstance().formatBigDecimalAsLocalString(new BigDecimal(0)));
			}
		});

		ImageButton bBackspace= (ImageButton) v.findViewById(R.id.btBackspace);
		bBackspace.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				try { 
					BigDecimal value = Extras.getInstance().getBigDecimalFromDecimalString( tvValue.getText().toString());
					value = Extras.getInstance().divideBy10(value);
					tvValue.setText(Extras.getInstance().formatBigDecimalAsLocalString(value));
					
				}
				catch (Exception exception) {
				}
			}
		});
		
		View parent = v.findViewById(R.id.linearLayoutKeypad);
		for (int i=0; i<=9; i++) {
			// Use tags to reuse similar code
			Button bNumber = (Button) parent.findViewWithTag(Integer.toString(i));
			final int buttonNumber = i;
			bNumber.setOnClickListener( new OnClickListener() {
				int intValueOfThisButton;
				@Override
				public void onClick(View v) {
					try {
						intValueOfThisButton = buttonNumber;						
						String sValue = tvValue.getText().toString();
						tvValue.setText(Extras.getInstance().formatBigDecimalAsLocalString(Extras.addDigitToBigDecimal(sValue, intValueOfThisButton)));
					}
					catch (Exception e) {
					}
				}
			});
		}

	}
}
