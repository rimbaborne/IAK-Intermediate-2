package iak.rimbaborne.intermediate2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iak.rimbaborne.intermediate2.adapter.CuacaAdapter;
import iak.rimbaborne.intermediate2.entity.Cuaca;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private AlertDialog.Builder alert;
    private ListView list;
    private List<Cuaca> listCuaca;
    private SwipeRefreshLayout swipe;
    private String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&APPID=2939b4f9a70e7dd25e181b06ab14bc5d&mode=json&units=metric&cnt=17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert = new AlertDialog.Builder(this);

        listCuaca = new ArrayList<>();

        list = (ListView) findViewById(R.id.list);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) MainActivity.this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                listCuaca.clear();
                RequestParams params = new RequestParams();

                requestWather(params);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("DT", "" + listCuaca.get(position).getDt());
                intent.putExtra("WEATHER", "" + listCuaca.get(position).getWeather());
                intent.putExtra("TEMP", "" + listCuaca.get(position).getTemp());
                startActivity(intent);
            }
        });
    }

    private void requestWather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        swipe.setRefreshing(true);
        client.get(URL, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                swipe.setRefreshing(false);
                android.util.Log.d("Response: ", "> " + response);

                try {
                    JSONObject weather = new JSONObject(response);
                    JSONArray listWeather = weather.getJSONArray("list");

                    for (int i = 0; i < listWeather.length(); i++) {
                        //inisialisasi banyak JSONObject dari JSONArray dengan proses perulangan for
                        JSONObject weatherData = listWeather.getJSONObject(i);

                        //mengambil data dt
                        String dt = convertDateTime(weatherData.getLong("dt"));

                        //mengambil data JSONObject main pada JSONArray weather
                        JSONArray main = weatherData.getJSONArray("weather");
                        String w = main.getJSONObject(0).getString("main");

                        //mengambil data day pada JSONObject temp
                        JSONObject temp = weatherData.getJSONObject("temp");
                        String t = String.valueOf(temp.getInt("day")) + "C";

                        listCuaca.add(new Cuaca(dt, t, w));
                    }

                    CuacaAdapter adapter = new CuacaAdapter(MainActivity.this, listCuaca);
                    list.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();

                    // gagal koneksi ketika terhubung jaringan tapi rto
                    alert.setTitle("Gagal");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("Terjadi kesalahan koneksi");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                swipe.setRefreshing(false);

                // respon dari server
                if (statusCode == 404){
                    alert.setTitle("Gagal");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("terjadi kesalahan 404");
                    alert.show();
                } else if (statusCode == 500){
                    alert.setTitle("Gagal");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("terjadi kesalahan 500");
                    alert.show();
                } else if (statusCode == 501){
                    alert.setTitle("Gagal");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("terjadi kesalahan 501");
                    alert.show();
                } else {
                    // tidak terhubung jaringan
                    alert.setTitle("Gagal");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("Tidak terhubung jaringan");
                    alert.show();
                }
            }
        });
    }

    private String convertDateTime(long dateTime){
        Date date = new Date(dateTime * 1000);
        Format dateTimeFormat = new SimpleDateFormat("EEE, dd MMM");
        return dateTimeFormat.format(date);
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        listCuaca.clear();
        RequestParams params = new RequestParams();

        requestWather(params);
    }

}
