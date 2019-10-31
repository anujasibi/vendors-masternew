package creo.com.vendors;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import creo.com.vendors.utils.ApiClient;
import creo.com.vendors.utils.SessionManager;

public class orderlist extends AppCompatActivity {
    private static ProgressDialog mProgressDialog;
    ArrayList<DirectOrderPojo> dataModelArrayList;
    private RecyclerView recyclerView;
    TextView amount;
    String token = null;
    private DirectOrderAdapter orderAdapter;
    SessionManager sessionManager;
    boolean doubleBackToExitPressedOnce = false;
    Activity  activity = this;
    ImageView back;
    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        recyclerView = findViewById(R.id.recyclerView);
        sessionManager = new SessionManager(this);
        back = findViewById(R.id.back);

        dialog=new ProgressDialog(orderlist.this,R.style.MyAlertDialogStyle);

        dialog.setMessage("Loading..");
        dialog.show();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(orderlist.this,MainUI.class));

            }
        });
        fetchingJSON();
//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                Toast.makeText(Orders.this,"orderid"+ApiClient.productids.get(position),Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Orders.this,Orderdetails.class);
//                intent.putExtra("order_id",ApiClient.productids.get(position));
//                startActivity(intent);
//            }
//        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        token = (pref.getString("token", ""));

    }
    private void fetchingJSON() {

        RequestQueue queue = Volley.newRequestQueue(orderlist.this);

        //this is the url where you want to send the request

        String url = ApiClient.BASE_URL+"manual_sel/manual_list/";

        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.d("ssssd", "resp" + obj);
                          //  amount.setText(obj.optString("total"));
                            dataModelArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(orderlist.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                DirectOrderPojo playerModel = new DirectOrderPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String images1 = dataobj.getString("image");
                                String[] seperated = images1.split(",");
                                String split = seperated[0].replace("[", "");
                                playerModel.setProductname(dataobj.optString("product_name"));
                               // ApiClient.productids.add(dataobj.optString("id"));
                                playerModel.setName(dataobj.optString("name"));
                                playerModel.setPrice(dataobj.optString("price"));
                                playerModel.setStatus(dataobj.optString("qty"));
                                playerModel.setdelivery(dataobj.optString("order_status"));
                                playerModel.setImg(ApiClient.BASE_URL+"media/" + split);

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
                Toast.makeText(orderlist.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            public Map<String, String> getHeaders()  {
              Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " +sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    private void setupRecycler(){
        dialog.dismiss();

        orderAdapter = new DirectOrderAdapter(this,dataModelArrayList);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(orderlist.this,"Press again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
