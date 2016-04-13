package com.jikexueyuan.jikecontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactsActivity extends Activity implements View.OnClickListener {


    private String etName = "";
    private String etNumber = "";
    Button btnAddOk = null;
    Button btnAddCancel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        btnAddOk = (Button) findViewById(R.id.btnAddOk);
        btnAddCancel = (Button) findViewById(R.id.btnAddCancel);

        btnAddOk.setOnClickListener(this);
        btnAddCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAddOk:
                etName = ((EditText) findViewById(R.id.etName)).getText().toString();
                etNumber = ((EditText) findViewById(R.id.etNumber)).getText().toString();
                System.out.println("Contacts:"+ etName + etNumber);



                break;
            case R.id.btnAddCancel:

                break;
        }
    }
}
