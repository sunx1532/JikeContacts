package com.jikexueyuan.jikecontacts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    //List Contacts
    ListView contactsView;

    ArrayAdapter<String> adapter;

    List<String> contactsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeList();

        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String contacts[] = contactsList.get(position).split("\n");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.menuTitle);

                String strName = getString(R.string.call);
                String strNumber = getString(R.string.sms);
                final String[] options = {strName, strNumber};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contacts[1]));
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(callIntent);
                                break;
                            case 1:
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + contacts[1]));
                                startActivity(smsIntent);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void makeList(){
        contactsList.clear();

        contactsView = (ListView) findViewById(R.id.listViewContacts);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);

        contactsView.setAdapter(adapter);

        readContacts();
    }

    private void readContacts(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(Phone.CONTENT_URI,null,null,null,null);
            while (cursor.moveToNext()){
                String displayName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                contactsList.add(displayName + "\n" + number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                Toast.makeText(MainActivity.this,R.string.Saved,Toast.LENGTH_SHORT).show();

                makeList();
                break;
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this,R.string.Cancel,Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
