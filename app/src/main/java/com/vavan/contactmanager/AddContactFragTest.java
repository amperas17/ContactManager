package com.vavan.contactmanager;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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


public class AddContactFragTest extends Fragment {

    final int REQUEST_CODE_PHOTO = 1;
    final String TAG = "myLogs";

    String imagePath = "";

    Button btAdd,btSelect,btDelete;
    TextView tvOutput;
    Integer firstRecordID=1;
    EditText etName,etPhoneNumber;
    CheckBox chbIsFavorite;

    File directory;
    ImageView ivPhoto;
    Button btMakePhoto;
    Button btGetLastId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact_test, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Contact");


        tvOutput = (TextView)view.findViewById(R.id.tvDBListAddContactFrag);

        etName = (EditText)view.findViewById(R.id.etNameAddContactFrag);
        etPhoneNumber = (EditText)view.findViewById(R.id.etPhoneNumberAddContactFrag);
        chbIsFavorite = (CheckBox)view.findViewById(R.id.chbIsFavoriteAddContactFrag);


        btAdd = (Button)view.findViewById(R.id.btAddAddContactFrag);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.addRecord(imagePath,etName.getText().toString(),
                        etPhoneNumber.getText().toString(),chbIsFavorite.isChecked());
            }
        });

        btSelect = (Button)view.findViewById(R.id.btSelectAddContactFrag);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = "";
                DBHelper db = new DBHelper(getActivity());

                if (db.getRecordCount() > 0) {

                    List<DBRecord> recordList = db.getAllContactsArrayList();

                    firstRecordID = recordList.get(0).getId();
                    for (DBRecord record : recordList) {
                        output = output +
                                record.getId() + " - " +
                                record.getImagePath()   + " - " +
                                record.getDescription() + " - " +
                                record.getPhoneNumber() + " - " +
                                record.getIsFavorite()  + "\n\n";
                    }
                }
                tvOutput.setText(output);
            }
        });

        btDelete = (Button)view.findViewById(R.id.btDeleteAddContactFrag);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getActivity());
                db.deleteRecord(firstRecordID);
            }
        });

        createDirectory();
        ivPhoto = (ImageView)view.findViewById(R.id.ivPhotoAddContactFrag);

        btMakePhoto = (Button)view.findViewById(R.id.btMakePhotoAddContactFrag);
        btMakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT,generateFileUri());
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        });

        btGetLastId = (Button)view.findViewById(R.id.btGetLastIDAddContactFrag);
        btGetLastId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getActivity());
                db.deleteRecord(db.getLastID());
            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        btSelect.callOnClick();
    }

    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "ContactsPhotosFolder");
        if (!directory.exists())
            directory.mkdirs();
    }

    private Uri generateFileUri() {
        File file = null;
        imagePath = directory.getPath() + "/" + "photo_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                + ".jpg";
        file = new File(imagePath);

        return Uri.fromFile(file);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent intent) {
        /*if (resultCode == Activity.RESULT_OK) {
            ivPhoto.setImageURI(Uri.parse(imagePath));

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast toast = Toast.makeText(getActivity(), "Photo didn`t make!", Toast.LENGTH_SHORT);
            toast.show();
        }*/

        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object obj = intent.getExtras().get("data");
                if (obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) obj;

                    storeImage(bitmap);

                    ivPhoto.setImageBitmap(bitmap);
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

            imageData.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);

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
