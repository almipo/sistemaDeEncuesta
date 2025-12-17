package data.conectionBd;

import data.DAOs.DAOException;

import java.sql.*;


public final class Conexion {

     private static final String DB_JDBC_DRIVER = "org.h2.Driver";
     private static final String URL = "jdbc:h2:tcp://localhost/C:/Users/ALAN/IdeaProjects/Encuesta/bd/Encuesta";
     private static final String USUARIO = "sa";
     private static final String CONTRASEÑA = "";


     static {
         try {
             Class.forName(DB_JDBC_DRIVER);
         } catch (ClassNotFoundException e) {
             System.out.println("Error al cargar el driver JDBC");
             e.printStackTrace();
         }
     }


     public static Connection getConexion() {
         Connection con = null;
         try {
             con = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            // System.out.println("Conexión establecida");
             return con;

         } catch (SQLException e) {
             throw new DAOException("no se pudo conectar a la base de datos",e);
         }


     }
//crea base de datos y tablas si no existen
     public static void crearTablas() {
         String sql = """
        CREATE TABLE rolUsuario (
            id INT AUTO_INCREMENT PRIMARY KEY,
            descripcion VARCHAR(30)
        );
        
        CREATE TABLE usuario (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(30),
            apellido VARCHAR(30),
            dni INT,
            idRol INT,
            CONSTRAINT FKIdRol FOREIGN KEY (idRol) REFERENCES rolUsuario(id)
        );
        
        CREATE TABLE encuesta (
            id INT AUTO_INCREMENT PRIMARY KEY,
            fechaCreacion DATE,
            idAdministrador INT,
            CONSTRAINT FKIdAdministrador FOREIGN KEY (idAdministrador) REFERENCES usuario(id)
        );
        
        CREATE TABLE pregunta (
            id INT AUTO_INCREMENT PRIMARY KEY,
            descripcion VARCHAR(254),
            idEncuesta INT,
            CONSTRAINT FKIdEncuesta FOREIGN KEY (idEncuesta) REFERENCES encuesta(id)
        );
        
        CREATE TABLE respuestasPosibles (
            id INT AUTO_INCREMENT PRIMARY KEY,
            valor INT,
            descripcion VARCHAR(30),
            idPregunta INT,
            CONSTRAINT FKIdPregunta FOREIGN KEY (idPregunta) REFERENCES pregunta(id)
        );
        
        CREATE TABLE respuestaUsuarios (
            id INT AUTO_INCREMENT PRIMARY KEY,
            fechaRealizacion DATE,
            idUsuario INT,
            idEncuesta INT,
            idPregunta INT,
            idRespuesta INT,
            CONSTRAINT FK_RespuestaUsuarios_Usuario FOREIGN KEY (idUsuario) REFERENCES usuario(id),
            CONSTRAINT FK_RespuestaUsuarios_Encuesta FOREIGN KEY (idEncuesta) REFERENCES encuesta(id),
            CONSTRAINT FK_RespuestaUsuarios_Pregunta FOREIGN KEY (idPregunta) REFERENCES pregunta(id),
            CONSTRAINT FK_RespuestaUsuarios_Respuesta FOREIGN KEY (idRespuesta) REFERENCES respuestasPosibles(id)
        );
        """;

         try (Connection con = getConexion();
              Statement stmt = con.createStatement()) {
             stmt.execute(sql);
             System.out.println("Tablas creadas correctamente.");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    public static boolean existenTablas(Connection con) {
        try (ResultSet rs = con.getMetaData().getTables(null, null, "ROLUSUARIO", null)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void inicializarBaseDeDatos() {
        try (Connection con = getConexion()) {
            if (!existenTablas(con)) {
                System.out.println("Las tablas no existen. Creando...");
                crearTablas();
            } else {
                System.out.println("Las tablas ya existen.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public static void cerrarConexion(Connection con) {
         if (con != null) {
             try {
                 con.close();
                 //System.out.println("conexion cerrada");
             } catch (SQLException e) {
                 System.out.println("Error al cerrar la conexión");
                 e.printStackTrace();
             }
         }

     }
 }


