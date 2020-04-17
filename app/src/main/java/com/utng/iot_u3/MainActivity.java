package com.utng.iot_u3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Instancia de objecto; uso de plantilla, de manera global a la clase
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conseguir los item del XML relacionado con esta clase por medio de su propiedad ID
        TextView led_on_sala = findViewById(R.id.led_on_sala);
        TextView led_off_sala = findViewById(R.id.led_off_sala);
        TextView led_on_cocina = findViewById(R.id.led_on_cocina);
        TextView led_off_cocina = findViewById(R.id.led_off_cocina);
        TextView led_on_mirador = findViewById(R.id.led_on_mirador);
        TextView led_off_mirador = findViewById(R.id.led_off_mirador);
        TextView led_on_pasillo = findViewById(R.id.led_on_pasillo);
        TextView led_off_pasillo = findViewById(R.id.led_off_pasillo);
        //Caracteristica final agregada para que sean unico e irrepetibles en la clase
        final TextView txt_sensorUltrasonico = findViewById(R.id.txt_sensorUltrasonico);
        final TextView txt_sensorMovimiento = findViewById(R.id.txt_sensorMovimiento);
        final TextView txt_sensorSonido = findViewById(R.id.txt_sensorSonido);

        //Método para cuando hay click en ese item, se cree una referencia y colocarle un valor
        led_on_cocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instacia de un campo desde la base de datos
                DatabaseReference myRef = database.getReference("led_cocina_status");
                myRef.setValue(1);
            }
        });

        led_off_cocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_cocina_status");
                myRef.setValue("Apagado");
            }
        });

        led_on_sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_sala_status");
                myRef.setValue(1);
            }
        });

        led_off_sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_sala_status");
                myRef.setValue("Apagado");
            }
        });

        led_on_mirador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_mirador_status");
                myRef.setValue(1);
            }
        });

        led_off_mirador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_mirador_status");
                myRef.setValue("Apagado");
            }
        });

        led_on_pasillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_pasillo_status");
                myRef.setValue(1);
            }
        });

        led_off_pasillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("led_pasillo_status");
                myRef.setValue("Apagado");
            }
        });

        //Detectar si hay algun cambio en la base de datos sobre el campo especificado
        DatabaseReference myRefUltrasonico = database.getReference("distanciaUltrasonico");
        myRefUltrasonico.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Casteo de un objeto a tipo de datos cadena
                String change = dataSnapshot.getValue().toString();
                txt_sensorUltrasonico.setText("Hay un objecto a " + change + " cm");
                if (Integer.parseInt(change) < 30) { //Conversion de cadena a entero
                    //Instancia de campo proveniente de la BD
                    DatabaseReference myRefAlarma = database.getReference("alarma_status");
                    //Colocacion del valor a la referencia (campo)
                    myRefAlarma.setValue(1);
                } else {
                    DatabaseReference myRefAlarma = database.getReference("alarma_status");
                    myRefAlarma.setValue("Apagado");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myRefMovimiento = database.getReference("movimiento_status");
        myRefMovimiento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String change = dataSnapshot.getValue().toString();
                if (Integer.parseInt(change) == 1) {
                    txt_sensorMovimiento.setText("Se ha detectado movimiento");
                } else {
                    txt_sensorMovimiento.setText("No se ha detectado movimiento");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myRefSonido = database.getReference("microfono_status");
        myRefSonido.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String change = dataSnapshot.getValue().toString();
                if (Integer.parseInt(change) == 1) {
                    txt_sensorSonido.setText("Cuide sus oídos, hay sonido alto alrededor");
                } else {
                    txt_sensorSonido.setText("Todo está tranquilo, no hay sonido");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
