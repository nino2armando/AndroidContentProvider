package com.example.nino.androidcontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddContact(View view){
        // add a new Contact record
        ContentValues values = new ContentValues();

        EditText nameView = (EditText) findViewById(R.id.EditText_Name);
        EditText numberView = (EditText) findViewById(R.id.EditText_Number);

        String name = nameView.getText().toString();
        String number = numberView.getText().toString();

        values.put(ContactProvider.NAME, name);
        values.put(ContactProvider.NUMBER, number);

        Uri uri = getContentResolver().insert(ContactProvider.CONTENT_URI, values);

        nameView.setText("");
        numberView.setText("");

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickDeleteContacts(View view){
         int id = getContentResolver().delete(ContactProvider.CONTENT_URI, null, null);
            //todo: we have to see if the list is full...if yes clear
         ListView listView = (ListView) findViewById(R.id.listView_Retrieve);
         listView.setAdapter(null);
    }

    public void onClickRetrieveContact(View view){
        String URL = "content://com.example.provider.ContactBook/contacts";
        Uri contacts = Uri.parse(URL);
        Cursor c = managedQuery(contacts, null, null, null, "name");

        ListView listView = (ListView) findViewById(R.id.listView_Retrieve);
        List<String> data = new ArrayList<String>();

        if(c.moveToFirst()){
            do{
/*                Toast.makeText(this,
                        c.getString(c.getColumnIndex(ContactProvider._ID)) +
                                ", " +  c.getString(c.getColumnIndex( ContactProvider.NAME)) +
                                ", " + c.getString(c.getColumnIndex( ContactProvider.NUMBER)),
                        Toast.LENGTH_SHORT).show();*/

                data.add(c.getString(c.getColumnIndex(ContactProvider._ID)) +
                        ", " +  c.getString(c.getColumnIndex( ContactProvider.NAME)) +
                        ", " + c.getString(c.getColumnIndex( ContactProvider.NUMBER)));

            } while (c.moveToNext());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        listView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
