package com.example.sorting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<AddressItem> mAddressItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<AddressItem> addressItems, Context mContext) {
        this.mAddressItems = addressItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.tv_number.setText(mAddressItems.get(position).getNumber());
        holder.tv_address.setText(mAddressItems.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mAddressItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_number;
        private TextView tv_address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_address=itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int curPos = getAdapterPosition(); // ?????? ????????? ????????? ????????? ??????
                    AddressItem addressItem = mAddressItems.get(curPos);

                    String[] strChoiceItems = {"????????????"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("????????? ????????? ?????? ????????????");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0) {
                                    // ????????????
                                    int id=addressItem.getId();
                                    mDBHelper.deleteAddress(id);
                                mAddressItems.remove(curPos);
                                    notifyItemRemoved(curPos);
                                    Toast.makeText(mContext,"????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                    builder.show();

                }
            });
        }
    }


    //?????????????????? ???????????? ????????????, ?????? ???????????? ????????? ????????? ???????????? ???????????? ???????????? ????????????.
    public void addItem(AddressItem _item){
        mAddressItems.add(_item);

    }
}
