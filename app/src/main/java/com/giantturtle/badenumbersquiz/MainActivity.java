package com.giantturtle.badenumbersquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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



    public void startPrepareMethod(View view) {
        Intent intent = new Intent(MainActivity.this,PrepareYourSelfActivity.class);
        startActivity(intent);
    }

    public void moreAppsMethod(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=2GiantTurtle&c=apps"));//TODO ispitaj ovo da li radi
        startActivity(intent);

    }

    public void rangListaMethod(View view) {
        Intent intent = new Intent(MainActivity.this, RankingActivity.class);
        startActivity(intent);

    }

    public void goToKvizFragmentActivity(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActiviyWithFragment.class);
        startActivity(intent);
    }

    //TODO pozadina da bude ninepatch slika

}
