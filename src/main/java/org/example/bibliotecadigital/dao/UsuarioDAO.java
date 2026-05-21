package org.example.bibliotecadigital.dao;

import org.example.bibliotecadigital.model.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements GenericDAO<Usuario, Integer>{

    private Connection conexion;

    public UsuarioDAO(){
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public Usuario save(Usuario usuario){
        String sql = "INSERT INTO usuarios(nombre, email, telefono, direccion, fecha_registro, tipo) VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getDireccion());
            ps.setDate(5, Date.valueOf(usuario.getFechaRegistro()));
            ps.setString(6, usuario.getTipo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                usuario.setIdUsuario(rs.getInt(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Integer id){
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                usuario.setTipo(rs.getString("tipo"));
                return Optional.of(usuario);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try(Statement stmt = conexion.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                usuario.setTipo(rs.getString("tipo"));
                usuarios.add(usuario);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario update(Usuario usuario){
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, direccion = ?, tipo = ? WHERE id_usuario = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getTipo());
            ps.setInt(6, usuario.getIdUsuario());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void delete(Integer id){
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Integer id){
        String sql = "SELECT 1 FROM usuarios WHERE id_usuario = ?";
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
        String sql = "SELECT COUNT(*) FROM usuarios";
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

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email){
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                usuario.setTipo(rs.getString("tipo"));
                return Optional.of(usuario);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}