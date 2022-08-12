package sg.edu.rp.c346.id20026389.carparkavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    ListView lvCarpark;
    AsyncHttpClient client;
    ArrayAdapter<Carpark> adapter,newadapter;
    Spinner spnLotType;
    String lotType;
    EditText inputNumber;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCarpark = findViewById(R.id.listviewCarpark);
        client = new AsyncHttpClient();
        spnLotType = findViewById(R.id.spinnerLotType);
        inputNumber=findViewById(R.id.editTextInput);
        btnSubmit=findViewById(R.id.buttonSubmit);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Carpark> alCarpark = new ArrayList<Carpark>();
        adapter = new ArrayAdapter<Carpark>(this,
                android.R.layout.simple_list_item_1, alCarpark);
        ArrayList<Carpark> newalCarpark = new ArrayList<Carpark>();
        newadapter = new ArrayAdapter<Carpark>(this,
                android.R.layout.simple_list_item_1, newalCarpark);

        client.get("https://api.data.gov.sg/v1/transport/carpark-availability", new JsonHttpResponseHandler() {

            String carparkNumber;
            int total_lots;
            String lot_type;
            int lots_available;


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("carpark_data");
                    for (int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        carparkNumber = jsonObjForecast.getString("carpark_number");
                        total_lots = Integer.parseInt(jsonObjForecast.getJSONArray("carpark_info").getJSONObject(0).getString("total_lots"));
                        lot_type = jsonObjForecast.getJSONArray("carpark_info").getJSONObject(0).getString("lot_type");
                        lots_available = Integer.parseInt(jsonObjForecast.getJSONArray("carpark_info").getJSONObject(0).getString("lots_available"));
                        Carpark carpark = new Carpark(carparkNumber, total_lots, lot_type, lots_available);
                        alCarpark.add(carpark);
                    }
                } catch (JSONException e) {

                }

                //POINT X – Code to display List View
                adapter.addAll(alCarpark);
                lvCarpark.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        });
        spnLotType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        lotType="ALL";
//                        adapter.clear();
                        adapter.addAll(alCarpark);
                        lvCarpark.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        lotType="C";
                        newalCarpark.clear();
                        for(int i=0;i<alCarpark.size();i++){
                            if(alCarpark.get(i).getLot_type().equalsIgnoreCase(lotType)){
                                newalCarpark.add(alCarpark.get(i));
                            }
                        }
                        newadapter.addAll(newalCarpark);
                        lvCarpark.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();
                        break;
                    case 2:
                        lotType="H";
                        newalCarpark.clear();
                        for(int i=0;i<alCarpark.size();i++){
                            if(alCarpark.get(i).getLot_type().equalsIgnoreCase(lotType)){
                                newalCarpark.add(alCarpark.get(i));
                            }
                        }
                        newadapter.addAll(newalCarpark);
                        lvCarpark.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();
                        break;
                    case 3:
                        lotType="Y";
                        newalCarpark.clear();
                        for(int i=0;i<alCarpark.size();i++){
                            if(alCarpark.get(i).getLot_type().equalsIgnoreCase(lotType)){
                                newalCarpark.add(alCarpark.get(i));
                            }
                        }
                        newadapter.addAll(newalCarpark);
                        lvCarpark.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputNumber.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this, "Please enter carpark number!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    newalCarpark.clear();
                    for(int i=0;i<alCarpark.size();i++){
                        if(alCarpark.get(i).getCarparkNumber().equalsIgnoreCase(inputNumber.getText().toString().trim())){
                            newalCarpark.add(alCarpark.get(i));
                        }
                    }
                    if(newalCarpark.size()>0){
                        newadapter.addAll(newalCarpark);
                        lvCarpark.setAdapter(newadapter);
                        newadapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, newalCarpark.size()+" records found for the carpark number "+inputNumber.getText().toString().trim(),
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        adapter.addAll(alCarpark);
                        lvCarpark.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "No records found for the carpark number "+inputNumber.getText().toString().trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }

            }
        });




    }
}