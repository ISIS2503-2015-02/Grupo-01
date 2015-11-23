package wilisoft.tbcapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import models.User;
import restcall.Client;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "wilisoft.tbcapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view){

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        String userN = username.getText().toString();
        String passW = password.getText().toString();

        if(userN != null && !userN.isEmpty() && passW != null && !passW.isEmpty()){
            new Call().execute("REQ_LOGIN",userN, passW);
        }
    }

    private class Call extends AsyncTask<String, String, User> {

        public Call(){}

        @Override
        protected User doInBackground(String... params) {
            return new Client().login(params[1], params[2]);
        }

        protected void onPostExecute(User result) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            intent.putExtra(EXTRA_MESSAGE, result);
            intent.putExtra("User", result);

            startActivity(intent);
        }
    }
}
