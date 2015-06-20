package com.giantturtle.badenumbersquiz;

import android.app.Activity;
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class QuizActiviyWithFragment extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_with_fragment);
        if (savedInstanceState == null) {//checks if its coming from a rotation, if not than savedInstanceState == null


            //setting a new fragment to be shown, dynamically (no fragment defined in xml)
            FragmentManager manager = getFragmentManager();
            QuizFragment kvizFragment = new QuizFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container_layout_OnActivity, kvizFragment, "QuizFragment");
            transaction.commit();


            //Autogenerisani kod
            /*getFragmentManager().beginTransaction()
                    .add(R.id.container_layout_OnActivity *//* id layouta u koji hoću da dodam fragment *//* , new PlaceholderFragment())
                    .commit();*/


            /* ovo iznad je ekvivalent koda (samo što gore kreiram new PlaceholderFragment kao jedan od parametara za add metod):
             QuizFragment kvizFragment = new QuizFragment();
             FragmentManager manager = getFragmentManager();
             FragmentTransaction transaction = manager.beginTransaction();
             transaction.add(R.id.container_layout_OnActivity, kvizFragment, "MojTagZaFragment");
             transaction.commit();
               */

            /* Može i da se napiše
             * QuizFragment kvizFragment = getFragmentManager().findFragmentById( R.id.fragment);
             * ako sam definisao fragment u xml kao <fragment> tag u xml layout-u ove aktivnosti, konkretno
             *  activity_kviz_with_fragment*/


             //neki korisni metodi koji mogu da se pozivaju iz Activity u koju hoću da dodam  ili izbacim fragment:

             /* za izbacivanje fragmenta:(mogu ovo da ekstrakujem kao metod public void addKvizFragment ))
               QuizFragment kvizFragment = getFragmentManager().findFragmentByTag("MojTagZaFragment");//dodao sam ovaj tag pri dodavanju fragmenta gore
               FragmentTransaction transaction = getFragmentManager().beginTransaction();
               if (kvizFragment != null){ //moram da proverim da li ima fragmenta pre nego što pokušam da ga izbacim da aplikacije  ne bi pukla
                   transaction.remove(kvizFragment);
                   transaction.commit(); }
               else {
                 Toast.makeText(this,"Fragment was naot added before",Toast.LENGHT_SHORT).show();
               }
                */

            /* za zamenu jednog fragmenta drugim:
               public void replaceFragment1withFragment2 (){
               QuizFragment fragmentKojiŽelimDaUbacim = new QuizFragment();
               FragmentManager manager = getFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();
               transaction.replace(R.id.layout_u_kojem_je_fragmet, fragmentKojiŽelimDaUbacim, "TagFragmentaKojiŽelimDaUbacim");
               transaction.commit();

               }

              */

            /* za prikazivanje i neprikazivanje fragmenta, koristim ovo kada ne želim da uništim objekat fragmenta, nego samo da ga sklonim sa aktivnosti.
                FragmentManager manager = getFragmentManager();
                QuizFragment fragmentKojiŽelimDaPrikažem = manager.findFragmentByTag("MojTagZaFragment");
               FragmentTransaction transaction = manager.beginTransaction();
               if (fragmentKojiŽelimDaPrikažem!=null){
               transaction.attach(fragmentKojiŽelimDaPrikažem);//ovo ga prikazuje
               transaction.detach(fragmentKojiŽelimDaPrikažem);//ovo sklanja fragment
               transaction.commit();
               }
               */

          }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_with_quiz, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {//ovo je autogenerisani kod

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_kviz_activiy_with, container, false);
            return rootView;
        }
    }*/
}
