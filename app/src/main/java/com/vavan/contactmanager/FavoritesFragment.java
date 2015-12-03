package com.vavan.contactmanager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class FavoritesFragment extends ListFragment implements LoaderManager.LoaderCallbacks{

    DBHelper db;
    ContactCursorAdapter contactCursorAdapter;
    Integer contactForDeletingBDID=0;
    String contactDescriptionForDeleting = null;
    AlertDialog.Builder alertDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        Log.d("MyLog", "onCreate");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("MyLog", "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox chbIsFavorite = (CheckBox) view.findViewById(R.id.chbIsFavoriteListItem);
                if (chbIsFavorite.isChecked()) {
                    db.updateRecord(Integer.parseInt(((TextView) view.findViewById(R.id.tvContactIDListItem)).getText().toString()), false);
                    chbIsFavorite.setChecked(false);
                } else {
                    chbIsFavorite.setChecked(true);
                    db.updateRecord(Integer.parseInt(((TextView) view.findViewById(R.id.tvContactIDListItem)).getText().toString()), true);
                }
                getActivity().getSupportFragmentManager().getFragments().get(1).onResume();

                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorites");

        db = new DBHelper(getActivity());

        Cursor cursor = db.getAllFavoritesCursor();
        contactCursorAdapter = new ContactCursorAdapter(getActivity(),cursor,0);
        setListAdapter(contactCursorAdapter);

        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Attention!");
        alertDialog.setMessage("Are you sure you want to delete " + contactDescriptionForDeleting + "?"); // сообщение
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                DBHelper db = new DBHelper(getActivity());
                db.deleteRecord(contactForDeletingBDID);
                getActivity().getSupportFragmentManager().getFragments().get(1).onResume();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btDeleteMenuItem:
                if (contactDescriptionForDeleting != null ){
                    alertDialog.setMessage("Are you sure you want to delete " + contactDescriptionForDeleting + "?");
                    alertDialog.show();
                } else {
                    Toast.makeText(getActivity(),"Chose contact first!", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        Log.d("MyLog", "onResume");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        contactForDeletingBDID = Integer.parseInt(((TextView)
                v.findViewById(R.id.tvContactIDListItem)).getText().toString());

        contactDescriptionForDeleting =  ((TextView)v.findViewById(R.id.tvDescriptionListItem)).getText().toString();

        DBHelper db = new DBHelper(getActivity());
        Toast.makeText(getActivity(), "Press \"Delete\" to delete " + contactDescriptionForDeleting, Toast.LENGTH_SHORT).show();

    }




    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllFavoritesCursor();

            return cursor;
        }

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity(),db);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        contactCursorAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
