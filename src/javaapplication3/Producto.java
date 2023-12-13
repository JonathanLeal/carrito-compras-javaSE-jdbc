package javaapplication3;

public class Producto {
    private int id;
    private String nombre_producto;
    private int existencia;
    private double precio;
    private String estilo;
    private String sku;

    public Producto() {
        this.existencia = 0;
    }

    public Producto(int id, String nombre_producto, int existencia, double precio, String estilo, String sku) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.existencia = existencia;
        this.precio = precio;
        this.estilo = estilo;
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
