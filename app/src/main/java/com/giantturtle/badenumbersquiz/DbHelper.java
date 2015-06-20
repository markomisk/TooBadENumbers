package com.giantturtle.badenumbersquiz;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 2;
	// Database Name
	private static final String DATABASE_NAME = "badENumbersBase";
	// tasks table name
	private static final String TABLE_QUESTIONS_FOR_QUIZ = "questionsForQuiz";
	// tasks Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QUES = "question";
	private static final String KEY_ANSWER = "answer"; //correct option
	private static final String KEY_OPTA= "opta"; //option a
	private static final String KEY_OPTB= "optb"; //option b
	private static final String KEY_OPTC= "optc"; //option c
    private static final String KOLIKO_JE_PUTA_PRIKAZIVANO= "koliko_je_puta_prikazivano";//nisam još kreirao polja u quesion objektu
    private static final String KOLIKO_JE_PUTA_TACNO_ODGOVORENO= "koliko_je_puta_tacno";// nisam još uvrstio ovo polje u bazu
    private static final String KOLIKO_JE_PUTA_NETACNO_ODGOVORENO= "koliko_je_puta_netacno";// nisam još uvrstio ovo polje u bazu


    //Saved card numbers and headings table to be shown in list view as favorites
    private static final String TABLE_SAVED_CARDS_AND_HEADINGS = "savedCardsAndHeadings";
    private static final String KEY_ID2 = "id";
    private static final String PAGE_ID = "idOfPageNumber";
    private static final String HEADINGS = "headingOfENumber";



    private SQLiteDatabase dbase;


	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		dbase=db;
		String createQuestionsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS_FOR_QUIZ + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT, "+KOLIKO_JE_PUTA_PRIKAZIVANO +" INTEGER)";

        //second table
        String createFavoritesCardsForLearningTable = "CREATE TABLE IF NOT EXISTS "+TABLE_SAVED_CARDS_AND_HEADINGS+
                " ( "+KEY_ID2+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PAGE_ID + " INTEGER, "+HEADINGS +" TEXT )";
        db.execSQL(createFavoritesCardsForLearningTable);

        //if db!=null Toast.makeText()

		//create first table after the second one
        db.execSQL(createQuestionsTable);
		addQuestions();
		//db.close();
        // TODO dodati kolonu koliko je puta pitanje bilo prikazano korisniku i da selektuje pitanja koja su najmanje bila prikazivana
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_FOR_QUIZ);
		// Create tables again
		onCreate(db);
	}
	// Adding new question
	public void putQuestionInDatabase(Question quest) {
		//SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUES, quest.getQUESTIONtext());
		values.put(KEY_ANSWER, quest.getANSWER());
		values.put(KEY_OPTA, quest.getOPTA());
		values.put(KEY_OPTB, quest.getOPTB());
		values.put(KEY_OPTC, quest.getOPTC());
        /*values.put(KOLIKO_JE_PUTA_PRIKAZIVANO,quest.getHOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN());*/
		// Inserting Row
		dbase.insert(TABLE_QUESTIONS_FOR_QUIZ, null, values);
	}
	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<Question>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS_FOR_QUIZ;
		dbase=this.getReadableDatabase();
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {//kreiranje novih questiion objekata
				Question quest = new Question();
				quest.setID(cursor.getInt(0));
				quest.setQUESTION(cursor.getString(1));
				quest.setANSWER(cursor.getString(2));
				quest.setOPTA(cursor.getString(3));
				quest.setOPTB(cursor.getString(4));
				quest.setOPTC(cursor.getString(5));
                /*quest.setHOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN(cursor.getInt(6));*/
				quesList.add(quest);
			} while (cursor.moveToNext());
            //TODO ovde mogu da izbacujem vrednosti iz liste ako je broj prikazivanja veći od nekog broja recimo 2,3
		}
        /*int x = (int) (Math. random()*100);*/      //izbacuje broj od 1-100

        while ( quesList.size()>10)//ovo je početak petlje za filtritanje
        {
            quesList.remove(randInt(0,quesList.size()-1));// u ovom opsegu da izbacuje pitanja
            Log.d("random broj", "Random broj: "+randInt(1,quesList.size()));
        }
		// return quest list
		return quesList;
	}

    //method for saving favorite cards for learning
    public void saveCard (int pageId, String headingText){
        dbase=this.getWritableDatabase();
        ContentValues values2 = new ContentValues();
        values2.put(PAGE_ID,pageId);
        values2.put(HEADINGS,headingText);
        dbase.insert(TABLE_SAVED_CARDS_AND_HEADINGS, null, values2);

    }

    public boolean checkIfCardWasSaved(int mPageNumber){

        //this method checks if the card was saved so it could display correcr checkmar on card
        dbase=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_CARDS_AND_HEADINGS + " WHERE "
                + PAGE_ID + " = " + mPageNumber;
        String number = dbase.rawQuery(selectQuery,null).toString();


        //TODO fix this because it always returns true
        //TODO search how to get specific entry from database, or to check if entry is present
        if (number.equals(mPageNumber)) {return true;}
        else return false;

    }

    public static int randInt(int min, int max) {//method for generating a random number which is used for filtering above

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }




    /*public void setKolikoJePutaPrikazivano (int kolikoJePutaPrikazivano){
        dbase=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOLIKO_JE_PUTA_PRIKAZIVANO, kolikoJePutaPrikazivano);
        dbase.insert(TABLE_QUESTIONS_FOR_QUIZ, null, values);


    }*/


	public int rowcount()//row count
	{
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS_FOR_QUIZ;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		row=cursor.getCount();
		return row;
	}

    private void addQuestions()//add questions here
    {

        //creates a new question and writes it to database table (TABLE_QUESTIONS_FOR_QUIZ) using putQuestionInDatabase method
        Question q1=new Question("Which additive is mostly used in  products that has brown to black colour and its potentially CARCINOGENIC?"
                ,"E150d", "E151", "E129", "E150d");//question, options, correct answer
        this.putQuestionInDatabase(q1);


        Question q2=new Question("Which additive is mostly used in  products that has red color and its potentially CARCINOGENIC in mice?", "E150d", "E129", "E133", "E129");
        this.putQuestionInDatabase(q2);

        Question q3=new Question("Which additive is mostly used in  products that has blue color and known to cause allergic reactions in some people?","E213", "E102","E133","E133");
        this.putQuestionInDatabase(q3);

        Question q4=new Question("Which one of these additives can cause anaemia or kidney inflammation?",	"E239", "E252", "E132","E252");
        this.putQuestionInDatabase(q4);

        Question q5=new Question(" Which one of these additives used in ice cream, sweets, baked goods and connfectionery can cause itching, high blood pressure and breathing problems?","E142","E154","E132","E132");
        this.putQuestionInDatabase(q5);

        Question q6=new Question("Which additive is mostly used in  products that has brown to black colour and its potentially CARCINOGENIC?","E150c","E150d","Both","Both");
        this.putQuestionInDatabase(q6);

        Question q7=new Question("Which one of these additives used in mint sauce, desserts, and tinned peas can cause hyperactivity, asthma, uticaria, and insomnia?","E142","E123","E222","E142");
        this.putQuestionInDatabase(q7);




        Question q8=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q8);

        Question q9=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q9);

        Question q10=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q10);

        Question q11=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q11);

        Question q12=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q12);

        Question q13=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q13);

        Question q14=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q14);

        Question q15=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q15);

        Question q16=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q16);

        Question q17=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q17);

        Question q18=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q18);

        Question q19=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q19);
        //Magiski ili magijski?
        //Pravilno je magijski

        Question q20=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q20);
        /* Mašući ili mahajući?
Pravilno je mašući. */

        Question q21=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q1);
        /* Leptir mašna ili leptir­mašna?
Pravilno je leptir­mašna.
 */

        Question q22=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q22);
        /* Lupio ili lupijo?
Pravilno je lupio.
  */


        Question q23=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q23);
        /* Levak ili levoruk?
Koriste se oba.  */

        Question q24=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q24);
        /* S obzirom na ili obzirom na?
Pravilno je s obzirom na.
*/


        Question q25=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q25);
        /* Objekat ili objekt?
Koriste se oba  */

        Question q26=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q26);
        /* Oboji ili oboj?
Koriste se oba.  */

        Question q27=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q27);
        /* Pesma Ive Andrića ili pesma od Ive Andrića?
Koriste se oba.
  */

        Question q28=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q28);
        /* Perpetuum mobile ili perpetum mobile?
Pravilno je perpetuum mobile.
 */

        Question q29=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q29);
        /*  Maršuta ili maršruta?
Pravilno je maršruta  */


        Question q30=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q30);
        /*  Penzijski ili penzioni?
Koriste se oba.  */


        Question q31=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q31);
        /*  Paljenje ili palenje?
Pravilno je paljenje.  */



        Question q32=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q32);
        /*  Pančevljanka ili Pančevka?
Pravlno je Pančevka.
  */



        Question q33=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q33);
        /*  PS ili P.S.?
Koriste se oba, preporučuje se P.S. kao skraćenica
za post skriptum.
  */



        Question q34=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q34);
        /*  Raženi ili ražani?
Koriste se oba.
  */




        Question q35=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q35);
        /* Rasklimatana ili rasklimana?
Koriste se oba.
   */



        Question q36=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q36);
        /*  Rang­lista ili rank lista?
Pravilno je rang­lista.
  */


        Question q37=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q37);
        /*  Rajferšlus ili rajsferšlus?
Pravilno je rajsferšlus.
  */


        Question q38=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q38);
        /*  Radije ili rađe?
Pravilno je radije.
  */

        Question q39=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q39);
        /*  Konjunktivitis ili konjuktivitis?
Pravilno je konjunktivitis.
  */


        Question q40=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q40);
        /*  Kontaktirati ili kontaktovati?
Pravilno je kontaktirati.
  */


        Question q41=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q41);
        /*  Konjuktiv ili konjunktiv?
Pravilno je konjunktiv.
  */


        Question q42=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q42);
        /* Računar ili kompjuter?
Obe su ispravne ali ne i kompjutor.
   */


        Question q43=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q43);
        /*    */


        Question q44=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q44);
        /*    */

        Question q45=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q45);
        /*    */

        Question q46=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q46);
        /*    */


        Question q47=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q47);
        /*    */


        Question q48=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q48);
        /*    */


        Question q49=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(q49);
        /*    */


        /*Question qXX=new Question("xxxxxxxxxxxxxxxxxx?","xxxx","xxxxxx","N/A","xxxxxxxx");
        this.putQuestionInDatabase(qXX);
        *//*    */







    }
}
