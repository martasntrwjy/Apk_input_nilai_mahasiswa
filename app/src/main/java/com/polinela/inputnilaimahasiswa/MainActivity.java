package com.polinela.inputnilaimahasiswa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper mydb;
    ArrayList<String> array_list;
    String kelas;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    ListView obj;
    TextView jml;
    Button logout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menampilkan jumlah data
        mydb = new DBHelper(this);
        jml = findViewById(R.id.jml_data);
        jml.setText("Jumlah Data : " + mydb.numberOfRows());

        //Membuat spinner/combo box
        spinner = findViewById(R.id.pilihKelas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kelas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ExtendedFloatingActionButton buttonSimpan = findViewById(R.id.tambah_data1);
        buttonSimpan.setOnClickListener(this);

        //Menampilkan semua data
        array_list = mydb.getAllData();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        obj = findViewById(R.id.listView);
        obj.setAdapter(arrayAdapter);

        logout = (Button) findViewById(R.id.logout);

        Boolean checkSession = mydb.checkSession("ada");
        if (checkSession == false) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updtSession = mydb.upgradeSession("kosong", 1);
                if (updtSession == true) {
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });

        //Menampilkan data berdasarkan kelas
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                kelas = spinner.getSelectedItem().toString();
                if(kelas.equals("Semua"))
                    array_list = mydb.getAllData();
                else
                    array_list = mydb.getSomeData(kelas);
                jml.setText("Jumlah Data : " + array_list.size());
                arrayAdapter.clear();
                arrayAdapter.addAll(array_list);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //Menampilkan rincian nilai
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO Auto-generated method stub
                String data = array_list.get(arg2);
                String id_To_Search = data.substring(0, data.indexOf(" "));
                Bundle dataBundle = new Bundle();
                dataBundle.putString("npm", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayNilai.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    //Ketika klik menu kembali
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tambah_data1) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("npm", "");
            Intent intent = new Intent(getApplicationContext(), DisplayNilai.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
        }
    }
}