package org.example.bibliotecadigital.dao;

import org.example.bibliotecadigital.model.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementacion para Prestamo, Patron Programacion Generica
public class PrestamoDAO implements GenericDAO<Prestamo, Integer>{

    private Connection conexion;

    // 1. Obtener conexion
    public PrestamoDAO(){
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public Prestamo save(Prestamo prestamo){
        String sql = "INSERT INTO prestamos(id_libro, id_usuario, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado, multa) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdUsuario());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
            if(prestamo.getFechaDevolucionReal() != null){
                ps.setDate(5, Date.valueOf(prestamo.getFechaDevolucionReal()));
            }else{
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, prestamo.getEstado());
            ps.setDouble(7, prestamo.getMulta());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                prestamo.setIdPrestamo(rs.getInt(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return prestamo;
    }

    @Override
    public Optional<Prestamo> findById(Integer id){
        String sql = "SELECT * FROM prestamos WHERE id_prestamo = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setIdLibro(rs.getInt("id_libro"));
                prestamo.setIdUsuario(rs.getInt("id_usuario"));
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                prestamo.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada").toLocalDate());
                if(rs.getDate("fecha_devolucion_real") != null){
                    prestamo.setFechaDevolucionReal(rs.getDate("fecha_devolucion_real").toLocalDate());
                }
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMulta(rs.getDouble("multa"));
                return Optional.of(prestamo);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Prestamo> findAll(){
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try(Statement stmt = conexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setIdLibro(rs.getInt("id_libro"));
                prestamo.setIdUsuario(rs.getInt("id_usuario"));
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                prestamo.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada").toLocalDate());
                if(rs.getDate("fecha_devolucion_real") != null){
                    prestamo.setFechaDevolucionReal(rs.getDate("fecha_devolucion_real").toLocalDate());
                }
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMulta(rs.getDouble("multa"));
                prestamos.add(prestamo);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return prestamos;
    }

    @Override
    public Prestamo update(Prestamo prestamo){
        String sql = "UPDATE prestamos SET id_libro = ?, id_usuario = ?, fecha_prestamo = ?, fecha_devolucion_esperada = ?, fecha_devolucion_real = ?, estado = ?, multa = ? WHERE id_prestamo = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdUsuario());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
            if(prestamo.getFechaDevolucionReal() != null){
                ps.setDate(5, Date.valueOf(prestamo.getFechaDevolucionReal()));
            }else{
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, prestamo.getEstado());
            ps.setDouble(7, prestamo.getMulta());
            ps.setInt(8, prestamo.getIdPrestamo());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return prestamo;
    }

    @Override
    public void delete(Integer id){
        String sql = "DELETE FROM prestamos WHERE id_prestamo = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Integer id){
        String sql = "SELECT 1 FROM prestamos WHERE id_prestamo = ?";
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
        String sql = "SELECT COUNT(*) FROM prestamos";
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

    // Buscar prestamos activos de un usuario
    public List<Prestamo> findPrestamosActivosPorUsuario(int idUsuario){
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE id_usuario = ? AND estado = 'ACTIVO'";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setIdLibro(rs.getInt("id_libro"));
                prestamo.setIdUsuario(rs.getInt("id_usuario"));
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                prestamo.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada").toLocalDate());
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMulta(rs.getDouble("multa"));
                prestamos.add(prestamo);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return prestamos;
    }
}