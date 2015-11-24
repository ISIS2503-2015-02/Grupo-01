package wilisoft.tbcapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import models.ReservaMobibus;
import models.User;
import restcall.Client;

public class CrearReservaActivity extends AppCompatActivity {

    private User usActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        usActual   = (User) intent.getSerializableExtra("User");

    }


    private class Call extends AsyncTask<String, String, ReservaMobibus> {

        public Call(){}

        @Override
        protected ReservaMobibus doInBackground(String... params) {
            return new Client().createReserva(params[0], params[1], usActual);
        }

        protected void onPostExecute(ReservaMobibus reserva) {

            if(reserva !=null) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrearReservaActivity.this).create();
                alertDialog.setTitle("Reserva");
                alertDialog.setMessage("Se creo la nueva solicitud de reserva");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("User", usActual);
                startActivity(intent);
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(CrearReservaActivity.this).create();
                alertDialog.setTitle("Reserva");
                alertDialog.setMessage("Ocurrio un error creando la reserva");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }

    public void crearReserva(View view){

        EditText ubOrigen = (EditText) findViewById(R.id.ubicacionOrigen);
        EditText ubDestino = (EditText) findViewById(R.id.ubicacionDestino);

        String origen = ubOrigen.getText().toString();
        String destino = ubDestino.getText().toString();

        if(origen != null && !origen.isEmpty() && destino != null && !destino.isEmpty()){
            new Call().execute(origen, destino);
        }
    }

}
