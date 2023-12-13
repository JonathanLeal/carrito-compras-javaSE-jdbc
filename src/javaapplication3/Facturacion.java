package javaapplication3;

public class Facturacion {
    private int id;
    private String nombre;
    private String correo;
    private Carrito carrito;
    private float sub_total;
    private float gran_total;
    private int user;

    public Facturacion() {
    }

    public Facturacion(int id, String nombre, String correo, Carrito carrito, float sub_total, float gran_total, int user) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.carrito = carrito;
        this.sub_total = sub_total;
        this.gran_total = gran_total;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public float getGran_total() {
        return gran_total;
    }

    public void setGran_total(float gran_total) {
        this.gran_total = gran_total;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
