package com.vavan.contactmanager;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class AddContactActivity extends AppCompatActivity {
    AddContactFragment addContactFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_stat_messagephone);

        Intent intent = getIntent();
        Bundle data = null;
        Log.d("MyLog", "AddContactAct  onCreate");

        if (intent.getStringExtra("IsFavorite")!=null) {
            data = new Bundle();
            Log.d("MyLog", "if");
            data.putString("IsFavorite",intent.getStringExtra("IsFavorite"));
        }

        if (intent.getStringExtra("ContactID") != null) {
            data = new Bundle();
            data.putString("ContactID",intent.getStringExtra("ContactID"));
        }


        addContactFragment = new AddContactFragment();
        if (data != null){
            addContactFragment.setArguments(data);
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentById(R.id.container) == null) {
            fragmentTransaction.add(R.id.container, addContactFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
