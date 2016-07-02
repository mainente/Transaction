package teste.transaction.com.testtransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,ItemFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();





    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);

                ItemFragment fragListCard;
                    fragListCard = new ItemFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragListCard, "mainFrag");
                    ft.commit();





                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "Pagamento";

        int flipCurrentView = -1;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }
        FlipperCreateTransaction createTransaction;
        Button next;
        SendTransaction sendTransaction;
        TextView tvValue;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            FlipperKeypad flipperKeypad = new FlipperKeypad();
            flipperKeypad.setView(rootView);
            flipperKeypad.initializeHistory();
            flipperKeypad.setBaseView((ViewFlipper) rootView.findViewById(R.id.layoutFlipperPane));
            flipperKeypad.flipToPane(getActivity());
            flipCurrentView = R.layout.flipper_keypad;
            next=(Button)rootView.findViewById(R.id.next);



            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (flipCurrentView == R.layout.flipper_keypad) {

                        tvValue=(TextView)rootView.findViewById(R.id.editTextValueToCharge);

                        BigDecimal value = Extras.getInstance().getBigDecimalFromDecimalString( tvValue.getText().toString());

                        if(value.doubleValue()>=1) {

                            Transaction.getInstance().setValue(value.doubleValue());


                            createTransaction = new FlipperCreateTransaction();
                            createTransaction.initializeHistory();
                            createTransaction.setBaseView((ViewFlipper) rootView.findViewById(R.id.layoutFlipperPane));
                            createTransaction.setView(rootView);
                            createTransaction.flipToPane(getActivity());
                            flipCurrentView = R.layout.create_transaction_pane;
                        }else {

                            Toast.makeText(getActivity(),"O valor da venda precisa ser superior a R$ 1,00",Toast.LENGTH_LONG).show();
                        }


                    }else if (flipCurrentView == R.layout.create_transaction_pane) {

                        createTransaction.setInfoAccount();

                        Boolean validation;

                        String msg = null;

                        if (TextUtils.isEmpty(Transaction.getInstance().getCard_holder())) {

                            msg="Informe o Titular do cartão.";
                            validation=false;


                        }else if (TextUtils.isEmpty(Transaction.getInstance().getCard_number())) {

                            msg = "Informe o Numero do cartão.";
                            validation = false;
                        }else if (TextUtils.isEmpty(Transaction.getInstance().getCVV())) {

                            msg = "Informe o CVV do cartão.";
                            validation = false;
                        }else if ((Transaction.getInstance().getExpiration_month()<=0)||(Transaction.getInstance().getExpiration_month()>12)) {

                            msg = "Informe o mês de vencimento do cartão.";
                            validation = false;
                        }else if ((Transaction.getInstance().getExpiration_year()<=0)) {

                            msg = "Informe o ano de vencimento do cartão.";
                            validation = false;
                        }


                        else {
                            validation=true;
                        }


                        if(validation) {

                            sendTransaction = new SendTransaction();

                            sendTransaction.execute((Void) null);

                            FlipperChargeStatus flipperChargeStatus = new FlipperChargeStatus();
                            flipperChargeStatus.initializeHistory();
                            flipperChargeStatus.setBaseView((ViewFlipper) rootView.findViewById(R.id.layoutFlipperPane));
                            flipperChargeStatus.flipToPane(getActivity());
                            next.setEnabled(false);
                        }else {

                            Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                        }



                    }
                    else if (flipCurrentView == R.layout.flipper_approved || flipCurrentView==R.layout.flipper_failed) {


                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);



                    }

                }
            });




            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


        public class SendTransaction extends AsyncTask<Void, Void, Boolean> {
            JSONObject joResponse = null;
            @Override
            protected Boolean doInBackground(Void... params) {



                try {

                    String url = "http://private-e1cc0-transactionsimulator.apiary-mock.com/transaction";

                    RequestParams requestParams = new RequestParams();


                    requestParams.put("card_holder",Transaction.getInstance().getCard_holder());
                    requestParams.put("card_number",Transaction.getInstance().getCard_number());
                    requestParams.put("card_brand",Transaction.getInstance().getCard_brand());
                    requestParams.put("expiration_month",Transaction.getInstance().getExpiration_month());
                    requestParams.put("expiration_year",Transaction.getInstance().getExpiration_year());
                    requestParams.put("value",Transaction.getInstance().getValue());
                    requestParams.put("CVV",Transaction.getInstance().getCVV());






                    joResponse=Session.getInstance().httpSynchronousRequest(url,requestParams);

                    joResponse.toString();


                    Transaction.getInstance().setStatus(joResponse.getString("status"));

                    return true;
                    








                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return false;
            }



            protected void  onPostExecute(final Boolean result) {

                next.setEnabled(true);
                if (result) {

                    try {




                        DbController db = new DbController(getActivity());
                        Boolean insertDb = db.insertTransaction();
                            FlipperApproved flipperApproved = new FlipperApproved();
                            flipperApproved.initializeHistory();
                            flipperApproved.setBaseView((ViewFlipper) getActivity().findViewById(R.id.layoutFlipperPane));
                            flipperApproved.flipToPane(getActivity());

                            flipCurrentView = R.layout.flipper_approved;

                            next.setText("Nova Venda");
                            Transaction.getInstance().setInstance(null);


                    }catch (Exception e){

                    }
                } else {

                    FlipperFailed flipperFailed = new FlipperFailed();
                    flipperFailed.initializeHistory();
                    flipperFailed.setBaseView((ViewFlipper) getActivity().findViewById(R.id.layoutFlipperPane));
                    flipperFailed.flipToPane(getActivity());

                    flipCurrentView = R.layout.flipper_failed;

                    next.setText("Nova Venda");
                    Transaction.getInstance().setInstance(null);


                }
            }
        }
    }



}
