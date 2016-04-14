package com.jikexueyuan.jikecontacts;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactsActivity extends Activity implements View.OnClickListener {

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
                /*etName = ((EditText) findViewById(R.id.etName)).getText().toString();
                etNumber = ((EditText) findViewById(R.id.etNumber)).getText().toString();
                System.out.println("Contacts:"+ etName + etNumber);*/
                EditText etName = (EditText) findViewById(R.id.etName);
                EditText etNumber = (EditText) findViewById(R.id.etNumber);

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);

                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                intent.putExtra(ContactsContract.Intents.Insert.NAME, etName.getText().toString());

                intent.putExtra(ContactsContract.Intents.Insert.PHONE, etNumber.getText());

                startActivity(intent);

                break;

            case R.id.btnAddCancel:

                break;
        }
    }

}
