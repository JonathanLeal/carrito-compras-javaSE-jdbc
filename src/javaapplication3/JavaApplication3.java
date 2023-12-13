/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jonathan
 */
public class JavaApplication3 {
    public static void main(String[] args) throws SQLException, Exception {
        Conexion conexion = new Conexion();
        ArrayList<Producto> lista = new ArrayList();
        Producto agencia = new Producto();
        Carrito carrito = new Carrito();
        int opcion;
        do {            
            opcion = menu();
            switch(opcion){
                case 1:
                    verProductos(lista);
                    break;
                case 2:
                    insertarCarrito(carrito);
                    break;
                case 3:
                    verCarrito();
                    break;
                case 4:
                    realizarVenta();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Saliendo....");
                    break;
            }
        } while (opcion <= 4);
    }
    
    private static int menu(){
        int opc = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("1. Ver productos\n");
        sb.append("2. Guardar en el carrito\n");
        sb.append("3. Ver carrito\n");
        sb.append("4. Realizar venta\n");
        sb.append("5. Saliendo...");
        opc = Integer.parseInt(JOptionPane.showInputDialog(null, sb));
        return opc;
    }
    
    private static void realizarVenta() {
        Conexion conexion = new Conexion();
        Carrito carrito = new Carrito();
        Facturacion facturacion = new Facturacion();
        ArrayList<Carrito> carritos = new ArrayList<>();

        PreparedStatement insert = null;
        PreparedStatement select = null;
        PreparedStatement delete = null;

        try {
            int user = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID de usuario"));
            if (user > 0) {
                carrito.setUser(user);
                select = conexion.con.prepareStatement("SELECT id, producto_id, cantidad FROM carrito WHERE user_id = ?");
                select.setInt(1, carrito.getUser());
                ResultSet rs = select.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No tiene productos en su carrito");
                    return;
                }

                while (rs.next()) {
                    Carrito carritoItem = new Carrito(); // Crear un nuevo objeto Carrito por cada iteración
                    Producto producto = new Producto();
                    carritoItem.setId(rs.getInt("id"));
                    producto.setId(rs.getInt("producto_id"));
                    producto.setExistencia(rs.getInt("cantidad"));
                    carritoItem.setProducto_id(producto);
                    carritos.add(carritoItem);
                }

                int idFacturacion = 0;
                String nombres = JOptionPane.showInputDialog("Ingrese su nombre y apellido");
                String correo = JOptionPane.showInputDialog("Ingrese su correo");

                insert = conexion.con.prepareStatement("INSERT INTO facturacion(id, nombres, correo, carrito_id, user_id) VALUES (?, ?, ?, ?, ?)");

                for (Carrito carritoItem : carritos) {
                    idFacturacion = idFacturacion + 1;
                    insert.setInt(1, idFacturacion);
                    insert.setString(2, nombres);
                    insert.setString(3, correo);
                    insert.setInt(4, carritoItem.getId()); // Se asocia el ID del carrito como carrito_id en facturación
                    insert.setInt(5, user);
                    insert.executeUpdate();
                }
                
                delete = conexion.con.prepareStatement("DELETE FROM carrito where id = ?");
                
                for (Carrito carrito1 : carritos) {
                    delete.setInt(1, carrito1.getId());
                    delete.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Venta realizada con éxito y eliminado del carrito");
            } else {
                JOptionPane.showMessageDialog(null, "El número de usuario no es válido");
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al realizar la venta: " + e.getMessage());
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
                if (select != null) {
                    select.close();
                }
                conexion.con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar las conexiones: " + e.getMessage());
            }
        }
    }
    
    private static void verCarrito() {
        Conexion conexion = new Conexion();
        StringBuilder sb = new StringBuilder();
        ArrayList<Carrito> carritos = new ArrayList<>();

        try {
            PreparedStatement pst = conexion.con.prepareStatement("SELECT c.id, p.nombre_producto, c.cantidad, c.user_id FROM carrito c " +
                                                                  "INNER JOIN productos p ON c.producto_id = p.id " +
                                                                  "WHERE c.user_id = ?;");
            int user = Integer.parseInt(JOptionPane.showInputDialog("Ingresa tu número de usuario"));

            if (user > 0) {
                pst.setInt(1, user);
                ResultSet rs = pst.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No tiene productos en su carrito");
                    return;
                }

                while (rs.next()) {
                    Carrito carritoItem = new Carrito();
                    Producto producto = new Producto();
                    carritoItem.setId(rs.getInt("c.id"));
                    producto.setNombre_producto(rs.getString("p.nombre_producto"));
                    carritoItem.setProducto_id(producto);
                    carritoItem.setCantidad(rs.getInt("c.cantidad"));
                    carritoItem.setUser(rs.getInt("c.user_id"));
                    carritos.add(carritoItem);
                }

                sb.append("ID \t Producto \t Cantidad \t Usuario\n");
                for (Carrito carritoItem : carritos) {
                    sb.append(carritoItem.getId() + " \t " + carritoItem.getProducto_id().getNombre_producto() + " \t " + carritoItem.getCantidad() + " \t " + carritoItem.getUser() + "\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "El número de usuario no es válido.");
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al listar el carrito: " + e.getMessage());
        }
    }
    
    private static int encontrarIdProductoPorNombre(String nombre) throws Exception {
        int id = 1;
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("SELECT id FROM productos WHERE nombre_producto=?");
        pst.setString(1, nombre);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
    
    private static void insertarCarrito(Carrito carrito) throws Exception{
    Conexion conexion = new Conexion();
    PreparedStatement pst = conexion.con.prepareStatement("insert into carrito(id,producto_id,cantidad,user_id) values(?,?,?,?)");
    int seguir = 0;
    try {
        do {                
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el id del carrito"));
            int user = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el id de tu usuario"));
            String producto = JOptionPane.showInputDialog("Ingrese el nombre del producto");
            int encontrado = encontrarIdProductoPorNombre(producto);
            if (encontrado > 0) {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("digita la cantidad"));
                Carrito car = new Carrito();
                Producto pro = new Producto();
                pro.setId(encontrado);
                car.setId(id);
                car.setCantidad(cantidad);
                car.setUser(user);
                
                pst.setInt(1, car.getId());
                pst.setInt(2, pro.getId());
                pst.setInt(3, car.getCantidad());
                pst.setInt(4, car.getUser());
                
                int exito = pst.executeUpdate();
                if (exito > 0) {  
                    JOptionPane.showMessageDialog(null, "Guardado al carrito con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE A PODIDO REGISTRAR AL CARRITO");
                }
                seguir = Integer.parseInt(JOptionPane.showInputDialog("1. Para salir 0. Para seguir")); 
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el producto");
            }
        } while (seguir == 0);
    } catch (Exception e) {
        System.out.println("error al insertar al carrito: "+e.getMessage());
    }
}
    
    private static void verProductos(ArrayList<Producto> lista) throws Exception{
        Conexion conexion = new Conexion();
        StringBuilder sb = new StringBuilder();
        PreparedStatement pst = conexion.con.prepareStatement("select * from productos");
        ResultSet rs = pst.executeQuery();
        try {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setExistencia(rs.getInt("existencia"));
                producto.setPrecio(rs.getDouble("precio_1"));
                producto.setEstilo(rs.getString("estilo"));
                producto.setSku(rs.getString("sku"));
                lista.add(producto);
            }
            
            if (lista.size() == 0) {
                JOptionPane.showMessageDialog(null, "No hay productos en la lista");
            } else {
                sb.append("ID \t Producto \t existencia \t precio \t estilo \t SKU \n");
                for (Producto producto : lista) {
                    sb.append(producto.getId() +" \t "+producto.getNombre_producto() +" \t "+producto.getExistencia() +" \t "+producto.getPrecio() 
                            +" \t "+producto.getEstilo() +" \t "+producto.getSku() +"\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (Exception e) {
            System.out.println("errro en listar productos: "+e.getMessage());
        }
    }
}
