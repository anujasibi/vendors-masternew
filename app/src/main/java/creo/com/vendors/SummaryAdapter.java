package creo.com.vendors;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import creo.com.vendors.utils.ApiClient;
import creo.com.vendors.utils.Global;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {


  public List<CartPojo> productPojo;
  Context context1 ;
  ArrayList<String> discount = new ArrayList<>();



  public SummaryAdapter(List<CartPojo> productPojo, Context context) {
    this.productPojo = productPojo;
    this.context1 = context;
  }

  @Override
  public SummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View listItem= layoutInflater.inflate(R.layout.order_item, parent, false);
    SummaryAdapter.ViewHolder viewHolder = new SummaryAdapter.ViewHolder(listItem);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final SummaryAdapter.ViewHolder holder, final int position) {


    holder.name.setText(productPojo.get(position).getName());
    holder.price.setText(productPojo.get(position).getPrice());
    holder.quantity.setText(productPojo.get(position).getQuantity());
//        holder.imageView.setImageResource((Picasso.with(context).load(productPojo[position].getImage())));
    Picasso.with(context1).load(productPojo.get(position).getImage()).into(holder.imageView);
    holder.apply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        holder.editText.setVisibility(View.VISIBLE);
        holder.imj.setVisibility(View.VISIBLE);
        holder.apply.setVisibility(View.GONE);
      }
    });
    if (Global.discount.size()==0) {


    }

    if (ApiClient.productB2BPojo.size()>0) {

    if (ApiClient.productB2BPojo.get(position).getDiscount() != null) {
      if (ApiClient.productB2BPojo.get(position).getDiscount().equals("0")){
        holder.removes.setVisibility(View.GONE);
        holder.rt.setVisibility(View.GONE);
        holder.editText.setVisibility(View.VISIBLE);
        holder.imj.setVisibility(View.VISIBLE);
        holder.apply.setVisibility(View.GONE);




      }
      if (!(ApiClient.productB2BPojo.get(position).getDiscount().equals("0"))) {
        holder.rt.setVisibility(View.VISIBLE);
        holder.imj.setVisibility(View.GONE);
        holder.editText.setVisibility(View.GONE);
        holder.rt.setText("Discount of amount RS" + ApiClient.productB2BPojo.get(position).getDiscount());
        holder.removes.setVisibility(View.VISIBLE);
        holder.apply.setVisibility(View.GONE);


      }
    }

    }

    holder.imj.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String df = holder.editText.getText().toString();
        discount.add(df);
        ApiClient.productB2BPojo.get(position).setDiscount(df);
        Global.discount.add(df);
        int value = 0;
        if (Global.discount.size() > 0) {
          for (int i = 0; i < Global.discount.size(); i++) {
            value += Integer.parseInt(Global.discount.get(i));
            Log.d("totalvalue","mm"+value);

          }
          Global.total = String.valueOf(value);
          Log.d("discounta", "value" + ApiClient.productB2BPojo.get(position).getDiscount());

          context1.startActivity(new Intent(context1, ordersummary.class));

          if (ApiClient.productB2BPojo.get(position).getDiscount() != null) {
            Global.total = String.valueOf(value);
            holder.rt.setVisibility(View.VISIBLE);
            holder.imj.setVisibility(View.GONE);
            holder.editText.setVisibility(View.GONE);
            holder.rt.setText("Discount of amount RS" + ApiClient.productB2BPojo.get(position).getDiscount());
            holder.apply.setVisibility(View.GONE);


            holder.removes.setVisibility(View.VISIBLE);

        }
        }








      }
    });
    holder.removes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Global.discount.remove(ApiClient.productB2BPojo.get(position).getDiscount());
        Log.d("discount", "value" + ApiClient.productB2BPojo.get(position).getDiscount());
        ApiClient.productB2BPojo.get(position).setDiscount("0");
        context1.startActivity(new Intent(context1,ordersummary.class));
        holder.removes.setVisibility(View.GONE);
        holder.rt.setVisibility(View.GONE);
        holder.apply.setVisibility(View.GONE);
        holder.imj.setVisibility(View.VISIBLE);
        holder.editText.setVisibility(View.VISIBLE);
        int value=0;
        for (int i = 0; i < Global.discount.size(); i++) {
          value += Integer.parseInt(Global.discount.get(i));
          Log.d("totalvalue","mm"+value);

        }
        Global.total=String.valueOf(value);

      }
    });









    /*holder.dis.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String df=holder.editText.getText().toString();
        Intent i=new Intent(context1,ordersummary.class);
      *//*  String price[] = ApiClient.productB2BPojo.get(position).getPrice().split("₹ ");
        int price1 =(Integer.parseInt(price[1]))-(Integer.parseInt(df));
        Global.price = String.valueOf(price1);
        Log.d("hjfhjfhjf","prtie"+price1+ApiClient.productB2BPojo.size());*//*
      Global.discount+=df;

        context1.startActivity(i);



      }
    });
*/

  }

  @Override
  public int getItemCount() {
    return productPojo.size();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView name,price,quantity,remove;
    public EditText editText;
    public ImageView dis;
    public TextView imj,rt,removes,apply;

    public ViewHolder(View itemView) {
      super(itemView);
      this.imageView =  itemView.findViewById(R.id.img);
      this.name =  itemView.findViewById(R.id.title);
      this.quantity = itemView.findViewById(R.id.quantity);
      this.price = itemView.findViewById(R.id.price);
      this.editText=itemView.findViewById(R.id.discount);
      this.imj=itemView.findViewById(R.id.imj);
      this.rt=itemView.findViewById(R.id.rt);
      this.removes=itemView.findViewById(R.id.remove);
      this.apply=itemView.findViewById(R.id.apply);

      //this.dis=itemView.findViewById(R.id.imj);
    }

  }


}
