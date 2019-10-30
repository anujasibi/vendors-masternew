package creo.com.vendors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class DirectOrderAdapter extends RecyclerView.Adapter<DirectOrderAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DirectOrderPojo> dataModelArrayList;
    public Context context1 ;

    public DirectOrderAdapter(Context ctx, ArrayList<DirectOrderPojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public DirectOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.order_list, parent, false);
        DirectOrderAdapter.MyViewHolder holder = new DirectOrderAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DirectOrderAdapter.MyViewHolder holder, final int position) {

        //Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getProductname());
        holder.delivery.setText(dataModelArrayList.get(position).getStatus());
        Picasso.with(context1).load(dataModelArrayList.get(position).getImg()).into(holder.iv);
        holder.customer.setText(dataModelArrayList.get(position).getName());
        holder.earning.setText(dataModelArrayList.get(position).getPrice());
        holder.status.setText(dataModelArrayList.get(position).getdelivery());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context1, Orderdetails.class);
//                intent.putExtra("order_id",dataModelArrayList.get(position).getProductid());
//                context1.startActivity(intent);
            }
        });
//        holder.qty.setText(dataModelArrayList.get(position).getQty());
        // holder.country.setText(dataModelArrayList.get(position).getCountry());
        // holder.city.setText(dataModelArrayList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView price, name, qty,delivery,earning,customer,status;
        LinearLayout linearLayout;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            delivery = itemView.findViewById(R.id.delivery);
            earning = itemView.findViewById(R.id.earning);
            linearLayout = itemView.findViewById(R.id.layout);
            name = (TextView) itemView.findViewById(R.id.title);
            customer = itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.st);
            price = (TextView) itemView.findViewById(R.id.price);
            iv = itemView.findViewById(R.id.image);
//            qty = (TextView) itemView.findViewById(R.id.non);
        }

    }
}
