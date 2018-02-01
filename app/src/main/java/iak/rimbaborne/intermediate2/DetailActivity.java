package iak.rimbaborne.intermediate2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDt;
    private TextView txtTemp;
    private TextView txtWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDt = (TextView) findViewById(R.id.dt);
        txtTemp = (TextView) findViewById(R.id.temp);
        txtWeather = (TextView) findViewById(R.id.weather);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        txtDt.setText(bundle.getString("DT"));
        txtTemp.setText(bundle.getString("TEMP"));
        txtWeather.setText(bundle.getString("WEATHER"));
    }
}
