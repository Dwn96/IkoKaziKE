import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wambu.ikokazike.Data.UserService;
import com.wambu.ikokazike.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {










    Context context;
    ArrayList<UserService> dataList = new ArrayList<>();

    TextView textRowTitle,textRowCategory,textRowDate;
    ImageView imageRowEdit,imageRowDelete;




    public ServiceAdapter(Context con, ArrayList<UserService> list){

        context = con;
        dataList=list;
    }



    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.row_recycler_painters,parent,false);
        ServiceHolder serviceHolder = new ServiceHolder(view);




        return serviceHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        UserService userService = dataList.get(position);
        String name  = userService.getServiceName();
        String category = userService.getServiceCategory();
        String date = userService.getDateAdded();





    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ServiceHolder extends RecyclerView.ViewHolder{







        public ServiceHolder(@NonNull View itemView) {
            super(itemView);

            textRowTitle = itemView.findViewById(R.id.text_row_title);
            textRowDate= itemView.findViewById(R.id.text_row_date);
            textRowCategory=itemView.findViewById(R.id.text_row_category);
            imageRowDelete= itemView.findViewById(R.id.image_row_delete);
            imageRowEdit=itemView.findViewById(R.id.image_row_edit);





        }
    }
}
