package teste.transaction.com.testtransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


@SuppressLint("NewApi")
public class Extras {

	private static Extras instance = null;
	private DecimalFormat currencyDecimalFormatter ;
	private DecimalFormat numberDecimalFormatter;
	private String sCurrencySymbol;


	public static Extras getInstance() {
		if (null == instance) {
			instance = new Extras();
		}
		return instance;
	}
	
	private Extras() {
		currencyDecimalFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.getDefault());
		currencyDecimalFormatter.setGroupingUsed(true);
		currencyDecimalFormatter.setDecimalSeparatorAlwaysShown(true);
		currencyDecimalFormatter.setParseBigDecimal(true);
		currencyDecimalFormatter.setMinimumFractionDigits(2);
		currencyDecimalFormatter.setMaximumFractionDigits(2);
		try {
			currencyDecimalFormatter.setRoundingMode(RoundingMode.HALF_DOWN);
		}
		catch (Exception e) {
		}
		sCurrencySymbol = currencyDecimalFormatter.getDecimalFormatSymbols().getCurrencySymbol();
		
		numberDecimalFormatter = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
		numberDecimalFormatter.setGroupingUsed(true);
		numberDecimalFormatter.setDecimalSeparatorAlwaysShown(true);
		numberDecimalFormatter.setParseBigDecimal(true);
		numberDecimalFormatter.setMinimumFractionDigits(2);
		numberDecimalFormatter.setMaximumFractionDigits(2);
		try {
			numberDecimalFormatter.setRoundingMode(RoundingMode.HALF_DOWN);
		}
		catch (Exception e2) {
		}
		

	}
	





	public String getCardBrand(String number){
		String cardBrand="";


		CardType card=CardType.detect(number);

		if(card==CardType.MASTERCARD){

			cardBrand="MASTERCARD";
		}else if(card==CardType.VISA){
			cardBrand="VISA";

		}else if(card==CardType.AMERICAN_EXPRESS){
			cardBrand="AMERICAN EXPRESS";

		}else if(card==CardType.DINERS_CLUB){
			cardBrand="DINERS CLUB";

		}else if(card==CardType.DISCOVER){
			cardBrand="DISCOVER";

		}else if(card==CardType.JCB){
			cardBrand="JCB";

		}else if(card==CardType.DANKORT){
			cardBrand="DANKORT";
		}else if(card==CardType.ELECTRON){
			cardBrand="ELECTRON";
		}else if(card==CardType.MAESTRO){
			cardBrand="MAESTRO";
		}else if(card==CardType.INTERPAYMENT){
			cardBrand="INTERPAYMENT";
		}else if(card==CardType.UNIONPAY){
			cardBrand="UNIONPAY";
		}
		else if(card==CardType.UNKNOWN) {
			cardBrand = "Desconhecido";
		}









			return cardBrand;
	}

	
	public static String getFormattedDate(Date date, String sDateFormat) {
		return DateFormat.format(sDateFormat, date).toString();
	}
	
	public static String getFormattedDateForUTCTimestamp(long timestamp) {
		Calendar utcDate = 	Calendar.getInstance(TimeZone.getDefault());
		utcDate.setTimeInMillis(timestamp);
		//Date date = new Date(utcDate);
		return utcDate.get(Calendar.DAY_OF_MONTH)+"/"+(utcDate.get(Calendar.MONTH)+1)+"/"+utcDate.get(Calendar.YEAR);
	}


	
	public static BigDecimal addDigitToBigDecimal(String sCurrentValue, int intDigitValue) throws Exception {
		try {

			BigDecimal newValue = null;
			//String customValueString =  getInstance().sCurrencySymbol+sCurrentValue;
			BigDecimal customValue = (BigDecimal) getInstance().numberDecimalFormatter.parse(sCurrentValue);
			newValue = customValue.movePointRight(3);
			newValue = newValue.add(new BigDecimal(intDigitValue));
			newValue = newValue.movePointLeft(2);
			return newValue;
		} catch (Exception exception) {

		}
		return null;
	}

	public BigDecimal divideBy10(BigDecimal value) throws ParseException {
		BigDecimal newValue = value.divide(new BigDecimal(10), 2, RoundingMode.DOWN);
		return newValue;
	}





	//NumberFormat.getCurrencyInstance().parse((String) tvCustomAmount.getText()).doubleValue()
	
	public String formatBigDecimalAsLocalString(BigDecimal value) {
		String sFormattedValue = numberDecimalFormatter.format(value);
		return sFormattedValue;
	}
	
	public String formatBigDecimalAsLocalMoneyString(BigDecimal value) {
		String sFormattedValue = currencyDecimalFormatter.format(value);
		return sFormattedValue;
	}
	
	public String getUSDecimalStringAsLocalMoneyString(String sDecimalValue) {
		BigDecimal bdValue = getBigDecimalFromDecimalString(sDecimalValue);
		return formatBigDecimalAsLocalMoneyString(bdValue);
	}

	public BigDecimal getBigDecimalFromDecimalString(String sMoneyValue) {
		try {

			BigDecimal value = (BigDecimal) numberDecimalFormatter.parse(sMoneyValue);
			return value;
			
		} catch (Exception e) {

            try {
                getBigDecimalFromDecimalStringInPaymentsFormat(sMoneyValue);
            }
            catch (Exception e2) {
            }
			return null;
		}
	}

    public BigDecimal getBigDecimalFromDecimalStringInPaymentsFormat(String sDecimal) {
        try {
            BigDecimal value = getBigDecimalFromDecimalStringInPaymentsFormatRaw(sDecimal);
            return value;
        }
        catch (Exception e) {

            return new BigDecimal(0);
        }
    }


	public BigDecimal getBigDecimalFromDecimalStringInPaymentsFormatRaw(String sDecimal) throws ParseException {
		DecimalFormat numberAPIDecimalFormatter = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
		numberAPIDecimalFormatter.setParseBigDecimal(true);

		try {
			numberAPIDecimalFormatter.setRoundingMode(RoundingMode.HALF_DOWN);
		}
		catch (Exception e2) {
		}
        BigDecimal value = (BigDecimal) numberAPIDecimalFormatter.parse(sDecimal);
        return value;
	}
	
	public static String leftPadZerosInteger(int number, int numberLength) {
		return String.format("%0" + numberLength + "d", number);
	}




}
