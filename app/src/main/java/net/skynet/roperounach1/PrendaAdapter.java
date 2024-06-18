package net.skynet.roperounach1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PrendaAdapter extends RecyclerView.Adapter<PrendaAdapter.PrendaViewHolder> {
    private List<Prenda> prendaList;

    public PrendaAdapter(List<Prenda> prendaList) {
        this.prendaList = prendaList;
    }

    @NonNull
    @Override
    public PrendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prenda_item, parent, false);
        return new PrendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrendaViewHolder holder, int position) {
        Prenda prenda = prendaList.get(position);
        holder.tituloTextView.setText(prenda.getTitulo());
        holder.descripcionTextView.setText(prenda.getDescripcion());
        Picasso.get().load(prenda.getImagenUrl()).into(holder.prendaImageView);
    }

    @Override
    public int getItemCount() {
        return prendaList.size();
    }

    public static class PrendaViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloTextView;
        public TextView descripcionTextView;
        public ImageView prendaImageView;

        public PrendaViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            prendaImageView = itemView.findViewById(R.id.prendaImageView);
        }
    }
}
