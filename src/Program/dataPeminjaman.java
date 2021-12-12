/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;
import static Program.prosesPeminjaman.tglHariIni;
import com.placeholder.PlaceHolder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.Calendar;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author USER
 */
public class dataPeminjaman extends javax.swing.JFrame {
     private Connection conn = new koneksi().getkoneksi();
     private String kdPeminjaman, kdDataPinjam = "", kdBuku, kdAnggota, kdPetugas, waktuPinjam,getKondisiCetak;
     private DefaultTableModel tabmode;
     public String tglPinjam, tglBatasPinjam, tglHariIni;
     SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
     Statement stm;
     ResultSet rs;
     public String tgl;
    /**
     * Creates new form dataPeminjaman
     */
    public dataPeminjaman() {
        initComponents();
        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "masukkan kata kunci");
        datatable();
        tampilJumlah();
        tampilPeminjam();
        tanggalHariIni();
        
        kode_kategori.hide();
        tabel_datapeminjaman.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tabel_datapeminjaman.getTableHeader().setOpaque(false);
        tabel_datapeminjaman.getTableHeader().setBackground(new Color(206, 18, 18));
        tabel_datapeminjaman.getTableHeader().setForeground(new Color(255, 255, 255));
    }
    
        private void resetInput(){
        //resetKodePeminjaman();
        labelKodePeminjaman.setText("XXXXX");
        fieldKodeBuku.setText("");
        kode_kategori.setText("");
        fieldWaktuPeminjaman.setText("");
        jTanggalBatasPinjam.setDate(null);
        }
        
 
     private void tampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_buku) FROM peminjaman_buku ";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlahBukuDipinjam.setText(rs.getString("COUNT(kode_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     
     private void tanggalHariIni(){
        try{
            Date ys = new Date(); // membuat oject ys dari class Date
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); //membuat object s dari class SimpleDateFormat dengan format (dd-MM-yyyy)yaitu (tanggal-bulan-tahun).
            tglHariIni = s.format(ys);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     
    private void tampilPeminjam(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(DISTINCT kode_peminjaman) FROM peminjaman_buku";
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()){
                System.out.println(rs.getString("COUNT(DISTINCT kode_peminjaman)"));
                jumlahPeminjaman.setText(rs.getString("COUNT(DISTINCT kode_peminjaman)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
        protected void datatable(){
        try{
            Object [] Baris = {"Kode Pinjam","Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tabel_datapeminjaman.setModel(tabmode);

            String sql = "SELECT * FROM peminjaman_buku ";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_peminjaman");
                String b = hasil.getString("kode_petugas");
                String c = hasil.getString("kode_buku"); 
                String d = hasil.getString("kode_anggota");
                String e = hasil.getString("waktu_pinjam");
                String f = hasil.getString("tgl_peminjaman");
                String g = hasil.getString("tgl_batas_peminjaman");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }
        
        private int cekstok(){
         int stok=0;
         String qtotal= "SELECT jumlah_awal FROM kategori_buku WHERE kode_kategori ='"+kode_kategori.getText()+"'";
         
         try{
             Statement s = conn.createStatement();
             rs = s.executeQuery(qtotal);
             
             while(rs.next()){
                 stok = Integer.parseInt(rs.getString(1));
             }
         } catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
         return stok;
    }
        
    private int jumlah_pinjam(){
         int jml=0;
         String qtotal= "SELECT jumlah_dipinjam FROM kategori_buku WHERE kode_kategori ='"+kode_kategori.getText()+"'";
         
         try{
             Statement s = conn.createStatement();
             rs = s.executeQuery(qtotal);
             
             while(rs.next()){
                 jml = Integer.parseInt(rs.getString(1));
             }
         } catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
         return jml;
    }
    
    private int sisabuku(){
         int sisa=0;
         String qtotal= "SELECT jumlah_awal FROM kategori_buku WHERE kode_kategori ='"+kode_kategori.getText()+"'";
         
         try{
             Statement s = conn.createStatement();
             rs = s.executeQuery(qtotal);
             
             while(rs.next()){
                 sisa = Integer.parseInt(rs.getString(1));
             }
         } catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
         return sisa;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBeranda = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        keluarAkun = new javax.swing.JLabel();
        btncari = new javax.swing.JLabel();
        btnCetakData = new javax.swing.JLabel();
        urutkanData = new javax.swing.JComboBox<>();
        btnHapusData = new javax.swing.JLabel();
        btnUbahData = new javax.swing.JLabel();
        jumlahPeminjaman = new javax.swing.JLabel();
        jumlahBukuDipinjam = new javax.swing.JLabel();
        fieldKodeBuku = new javax.swing.JTextField();
        fieldWaktuPeminjaman = new javax.swing.JTextField();
        labelKodePeminjaman = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_datapeminjaman = new javax.swing.JTable();
        jTanggalBatasPinjam = new com.toedter.calendar.JDateChooser();
        kode_kategori = new javax.swing.JTextField();
        fieldCariData = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuBeranda.png"))); // NOI18N
        menuBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBerandaMouseClicked(evt);
            }
        });
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 249, -1, -1));

        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataAnggota.png"))); // NOI18N
        menuDataAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataAnggotaMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 307, 130, 20));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataPetugas.png"))); // NOI18N
        menuDataPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataPetugasMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 365, -1, -1));

        menuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuPeminjaman.png"))); // NOI18N
        menuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(menuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 438, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 511, -1, -1));

        keluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        keluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(keluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 703, -1, -1));

        btncari.setBackground(new java.awt.Color(255, 255, 255));
        btncari.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btncari.setForeground(new java.awt.Color(255, 255, 255));
        btncari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btncari.setText("cari");
        btncari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncari.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                btncariAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        btncari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncariMouseClicked(evt);
            }
        });
        getContentPane().add(btncari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 110, 50, 40));

        btnCetakData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnCetakData.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCetakData.setText("cetak data");
        btnCetakData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetakData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCetakDataMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCetakDataMouseEntered(evt);
            }
        });
        getContentPane().add(btnCetakData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1222, 495, 100, 30));

        urutkanData.setBackground(new java.awt.Color(249, 249, 249));
        urutkanData.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        urutkanData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kondisi ==", "Seluruh Data", "Data Hari Ini", "Kondisi Pencarian" }));
        getContentPane().add(urutkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1046, 495, 160, 30));

        btnHapusData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnHapusData.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHapusData.setText("hapus data");
        btnHapusData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapusData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnHapusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1067, 683, 143, 40));

        btnUbahData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnUbahData.setForeground(new java.awt.Color(47, 53, 66));
        btnUbahData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUbahData.setText("ubah data");
        btnUbahData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUbahData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUbahDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnUbahData, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 683, 143, 40));

        jumlahPeminjaman.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahPeminjaman.setForeground(new java.awt.Color(206, 18, 18));
        jumlahPeminjaman.setText("5");
        getContentPane().add(jumlahPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 110, 110, 60));

        jumlahBukuDipinjam.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahBukuDipinjam.setForeground(new java.awt.Color(206, 18, 18));
        jumlahBukuDipinjam.setText("5");
        getContentPane().add(jumlahBukuDipinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 110, 100, 60));

        fieldKodeBuku.setBackground(new java.awt.Color(238, 235, 221));
        fieldKodeBuku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldKodeBuku.setForeground(new java.awt.Color(47, 53, 66));
        fieldKodeBuku.setBorder(null);
        fieldKodeBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldKodeBukuActionPerformed(evt);
            }
        });
        getContentPane().add(fieldKodeBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 683, 160, 40));

        fieldWaktuPeminjaman.setBackground(new java.awt.Color(238, 235, 221));
        fieldWaktuPeminjaman.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldWaktuPeminjaman.setForeground(new java.awt.Color(47, 53, 66));
        fieldWaktuPeminjaman.setBorder(null);
        getContentPane().add(fieldWaktuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 683, 160, 40));

        labelKodePeminjaman.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        labelKodePeminjaman.setText("XXXXX");
        getContentPane().add(labelKodePeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 606, 180, 25));

        tabel_datapeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_datapeminjaman.setPreferredSize(new java.awt.Dimension(300, 1000));
        tabel_datapeminjaman.setRequestFocusEnabled(false);
        tabel_datapeminjaman.setRowHeight(30);
        tabel_datapeminjaman.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tabel_datapeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_datapeminjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_datapeminjaman);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 1030, 240));

        jTanggalBatasPinjam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalBatasPinjamPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalBatasPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 683, 160, 40));

        kode_kategori.setBorder(null);
        kode_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_kategoriActionPerformed(evt);
            }
        });
        getContentPane().add(kode_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 610, 170, -1));

        fieldCariData.setBackground(new java.awt.Color(238, 235, 221));
        fieldCariData.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldCariData.setForeground(new java.awt.Color(47, 53, 66));
        fieldCariData.setBorder(null);
        fieldCariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyTyped(evt);
            }
        });
        getContentPane().add(fieldCariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 112, 230, 38));

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/dataPeminjaman.png"))); // NOI18N
        background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backgroundMouseClicked(evt);
            }
        });
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBerandaMouseClicked
        // TODO add your handling code here:
        beranda b = new beranda();
        b.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuBerandaMouseClicked

    private void menuDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataBukuMouseClicked
        // TODO add your handling code here:
        dataBuku db = new dataBuku();
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataBukuMouseClicked

    private void menuDataAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataAnggotaMouseClicked
        // TODO add your handling code here:
        detailAnggota da = new detailAnggota();
        da.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataAnggotaMouseClicked

    private void menuDataPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataPetugasMouseClicked
        // TODO add your handling code here:
        dataPetugas dp = new dataPetugas();
        dp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataPetugasMouseClicked

    private void keluarAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarAkunMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_keluarAkunMouseClicked

    private void btncariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseClicked
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pinjam", "Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tabel_datapeminjaman.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE kode_peminjaman like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or waktu_pinjam like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_batas_peminjaman like '%" + fieldCariData.getText() + "%' ";
                      
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_peminjaman");
                String b = hasil.getString("kode_petugas");
                String c = hasil.getString("kode_buku");
                String d = hasil.getString("kode_anggota");
                String e = hasil.getString("waktu_pinjam");
                String f = hasil.getString("tgl_peminjaman"); 
                String g = hasil.getString("tgl_batas_peminjaman");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_btncariMouseClicked

    private void fieldKodeBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldKodeBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldKodeBukuActionPerformed

    private void tabel_datapeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_datapeminjamanMouseClicked
        // TODO add your handling code here:
            try{
            int table = tabel_datapeminjaman.getSelectedRow();
            kdPeminjaman = tabel_datapeminjaman.getValueAt(table, 0).toString();
            kdBuku = tabel_datapeminjaman.getValueAt(table, 2).toString();

            Statement s = conn.createStatement();
            String sql = "SELECT * FROM peminjaman_buku WHERE kode_peminjaman = '"+kdPeminjaman+"' AND kode_buku ='"+kdBuku+"'";
            ResultSet rs = s.executeQuery(sql);
            System.out.println("kode peminjamannya = " + kdPeminjaman);
            System.out.println("kode bukunya = " + kdBuku);
            if(rs.next()){
                labelKodePeminjaman.setText(rs.getString("kode_peminjaman"));
                fieldKodeBuku.setText(rs.getString("kode_buku"));
                fieldWaktuPeminjaman.setText(rs.getString("waktu_pinjam"));
                jTanggalBatasPinjam.setDate(rs.getDate("tgl_batas_peminjaman"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tabel_datapeminjamanMouseClicked

    private void jTanggalBatasPinjamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalBatasPinjamPropertyChange
        // TODO add your handling code here:
                if (jTanggalBatasPinjam.getDate()!=null){
            tgl = format.format(jTanggalBatasPinjam.getDate());
        }
    }//GEN-LAST:event_jTanggalBatasPinjamPropertyChange

    private void btnUbahDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUbahDataMouseClicked
        // TODO add your handling code here:
        kdBuku = fieldKodeBuku.getText();
        try{
//            int table = tabel_datapeminjaman.getSelectedRow();
//            tglHariIni = tabel_datapeminjaman.getValueAt(table, 6).toString();
            
            if(!kdDataPinjam.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(fieldKodeBuku.getText().equals("") || fieldWaktuPeminjaman.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Tentukan kurun waktu peminjaman dahulu!");
            }else if(jTanggalBatasPinjam.getDate().equals("") ){ 
                JOptionPane.showMessageDialog(null, "Tentukan tanggal peminjaman dan tanggal batas waktu peminjaman dahulu!");
            }else{
            String sql = "UPDATE peminjaman_buku SET kode_buku =?, waktu_pinjam = ?, tgl_batas_peminjaman =? WHERE kode_peminjaman =? ";
               
            PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
            stat.setString(1, kdBuku);
            stat.setString(2, fieldWaktuPeminjaman.getText());
            stat.setString(3, tgl);
            stat.setString(4, labelKodePeminjaman.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di ubah");
            datatable();
            }
        } catch (SQLException ex) {
             Logger.getLogger(dataPeminjaman.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_btnUbahDataMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian p = new pengembalian();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

    private void backgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_backgroundMouseClicked

    private void kode_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_kategoriActionPerformed

    private void fieldCariDataKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyTyped
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pinjam", "Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tabel_datapeminjaman.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE kode_peminjaman like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or waktu_pinjam like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_batas_peminjaman like '%" + fieldCariData.getText() + "%' ";
                      
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_peminjaman");
                String b = hasil.getString("kode_petugas");
                String c = hasil.getString("kode_buku");
                String d = hasil.getString("kode_anggota");
                String e = hasil.getString("waktu_pinjam");
                String f = hasil.getString("tgl_peminjaman"); 
                String g = hasil.getString("tgl_batas_peminjaman");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyTyped

    private void btncariAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_btncariAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariAncestorAdded

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
        // TODO add your handling code here:
              int stokbaru=0;
      int jml_pinjam=0;
      int sisa_buku=0; 
        try{
            String sql = "DELETE FROM peminjaman_buku WHERE kode_buku ='"+ fieldKodeBuku.getText() +"'";
//                    + "kode_anggota ='"+ fieldKodeAnggota.getText() +"' AND"
//                    +  "waktu_pinjam ='"+ selectKurunWaktu.getSelectedItem() +"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            
            stokbaru = cekstok() + 1;
            stat.executeUpdate("UPDATE kategori_buku SET jumlah_awal="+stokbaru+" WHERE kode_kategori='"+kode_kategori.getText()+"'");
            jml_pinjam = jumlah_pinjam() - 1;
            stat.executeUpdate("UPDATE kategori_buku SET jumlah_dipinjam="+jml_pinjam+" WHERE kode_kategori='"+kode_kategori.getText()+"'");
            sisa_buku= sisabuku();
            stat.executeUpdate("UPDATE kategori_buku SET sisa_buku="+sisa_buku+" WHERE kode_kategori='"+kode_kategori.getText()+"'");
            
            JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            resetInput();
            tampilPeminjam();
            tampilJumlah();
            datatable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void menuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku pb = new peminjamanBuku();
        pb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPeminjamanMouseClicked

    private void btnCetakDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakDataMouseClicked
        // TODO add your handling code here:
        try{
            getKondisiCetak = (String) urutkanData.getSelectedItem();
            if(getKondisiCetak.equals("== Pilih Kondisi ==")){
                //kasih alert
                JOptionPane.showMessageDialog(this, "Pilih kondisi terlebih dahulu!!");
            }else if(getKondisiCetak.equals("Seluruh Data")){
                //cetak semua
                Map<String, Object> parameter2 = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPeminjaman.jrxml");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
                parameter2.put("kondisi", "%20%");
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter2, conn);
                JasperViewer.viewReport(jasperPrint, false);
                
            }else if(getKondisiCetak.equals("Data Hari Ini")){
                //data hari ini
                Map<String, Object> parameter1 = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPeminjaman.jrxml");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
                parameter1.put("kondisi", tglHariIni);
                System.out.println(tglHariIni);
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter1, conn);
                JasperViewer.viewReport(jasperPrint, false);
            } else if(getKondisiCetak.equals("Kondisi Pencarian")){
                //data hari ini
                Map<String, Object> parameter = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPeminjaman.jrxml");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
                parameter.put("kondisi", fieldCariData.getText());
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
                JasperViewer.viewReport(jasperPrint, false);
            }
        }catch(Exception e){
//            System.out.println(e);
            JOptionPane.showMessageDialog(this, "ada kesalahan " + e);
        }
    }//GEN-LAST:event_btnCetakDataMouseClicked

    private void btnCetakDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCetakDataMouseEntered

    private void fieldCariDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyReleased
        // TODO add your handling code here:
                // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pinjam", "Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tabel_datapeminjaman.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE kode_peminjaman like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or waktu_pinjam like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_batas_peminjaman like '%" + fieldCariData.getText() + "%' ";
                      
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_peminjaman");
                String b = hasil.getString("kode_petugas");
                String c = hasil.getString("kode_buku");
                String d = hasil.getString("kode_anggota");
                String e = hasil.getString("waktu_pinjam");
                String f = hasil.getString("tgl_peminjaman"); 
                String g = hasil.getString("tgl_batas_peminjaman");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyReleased

    private void fieldCariDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyPressed
        // TODO add your handling code here:
                // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pinjam", "Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tabel_datapeminjaman.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE kode_peminjaman like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or waktu_pinjam like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_batas_peminjaman like '%" + fieldCariData.getText() + "%' ";
                      
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_peminjaman");
                String b = hasil.getString("kode_petugas");
                String c = hasil.getString("kode_buku");
                String d = hasil.getString("kode_anggota");
                String e = hasil.getString("waktu_pinjam");
                String f = hasil.getString("tgl_peminjaman"); 
                String g = hasil.getString("tgl_batas_peminjaman");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnCetakData;
    private javax.swing.JLabel btnHapusData;
    private javax.swing.JLabel btnUbahData;
    private javax.swing.JLabel btncari;
    private javax.swing.JTextField fieldCariData;
    private javax.swing.JTextField fieldKodeBuku;
    private javax.swing.JTextField fieldWaktuPeminjaman;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jTanggalBatasPinjam;
    private javax.swing.JLabel jumlahBukuDipinjam;
    private javax.swing.JLabel jumlahPeminjaman;
    private javax.swing.JLabel keluarAkun;
    private javax.swing.JTextField kode_kategori;
    private javax.swing.JLabel labelKodePeminjaman;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable tabel_datapeminjaman;
    private javax.swing.JComboBox<String> urutkanData;
    // End of variables declaration//GEN-END:variables
}
