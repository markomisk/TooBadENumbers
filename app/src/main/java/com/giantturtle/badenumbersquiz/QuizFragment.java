package com.giantturtle.badenumbersquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { @link QuizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    //Ovde mogu da declare UI elemente kao što to radim u aktivnostima

    List<Question> quesList;
    int score=0;
    int qid=0;
    Question currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button buttonPotvrdi;
    TextView scoreTextView;
    DbHelper db;

    // ovde mogu i da pristupim i drugim fragmentima, npr:
    // Fragment2  fragment2;
    // i onda u onCreate od ovog fragmenta inicijalizujem fragment:
    //  fragment2 = new Fragment2();  ili =newInstance(parametar1, parametar2);
    //i onda mogu da pristupam metodima tog fragmenta i poljima

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /*private OnFragmentInteractionListener mListener;*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        //ovde mogu da pravim fragment sa novim parametrima pozivajući ovaj metod
        //npr QuizFragment fragment = QuizFragment.newInstance(parametar1, parametar2)
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        //ovo je već bilo u kodu kao template
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) /* LayoutInflater inflater je objekat koji konvertuje xml u view */{
        // Inflate the layout for this fragment
        //poveujem fragment sa njegovim layoutom u ovom metodu.
        //mogu i ovde da pristupam sačuvanim podatcima u savedInstanceState naročito ako su vezani za UI to je preporučljivo
        // pristupam na isti način kao u onCreate

        //ovde ide kod koji hoću da inicijalizujem
        //ako fragment ima neko dugme u sebi, ne mogu direktno da mu pristupim nego moram preko view-a:

        /*View view = inflater.inflate(R.layout.fragment_kviz, container, false);
        buttonPotvrdi = (Button) view.findViewById(R.id.potvrdiOdgoovorButton);
        return view;*/

        //možda treba i ovako:
        /* ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_kviz, container, false); */

        //ovo ispod je view, samo je napisan lančano

        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {//ovde pristupam elementima UI od ovog fragmenta
    // na primer Button button = (Button)getActivity().findViewById(R.id.button);
    // jer je onCreate od Activity koja sadrži ovaj fragment je završen,
     // a da sam pokušao da pristupim elementima UI u nekom drugom metodu fragmenta, možda bih dobio grešku.
     // ne mogu direktno da koristim findViewById nego mora getActivity().findViewById ili getView().findViewById i ove pozive
     //mogu da koristim (getView...) samo posle onCreateView metoda da nebi izbacili null jel još ništa nije kreirano
        super.onActivityCreated(savedInstanceState);
        //ovde stavljam kod (ispod super)



        db=new DbHelper(getActivity());
        quesList=db.getAllQuestions();
        currentQ=quesList.get(qid);
        txtQuestion=(TextView)getActivity().findViewById(R.id.textPitanja);
        rda=(RadioButton)getActivity().findViewById(R.id.radio0);
        rdb=(RadioButton)getActivity().findViewById(R.id.radio1);
        rdc=(RadioButton)getActivity().findViewById(R.id.radio2);
        buttonPotvrdi =(Button)getActivity().findViewById(R.id.potvrdiOdgoovorButton);
        scoreTextView =(TextView)getActivity().findViewById(R.id.fragmentScoreTextView);



        setQuestionView();
        buttonPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp = (RadioGroup) getView().findViewById(R.id.radioGroupOdgovori);
                RadioButton answer = (RadioButton) getView().findViewById(grp.getCheckedRadioButtonId());
                Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());
                if (currentQ.getANSWER().equals(answer.getText())) {
                    score++;
                    Log.d("score", "Your score" + score);
                    //TODO stavi ovde kod koji upisuje u bazu da je prikazano pitanje i da je TAČNO odgovoreno
                } else if (!currentQ.getANSWER().equals(answer.getText())) {
                    score--;//umanjuje rezultat, razmisli jel želiš to da ostaviš
                    //možda da to bude da u sledećem nivou oduzima ako daš pogrešan odgovor
                    // a do sledećeg nivoa bi mogao da stigne tako što bi prešao nekkoliko puta po 8
                    //pitanja i onda ide objašnjenje da oduzima ako pogrešiš

                    //TODO stavi ovde kod koji upisuje u bazu da je prikazano pitanje i da je NETAČNO odgovoreno
                }
                if (qid < 10) {//ovde podešavam broj pitanja, vidi zašto
                    /* rešenje: ako je qid manji od određenog broja, onda prikaži sledeće pitanje
                     * a ako je na poslednjem pitanju onda pokaži ResultsActivity sa rezultatima */
                    currentQ = quesList.get(qid);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    /*finish();*/
                }
            }
        });

    }

    private void setQuestionView()
    {   //TODO stavi ovde kod koji upisuje u bazu da je PRIKAZANO pitanje
        Log.d("qid","qid ili broj pitanja pre"+qid);
        txtQuestion.setText(currentQ.getQUESTIONtext());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        Log.d("log","broj pitanja: "+currentQ.getID());

        if (currentQ.getOPTC().equals("N/A")){//ispituje da li je dostupna treća opcija za odgovor
            // ako nije, onda treće dugme postaje nevidljivo

            rdc.setVisibility(View.INVISIBLE);
        }
        else {
            rdc.setVisibility(View.VISIBLE);
            rdc.setText(currentQ.getOPTC()); }
        scoreTextView.setText(""+score);
/*
        db.setKolikoJePutaPrikazivano(currentQ.getHOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN()+1);//povećavam za jedan koliko je puta prikazano pitanje
*/
        qid++;//posle svakog pitanja zovem setQuestionView i povećavam qid za jedan
        Log.d("qid","qid ili broj pitanja posle"+qid);
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }



   /* @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {//u ovom metodu čuvam stanje fragmenta
        //na primer ako imam neke selektovane opcije i pre nego što aplikacija se pauzira
        //ovde mogu da sačuvam koji su selektovani objekti
        //ovaj metod je pozvan pre uništavanja fragmenta pri na primer rotaciji telefona
        //podatke koje hoću da sačuvam stavljam u outState objekat klase Bundle, koji se nalazi kao
        //parametar za ovaj metod. Čuvam to posle super poziva.
        //ovaj outState objekat ulazi kao parametar savedInstanceState u onCreate metod ovog fragmenta
        super.onSaveInstanceState(outState);
        //ovde da ide neki kod za čuvanje informacija kao na primer
        // outState.putInt("counter", counter)

        /*int num1 = Integer.parseInt(tvValue);*/



    }

    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);// saves the fragments state on screen rotation
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
