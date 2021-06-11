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
import com.example.aplikasiclass.db.Constant;
import com.example.aplikasiclass.model.ClassModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateClassActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edtNamaWali)
    EditText edtNamaWali;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    Bundle bundle;
    ClassDatabase classDatabase;
    int id_kelas;
    String nama_kelas, nama_wali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_class);

        ButterKnife.bind(this);

        Toolbar tbDetailDokter = findViewById(R.id.toolbar);
        tbDetailDokter.setTitle("Update Data");
        setSupportActionBar(tbDetailDokter);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Menangkap data dari activity sebelumnya
        bundle = getIntent().getExtras();

        // Buat object database
        classDatabase = ClassDatabase.createDatabase(this);

        // Menampilkan data sebelumnya ke layar
        showData();
    }

    private void showData() {
        // mengambil data di dalam bundle
        id_kelas = bundle.getInt(Constant.KEY_ID_KELAS);
        nama_kelas = bundle.getString(Constant.KEY_NAMA_KELAS);
        nama_wali = bundle.getString(Constant.KEY_NAMA_WALI);

        // Menampilkan ke layar
        edtNamaKelas.setText(nama_kelas);
        edtNamaWali.setText(nama_wali);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {

        saveData();

    }

    private void saveData() {

        nama_kelas = edtNamaKelas.getText().toString();
        nama_wali = edtNamaWali.getText().toString();
        // Membuat object kelasmodel
        ClassModel classModel = new ClassModel();

        // Memasukkan data ke kelasmodel
        classModel.setId_kelas(id_kelas);
        classModel.setNama_kelas(nama_kelas);
        classModel.setNama_wali(nama_wali);


        if (nama_kelas.isEmpty() || nama_wali.isEmpty()){
            Toast.makeText(this, "Field tidak bisa kosong", Toast.LENGTH_SHORT).show();
        } else {

            classDatabase.kelasDao().update(classModel);
            Toast.makeText(this, "Berhasil di update", Toast.LENGTH_SHORT).show();
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