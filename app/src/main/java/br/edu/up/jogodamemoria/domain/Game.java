package br.edu.up.jogodamemoria.domain;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private final String player1;
    private final String player2;
    private String currentPlayer;
    private int player1Score = 0;
    private int player2Score = 0;

    private ArrayList<GameItem> items = new ArrayList<GameItem>();
    private View firstView = null;
    private Runnable notifierMatchFailed;
    private Runnable notifierMatch;
    private Runnable notifierFinished;

    public Game(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 1; i <= 719; i++) {
            ids.add(i);
        }

        Collections.shuffle(ids);

        for (int i = 1; i <= 8; i++) {
            Integer id = ids.get(i);

            items.add(new GameItem(id));
            items.add(new GameItem(id));
        }

        Collections.shuffle(items);
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public ArrayList<GameItem> getItems() {
        return items;
    }

    public GameItem getItem(int position) {
        return items.get(position);
    }

    public int size() {
        return items.size();
    }

    public String getWinner() {
        if (player1Score > player2Score) {
            return player1;
        } else if (player2Score > player1Score) {
            return player2;
        } else {
            return null;
        }
    }

    public void matchItem(@NonNull View view) {
        GameItem item = (GameItem) view.getTag();

        if (item.getStatus() == "chosen") {
            return;
        }

        if (firstView == null) {
            firstView = view;
            item.setStatus("chosen");
        } else {
            GameItem firstItem = (GameItem) firstView.getTag();

            if (firstItem.getId() == item.getId()) {
                firstItem.setStatus("matched");
                item.setStatus("matched");

                if (currentPlayer == player1) {
                    player1Score++;
                } else {
                    player2Score++;
                }
            } else {
                firstItem.setStatus("available");

                currentPlayer = currentPlayer == player1 ? player2 : player1;

                new android.os.Handler().postDelayed(() -> {
                    notifierMatchFailed.run();
                }, 1000);
            }

            firstView = null;
        }

        notifierMatch.run();

        if (player1Score + player2Score == 8) {
            notifierFinished.run();
        }
    }

    public void onMatch(Runnable notifierMatch) {
        this.notifierMatch = notifierMatch;
    }
    
    public void onMatchFailed(Runnable notifierMatchFailed) {
        this.notifierMatchFailed = notifierMatchFailed;
    }

    public void onFinished(Runnable notifierFinished) {
        this.notifierFinished = notifierFinished;
    }
}
