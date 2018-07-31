package com.example.quang.timnhatro;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.timnhatro.adapters.HouseAdapter;
import com.example.quang.timnhatro.constants.Constants;
import com.example.quang.timnhatro.object.City;
import com.example.quang.timnhatro.object.District;
import com.example.quang.timnhatro.object.ItemHouse;
import com.example.quang.timnhatro.object.Ward;
import com.example.quang.timnhatro.utils.DatabaseUtils;
import com.example.quang.timnhatro.view.addHouse.AddHouseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Dialog dialogSearch;
    private ListView lvHouses;
    private HouseAdapter adapter;
    private ArrayList<ItemHouse> arrHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();
        initViews();
        loadData();
        initDialogSearch();
  //      dialogSearch.show();
    }


    private void findID() {
        toolbar = findViewById(R.id.toolbarMain);
        lvHouses = findViewById(R.id.lvHouse);

    }

    @SuppressLint("MissingPermission")
    private void initViews(){
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void initDialogSearch() {
        dialogSearch = new Dialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSearch.setContentView(R.layout.dialog_filter);
        dialogSearch.setCancelable(false);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        final ArrayList<City> arrCity = databaseUtils.getCity();
        final ArrayList<District> arrDistrict = databaseUtils.getDistrict();
        final ArrayList<Ward> arrWard = databaseUtils.getWard();

        final ArrayList<String> arrCityString = new ArrayList<>();
        final ArrayList<String> arrDistrictString = new ArrayList<>();
        final ArrayList<String> arrWardString = new ArrayList<>();
        arrDistrictString.add("Quận/Huyện");
        arrWardString.add("Xã/Phường");
        for (int i=0; i<arrCity.size();i++){
            arrCityString.add(arrCity.get(i).getName());
        }

        ImageView imDismiss = dialogSearch.findViewById(R.id.btnDimiss);
        final Spinner spinnerCity = dialogSearch.findViewById(R.id.spinnerCity);
        final Spinner spinnerDistrict = dialogSearch.findViewById(R.id.spinnerDistrict);
        final Spinner spinnerWard = dialogSearch.findViewById(R.id.spinnerChildOfDistrict);
        final TextView tvPriceFrom = dialogSearch.findViewById(R.id.tvPriceFrom);
        final TextView tvPriceTo = dialogSearch.findViewById(R.id.tvPriceTo);
        final TextView tvAcreageFrom = dialogSearch.findViewById(R.id.tvAcreageFrom);
        final TextView tvAcreageTo = dialogSearch.findViewById(R.id.tvAcreageTo);
        final SeekBar seekBarPriceFrom = dialogSearch.findViewById(R.id.seekBarPriceFrom);
        final SeekBar seekBarPriceTo = dialogSearch.findViewById(R.id.seekBarPriceTo);
        final SeekBar seekBarAcreageFrom = dialogSearch.findViewById(R.id.seekBarAcreageFrom);
        final SeekBar seekBarAcreageTo = dialogSearch.findViewById(R.id.seekBarAcreageTo);
        Button btnSearch = dialogSearch.findViewById(R.id.btnSearch);
        final CheckBox cbPrice = dialogSearch.findViewById(R.id.cbPrice);
        final CheckBox cbAcreage = dialogSearch.findViewById(R.id.cbAcreage);
        final LinearLayout lnPrice = dialogSearch.findViewById(R.id.lnPrice);
        final LinearLayout lnAcreage = dialogSearch.findViewById(R.id.lnAcreage);

        // spinner city
        ArrayAdapter<String> adapterCity=new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrCityString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerCity.setAdapter(adapterCity);


        // spinner district
        final ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrDistrictString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterDistrict.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerDistrict.setAdapter(adapterDistrict);


        // spinner ward
        final ArrayAdapter<String> adapterWard = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrWardString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterWard.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerWard.setAdapter(adapterWard);




        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = arrCityString.get(i);
                arrDistrictString.clear();
                arrDistrictString.add("Quận/Huyện");
                for (int index=0 ;index<arrDistrict.size() ;index++){
                    if (arrDistrict.get(index).getMaCity().equalsIgnoreCase(arrCity.get(i).getMa())){
                        arrDistrictString.add(arrDistrict.get(index).getName());
                    }
                }
                adapterDistrict.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                arrWardString.clear();
                arrWardString.add("Xã/Phường");
                String maDistrict = null;
                for (District d : arrDistrict){
                    if (d.getName().equalsIgnoreCase(arrDistrictString.get(i))){
                        maDistrict = d.getMa();
                        break;
                    }
                }
                for (int index=0 ;index<arrWard.size() ;index++){
                    if (i > 0 && arrWard.get(index).getMaDistrict().equalsIgnoreCase(maDistrict)){
                        arrWardString.add(arrWard.get(index).getName());
                    }
                }
                adapterWard.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSearch.dismiss();
            }
        });


        seekBarPriceFrom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 1000){
                    tvPriceFrom.setText(i + " Trăm");
                }else {
                    double r = (double)i/(double)1000;
                    tvPriceFrom.setText((double)Math.round(r*10)/10 + " Triệu");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarPriceTo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvPriceTo.setText(i + " Triệu");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAcreageFrom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAcreageFrom.setText(i + " " + Html.fromHtml("m<sup>2</sup>"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAcreageTo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAcreageTo.setText(i + " " + Html.fromHtml("m<sup>2</sup>"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    lnPrice.setVisibility(View.VISIBLE);
                }else {
                    lnPrice.setVisibility(View.GONE);
                }
            }
        });

        cbAcreage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    lnAcreage.setVisibility(View.VISIBLE);
                }else {
                    lnAcreage.setVisibility(View.GONE);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, seekBarPriceFrom.getProgress()+"", Toast.LENGTH_SHORT).show();
                String city = spinnerCity.getSelectedItem().toString();
                String district = "";
                String ward = "";
                if (!spinnerDistrict.getSelectedItem().equals("Quận/Huyện")){
                    district = spinnerDistrict.getSelectedItem().toString();
                }

                if (!spinnerDistrict.getSelectedItem().equals("Xã/Phường")){
                    ward = spinnerWard.getSelectedItem().toString();
                }

                ArrayList<ItemHouse> arrNewHouse = new ArrayList<>();
                for (int i=0; i<arrHouse.size(); i++){
                    if (arrHouse.get(i).getAddress().contains(city)){
                        if (arrHouse.get(i).getAddress().contains(district)){
                            if (arrHouse.get(i).getAddress().contains(ward)){
                                arrNewHouse.add(arrHouse.get(i));
                            }else {
                                arrNewHouse.add(arrHouse.get(i));
                            }
                        }else {
                            arrNewHouse.add(arrHouse.get(i));
                        }
                    }
                }

                double r = (double)seekBarPriceFrom.getProgress()/(double)1000;
                double temp = (double)Math.round(r*10)/10;
                ArrayList<ItemHouse> arrNewHouse2 = new ArrayList<>();
                if (cbPrice.isChecked()){
                    for (int i=0; i<arrNewHouse.size(); i++){
                        if (arrNewHouse.get(i).getPrice()>= temp && arrNewHouse.get(i).getPrice() <= seekBarPriceTo.getProgress()){
                            arrNewHouse2.add(arrNewHouse.get(i));
                        }
                    }
                }else {
                    arrNewHouse2.addAll(arrNewHouse);
                }

                ArrayList<ItemHouse> arrNewHouse3 = new ArrayList<>();
                if (cbAcreage.isChecked()){
                    for (int i=0; i<arrNewHouse2.size();i++){
                        if (arrNewHouse2.get(i).getAcreage() >= seekBarAcreageFrom.getProgress() && arrNewHouse2.get(i).getAcreage() <= seekBarAcreageTo.getProgress()){
                            arrNewHouse3.add(arrNewHouse2.get(i));
                        }
                    }
                }else {
                    arrNewHouse3.addAll(arrNewHouse2);
                }

                adapter = new HouseAdapter(MainActivity.this,R.layout.item_house,arrNewHouse3);
                lvHouses.setAdapter(adapter);
                dialogSearch.dismiss();
            }
        });


    }

    private void loadData(){
        arrHouse = new ArrayList<>();
        getData();
    }

    private void getData(){
        arrHouse.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.GET_HOUSES, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        int id = obj.getInt("IDHOUSE");
                        String title = obj.getString("TITLE");
                        String address = obj.getString("ADDRESS");
                        String image = obj.getString("IMAGE");
                        String desc = obj.getString("DESC");
                        String contact = obj.getString("CONTACT");
                        float acreage = (float) obj.getDouble("ACREAGE");
                        float price = (float) obj.getDouble("PRICE");
                        int iduser = obj.getInt("IDUSER");
                        ItemHouse itemHouse = new ItemHouse(id,title,address,image,desc,contact,acreage,price,iduser);
                        arrHouse.add(itemHouse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new HouseAdapter(MainActivity.this,R.layout.item_house,arrHouse);
                lvHouses.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search){
            dialogSearch.show();
            return true;
        }else if (id == R.id.action_add_house){
            startActivity(new Intent(MainActivity.this,AddHouseActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
