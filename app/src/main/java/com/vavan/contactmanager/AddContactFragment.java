package com.vavan.contactmanager;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddContactFragment extends Fragment {
    final int REQUEST_CODE_PHOTO = 1;

    DBHelper db;

    File directory;
    ImageButton ibPhoto;
    EditText etName,etPhone;

    String imagePath = "";
    Bitmap contactImage;

    Integer contactEditingId = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = new DBHelper(getActivity());
        createDirectory();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Contact");

        etName = (EditText)view.findViewById(R.id.etNameAddContactFrag);
        etPhone = (EditText)view.findViewById(R.id.etPhoneNumberAddContactFrag);

        ibPhoto = (ImageButton)view.findViewById(R.id.ibPhotoAddContactFrag);
        ibPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        });


        if (getArguments()!=null) {
            contactEditingId = Integer.parseInt(getArguments().getString("ContactID"));

            DBRecord contactRecord = db.getRecord(contactEditingId);
            etName.setText(contactRecord.getDescription());
            etPhone.setText(contactRecord.getPhoneNumber());

            imagePath = contactRecord.getImagePath();
            ibPhoto.setImageURI(Uri.parse(contactRecord.getImagePath()));

            if (ibPhoto.getDrawable() != null){
                contactImage = ((BitmapDrawable)ibPhoto.getDrawable()).getBitmap();
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (savedInstanceState!=null){
            etName.setText(savedInstanceState.getString("Name"));
            etPhone.setText(savedInstanceState.getString("Phone"));
            if (savedInstanceState.getParcelable("bitmap")!=null){
                ibPhoto.setImageBitmap((Bitmap) savedInstanceState.getParcelable("bitmap"));
            } else {
                ibPhoto.setImageResource(R.drawable.contact_image);
            }
            contactImage = (Bitmap) savedInstanceState.getParcelable("bitmap");
            imagePath = savedInstanceState.getString("imagePath");
        }
        super.onActivityCreated(savedInstanceState);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.size() == 0)
        inflater.inflate(R.menu.add_button, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btAddMenuItem:

                if (contactEditingId!=null){

                    if (etName.getText().length() > 0 || etPhone.getText().length() > 0){
                        db.updateRecord(contactEditingId,
                                imagePath,
                                etName.getText().toString(),
                                etPhone.getText().toString(),
                                Boolean.FALSE);
                                getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), "Enter the name and the phone number!",
                                Toast.LENGTH_LONG).show();
                    }

                } else {


                    if (etName.getText().length() > 0 || etPhone.getText().length() > 0) {
                        DBHelper db = new DBHelper(getActivity());
                        db.addRecord(imagePath,
                                etName.getText().toString(),
                                etPhone.getText().toString(),
                                Boolean.FALSE);
                        getActivity().onBackPressed();

                    } else {
                        Toast.makeText(getActivity(), "Enter the name and the phone number!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("imagePath", imagePath);
        outState.putString("Name", etName.getText().toString());
        outState.putString("Phone", etPhone.getText().toString());
        outState.putParcelable("bitmap", contactImage);
        super.onSaveInstanceState(outState);

    }


    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "ContactsPhotosFolder");
        if (!directory.exists())
            directory.mkdirs();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object obj = intent.getExtras().get("data");
                if (obj instanceof Bitmap) {
                    contactImage = (Bitmap) obj;

                    storeImage(contactImage);

                    ibPhoto.setImageBitmap(contactImage);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Toast toast = Toast.makeText(getActivity(), "Photo didn`t make!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    private boolean storeImage(Bitmap imageData) {
        try {
            imagePath = directory.getPath() + "/" + "photo_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            imageData.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);

            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }

        return true;
    }
}
