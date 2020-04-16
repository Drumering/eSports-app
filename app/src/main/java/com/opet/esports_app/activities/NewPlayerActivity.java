package com.opet.esports_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class NewPlayerActivity extends AppCompatActivity {
    private EditText editPlayerName, editNickName, editTeamName, editRole, editTotalKills, editTotalAssists, editTotalDeaths, editTotalMatchs, editTotalVictories;
    private TextView textKda, textWinningRate;
    private Long id;
    private Player player;

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_new_player);

        editPlayerName = findViewById(R.id.editPlayerName);
        editNickName = findViewById(R.id.editNickName);
        editTeamName = findViewById(R.id.editTeamName);
        editRole = findViewById(R.id.editRole);
        editTotalKills = findViewById(R.id.editTotalKills);
        editTotalAssists = findViewById(R.id.editTotalAssists);
        editTotalDeaths = findViewById(R.id.editTotalDeaths);
        editTotalMatchs = findViewById(R.id.editTotalMatchs);
        editTotalVictories = findViewById(R.id.editTotalVictories);

        id = getIntent().getLongExtra("ID", 0);

        if (id != 0){
            loadPlayer();
        }
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

                    editPlayerName.setText(player.getPlayerName());
                    editNickName.setText(player.getNickName());
                    editTeamName.setText(player.getTeamName());
                    editRole.setText(player.getRole());
                    editTotalKills.setText(String.valueOf(player.getTotalKills()));
                    editTotalAssists.setText(String.valueOf(player.getTotalAssists()));
                    editTotalDeaths.setText(String.valueOf(player.getTotalDeaths()));
                    editTotalMatchs.setText(String.valueOf(player.getTotalMatchs()));
                    editTotalVictories.setText(String.valueOf(player.getTotalVictories()));
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

    private void createPlayer(String url, final int method){
        JSONObject object = new JSONObject();

        try {
            object.put("playerName", editPlayerName.getText().toString());
            object.put("nickName", editNickName.getText().toString());
            object.put("teamName", editTeamName.getText().toString());
            object.put("role", editRole.getText().toString());
            object.put("totalKills", editTotalKills.getText().toString());
            object.put("totalAssists", editTotalAssists.getText().toString());
            object.put("totalDeaths", editTotalDeaths.getText().toString());
            object.put("totalMatchs", editTotalMatchs.getText().toString());
            object.put("totalVictories", editTotalVictories.getText().toString());

            JsonObjectRequest request = new JsonObjectRequest(method, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = "";
                        if (method == Request.Method.POST) {
                            message = "Player " + response.getString("playerName") + " salvo com sucesso!";
                        } else {
                            message = "Player " + response.getString("id") + " atualizado com sucesso!";
                        }
                        Toast.makeText(NewPlayerActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(NewPlayerActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void savePlayer(View view){
        String url = Utils.ENDPOINT;

        if (id != 0){
            createPlayer(url + "/" + id, Request.Method.PUT);
        } else {
            createPlayer(url, Request.Method.POST);
        }
    }
}
