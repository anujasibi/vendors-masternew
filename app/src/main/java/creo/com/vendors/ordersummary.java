package creo.com.vendors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.List;
import java.util.Map;

import creo.com.vendors.utils.ApiClient;
import creo.com.vendors.utils.Global;
import creo.com.vendors.utils.SessionManager;

public class ordersummary extends AppCompatActivity {

    TextView makepayment, total;
    ScrollView scrollView;
    Activity activity = this;
    RecyclerView recyclerView;
    PopupWindow popupWindow;
    ImageView back;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    TextView visibilty;
    RelativeLayout relativeLayout;
    String token = null;
    ArrayList<String> name = new ArrayList<>();
    public List<CartPojo> pjo = new ArrayList<>();
    String payment_mode= "null";
    Context context = this;
    ArrayList<String>total_price = new ArrayList<>();
    public int i = 0;
    JSONArray arr = new JSONArray();
    JSONObject products = new JSONObject();
    public String orderid = null;
    public String amount =
            "0";
    CardView cardView;
    String po;
    TextView dis;
    int value = 0;
    TextView apply,rt,remove,imj,dt,dtn;
    EditText discou;
    int disount_amount=0;
    int total_discount=0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersummary);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        apply=findViewById(R.id.apply);
        discou=findViewById(R.id.discount);
        imj=findViewById(R.id.imj);
        rt=findViewById(R.id.rt);
        remove=findViewById(R.id.remove);
        dt=findViewById(R.id.dt);
        dtn=findViewById(R.id.dtn);
//if (getIntent().getExtras() !=null) {
//    Bundle extras = getIntent().getExtras();
//     po = extras.getString("dis");
//}

//        if (getIntent().getExtras() ==null) {
//            po="null";
//        }

        dtn.setText("0");


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discou.setVisibility(View.VISIBLE);
                imj.setVisibility(View.VISIBLE);
                apply.setVisibility(View.GONE);


            }
        });

        imj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=discou.getText().toString();
                discou.setVisibility(View.GONE);
                imj.setVisibility(View.GONE);
                rt.setVisibility(View.VISIBLE);
                rt.setText("Discount of amount Rs "+amount);
                remove.setVisibility(View.VISIBLE);
                dt.setVisibility(View.VISIBLE);
                dtn.setVisibility(View.VISIBLE);

                if(amount.equals("0")){
                    int disount_amount=(value-Integer.parseInt(Global.total));
                    dtn.setText(String.valueOf(disount_amount));
                }

                if(!(amount.equals("0"))){
                    int disount_amount=(value-Integer.parseInt(Global.total)-Integer.parseInt(amount));
                    dtn.setText(String.valueOf(disount_amount));
                    Log.d("totak_gggg","mm"+dtn.getText().toString());
                    Log.d("totak_gggg","mm"+String.valueOf(disount_amount));

                }


            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount="0";
                discou.setVisibility(View.VISIBLE);
                discou.setText("0");
                imj.setVisibility(View.VISIBLE);
                rt.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                dt.setVisibility(View.GONE);
                dtn.setVisibility(View.GONE);
                dtn.setText("0");


                imj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amount=discou.getText().toString();
                        discou.setVisibility(View.GONE);
                        imj.setVisibility(View.GONE);
                        rt.setVisibility(View.VISIBLE);
                        rt.setText("Discount of amount Rs "+amount);
                        remove.setVisibility(View.VISIBLE);
                        dt.setVisibility(View.VISIBLE);
                        dtn.setVisibility(View.VISIBLE);


                        if(amount.equals("0")){
                             disount_amount=(value-Integer.parseInt(Global.total));
                            dtn.setText(String.valueOf(disount_amount));

                        }

                        if(!(amount.equals("0"))){
                             disount_amount=(value-Integer.parseInt(Global.total)-Integer.parseInt(amount));
                            dtn.setText(String.valueOf(disount_amount));
                            Log.d("tota;l_fff","mm"+dtn.getText().toString());
                            Log.d("tota;l_fff","mm"+String.valueOf(disount_amount));

                        }
                    }
                });





            }
        });
        cardView=findViewById(R.id.cardu);

        visibilty=findViewById(R.id.vihjjk);

        if(Global.discount.equals("")){
            cardView.setVisibility(View.GONE);

        }
        if(Global.discount.equals("clicked")){
            cardView.setVisibility(View.VISIBLE);
        }

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
     //   Window window = activity.getWindow();
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        dis=findViewById(R.id.discount);
        // clear FLAG_TRANSLUCENT_STATUS flag:
       /* window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));*/
        relativeLayout = findViewById(R.id.relative);
        scrollView = findViewById(R.id.scrollview);
        sessionManager = new SessionManager(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ordersummary.this,chooseaddress.class));
            }
        });
        total = findViewById(R.id.total);
        makepayment = findViewById(R.id.gotopayment);

        scrollView.fullScroll(ScrollView.FOCUS_UP);
        recyclerView = findViewById(R.id.recyclerview);
       // Log.d("ajkd","bhjnkm"+ sessionManager.getTokens());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        token = (pref.getString("token", ""));
        Log.d("ajkd","bhjnkm"+ token);
            try {
                HashMap<String, JSONObject> map = new HashMap<String, JSONObject>();
                for (int i = 0; i < ApiClient.productB2BPojo.size(); i++) {
                    if (!(ApiClient.productB2BPojo.get(i).getQuantity().equals("0"))) {
                        JSONObject json = new JSONObject();
                        json.put("id", ApiClient.productB2BPojo.get(i).getId());
                        Log.d("idjjj", "mm" + ApiClient.productB2BPojo.get(i).getDiscount());
//                    String prices=ApiClient.productB2BPojo.get(i).getId();
//
//                        int totalp = Integer.parseInt(prices) - Integer.parseInt(po);
//                        String totalpr = String.valueOf(totalp);
//
//                        Log.d("id", "mm" + totalpr);

//                        Log.d("quantityhgg", "qty" + ApiClient.productB2BPojo.get(i).getQuantity());
//
                        json.put("price", ApiClient.productB2BPojo.get(i).getPrice());
                        total_price.add(ApiClient.productB2BPojo.get(i).getPrice());
                        if (ApiClient.productB2BPojo.get(i).getDiscount()==null){
                            json.put("discount","0");
                        }
                        if (ApiClient.productB2BPojo.get(i).getDiscount()!=null) {
                            json.put("discount", ApiClient.productB2BPojo.get(i).getDiscount());
                        }
                        json.put("quantity", ApiClient.productB2BPojo.get(i).getQuantity());
                       // json.put("refer_token", "");
                        map.put("json" + i, json);
                        arr.put(map.get("json" + i));
                    }
                }

                products.put("product", arr);
                //   Log.d("Thejsonstringis " ,"dsfs"+products.length());

                Log.d("size", "mm" + ApiClient.productB2BPojo.size());
                Log.d("size", "mm" + products.toString());


                //Log.d("Total","mm"+(total-value));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < ApiClient.productB2BPojo.size(); i++) {

                String price[] = ApiClient.productB2BPojo.get(i).getPrice().split("₹ ");


                value += Integer.parseInt(price[1]) * Integer.parseInt(ApiClient.productB2BPojo.get(i).getQuantity());
                 Log.d("kjbjhvhcv", "jvghcgfc" + value);

            }

            visibilty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Global.discount.size()==0) {
                        total.setText("₹ " + String.valueOf(value));
                        cardView.setVisibility(View.VISIBLE);
                        makepayment.setVisibility(View.VISIBLE);
                        visibilty.setVisibility(View.GONE);
                        Log.d("totalaa","mmmm"+Global.total);

                    }
                    if (!(Global.discount.size()==0)){
                        total.setText("₹ " + (value-Integer.parseInt(Global.total)));
                        cardView.setVisibility(View.VISIBLE);
                        makepayment.setVisibility(View.VISIBLE);
                        visibilty.setVisibility(View.GONE);
                        Log.d("totalaa","mmmm"+Global.total);

                    }


                }
            });






            /*dis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("discounted","price"+(value - Integer.parseInt(Global.discount)));
                }
            });*/



       // Checkout.preload(getApplicationContext());


        for (int i = 0; i < ApiClient.productB2BPojo.size(); i++) {
            if (!(ApiClient.productB2BPojo.get(i).getQuantity().equals("0"))) {
                CartPojo productPojo = new CartPojo(ApiClient.productB2BPojo.get(i).getProduct_name(), ApiClient.productB2BPojo.get(i).getPrice(), ApiClient.productB2BPojo.get(i).getImage(), "Quantity : " + ApiClient.productB2BPojo.get(i).getQuantity());
                pjo.add(productPojo);
            }
        }



        SummaryAdapter cartAdapter = new SummaryAdapter(pjo, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(cartAdapter);
        makepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(discou.getText().length()==0){
                    total_discount=(Integer.parseInt(Global.total));

                    Log.d("hhhhhhhhhhhh","mm"+total_discount);

                }
                if(discou.getText().length()>0){
                    total_discount=(Integer.parseInt(Global.total)+Integer.parseInt(discou.getText().toString()));

                    Log.d("hhhhhhhhhhhh","mm"+total_discount);
                }

                if(Global.total.equals("0")){
                    total_discount=Integer.parseInt(amount);

                    Log.d("hhhhhhhhhhhh","mm"+total_discount);
                }

                LayoutInflater layoutInflater = (LayoutInflater) ordersummary.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.payment_mode, null);
                RadioButton cod = customView.findViewById(R.id.cod);
                RadioButton online = customView.findViewById(R.id.online);
                cod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payment_mode = "COD";
                    }
                });
//                online.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        payment_mode = "ONLINE";
//                    }
//                });
                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                TextView apply = customView.findViewById(R.id.apply);
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(payment_mode.equals("null")){
                            Toast.makeText(ordersummary.this,"Please select the mode of payment",Toast.LENGTH_SHORT).show();
                        }

                        if(payment_mode.equals("COD")) {

                          //  checkavail();
                            showProgressDialogWithTitle("Confirming order..");

                          create_order(payment_mode);


                        }
//                        if(payment_mode.equals("ONLINE")){
//
//                            //checkavail_online();
////                            Intent intent = new Intent(OrderSummary.this,MakePayment.class);
////                            intent.putExtra("products",products.toString());
////                            intent.putExtra("address",ApiClient.address);
////                            intent.putExtra("payment_method",payment_mode);
////                            ApiClient.token = token;
////                            intent.putExtra("token",token);
////                            startActivity(intent);
//                        }
                    }
                });

                popupWindow = new PopupWindow(customView, ViewPager.LayoutParams.MATCH_PARENT, 1000);
                popupWindow.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                relativeLayout.setAlpha(0.2F);

                //display the popup window
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        total_discount=0;
                        popupWindow.dismiss();
                        relativeLayout.setAlpha(1.0F);
                    }
                });

            }


        });
    }


    private void showProgressDialogWithTitle(String substring) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setMessage(substring);
        progressDialog.show();
    }

    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

    void checkavail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL+"order_details/check_pin_payment/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("availresss","ordr"+ApiClient.address);
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("Success")){
                                //  Toast.makeText(OrderSummary.this,"hhh"+jsonObject,Toast.LENGTH_SHORT).show();
                            //    create_order(payment_mode);
                            }
                            if(message.equals("Failed")){
                                Toast.makeText(ordersummary.this,"Delivery not available to this location",Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ordersummary.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("product",products.toString());
                params.put("address_id",ApiClient.address);


                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());

                return params;

            }
        };

        RequestQueue requestQueue = (RequestQueue) Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public  void create_order(final String payment_mode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL+"manual_sel/order_manual_det/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Log.d("orderresponse","ordr"+response);
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("message").toString().equals("Success")){
                                popupWindow.dismiss();
                                relativeLayout.setAlpha(1.0F);
                                hideProgressDialogWithTitle();
                                 startActivity(new Intent(ordersummary.this,orderplacesuccess.class));
                                Toast.makeText(ordersummary.this,"Success",Toast.LENGTH_SHORT).show();
                            }
                            if (jsonObject.optString("code").toString().equals("204")){
                                popupWindow.dismiss();
                                relativeLayout.setAlpha(1.0F);
                                hideProgressDialogWithTitle();
                                Toast.makeText(ordersummary.this,"Entered Pincode is not available now",Toast.LENGTH_SHORT).show();}
                            if (jsonObject.optString("message").toString().equals("Failed")){
                                popupWindow.dismiss();
                                relativeLayout.setAlpha(1.0F);
                                hideProgressDialogWithTitle();
                                Toast.makeText(ordersummary.this,"Failed",Toast.LENGTH_SHORT).show();}
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ordersummary.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("product",products.toString());
                Log.d("products","mm"+products.toString());
                params.put("det_id",sessionManager.getID());
                Log.d("products","mm"+sessionManager.getID());
                params.put("payment_status","0");
                params.put("payment_method",payment_mode);
                Log.d("products","mm"+payment_mode);
              //  params.put("total_discount",String.valueOf(disount_amount));
                params.put("total_discount",dtn.getText().toString());
                Log.d("total_discount","mm"+dtn.getText().toString());
                params.put("dis_amount",String.valueOf(total_discount));
                Log.d("dis_amount","mm"+total_discount);
                params.put("total_amount","₹ "+String.valueOf(value));
                Log.d("dis_amount","mm"+String.valueOf(value));
                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                Log.d("Tokenccccc","mm"+sessionManager.getTokens());
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ordersummary.this,chooseaddress.class));

    }



}
