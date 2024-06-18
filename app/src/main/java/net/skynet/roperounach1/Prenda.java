package net.skynet.roperounach1;

public class Prenda {
    public String usuarioId;
    public String titulo;
    public String descripcion;
    public String imagenUrl;

    // Constructor vacío necesario para las llamadas a DataSnapshot.getValue(Prenda.class)
    public Prenda() {
    }

    // Constructor con parámetros para facilitar la creación de objetos Prenda
    public Prenda(String usuarioId, String titulo, String descripcion, String imagenUrl) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
    }

    // Getters y setters (opcional, dependiendo de tu implementación)
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
