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

import java.util.List;

import creo.com.vendors.utils.ApiClient;
import creo.com.vendors.utils.Global;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {


  public List<CartPojo> productPojo;
  Context context1 ;

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

    holder.dis.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Global.discount="clicked";
        String df=holder.editText.getText().toString();
        Intent i=new Intent(context1,ordersummary.class);
        String price[] = ApiClient.productB2BPojo.get(position).getPrice().split("₹ ");
        int price1 =(Integer.parseInt(price[1]))-(Integer.parseInt(df));
        Global.price = String.valueOf(price1);
        Log.d("hjfhjfhjf","prtie"+price1+ApiClient.productB2BPojo.size());
        i.putExtra("dis",df);
        context1.startActivity(i);



      }
    });


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

    public ViewHolder(View itemView) {
      super(itemView);
      this.imageView =  itemView.findViewById(R.id.img);
      this.name =  itemView.findViewById(R.id.title);
      this.quantity = itemView.findViewById(R.id.quantity);
      this.price = itemView.findViewById(R.id.price);
      this.editText=itemView.findViewById(R.id.discount);
      this.dis=itemView.findViewById(R.id.imj);
    }

  }


}
