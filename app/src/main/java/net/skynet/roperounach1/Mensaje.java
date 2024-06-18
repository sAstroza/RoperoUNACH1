package net.skynet.roperounach1;

public class Mensaje {
    public String emisorId;
    public String contenido;
    public long timestamp; // Puedes agregar esto si lo necesitas

    public Mensaje() {
        // Constructor vacío requerido para Firebase
    }

    public Mensaje(String emisorId, String contenido) {
        this.emisorId = emisorId;
        this.contenido = contenido;
        // Puedes inicializar el timestamp aquí si lo necesitas
    }

    // Getters y setters si es necesario
    public String getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(String emisorId) {
        this.emisorId = emisorId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
