package creo.com.vendors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.vendors.R;
import creo.com.vendors.utils.Global;

public class forgototp extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Activity activity = this;
    String phone_no = null;
    Context context=this;
    public String otp;
    private ProgressDialog dialog ;

    private String URLline = Global.BASE_URL+"login/phone_verify/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgototp);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(forgototp.this,R.style.MyAlertDialogStyle);
        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");


        editText = findViewById(R.id.otp);
        textView = findViewById(R.id.verifyotp);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() < 4 ){
                    editText.setError("Please enter the otp to proceed");
                }
                if(editText.getText().toString().length() == 4) {
                    dialog.setMessage("Loading");
                    dialog.show();

                    verifyotp();
                }
            }
        });

    }
    private void verifyotp()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String token=jsonObject.optString("Token");
                            //    sessionManager.setTokens(token);




                            Log.d("otp","mm"+token);
                            Log.d("code","mm"+status);
                            if(ot.equals("verify")){
                                Toast.makeText(forgototp.this, "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(forgototp.this, ResetPassword.class);
                                intent.putExtra("phone_no",phone_no);
                                startActivity(intent);
                            }
                            if(ot.equals("failed")){
                                Toast.makeText(forgototp.this, "Invalid Token."+ot, Toast.LENGTH_LONG).show();


                            }
                            else {
                                Toast.makeText(forgototp.this, "Failed."+ot, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                          Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(forgototp.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phone_no);
                params.put("otp",editText.getText().toString());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(forgototp.this,ForgotPassword.class));
    }

}