package com.giantturtle.badenumbersquiz;


//this is a model class that is used for fetching the questions from database and manipulating them in app

public class Question {
	private int ID;
	private String QUESTION;
	private String OPTA;
	private String OPTB;
	private String OPTC;
	private String ANSWER;
    private int HOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN;



    public Question()
	{
		ID=0;
		QUESTION="";
		OPTA="";
		OPTB="";
		OPTC="";
		ANSWER="";
        HOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN =0;
	}
	public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
			String aNSWER) {
		
		QUESTION = qUESTION;
		OPTA = oPTA;
		OPTB = oPTB;
		OPTC = oPTC;
		ANSWER = aNSWER;
	}
    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER, int kOLIKO_PUTA_PRIKAZIVANO) {
        //drugi tip konstruktora koji ću koristiti kad napravim da upisuje u bazu koliko je puta prikazano
        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
        HOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN=0;
    }

	public int getID()
	{
		return ID;
	}
	public String getQUESTIONtext() {
		return QUESTION;
	}
	public String getOPTA() {
		return OPTA;
	}
	public String getOPTB() {
		return OPTB;
	}
	public String getOPTC() {
		return OPTC;
	}
	public String getANSWER() {
		return ANSWER;
	}
    public int getHOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN() {
        return HOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN;
    }
	public void setID(int id)
	{
		ID=id;
	}
	public void setQUESTION(String qUESTION) {
		QUESTION = qUESTION;
	}
	public void setOPTA(String oPTA) {
		OPTA = oPTA;
	}
	public void setOPTB(String oPTB) {
		OPTB = oPTB;
	}
	public void setOPTC(String oPTC) {
		OPTC = oPTC;
	}
	public void setANSWER(String aNSWER) {
		ANSWER = aNSWER;
	}
    public void setHOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN(int SHOWN_HOW_MENY_TIMES) {
        HOW_MENY_TIMES_THE_QUESTION_WAS_SHOWN = SHOWN_HOW_MENY_TIMES;
    }
	
}
