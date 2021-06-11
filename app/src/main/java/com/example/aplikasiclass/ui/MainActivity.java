package com.example.aplikasiclass.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aplikasiclass.R;
import com.example.aplikasiclass.db.ClassDatabase;
import com.example.aplikasiclass.model.ClassModel;
import com.example.aplikasiclass.ui.adapter.KelasAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvKelas)
    RecyclerView rvKelas;

    private ClassDatabase classDatabase;
    private List<ClassModel> classModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Membuat object database
        classDatabase = ClassDatabase.createDatabase(this);

        // Membuat membuat object List
        classModelList = new ArrayList<>();

        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahClassActivity.class));
            }
        });
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        classModelList.clear();

        // Mengambil data dari Sqlite
        getData();

        // Mensetting dan proses menampilkan data ke RecyclerView
        rvKelas.setLayoutManager(new GridLayoutManager(this, 1));
        rvKelas.setAdapter(new KelasAdapter(this, classModelList));
    }

    private void getData() {
        // Operasi mengambil data dari database Sqlite
        classModelList = classDatabase.kelasDao().select();
    }
}