package creo.com.vendors;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import creo.com.vendors.utils.ApiClient;
import creo.com.vendors.utils.Global;
import creo.com.vendors.utils.SessionManager;

import static android.content.Context.MODE_PRIVATE;

public class Earnings extends AppCompatActivity {

    RecyclerView recyclerView;
    String token = null;
    ArrayList<String>name = new ArrayList<>();
    ArrayList<String>earnings = new ArrayList<>();
    ArrayList<String>date = new ArrayList<>();
    ArrayList<EarningPojo> dataModelArrayList;
    private EarningAdapter orderAdapter;
    Context context=this;
    SessionManager sessionManager;
    private String URLline = Global.BASE_URL+"manual_sel/add_manual_det/";
    private ProgressDialog dialog ;
    boolean doubleBackToExitPressedOnce = false;
    Activity activity=this;
    ImageView imageView;
    TextView textView,proccess;

    public Earnings() {
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

       imageView=findViewById(R.id.back);
       textView=findViewById(R.id.total);

       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        dialog=new ProgressDialog(Earnings.this,R.style.MyAlertDialogStyle);
        sessionManager = new SessionManager(this);
dialog.setMessage("Loading");
dialog.show();

        fetchingJSON();
        //Toast.makeText(getContext(),"direct",Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.listview);

//         names = view.findViewById(R.id.name);
//         price = view.findViewById(R.id.price);


        // Inflate the layout for this fragment
    }

    private void fetchingJSON() {


        // Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(Earnings.this);

        //this is the url where you want to send the request

        String url = ApiClient.BASE_URL+"manual_sel/manual_earnings/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.d("ssssd", "resp" + obj);
                            // amount.setText(obj.optString("total"));
                            dataModelArrayList = new ArrayList<>();
                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(Earnings.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                EarningPojo playerModel = new EarningPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                             //   playerModel.setProductname(dataobj.optSt ring("name"));
                               // ApiClient.productids.add(dataobj.optString("id"));
                                playerModel.setName(dataobj.optString("product"));
                                playerModel.setProcess(dataobj.optString("order_status"));
                                textView.setText("₹ "+total);
                              playerModel.setAmount("₹ "+dataobj.optString("earnings"));
                                playerModel.setDate(dataobj.optString("order_date"));
                              //  playerModel.setStatus("");


                                dataModelArrayList.add(playerModel);

                            }

                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Earnings.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void setupRecycler(){

        orderAdapter = new EarningAdapter(dataModelArrayList, this);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

}
