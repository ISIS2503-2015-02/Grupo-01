package wilisoft.tbcapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import models.User;

public class LoginActivity extends AppCompatActivity {

    private User usActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        usActual = (User) intent.getSerializableExtra("User");

        TextView usernom = (TextView) findViewById(R.id.textView);
        usernom.setText("Bienvenido de vuelta " + usActual.getNombre() + "!");
    }

    public void verReservas(View view){
        Intent intent = new Intent(getApplicationContext(), VerReservasActivity.class);
        intent.putExtra("User", usActual);
        startActivity(intent);
    }

    public void nuevaReserva(View view){
        Intent intent = new Intent(getApplicationContext(), CrearReservaActivity.class);
        intent.putExtra("User", usActual);
        startActivity(intent);
    }

}
