package com.example.app_phone_store_manager_du_an_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.app_phone_store_manager_du_an_1.R;
import com.example.app_phone_store_manager_du_an_1.model.Hang;
import com.example.app_phone_store_manager_du_an_1.utilities.ItemHangClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class ChonHangAdapter extends RecyclerView.Adapter<ChonHangAdapter.ViewHolder> {
    private List<Hang> list;
    private ItemHangClick itemHangClick;
    private Context context;
    private int checkedPositon = -1;
    private TextDrawable textDrawable;

    public void setCheckedPositon(int checkedPositon) {
        this.checkedPositon = checkedPositon;
        notifyDataSetChanged();
    }

    public void setItemHangClick(ItemHangClick itemHangClick) {
        this.itemHangClick = itemHangClick;
    }

    public ChonHangAdapter(List<Hang> list) {
        this.list = list;
    }

    //RecyclerView gọi phương thức này bất cứ khi nào cần tạo ViewHolder mới. Phương thức này tạo và khởi động ViewHolder
    // cùng với View đã liên kết, nhưng không điền vào nội dung của thành phần hiển thị
    // – ViewHolder chưa liên kết với dữ liệu cụ thể.
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_chon_hang, parent, false);
        return new ViewHolder(view);
    }

    //RecyclerView gọi phương thức này để liên kết ViewHolder với dữ liệu. Phương thức này tìm nạp dữ liệu thích hợp
    // và sử dụng dữ liệu đó để điền vào bố cục của ngăn chứa thành phần hiển thị.
    // Ví dụ: nếu RecyclerView hiển thị một danh sách tên, phương thức này có thể tìm tên thích hợp trong danh sách và
    // điền vào tiện ích TextView của ngăn chứa thành phần hiển thị.
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Hang hang = list.get(position);

        if (hang == null) {
            return;
        }
        if (hang.getHinhAnh() == null) {
            String tenHang = hang.getTenHang();
            textDrawable = TextDrawable.builder().beginConfig().width(48).height(48).endConfig().buildRect(tenHang.substring(0, 1).toUpperCase(), getRandomColor());
            holder.imgHang.setImageDrawable(textDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hang.getHinhAnh(), 0, hang.getHinhAnh().length);
            holder.imgHang.setImageBitmap(bitmap);
        }

        holder.tvHang.setText(hang.getTenHang());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedPositon = position;
                notifyDataSetChanged();
                itemHangClick.ItemClick(hang);
            }
        });

        if (checkedPositon == position) {
            holder.itemView.setBackgroundResource(R.color.selected);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }
    }

    //RecyclerView gọi phương thức này để lấy kích thước của tập dữ liệu. Ví dụ: trong một ứng dụng sổ địa chỉ,
    // đây có thể là tổng số địa chỉ. RecyclerView sử dụng tính năng này để xác định thời điểm không thể
    // hiển thị thêm mục nào.
    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
    //Xác định ngăn chứa thành phần hiển thị bằng cách mở rộng RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHang;
        private TextView tvHang;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgHang = itemView.findViewById(R.id.imgChonHang);
            tvHang = itemView.findViewById(R.id.tvTenHangChon);
        }
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
