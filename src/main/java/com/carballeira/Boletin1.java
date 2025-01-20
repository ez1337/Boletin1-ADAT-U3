package com.carballeira;

import java.sql.*;
import java.util.Scanner;

public class Boletin1 {
    final static String query1 = "SELECT dept_no, dnombre FROM departamentos";
    final String query2 = "UPDATE departamentos SET dept_no = ?, dnombre = ? WHERE dept_no = ?";

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc0 = new Scanner(System.in);
        int i = 0;
        boolean validInput;

        do {
            System.out.println("Selecciona una de las opciones: ");
            System.out.println("1. Muestra todos los departamentos // 2. Modifica un departamento // 3. Salir");

            validInput = sc0.hasNextInt();

            if(validInput){
                i = sc0.nextInt();
                sc0.nextLine();

                switch (i) {
                    case 1:
                        queryExe(query1);
                        break;
                    case 2:
                        modifyEntry();
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        break;
                }
            } else{
                System.out.println("Introduce un número válido.");
                sc0.next();
            }
        } while (i != 3);

    }

    public static void modifyEntry(){
        Scanner sc = new Scanner(System.in);
        Scanner sn = new Scanner(System.in);

        System.out.println("Escribe el numero del departamento: ");
        int depNumOld = sn.nextInt();

        System.out.println("Escribe el nuevo numero del departamento: ");
        int depNumNew = sn.nextInt();

        System.out.println("Escribe el nuevo nombre del departamento: ");
        String depNameNew = sc.nextLine();

        System.out.println("Elige el tipo de funcionalidad: 1) Statement / 2) Prepared Statement / 3) Transacción");
        int op = sn.nextInt();

        switch(op){
            case 1:
                updateDepartmentStmt(depNumOld,depNumNew,depNameNew);
                break;
            case 2:
                updateDepartmentPS(depNumOld,depNumNew,depNameNew);
                break;
            case 3:
                updateDepartmentTS(depNumOld,depNumNew,depNameNew);
                break;
        }

        sc.close();
        sn.close();
    }

    // CRUD methods

    public static void queryExe(String query) throws ClassNotFoundException{

        // Creación del controlador JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Control de errores para el driver
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.err.println("Driver no encontrado" + e.getMessage());
        }

        // Control de errores al conectar con BBDD y ejecutar query
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/ejerciciosboletin",
                    "root",
                    "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                int dept_num = rs.getInt("dept_no");
                String dept_name = rs.getString("dnombre");
                System.out.println(dept_num + "\t" + dept_name);
            }

        } catch(SQLException e){
            System.err.println("Error en la base de datos" + e.getMessage());
        } finally{
            if(conn != null){
                try{
                    conn.close();
                    rs.close();
                    stmt.close();
                } catch(SQLException e){
                    System.err.println("Error al cerrar la conexión" + e.getMessage());
                }
            }
        }
    }

    public static void updateDepartmentStmt(int deptNoExistente, int nuevoDeptNo, String nuevoDeptNombre) {
        final String URL = "jdbc:mysql://localhost:3307/ejerciciosboletin";
        final String USER = "root";
        final String PASSWORD = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Crear el objeto Statement
            stmt = conn.createStatement();

            // Definir la consulta SQL con los valores ingresados por el usuario
            String sql = "UPDATE departamentos SET dept_no = " + nuevoDeptNo + ", dnombre = '" + nuevoDeptNombre + "' WHERE dept_no = " + deptNoExistente;

            // Ejecutar la consulta y obtener el número de filas modificadas
            int filasModificadas = stmt.executeUpdate(sql);

            // Mostrar el número de filas modificadas
            System.out.println("Número de filas modificadas: " + filasModificadas);

        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el objeto Statement
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void updateDepartmentPS(int deptNoExistente, int nuevoDeptNo, String nuevoDeptNombre){
        final String URL = "jdbc:mysql://localhost:3307/ejerciciosboletin";
        final String USER = "root";
        final String PASSWORD = "";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Definir la consulta SQL con parámetros
            String sql = "UPDATE departamentos SET dept_no = ?, dnombre = ? WHERE dept_no = ?";

            // Crear el objeto PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setInt(1, nuevoDeptNo);
            pstmt.setString(2, nuevoDeptNombre);
            pstmt.setInt(3, deptNoExistente);

            // Ejecutar la consulta y obtener el número de filas modificadas
            int filasModificadas = pstmt.executeUpdate();

            // Mostrar el número de filas modificadas
            System.out.println("Número de filas modificadas: " + filasModificadas);

        } catch (ClassNotFoundException e) { System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) { System.err.println("Error en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el objeto PreparedStatement
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void updateDepartmentTS(int deptNoExistente, int nuevoDeptNo, String nuevoDeptNombre){
        final String URL = "jdbc:mysql://localhost:3307/ejerciciosboletin";
        final String USER = "root";
        final String PASSWORD = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Desactivar el auto-commit para utilizar transacciones
            conn.setAutoCommit(false);

            // Definir la consulta SQL con parámetros
            String sql = "UPDATE departamentos SET dept_no = ?, dnombre = ? WHERE dept_no = ?";

            // Crear el objeto PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setInt(1, nuevoDeptNo);
            pstmt.setString(2, nuevoDeptNombre);
            pstmt.setInt(3, deptNoExistente);

            // Ejecutar la consulta y obtener el número de filas modificadas
            int filasModificadas = pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            // Mostrar el número de filas modificadas
            System.out.println("Número de filas modificadas: " + filasModificadas);

        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            try {
                // Si hay un error, revertir la transacción
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error al revertir la transacción: " + rollbackEx.getMessage());
            } System.err.println("Error en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el objeto PreparedStatement
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
