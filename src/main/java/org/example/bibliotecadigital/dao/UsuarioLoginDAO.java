package org.example.bibliotecadigital.dao;

import org.example.bibliotecadigital.model.UsuarioLogin;
import org.example.bibliotecadigital.util.PasswordUtil;
import java.sql.*;
import java.util.Optional;

public class UsuarioLoginDAO{

    private Connection conexion;

    public UsuarioLoginDAO(){
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    // Autenticar usuario
    public Optional<UsuarioLogin> autenticar(String username, String password){
        String sql = "SELECT * FROM usuarios_login WHERE username = ? AND password_hash = ?";
        String hash = PasswordUtil.encriptarSHA1(password);

        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                UsuarioLogin user = new UsuarioLogin();
                user.setIdLogin(rs.getInt("id_login"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setIdUsuario(rs.getInt("id_usuario"));
                return Optional.of(user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Registrar nuevo usuario login
    public boolean registrar(String username, String password, int idUsuario){
        // 2. Inserta en BD
        String sql = "INSERT INTO usuarios_login(username, password_hash, id_usuario) VALUES(?, ?, ?)";
        // 1. Encripta contraseña
        String hash = PasswordUtil.encriptarSHA1(password);

        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, hash);
            ps.setInt(3, idUsuario);
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si username existe
    public boolean existsByUsername(String username){
        String sql = "SELECT 1 FROM usuarios_login WHERE username = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}