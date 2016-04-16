package com.jikexueyuan.jikecontacts;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddContactsActivity extends Activity implements View.OnClickListener {

    Button btnAddOk = null;
    Button btnAddCancel = null;

    ContentProvider contentProvider;

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

                String etName = ((EditText) findViewById(R.id.etName)).getText().toString();
                String etNumber = ((EditText) findViewById(R.id.etNumber)).getText().toString();

                ContentValues values = new ContentValues();
                Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
                long rawContactId = ContentUris.parseId(rawContactUri);

                values.clear();
                values.put(RawContacts.Data.RAW_CONTACT_ID, rawContactId);
                // 设置内容类型
                values.put(RawContacts.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                // 设置联系人名字
                values.put(StructuredName.GIVEN_NAME, etName);
                // 向联系人URI添加联系人名字
                getContentResolver().insert(Data.CONTENT_URI, values);

                values.clear();
                values.put(RawContacts.Data.RAW_CONTACT_ID, rawContactId);
                values.put(RawContacts.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                // 设置联系人的电话号码
                values.put(Phone.NUMBER, etNumber);
                // 设置电话类型
                values.put(Phone.TYPE, Phone.TYPE_MOBILE);
                // 向联系人电话号码URI添加电话号码
                getContentResolver().insert(Data.CONTENT_URI, values);

                AddContactsActivity.this.setResult(RESULT_OK);
                AddContactsActivity.this.finish();
                break;

            case R.id.btnAddCancel:
                AddContactsActivity.this.setResult(RESULT_CANCELED);
                AddContactsActivity.this.finish();
                break;
        }
    }

}
