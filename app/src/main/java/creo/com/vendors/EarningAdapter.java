package creo.com.vendors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.ViewHolder> {

    public List<EarningPojo> downloadPojos;
    Context context1 ;

    public EarningAdapter(List<EarningPojo> productPojo, Context context) {
        this.downloadPojos = productPojo;
        this.context1 = context;
    }

    @Override
    public EarningAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.earning_list, parent, false);
        EarningAdapter.ViewHolder viewHolder = new EarningAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EarningAdapter.ViewHolder holder, int position) {

        holder.textView.setText(downloadPojos.get(position).getName());
        holder.date.setText(downloadPojos.get(position).getDate());
        holder.amount.setText(downloadPojos.get(position).getAmount());
        holder.process.setText(downloadPojos.get(position).getProcess());
    }

    @Override
    public int getItemCount() {
        return downloadPojos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,date,amount,process;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView =  itemView.findViewById(R.id.name);
            this.date = itemView.findViewById(R.id.date);
            this.amount = itemView.findViewById(R.id.amount);
            this.process=itemView.findViewById(R.id.processing);
        }

    }

}
