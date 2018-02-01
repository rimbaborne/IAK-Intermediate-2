package iak.rimbaborne.intermediate2.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iak.rimbaborne.intermediate2.R;
import iak.rimbaborne.intermediate2.entity.Cuaca;

/**
 * Created by github.com/rimbaborne on 2/1/2018.
 */

public class CuacaAdapter extends ArrayAdapter<Cuaca> {
    private final AppCompatActivity context;
    private final List<Cuaca> cuaca;


    public CuacaAdapter(AppCompatActivity context, List<Cuaca> cuaca) {
        super(context, R.layout.mylist, cuaca);
        this.context = context;
        this.cuaca = cuaca;
    }

    public View getView(int position, View view, ViewGroup parent) {

        //deklarasi inflater
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        //deklarasi id
        TextView dt = (TextView) rowView.findViewById(R.id.dt);
        TextView weather = (TextView) rowView.findViewById(R.id.weather);
        TextView temp = (TextView) rowView.findViewById(R.id.temp);

        //inisialisasi array
        dt.setText(cuaca.get(position).getDt());
        weather.setText(cuaca.get(position).getWeather());
        temp.setText(cuaca.get(position).getTemp());

        return rowView;
    }
}
