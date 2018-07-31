package com.example.quang.timnhatro.view.addHouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang.timnhatro.R;
import com.example.quang.timnhatro.presenter.addHouse.PresenterLogicAddHouse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.BitSet;

public class AddHouseActivity extends AppCompatActivity implements View.OnClickListener, ViewAddHouse {

    private EditText edtTitle, edtAddress, edtInfo, edtPhone, edtAcreage, edtPrice;
    private ImageView imChooseImage;
    private Button btnAdd;
    private PresenterLogicAddHouse presenterLogicAddHouse;
    private String imageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        findID();
        initViews();
    }

    private void findID() {
        edtTitle = findViewById(R.id.edtTitle);
        edtAddress = findViewById(R.id.edtAddress);
        edtInfo = findViewById(R.id.edtDesc);
        edtPhone = findViewById(R.id.edtPhone);
        edtAcreage = findViewById(R.id.edtAcreage);
        edtPrice = findViewById(R.id.edtPrice);
        imChooseImage = findViewById(R.id.imChoosePhoto);
        btnAdd = findViewById(R.id.btnAddHouse);
    }

    private void initViews() {

        presenterLogicAddHouse = new PresenterLogicAddHouse(this);

        btnAdd.setOnClickListener(this);
        imChooseImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imChoosePhoto:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                break;
            case R.id.btnAddHouse:
                if (imageBase64 != null)
                    presenterLogicAddHouse.updateImage(imageBase64,this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imChooseImage.setImageURI(selectedImage);

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageBase64 = imageToString(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encode = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encode;

    }

    @Override
    public void successUpImage(String url) {
        SharedPreferences pre=this.getSharedPreferences("data_timnhatro", MODE_PRIVATE);
        String data = pre.getString("user","");
        String[] arr = data.split("-");
        presenterLogicAddHouse.addHouse(edtTitle.getText().toString(),edtAddress.getText().toString(),url
            ,edtInfo.getText().toString(), edtPhone.getText().toString(),Float.parseFloat(edtAcreage.getText().toString())
            ,Float.parseFloat(edtPrice.getText().toString()),Integer.parseInt(arr[0]),this);
    }

    @Override
    public void failUpImage() {

    }

    @Override
    public void successAddHouse() {
        Toast.makeText(this, "thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failAddHouse() {
        Toast.makeText(this, "thất bại", Toast.LENGTH_SHORT).show();
    }
}
