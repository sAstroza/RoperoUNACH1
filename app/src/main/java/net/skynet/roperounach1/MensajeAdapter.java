package net.skynet.roperounach1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {

    private List<Mensaje> mensajeList;

    public MensajeAdapter(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_item, parent, false);
        return new MensajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        Mensaje mensaje = mensajeList.get(position);
        holder.bind(mensaje);
    }

    @Override
    public int getItemCount() {
        return mensajeList.size();
    }

    public static class MensajeViewHolder extends RecyclerView.ViewHolder {

        private TextView contenidoTextView;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);
            contenidoTextView = itemView.findViewById(R.id.contenidoTextView);
        }

        public void bind(Mensaje mensaje) {
            contenidoTextView.setText(mensaje.getContenido());
        }
    }
}
