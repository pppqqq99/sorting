package com.example.sorting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class reading extends AppCompatActivity {

    private RecyclerView mRv_sorting;
    private ArrayList<AddressItem> mAddressItems;
    private DBHelper mDBHelper = new DBHelper(this);
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        setInit();
    }

    private void setInit() {
        FloatingActionButton btn_add = findViewById(R.id.btn_add);
        mAddressItems = new ArrayList<>();


        loadRecentDB();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //팝업 창 띄우기
                Dialog dialog = new Dialog(reading.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit);
                EditText et_number = dialog.findViewById(R.id.et_number);
                EditText et_address = dialog.findViewById(R.id.et_address);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // insert DB
                         mDBHelper.InsertAddress(et_number.getText().toString(),et_address.getText().toString());

                        // insert UI
                        AddressItem item = new AddressItem();
                        item.setNumber(et_number.getText().toString());
                        item.setAddress(et_address.getText().toString());
                        mAdapter.addItem(item);
                        mRv_sorting.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(reading.this, "할일 목록에 추가 되었습니다 !", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();
            }
        });
    }

    private void loadRecentDB() {

        //저장되어 있던 DB를 가져온다.
        mAddressItems = mDBHelper.getAddressList();
        if(mAdapter == null){
            mAdapter = new CustomAdapter(mAddressItems,this);
            mRv_sorting = findViewById(R.id.rv_sorting);
            mRv_sorting.setHasFixedSize(true);
            mRv_sorting.setAdapter(mAdapter);
        }
    }
}