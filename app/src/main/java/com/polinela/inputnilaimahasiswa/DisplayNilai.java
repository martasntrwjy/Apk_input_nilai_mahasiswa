package com.polinela.inputnilaimahasiswa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayNilai extends AppCompatActivity {
    private DBHelper mydb;
    EditText npm, nama, kelas, nilai_tugas, nilai_kuis, nilai_uts, nilai_uas, nilai_akhir;
    Button bSimpan;
    Double nTugas=0.0, nKuis=0.0, nUts=0.0, nUas=0.0, nAkhir=0.0;
    String id_To_Update = "";

    public String[] dataKelas = {
            "MI 5A",
            "MI 5B",
            "MI 5C",

    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_nilai);

        npm = findViewById(R.id.npm);
        nama = findViewById(R.id.nama);
        kelas = findViewById(R.id.kelas);
        nilai_tugas = findViewById(R.id.nilai_tugas);
        nilai_kuis = findViewById(R.id.nilai_kuis);
        nilai_uts = findViewById(R.id.nilai_uts);
        nilai_uas = findViewById(R.id.nilai_uas);
        nilai_akhir = findViewById(R.id.nilai_akhir);
        bSimpan = findViewById(R.id.buttonSimpan);
        bSimpan.setEnabled(false);
        getSupportActionBar().setTitle("Input Data");

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("npm");
            id_To_Update = Value;
            if (!Value.equals("")) {
                getSupportActionBar().setTitle("Tampil Data");
                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();
                String val_npm = rs.getString(rs.getColumnIndex("npm"));
                String val_nama = rs.getString(rs.getColumnIndex("nama"));
                String val_kelas = rs.getString(rs.getColumnIndex("kelas"));
                String val_nilai_tugas = rs.getString(rs.getColumnIndex("nilai_tugas"));
                String val_nilai_kuis = rs.getString(rs.getColumnIndex("nilai_kuis"));
                String val_nilai_uts = rs.getString(rs.getColumnIndex("nilai_uts"));
                String val_nilai_uas = rs.getString(rs.getColumnIndex("nilai_uas"));
                String val_nilai_akhir = rs.getString(rs.getColumnIndex("nilai_akhir"));
                if (!rs.isClosed()) {
                    rs.close();
                }

                bSimpan.setVisibility(View.INVISIBLE);
                bSimpan.setEnabled(true);

                npm.setText(val_npm);
                npm.setFocusable(false);
                npm.setClickable(false);

                nama.setText(val_nama);
                nama.setFocusable(false);
                nama.setClickable(false);

                kelas.setText(val_kelas);
                kelas.setFocusable(false);
                kelas.setClickable(false);

                nilai_tugas.setText(val_nilai_tugas);
                nilai_tugas.setFocusable(false);
                nilai_tugas.setClickable(false);

                nilai_kuis.setText(val_nilai_kuis);
                nilai_kuis.setFocusable(false);
                nilai_kuis.setClickable(false);

                nilai_uts.setText(val_nilai_uts);
                nilai_uts.setFocusable(false);
                nilai_uts.setClickable(false);

                nilai_uas.setText(val_nilai_uas);
                nilai_uas.setFocusable(false);
                nilai_uas.setClickable(false);

                nilai_akhir.setText(val_nilai_akhir);
                nilai_akhir.setFocusable(false);
                nilai_akhir.setClickable(false);
            }
        }

        npm.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(npm.getText().toString().equals(""))
                    npm.setError("Data tidak boleh kosong");
                else
                    npm.setError(null);
                cekDataKosong();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nama.getText().toString().equals(""))
                    nama.setError("Data tidak boleh kosong");
                else
                    nama.setError(null);
                cekDataKosong();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        kelas.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(kelas.getText().toString().equals(""))
                    kelas.setError("Data tidak boleh kosong");
                else
                    kelas.setError(null);
                cekDataKosong();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        nilai_tugas.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nilai_tugas.getText().toString().equals(""))
                    nilai_tugas.setError("Data tidak boleh kosong");
                else
                    nilai_tugas.setError(null);
                hitung();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        nilai_kuis.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nilai_kuis.getText().toString().equals(""))
                    nilai_kuis.setError("Data tidak boleh kosong");
                else
                    nilai_kuis.setError(null);
                hitung();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        nilai_uts.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nilai_uts.getText().toString().equals(""))
                    nilai_uts.setError("Data tidak boleh kosong");
                else
                    nilai_uts.setError(null);
                hitung();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        nilai_uas.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nilai_uas.getText().toString().equals(""))
                    nilai_uas.setError("Data tidak boleh kosong");
                else
                    nilai_uas.setError(null);
                hitung();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void pilihKelas (View v) {
        kelas = findViewById(R.id.kelas);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Kelas");
        builder.setItems(dataKelas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                kelas.setText(dataKelas[item]);
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!id_To_Update.equals(""))
            getMenuInflater().inflate(R.menu.display_nilai, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.edit_data:
                getSupportActionBar().setTitle("Edit Data");
                bSimpan.setVisibility(View.VISIBLE);
                bSimpan.setEnabled(true);

                npm.setEnabled(false);

                nama.setEnabled(true);
                nama.setFocusableInTouchMode(true);
                nama.setClickable(true);

                kelas.setEnabled(true);
                kelas.setClickable(true);

                nilai_tugas.setEnabled(true);
                nilai_tugas.setFocusableInTouchMode(true);
                nilai_tugas.setClickable(true);

                nilai_kuis.setEnabled(true);
                nilai_kuis.setFocusableInTouchMode(true);
                nilai_kuis.setClickable(true);

                nilai_uts.setEnabled(true);
                nilai_uts.setFocusableInTouchMode(true);
                nilai_uts.setClickable(true);

                nilai_uas.setEnabled(true);
                nilai_uas.setFocusableInTouchMode(true);
                nilai_uas.setClickable(true);

                return true;

            case R.id.hapus_data:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Hapus Data")
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteData(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Apakah anda yakin?");
                d.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SetTextI18n")
    public void hitung() {
        if(!nilai_tugas.getText().toString().equals(""))
            nTugas = Double.parseDouble(nilai_tugas.getText().toString());
        else
            nTugas = 0.0;
        if(!nilai_kuis.getText().toString().equals(""))
            nKuis = Double.parseDouble(nilai_kuis.getText().toString());
        else
            nKuis = 0.0;
        if(!nilai_uts.getText().toString().equals(""))
            nUts = Double.parseDouble(nilai_uts.getText().toString());
        else
            nUts = 0.0;
        if(!nilai_uas.getText().toString().equals(""))
            nUas = Double.parseDouble(nilai_uas.getText().toString());
        else
            nUas = 0.0;
        nAkhir = (2*nTugas+2*nKuis+3*nUts+3*nUas)/10;
        nilai_akhir = findViewById(R.id.nilai_akhir);
        nilai_akhir.setText(nAkhir.toString());
        cekDataKosong();
    }

    public void cekDataKosong() {
        if(npm.getError()==null && nama.getError()==null && kelas.getError()==null
                && nilai_tugas.getError()==null && nilai_kuis.getError()==null
                && nilai_uts.getError()==null && nilai_uas.getError()==null)
            bSimpan.setEnabled(true);
        else
            bSimpan.setEnabled(false);
    }

    public void simpan(View v) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String Value = extras.getString("npm");
            if(!Value.equals("")){
                if(mydb.updateData(id_To_Update, nama.getText().toString(),
                        kelas.getText().toString(), Double.parseDouble(nilai_tugas.getText().toString()),
                        Double.parseDouble(nilai_kuis.getText().toString()), Double.parseDouble(nilai_uts.getText().toString()),
                        Double.parseDouble(nilai_uas.getText().toString()), Double.parseDouble(nilai_akhir.getText().toString()))){
                    Toast.makeText(getApplicationContext(), "Data Berhasil Diupdate",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Edit Data Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertData(npm.getText().toString(), nama.getText().toString(),
                        kelas.getText().toString(), Double.parseDouble(nilai_tugas.getText().toString()),
                        Double.parseDouble(nilai_kuis.getText().toString()), Double.parseDouble(nilai_uts.getText().toString()),
                        Double.parseDouble(nilai_uas.getText().toString()), Double.parseDouble(nilai_akhir.getText().toString()))){
                    Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Input Data Gagal",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
