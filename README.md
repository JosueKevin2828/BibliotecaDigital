# Sistema de Gestion de Biblioteca Digital

## Descripcion general
Aplicacion de escritorio para la gestion de una biblioteca digital. Permite administrar libros, usuarios, prestamos y devoluciones, con generacion de reportes y exportacion de datos. Desarrollada en Java con JavaFX y arquitectura MVC.

## Tecnologias utilizadas
- Java 17
- JavaFX 17
- MySQL 8
- Maven
- JDBC
- Git & GitHub

## Autores
- Josue Kevin - Desarrollador principal

## Requisitos previos
- JDK 17 o superior instalado
- MySQL 8 instalado y configurado
- Maven (opcional, se incluye Maven Wrapper)
- IntelliJ IDEA (recomendado)

## Instalacion y configuracion

### 1. Clonar el repositorio
```bash
git clone https://github.com/JosueKevin2828/BibliotecaDigital.git
cd BibliotecaDigital 
``` 
### 2. Configurar la base de datos
Abrir MySQL Workbench o phpMyAdmin y ejecutar el siguiente script SQL:
```bash
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

CREATE TABLE IF NOT EXISTS libros(
    id_libro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    isbn VARCHAR(20),
    cantidad_disponible INT DEFAULT 1,
    fecha_registro DATE
);

CREATE TABLE IF NOT EXISTS usuarios(
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    fecha_registro DATE,
    tipo VARCHAR(20) DEFAULT 'ESTUDIANTE'
);

CREATE TABLE IF NOT EXISTS prestamos(
    id_prestamo INT PRIMARY KEY AUTO_INCREMENT,
    id_libro INT,
    id_usuario INT,
    fecha_prestamo DATE,
    fecha_devolucion_esperada DATE,
    fecha_devolucion_real DATE,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    multa DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_libro) REFERENCES libros(id_libro),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE IF NOT EXISTS usuarios_login(
    id_login INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(40) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Usuario admin por defecto (password: admin123)
INSERT INTO usuarios(nombre, email, fecha_registro, tipo) VALUES ('Administrador', 'admin@biblioteca.com', CURDATE(), 'ADMIN');
INSERT INTO usuarios_login(username, password_hash, id_usuario) VALUES ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1);
```
### 3. Configurar conexion a BD
En el archivo ConexionBD.java, verificar o modificar las credenciales:
```bash
private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña";
```

### 4. Ejecutar el programa
Desde IntelliJ: Ejecutar la clase MainApp

### Funcionalidades del sistema

### Modulo	|   Funcionalidades
- Login: 	    Autenticacion con SHA-1, creacion de cuentas
- Dashboard:	Estadisticas en tarjetas (libros, usuarios, prestamos)
- Libros:	    CRUD completo, busqueda por titulo, exportar CSV/JSON
- Usuarios:	    CRUD completo, busqueda por email
- Prestamos:	Registrar prestamo, devolucion, multas automaticas

### Estructura del proyecto
```bash
src/main/java/org/example/bibliotecadigital/
├── MainApp.java                 # Clase principal
├── controller/                  # Controladores MVC
│   ├── LoginController.java
│   ├── RegistroController.java
│   ├── DashboardController.java
│   ├── LibrosController.java
│   ├── UsuariosController.java
│   └── PrestamosController.java
├── model/                       # Entidades
│   ├── Libro.java
│   ├── Usuario.java
│   ├── Prestamo.java
│   └── UsuarioLogin.java
├── dao/                         # Acceso a datos
│   ├── ConexionBD.java (Singleton)
│   ├── GenericDAO.java (Generico)
│   ├── LibroDAO.java
│   ├── UsuarioDAO.java
│   ├── PrestamoDAO.java
│   └── UsuarioLoginDAO.java
├── observer/                    # Patron Observer
│   ├── Observador.java
│   ├── Sujeto.java
│   ├── NotificadorMultas.java
│   └── PrestamoObserver.java
├── factory/                     # Patron Factory
│   └── PrestamoFactory.java
└── util/                        # Utilidades
    ├── PasswordUtil.java (SHA-1)
    ├── ExportadorCSV.java
    └── ExportadorJSON.java
```

### Patrones de diseño implementados
- Singleton - ConexionBD (una sola instancia de conexion)
- DAO - Acceso a datos (LibroDAO, UsuarioDAO, PrestamoDAO)
- Factory - PrestamoFactory (creacion de prestamos)
- Observer - NotificadorMultas (alertas de prestamos vencidos)
- Generic Repository - GenericDAO (programacion generica)

