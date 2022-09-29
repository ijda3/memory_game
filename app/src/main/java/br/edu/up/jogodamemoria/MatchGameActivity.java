package br.edu.up.jogodamemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import br.edu.up.jogodamemoria.adapter.ItemAdapter;
import br.edu.up.jogodamemoria.domain.Game;

public class MatchGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);

        RecyclerView recyclerMatchGame = findViewById(R.id.recycler_match_game);
        recyclerMatchGame.setLayoutManager(new GridLayoutManager(this, 4));

        TextView textPlayer1Name = findViewById(R.id.text_player1_name);
        TextView textPlayer2Name = findViewById(R.id.text_player2_name);
        TextView textPlayer1Score = findViewById(R.id.text_player1_score);
        TextView textPlayer2Score = findViewById(R.id.text_player2_score);

        Game game = new Game(
            getIntent().getExtras().getString("player1").toUpperCase(Locale.ROOT),
            getIntent().getExtras().getString("player2").toUpperCase(Locale.ROOT)
        );

        textPlayer1Name.setText(game.getPlayer1());
        textPlayer2Name.setText(game.getPlayer2());

        game.onMatch(() -> {
            if (game.getCurrentPlayer() == game.getPlayer1()) {
                textPlayer1Name.setTypeface(null, Typeface.BOLD);
                textPlayer1Score.setTypeface(null, Typeface.BOLD);
                textPlayer2Name.setTypeface(null, Typeface.NORMAL);
                textPlayer2Score.setTypeface(null, Typeface.NORMAL);
            } else {
                textPlayer1Name.setTypeface(null, Typeface.NORMAL);
                textPlayer1Score.setTypeface(null, Typeface.NORMAL);
                textPlayer2Name.setTypeface(null, Typeface.BOLD);
                textPlayer2Score.setTypeface(null, Typeface.BOLD);
            }
            textPlayer1Score.setText(String.valueOf(game.getPlayer1Score()));
            textPlayer2Score.setText(String.valueOf(game.getPlayer2Score()));
        });

        game.onFinished(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Partida finalizada");

            String winner = game.getWinner();

            if (winner == null) {
                builder.setMessage("Partida empatada!");
            } else {
                builder.setMessage(String.format("%s venceu!", game.getWinner()));
            }

            builder.setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    onBackPressed();
                }
            });

            builder.setNegativeButton(R.string.retry_game, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            builder.create().show();
        });

        ItemAdapter adapter = new ItemAdapter(game);
        recyclerMatchGame.setAdapter(adapter);
    }
}