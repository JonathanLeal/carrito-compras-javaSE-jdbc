package javaapplication3;

public class Carrito {
    private int id;
    private Producto producto_id;
    private int cantidad;
    private float total;
    private int user;

    public Carrito() {
    }

    public Carrito(int id, Producto producto_id, int cantidad, float total, int user) {
        this.id = id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.total = total;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Producto producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
