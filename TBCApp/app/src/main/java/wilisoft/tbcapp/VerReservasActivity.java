package wilisoft.tbcapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import models.ReservaMobibus;
import models.User;

public class VerReservasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reservas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        User userLog = (User) intent.getSerializableExtra("User");

        List<ReservaMobibus> reservas = userLog.getReservas();

        TableLayout tl=(TableLayout)findViewById(R.id.maintable);

        for(int i = 0; i< reservas.size();i++){
            ReservaMobibus resActual = reservas.get(i);
            addReservaRow(tl, resActual);
        }
    }


    public void addReservaRow(TableLayout father, ReservaMobibus reserva){

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tvId = new TextView(this);
        tvId.setText(reserva.getId());

        TextView tvEstado = new TextView(this);
        tvEstado.setText(reserva.getEstado());

        TextView tvUbOrigen = new TextView(this);
        tvUbOrigen.setText(reserva.getUbicacionOrigen());

        TextView tvUbDestino = new TextView(this);
        tvUbDestino.setText(reserva.getUbicacionDestino());

        TextView tvFecha = new TextView(this);
        tvFecha.setText(reserva.getFecha().toString());

        tr.addView(tvId);
        tr.addView(tvEstado);
        tr.addView(tvUbOrigen);
        tr.addView(tvUbDestino);
        tr.addView(tvFecha);

        father.addView(tr);
    }


}
