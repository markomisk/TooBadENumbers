package com.giantturtle.badenumbersquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    Button startQuiz, studyPrepare, scoresRankings, moreApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLayout();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_author) {
            Intent intent = new Intent(MainActivity.this,AboutMeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void initializeLayout(){

        startQuiz = (Button) findViewById(R.id.quizFragmentActivityButton);
        startQuiz.setOnClickListener(this);

        studyPrepare = (Button) findViewById(R.id.startPrepareActivityButton);
        studyPrepare.setOnClickListener(this);

        scoresRankings = (Button) findViewById(R.id.goToRankingListButton);
        scoresRankings.setOnClickListener(this);

        moreApps = (Button) findViewById(R.id.goToMyPlaystoreButton);
        moreApps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.quizFragmentActivityButton:
                intent = new Intent(MainActivity.this, QuizActiviyWithFragment.class);
                startActivity(intent);
                break;

            case R.id.startPrepareActivityButton:
                intent = new Intent(MainActivity.this,PrepareYourSelfActivity.class);
                startActivity(intent);
                break;

            case R.id.goToRankingListButton:
                intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.goToMyPlaystoreButton:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://search?q=2GiantTurtle&c=apps"));//TODO ispitaj ovo da li radi
                startActivity(intent);
                break;

        }

    }

    //TODO background should be ninepatch image

}
