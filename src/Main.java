
import data.DAOs.AdministradorDAO;
import data.DAOs.RolUsuarioDAO;
import data.Model.Administrador;
import data.Model.Rol;
import data.DAOs.RolUsuarioDAO;
import data.conectionBd.Conexion;
import presentation.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import  java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    public static void main(String[] args) {
        //para inicializar la base de datos si se borra
       // Conexion.inicializarBaseDeDatos();
//----------------------------------------------------------------






        SwingUtilities.invokeLater(() -> {
            Principal principal = new Principal();
            //Principal.setVisible(true);
        });

    }
}
























/*
//crea el rol administrador
        Rol rolA = new Rol(1,"Administrador");
        RolUsuarioDAO rolUsuarioDAO = new RolUsuarioDAO();
      rolUsuarioDAO.crear(rolA);

//crea el rol promotor
        Rol rolP = new Rol(2,"Promotor");
        RolUsuarioDAO rolUsuarioDAO1 = new RolUsuarioDAO();
        rolUsuarioDAO1.crear(rolP);

//crear administradores

        Administrador ad = new Administrador("Juan", "Perez", 12345678,rol);
        AdministradorDAO administradorDAO = new AdministradorDAO();
        administradorDAO.crear(ad);

        Administrador ad1 = new Administrador("Jose", "Pedfrez", 222678, rol);
        AdministradorDAO administradorDAO1 = new AdministradorDAO();
        administradorDAO1.crear(ad1);



        */
        /*
//buscar todos los roles
        Rol rol =new Rol();
RolUsuarioDAO rolUsuarioDAO3 = new RolUsuarioDAO();
rolUsuarioDAO3.buscarTodosLosRoles();

/* crear roles



//busca por nombre de rol
      RolUsuarioDAO rolA = new RolUsuarioDAO();
        Rol rol = new Rol();
        rol= rolA.buscarPorNombre("Promotor");
        System.out.println(rol);


//buscar por nombre rol administrador
        RolUsuarioDAO rolA = new RolUsuarioDAO();
        Rol rol = new Rol();
        rol= rolA.buscarPorNombre("Administrador");










*/

