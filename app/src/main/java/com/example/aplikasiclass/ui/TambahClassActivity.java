package com.example.aplikasiclass.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasiclass.R;
import com.example.aplikasiclass.db.ClassDatabase;
import com.example.aplikasiclass.model.ClassModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahClassActivity extends AppCompatActivity {
    @BindView(R.id.edtNamaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edtNamaWali)
    EditText edtNamaWali;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    ClassDatabase classDatabase;
    String namaKelas, namaWali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_class);

        ButterKnife.bind(this);

        Toolbar tbDetailDokter = findViewById(R.id.toolbar);
        tbDetailDokter.setTitle("Tambah Kelas");
        setSupportActionBar(tbDetailDokter);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        classDatabase = ClassDatabase.createDatabase(this);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {
        saveData();
        clearData();

    }

    private void clearData() {
        edtNamaKelas.setText("");
        edtNamaWali.setText("");
    }

    private void saveData() {
        namaKelas = edtNamaKelas.getText().toString();
        namaWali = edtNamaWali.getText().toString();
        ClassModel classModel = new ClassModel();
        classModel.setNama_kelas(namaKelas);
        classModel.setNama_wali(namaWali);

        //validasi
        if (namaKelas.isEmpty() || namaWali.isEmpty()){
            Toast.makeText(this, "Field tidak bisa kosong", Toast.LENGTH_SHORT).show();
        }   else {
            // Perintah untuk melakukan operasi Insert menggunakan siswaDatabase
            classDatabase.kelasDao().insert(classModel);
            Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}