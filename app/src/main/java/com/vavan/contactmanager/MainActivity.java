package com.vavan.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    //ImageButton btAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View tabView = createTabView(this, "Contacts");

        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator(tabView),
                ContactsFragment.class,null);

        tabView = createTabView(this, "Favorites");
        mTabHost.addTab(mTabHost.newTabSpec("favorites").setIndicator(tabView),
                FavoritesFragment.class, null);

        //to show the icon in actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_stat_messagephone);

        /*btAddFragment = (ImageButton)findViewById(R.id.btOpenAddContactFragment);
        btAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(intent);

            }
        });*/

    }

    private static View createTabView(Context context, String tabText) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null, false);
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
        tv.setText(tabText);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
