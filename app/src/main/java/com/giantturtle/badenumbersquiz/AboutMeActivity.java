package com.giantturtle.badenumbersquiz;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;


/**
 * Created by Standard on 31-Jan-15.
 */
public class AboutMeActivity extends Activity{

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        intent = new Intent(this,PrepareYourSelfActivity.class);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_back) {
            Intent intent1 = new Intent(AboutMeActivity.this,MainActivity.class);
            startActivity(intent1);
            return true;//return true znači da je događaj kliktanja na optionsItem rukovan.
        }
        return super.onOptionsItemSelected(item);

    }
}
