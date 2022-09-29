package br.edu.up.jogodamemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private TextInputEditText player1;
    private TextInputEditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = findViewById(R.id.text_input_player1);
        player2 = findViewById(R.id.text_input_player2);
        buttonStart = findViewById(R.id.button_start);

        buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MatchGameActivity.class);

            intent.putExtra("player1", player1.getText().toString());
            intent.putExtra("player2", player2.getText().toString());

            startActivity(intent);
        });
    }
}