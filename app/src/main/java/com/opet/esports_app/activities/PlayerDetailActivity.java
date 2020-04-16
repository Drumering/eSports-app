package com.opet.esports_app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.opet.esports_app.R;
import com.opet.esports_app.models.Player;
import com.opet.esports_app.utils.APISingleton;
import com.opet.esports_app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerDetailActivity extends AppCompatActivity {
    private TextView textPlayerName, textNickName, textTeamName, textRole, textTotalKills, textTotalAssists, textTotalDeaths, textTotalMatchs, textTotalVictories, textKda, textWinningRate;
    private long id;
    private Player player;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_player_detail);

        id = getIntent().getLongExtra("ID", 0);

        textPlayerName = findViewById(R.id.textPlayerName);
        textNickName = findViewById(R.id.textNickName);
        textTeamName = findViewById(R.id.textTeamName);
        textRole = findViewById(R.id.textRole);
        textTotalKills = findViewById(R.id.textTotalKills);
        textTotalAssists = findViewById(R.id.textTotalAssists);
        textTotalDeaths = findViewById(R.id.textTotalDeaths);
        textTotalMatchs = findViewById(R.id.textTotalMatchs);
        textTotalVictories = findViewById(R.id.textTotalVictories);
        textKda = findViewById(R.id.textKda);
        textWinningRate = findViewById(R.id.textWinningRate);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadPlayer();
    }

    private void loadPlayer() {
        String url = Utils.ENDPOINT + "/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                player = new Player();

                try {
                    player.setId(response.getLong("id"));
                    player.setPlayerName(response.getString("playerName"));
                    player.setNickName(response.getString("nickName"));
                    player.setTeamName(response.getString("teamName"));
                    player.setRole(response.getString("role"));
                    player.setTotalKills(response.getLong("totalKills"));
                    player.setTotalAssists(response.getLong("totalAssists"));
                    player.setTotalDeaths(response.getLong("totalDeaths"));
                    player.setTotalMatchs(response.getLong("totalMatchs"));
                    player.setTotalVictories(response.getLong("totalVictories"));
                    player.setKda(response.getDouble("kda"));
                    player.setWinningRate(response.getDouble("winningRate"));

                    textPlayerName.setText(player.getPlayerName());
                    textNickName.setText(player.getNickName());
                    textTeamName.setText(player.getTeamName());
                    textRole.setText(player.getRole());
                    textTotalKills.setText(String.valueOf(player.getTotalKills()));
                    textTotalAssists.setText(String.valueOf(player.getTotalAssists()));
                    textTotalDeaths.setText(String.valueOf(player.getTotalDeaths()));
                    textTotalMatchs.setText(String.valueOf(player.getTotalMatchs()));
                    textTotalVictories.setText(String.valueOf(player.getTotalVictories()));
                    textKda.setText(String.valueOf(player.getKda()));
                    textWinningRate.setText(String.valueOf(player.getWinningRate()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    public void updatePlayer(View view){
        Intent newPlayer = new Intent(PlayerDetailActivity.this, NewPlayerActivity.class);
        newPlayer.putExtra("ID", player.getId());
        startActivity(newPlayer);
    }

    public void deletePlayer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja excluir o Player " + player.getId() + "?");
        builder.setTitle("Excluir");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = Utils.ENDPOINT + "/" + id;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(PlayerDetailActivity.this, "Player " + response.getLong("id") + " removido", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(PlayerDetailActivity.this, MainActivity.class);
                            startActivity(main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

            }
        });

        builder.setNegativeButton("NÃO", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}