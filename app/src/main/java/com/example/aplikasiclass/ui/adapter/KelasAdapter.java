package com.example.aplikasiclass.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.aplikasiclass.R;
import com.example.aplikasiclass.db.ClassDatabase;
import com.example.aplikasiclass.db.Constant;
import com.example.aplikasiclass.model.ClassModel;
import com.example.aplikasiclass.ui.UpdateClassActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    private final Context context;
    private final List<ClassModel> classModelList;
    private ClassDatabase classDatabase;
    private Bundle bundle;

    public KelasAdapter(Context context, List<ClassModel> classModelList) {
        this.context = context;
        this.classModelList = classModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_kelas, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Memindahkan data di dalam list dengan index position ke dalam KelasModel
        final ClassModel classModel = classModelList.get(position);

        // Menampilkan data ke layar
        holder.tvNamaWali.setText(classModel.getNama_wali());
        holder.tvNamaKelas.setText(classModel.getNama_kelas());

        ColorGenerator generator = ColorGenerator.MATERIAL;

        // generate random color
        int color = generator.getRandomColor();
        holder.cvKelas.setCardBackgroundColor(color);

        ;

        // Membuat onclick icon overflow
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Buat object database
                classDatabase = classDatabase.createDatabase(context);

                // Membuat object popumemu
                PopupMenu popupMenu = new PopupMenu(context, view);

                // Inflate menu ke dalam popupmenu
                popupMenu.inflate(R.menu.popup_menu);

                // Menampilkan menu
                popupMenu.show();

                // Onclick pada salah satu menu pada popupmenu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Are you sure delete " + classModel.getNama_kelas() + " ?");
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        // Melakukan operasi delete data
                                        classDatabase.kelasDao().delete(classModel);

                                        // Menghapus data yang telash di hapus pada List
                                        classModelList.remove(position);

                                        // Memberitahu bahwa data sudah hilang
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(0, classModelList.size());

                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                break;

                            case R.id.edit:

                                // Membuat object bundle
                                bundle = new Bundle();

                                // Mengisi bundle dengan data
                                bundle.putInt(Constant.KEY_ID_KELAS, classModel.getId_kelas());
                                bundle.putString(Constant.KEY_NAMA_KELAS, classModel.getNama_kelas());
                                bundle.putString(Constant.KEY_NAMA_WALI, classModel.getNama_wali());

                                // Berpindah halaman dengan membawa data
                                context.startActivity(new Intent(context, UpdateClassActivity.class).putExtras(bundle));
                                break;
                        }
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return classModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamaKelas)
        TextView tvNamaKelas;
        @BindView(R.id.tvNamaWali)
        TextView tvNamaWali;
        @BindView(R.id.cvKelas)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}