package com.wambu.ikokazike;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;
import com.wambu.ikokazike.Data.UserService;
import com.wambu.ikokazike.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    String serviceId="",serviceDate="";

    UpdateInterface updateInterface;






    Spinner  addCategory,addRate,testSpinner;










    Context context;
    ArrayList<UserService> dataList = new ArrayList<>();

    private TextView textRowTitle,textRowCategory,textRowDate;
   private ImageView imageRowEdit,imageRowDelete;
    Spinner hintSpinner;
    private String servPosterId;




    public ServiceAdapter(Context con, ArrayList<UserService> list,String posterId){

        context = con;
        dataList=list;
       updateInterface = (UpdateInterface) context;
      servPosterId=posterId;

    }



    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.row_recycler_painters,parent,false);
        ServiceHolder serviceHolder = new ServiceHolder(view);






        return serviceHolder;








    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, final int position) {
        UserService userService = dataList.get(position);
        String name  = userService.getServiceName();
        String category = userService.getServiceCategory();
        String date = userService.getDateAdded();












        textRowTitle.setText(name);
        textRowCategory.setText(category);
        textRowDate.setText(date);


        imageRowEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService userService1= dataList.get(position);

                showDialog(userService1);

            }
        });

        imageRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService userService1= dataList.get(position);
                confirmDeleteDialog(userService1);

            }
        });






    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder{







        public ServiceHolder(@NonNull View itemView) {
            super(itemView);

            textRowTitle = itemView.findViewById(R.id.text_row_title);
            textRowDate= itemView.findViewById(R.id.text_row_date);
            textRowCategory=itemView.findViewById(R.id.text_row_category);
            imageRowDelete= itemView.findViewById(R.id.image_row_delete);
            imageRowEdit=itemView.findViewById(R.id.image_row_edit);


           // testSpinner = itemView.findViewById(R.id.spinner_testSpinner);









        }
    }

    public  void showDialog(UserService serviceObject){



        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_note);
        dialog.show();

        final EditText etName = dialog.findViewById(R.id.editText_update_name);
        final EditText etPhone = dialog.findViewById(R.id.editText_update_phone);
        final EditText etAddress = dialog.findViewById(R.id.edit_text_update_Location);
        final Spinner updateCategory = dialog.findViewById(R.id.spinner_updateCategory);
        final Spinner updateRate = dialog.findViewById(R.id.spinner_updateRate);
        final EditText etDesc = dialog.findViewById(R.id.editText_update_description);

        Toolbar toolbarUpdateService = dialog.findViewById(R.id.toolbarUpdateService);

        toolbarUpdateService.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbarUpdateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




        etAddress.setFocusable(false);
        etAddress.setEnabled(false);
        etAddress.setClickable(false);



        /*
        This implementation is really funny actually,it is in MyServiceActivity, i figured why use 2 methods
        to populate our spinners when you can use just one? #BigBrainTime haha
        Sad news. It doesnt work for some reason (the spinner.SetSelection(pos) method ,but onwards my good sir. Si ni life )
         */






        /*
        These 2 method calls below are for:
        1. Calls the adapter to populate our spinner entries
        2.  Sets the adapter position to the value we're pulling from the DB
         */

        updateInterface.populateSpinner(updateRate, 1);
        updateInterface.updateRateSpinnerPosition(updateRate,serviceObject.getServiceRate());


        updateInterface.populateSpinner(updateCategory, 2);
        updateInterface.updateCategorySpinnerPosition(updateCategory,serviceObject.getServiceCategory());















        etName.setText(serviceObject.getServiceName());
        etPhone.setText(serviceObject.getServicePhone());
        etAddress.setText(serviceObject.getServiceRegion());
       // etCategory.setText(serviceObject.getServiceCategory());
        etDesc.setText(serviceObject.getServiceDescription());
       // etRate.setText(serviceObject.getServiceRate());


        serviceId = serviceObject.getServiceId();

        Button buttonUpdate = dialog.findViewById(R.id.button_update_Service);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar = Calendar.getInstance();
                serviceDate=simpleDateFormat.format(calendar.getTime());


                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                String category = updateCategory.getSelectedItem().toString();
                String desc = etDesc.getText().toString();
                String rate = updateRate.getSelectedItem().toString();


                UserService userService = new UserService(name,phone,category,desc,rate,address,serviceDate,serviceId,servPosterId);

                updateInterface.updateUserService(userService);





            }
        });


    }

    public void confirmDeleteDialog(final UserService serviceObject){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.show();


        TextView tvDelete  = dialog.findViewById(R.id.tv_delete);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);



        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateInterface.deleteUserService(serviceObject);

            }
        });



    }


}
