package org.example.bibliotecadigital.dao;

import org.example.bibliotecadigital.model.Libro;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO implements GenericDAO<Libro, Integer>{

    private Connection conexion;

    public LibroDAO(){
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public Libro save(Libro libro){
        String sql = "INSERT INTO libros(titulo, autor, categoria, isbn, cantidad_disponible, fecha_registro) VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setString(4, libro.getIsbn());
            ps.setInt(5, libro.getCantidadDisponible());
            ps.setDate(6, Date.valueOf(libro.getFechaRegistro()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                libro.setIdLibro(rs.getInt(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return libro;
    }

    @Override
    public Optional<Libro> findById(Integer id){
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                libro.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                return Optional.of(libro);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Libro> findAll(){
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try(Statement stmt = conexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                libro.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                libros.add(libro);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return libros;
    }

    @Override
    public Libro update(Libro libro){
        String sql = "UPDATE libros SET titulo = ?, autor = ?, categoria = ?, isbn = ?, cantidad_disponible = ? WHERE id_libro = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setString(4, libro.getIsbn());
            ps.setInt(5, libro.getCantidadDisponible());
            ps.setInt(6, libro.getIdLibro());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return libro;
    }

    @Override
    public void delete(Integer id){
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Integer id){
        String sql = "SELECT 1 FROM libros WHERE id_libro = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long count(){
        String sql = "SELECT COUNT(*) FROM libros";
        try(Statement stmt = conexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getLong(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    // Metodo extra para buscar por titulo
    public List<Libro> buscarPorTitulo(String titulo){
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                libro.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                libros.add(libro);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return libros;
    }
}