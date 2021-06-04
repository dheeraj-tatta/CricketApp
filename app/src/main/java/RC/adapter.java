    package RC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.rc.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    List<model> modelList = new ArrayList<>();
    Context context;
    public RecyclerViewClickListener listener;

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public adapter(List<model> modelList, Context context, RecyclerViewClickListener listener) {
        this.modelList = modelList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_layout,parent,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter.viewHolder holder, int position) {
        if(modelList.size()>0){
        model model =modelList.get(position);
        holder.t1.setText(model.getTeam1());
        holder.t2.setText(model.getTeam2());
        holder.d.setText(model.getDtg());
        }
//        holder.t2.setText(model.getTeam2());        }

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1,t2,d;

        public viewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView )  {
            super(itemView);
            t1 = itemView.findViewById(R.id.tv1);
            t2 = itemView.findViewById(R.id.tv2);
            d = itemView.findViewById(R.id.tv3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }



    }



}
