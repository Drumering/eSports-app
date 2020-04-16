package com.opet.esports_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.opet.esports_app.R;
import com.opet.esports_app.models.Player;
import com.opet.esports_app.utils.APISingleton;
import com.opet.esports_app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView lvPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPlayers = findViewById(R.id.listPlayers);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadPlayers();
    }

    private void loadPlayers() {
        String url = Utils.ENDPOINT;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Player> players = new ArrayList<>();
                for (int i = 0;i < response.length(); i++){
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        Player player = new Player();
                        player.setId(object.getLong("id"));
                        player.setPlayerName(object.getString("playerName"));
                        players.add(player);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setPlayerAdapter(players);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void setPlayerAdapter(List<Player> players) {
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, players);
        lvPlayers.setAdapter(adapter);
        lvPlayers.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Player player = (Player) parent.getItemAtPosition(position);
        Intent detailPlayer = new Intent(MainActivity.this, PlayerDetailActivity.class);

        detailPlayer.putExtra("ID", player.getId());
        startActivity(detailPlayer);
    }

    public void newPlayer(View view){
        Intent newPlayer = new Intent(MainActivity.this, NewPlayerActivity.class);
        startActivity(newPlayer);
    }
}
