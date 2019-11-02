package creo.com.vendors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class Productdetail extends AppCompatActivity {

    private B2BListAdapter b2BListAdapter;
    private RecyclerView recyclerView;
    Context context=this;
    private String URLline = Global.BASE_URL+"product_details/product_view/";
    ArrayList<String>ids = new ArrayList<>();
    Activity activity = this;
    ArrayList<String>names = new ArrayList<>();
    ArrayList<String>images = new ArrayList<>();
    ArrayList<String> shop_names = new ArrayList<>();
    ArrayList<ProductB2BPojo> bookingPojos;
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    TextView customername,proceed;
    ImageView imageView;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        proceed = findViewById(R.id.proceed);

        recyclerView = findViewById(R.id.recyclerView);


        imageView=findViewById(R.id.back);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sessionManager = new SessionManager(this);
        dialog=new ProgressDialog(Productdetail.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading..");
        dialog.show();

        product();
        ApiClient.ids.clear();
        ApiClient.prices.clear();
        ApiClient.quantity.clear();
        ApiClient.images.clear();
        ApiClient.name.clear();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApiClient.directsell_cartcount.equals("0")){
                    Toast.makeText(Productdetail.this,"Please select atleast one product",Toast.LENGTH_SHORT).show();
                }
                if (!(ApiClient.directsell_cartcount.equals("0"))) {
                    startActivity(new Intent(Productdetail.this, AddAddress.class));
                }
            }
        });
    }

    private void product(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                  //      Toast.makeText(Productdetail.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("results");


                            Log.d("otp","mm"+status);


                            Toast.makeText(Productdetail.this, ot, Toast.LENGTH_LONG).show();
                            //  JSONObject obj = new JSONObject(response);





                            bookingPojos = new ArrayList<>();
                            //   JSONObject jsonObject1=jsonObject.getJSONObject("places");
                            Log.d("data","mm"+status);
                            JSONArray dataArray  = new JSONArray(status);
                            JSONObject jsonObject4 = dataArray.optJSONObject(0);

                            // Log.d("fieldcab","mm"+fieldcab);

                            for (int i = 0; i < dataArray.length(); i++) {

                                ProductB2BPojo playerModel = new ProductB2BPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String pk=dataobj.getString("pk");
                                ids.add(dataobj.getString("pk"));
                              playerModel.setId(dataobj.optString("pk"));
                                Log.d("pkkkkkkk","mm"+pk);
                                JSONObject fieldcab = dataobj.optJSONObject("fields");
                                playerModel.setProduct_name(fieldcab.getString("name"));
                                names.add(fieldcab.getString("name"));
                                playerModel.setPrice("₹ "+fieldcab.getString("price"));
                                String images1 = fieldcab.getString("image");
                                String[] seperated = images1.split(",");
                                String split = seperated[0].replace("[", "");
                                   images.add(split);
                                playerModel.setQuantity("0");
                                playerModel.setImage(Global.BASE_URL+"media/"+split);
                             //   playerModel.setImage(Global.BASE_URL+"media/"+dataobj.getString("image"));
                               // playerModel.setSources(dataobj.getString("source"));
                              //  playerModel.setDestination(dataobj.getString("destination"));


                                bookingPojos.add(playerModel);



                                setupRecycler();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Productdetail.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    @SuppressLint("WrongConstant")
    private void setupRecycler(){

        b2BListAdapter = new B2BListAdapter(this,bookingPojos);
        recyclerView.setAdapter(b2BListAdapter);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // Set layout manager.
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
      super.onBackPressed();
    }

}
