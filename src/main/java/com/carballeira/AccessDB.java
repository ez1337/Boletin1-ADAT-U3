package com.carballeira;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccessDB {
    final String URL = "jdbc:mysql://localhost:3307/ejerciciosboletin";
    final String USER = "root";
    final String PASSWORD = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    DepartmentModel dept;

    public AccessDB(DepartmentModel dept) {
        this.dept = dept;
    }

    /**
     * Método para establecer la conexión con la base de datos
     */
    public void connectToDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException e){
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para insertar un nuevo departamento. Recibe un objeto departamento por parámetro.
     * @param dept
     */
    public void insertDept(DepartmentModel dept){
        if(dept.validDept()){
            try{
                conn.setAutoCommit(false);

                String sql = "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, dept.getDeptNum());
                pstmt.setString(2, dept.getDeptName());
                pstmt.setString(3,dept.getLocation());

                int filasModificadas = pstmt.executeUpdate();

                conn.commit();

                // Mostrar el número de filas modificadas
                System.out.println("Número de filas modificadas: " + filasModificadas);

            }catch(SQLException e){
                System.err.println("Error SQL: " + e.getMessage());
            }finally {
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

    public void insertNewDept(int deptNum, String deptName, String deptLoc){
        try{
            conn.setAutoCommit(false);

            String sql = "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, deptNum);
            pstmt.setString(2, deptName.toUpperCase());
            pstmt.setString(3,deptLoc.toUpperCase());

            int filasModificadas = pstmt.executeUpdate();

            conn.commit();

            // Mostrar el número de filas modificadas
            System.out.println("Número de filas modificadas: " + filasModificadas);

        }catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }finally {
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

    /**
     * Método que devuelve un ArrayList de todos los departamentos.
     * @return ArrayList<DepartmentModel>
     */
    public ArrayList<DepartmentModel> getAllDept(){
        ArrayList<DepartmentModel> dept_list = new ArrayList<>();
        try{
            conn.setAutoCommit(false);
            String sql = "SELECT dept_no, dnombre, loc FROM departamentos";

            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            conn.commit();

            while(rs.next()){
                int dept_num = rs.getInt("dept_no");
                String dept_name = rs.getString("dnombre");
                String dept_location = rs.getString("loc");
                DepartmentModel dept = new DepartmentModel(dept_num, dept_name, dept_location);
                dept_list.add(dept);
            }
        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                                "SQLState: "+ e.getSQLState() + "\n" +
                                "Mensaje: "+ e.getMessage());
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return dept_list;
    }

    /**
     * Método que devuelve un objeto departamento especificando su número.
     * @param deptNum
     * @return
     */
    public DepartmentModel getDept(int deptNum){
        int dept_num = 0;
        String dept_name = "";
        String dept_loc = "";
        try{
            conn.setAutoCommit(false);
            String sql = "SELECT dept_no, dnombre, loc FROM departamentos WHERE dept_no = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptNum);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                dept_num = rs.getInt("dept_no");
                dept_name = rs.getString("dnombre");
                dept_loc = rs.getString("loc");
            }
        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return new DepartmentModel(dept_num,dept_name,dept_loc);
    }

    /**
     * Método que elimina de la BBDD un departamennto por su número.
     * @param deptNum
     */
    public void deleteDept(int deptNum){
        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM departamentos WHERE dept_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptNum);
            int filasModificadas = pstmt.executeUpdate();

            conn.commit();
            System.out.println("Departamento eliminado exitosamente \n"+"Filas afectadas: "+filasModificadas);

        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true); // Volver a activar el auto-commit
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Método que actualiza la información de un departamento a partir de su número y localización
     * @param deptNum
     * @param deptLocation
     */
    public void updateDept(int deptNum, String deptLocation){
        try{
            conn.setAutoCommit(false);
            String sql = "{call actualizaDept(?,?)}";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptNum);
            pstmt.setString(2, deptLocation);
            int filasModificadas = pstmt.executeUpdate();

            conn.commit();
            System.out.println("Departamento actualizado exitosamente \n"+"Filas afectadas: "+filasModificadas);

        }catch(SQLException e){
            System.err.println("Código de error: " + e.getErrorCode() +"\n"+
                    "SQLState: "+ e.getSQLState() + "\n" +
                    "Mensaje: "+ e.getMessage());
        }finally {
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
