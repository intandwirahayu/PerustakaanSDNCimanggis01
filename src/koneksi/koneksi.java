package koneksi;

import java.sql.*;

/**
 *
 * @author Erryza Nur Alif
 */
public class koneksi {
       //koneksi private
    private static Connection koneksi; 
    //menampilkan koneksi
    public Connection getkoneksi(){ 
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Koneksi");
        }catch(ClassNotFoundException ex){
            System.out.println("Gagal Koneksi " +ex);
        }
        
        String url = "jdbc:mysql://localhost/perpustakaan";
        
        try{
            koneksi = DriverManager.getConnection(url,"root","");
            System.out.println("Berhasil Koneksi Database");
            
        }catch (SQLException ex) {
                System.out.println("Gagal Koneksi Database" +ex);
            }
    return koneksi;
    }  
}
