/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

//import com.placeholder.PlaceHolder;
import com.placeholder.PlaceHolder;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.Calendar;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
 * @author Kyyyy
 */
public class detailAnggota extends javax.swing.JFrame {
private Connection conn = new koneksi().getkoneksi();
SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
private DefaultTableModel tabmode;
public PreparedStatement ps;
public String tgl, tglHariIni;
private String kdData = "", getKondisiCetak;
    /**
     * Creates new form detailAnggota
     */
    public detailAnggota() {
        initComponents();
        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "masukkan kata kunci");
        datatable();
        kdAnggota();
        tanggalHariIni();
        fieldNamaAnggota.requestFocus();
        tabelDetailAnggota.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tabelDetailAnggota.getTableHeader().setOpaque(false);
        tabelDetailAnggota.getTableHeader().setBackground(new Color(206, 18, 18));
        tabelDetailAnggota.getTableHeader().setForeground(new Color(255, 255, 255));
    }
    
    private void resetInput(){
        fieldNamaAnggota.setText("");
        jTanggalLahir.setDate(null);
        fieldAlamat.setText("");
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
    
    protected void datatable(){
        try{
            Object [] Baris = {"Kode Anggota", "Nama Anggota", "Tanggal Lahir", "Alamat","Tanggal Daftar"};
            DefaultTableModel tabmode = new DefaultTableModel(null,Baris);
            tabelDetailAnggota.setModel(tabmode);

            String sql = "SELECT * FROM detail_anggota ORDER BY kode_anggota DESC";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_anggota");
                String b = hasil.getString("nama_anggota"); 
                String c = hasil.getString("tgl_lahir");
                String d = hasil.getString("alamat");
                String e = hasil.getString("tgl_daftar");
                
                
                String[] data = {a,b,c,d,e};
                tabmode.addRow(data);
            }    
            tampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }
    
    private void tampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_anggota) FROM detail_anggota";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                JumlahAnggota.setText(rs.getString("COUNT(kode_anggota)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void editTampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_anggota) FROM detail_anggota WHERE kode_anggota like '%" 
                        + kodeAnggota.getText().toLowerCase() + "%'" 
                        + "or nama_anggota like '%" + fieldCariData.getText().toLowerCase() + "%'"
                        + "or tgl_lahir like '%" + fieldCariData.getText().toLowerCase() + "%'"
                        + "or alamat like '%" + fieldCariData.getText().toLowerCase() + "%' "
                        + "or tgl_daftar like '%" + fieldCariData.getText().toLowerCase() + "%'"
                        + "ORDER BY kode_anggota ASC";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                JumlahAnggota.setText(rs.getString("COUNT(kode_anggota)"));
                
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void kdAnggota(){
//       String kode = "ANG0000";
//       int i = 0;
       try {
            String sql="SELECT * FROM detail_anggota ORDER BY kode_anggota DESC";
            Statement s = conn.createStatement();
            ResultSet rs=s.executeQuery(sql);
//            while(rs.next()){
//                kode = rs.getString("kode_anggota");
//            }
//            kode = kode.substring(3);
//            i = Integer.parseInt(kode) + 1;
//            kode = "000" + i;
//            kode = "ANG" + kode.substring(kode.length()-4);
//            kodeAnggota.setText(kode);
            if (rs.next()) {
                String kda = rs.getString("kode_anggota").substring(4);
                String an = "" + (Integer.parseInt(kda) + 1);
                String nol = "";

                if(an.length() == 1){
                        nol = "000";
                    }else if(an.length() == 2){
                        nol = "00";
                    }else if(an.length() == 3){
                        nol = "0";
                    }else if(an.length() == 4){
                        nol = "";
                    }

               kodeAnggota.setText("ANG" + nol + an);
            } else {
               kodeAnggota.setText("ANG0001");
            }
           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
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
        menuKeluarAkun = new javax.swing.JLabel();
        JumlahAnggota = new javax.swing.JLabel();
        kondisiCetak = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JLabel();
        btnHapusData = new javax.swing.JLabel();
        btnCetakKartu = new javax.swing.JLabel();
        btnEditData = new javax.swing.JLabel();
        btnMasukkanData = new javax.swing.JLabel();
        kodeAnggota = new javax.swing.JLabel();
        btnCetakData = new javax.swing.JLabel();
        fieldNamaAnggota = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        fieldAlamat = new javax.swing.JTextArea();
        fieldCariData = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDetailAnggota = new javax.swing.JTable();
        jTanggalLahir = new com.toedter.calendar.JDateChooser();
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
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 249, 98, 20));

        menuDataAnggota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuDataAnggota.png"))); // NOI18N
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 322, 130, 20));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataPetugas.png"))); // NOI18N
        menuDataPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataPetugasMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 395, -1, -1));

        menuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPeminjaman.png"))); // NOI18N
        menuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(menuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 453, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 511, -1, -1));

        menuKeluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        menuKeluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuKeluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuKeluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(menuKeluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 703, -1, -1));

        JumlahAnggota.setBackground(new java.awt.Color(104, 214, 228));
        JumlahAnggota.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        JumlahAnggota.setForeground(new java.awt.Color(206, 18, 18));
        JumlahAnggota.setText("0");
        getContentPane().add(JumlahAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(692, 118, 100, 52));

        kondisiCetak.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        kondisiCetak.setForeground(new java.awt.Color(47, 53, 66));
        kondisiCetak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kondisi ==", "Seluruh Data", "Data Hari Ini", "Kondisi Pencarian" }));
        kondisiCetak.setBorder(null);
        kondisiCetak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kondisiCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kondisiCetakActionPerformed(evt);
            }
        });
        getContentPane().add(kondisiCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 566, 120, 30));

        btnCari.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnCari.setForeground(new java.awt.Color(255, 255, 255));
        btnCari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCari.setText("cari");
        btnCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCariMouseClicked(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        getContentPane().add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 118, 50, 40));

        btnHapusData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnHapusData.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHapusData.setText("hapus");
        btnHapusData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapusData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnHapusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1221, 566, 100, 30));

        btnCetakKartu.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnCetakKartu.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakKartu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCetakKartu.setText("cetak kartu");
        btnCetakKartu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetakKartu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCetakKartuMouseClicked(evt);
            }
        });
        getContentPane().add(btnCetakKartu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1122, 673, 171, 40));

        btnEditData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnEditData.setForeground(new java.awt.Color(47, 53, 66));
        btnEditData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEditData.setText("edit data");
        btnEditData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnEditData, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 683, 143, 40));

        btnMasukkanData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnMasukkanData.setForeground(new java.awt.Color(255, 255, 255));
        btnMasukkanData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMasukkanData.setText("masukkan data");
        btnMasukkanData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMasukkanData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMasukkanDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnMasukkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 683, 143, 40));

        kodeAnggota.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        kodeAnggota.setForeground(new java.awt.Color(47, 53, 66));
        kodeAnggota.setText("XXXXXX");
        getContentPane().add(kodeAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 205, 205, 35));

        btnCetakData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnCetakData.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCetakData.setText("cetak data");
        btnCetakData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetakData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCetakDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnCetakData, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 566, 100, 30));

        fieldNamaAnggota.setBackground(new java.awt.Color(238, 235, 221));
        fieldNamaAnggota.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldNamaAnggota.setForeground(new java.awt.Color(47, 53, 66));
        fieldNamaAnggota.setBorder(null);
        getContentPane().add(fieldNamaAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 292, 290, 40));

        jScrollPane2.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane2.setBorder(null);

        fieldAlamat.setBackground(new java.awt.Color(238, 235, 221));
        fieldAlamat.setColumns(20);
        fieldAlamat.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldAlamat.setForeground(new java.awt.Color(47, 53, 66));
        fieldAlamat.setLineWrap(true);
        fieldAlamat.setRows(5);
        fieldAlamat.setBorder(null);
        jScrollPane2.setViewportView(fieldAlamat);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 496, 276, 125));

        fieldCariData.setBackground(new java.awt.Color(238, 235, 221));
        fieldCariData.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        fieldCariData.setForeground(new java.awt.Color(47, 53, 66));
        fieldCariData.setBorder(null);
        fieldCariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyPressed(evt);
            }
        });
        getContentPane().add(fieldCariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1082, 119, 180, 38));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        tabelDetailAnggota.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        tabelDetailAnggota.setForeground(new java.awt.Color(47, 53, 66));
        tabelDetailAnggota.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelDetailAnggota.setMinimumSize(new java.awt.Dimension(60, 100));
        tabelDetailAnggota.setPreferredSize(new java.awt.Dimension(300, 1000));
        tabelDetailAnggota.setRowHeight(30);
        tabelDetailAnggota.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tabelDetailAnggota.setSelectionForeground(new java.awt.Color(47, 53, 66));
        tabelDetailAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDetailAnggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelDetailAnggota);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(643, 205, 661, 290));

        jTanggalLahir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalLahirPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 394, 300, 40));

        background.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/dataAnggota.png"))); // NOI18N
        background.setText("jLabel1");
        background.setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasukkanDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseClicked
        try {
            if(!kdData.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(fieldNamaAnggota.getText().equals("") || jTanggalLahir.getDate().equals("") || fieldAlamat.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            } else{
            String sql = "INSERT INTO detail_anggota VALUES (?,?,?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, kodeAnggota.getText());
            stat.setString(2, fieldNamaAnggota.getText());
            stat.setString(3, tgl);
            stat.setString(4, fieldAlamat.getText());
            stat.setString(5, tglHariIni);
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");
            kdAnggota();
            resetInput();
            datatable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Di Tambahkan"+e);
        }
    }//GEN-LAST:event_btnMasukkanDataMouseClicked

    private void jTanggalLahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalLahirPropertyChange
        if (jTanggalLahir.getDate()!=null){
            tgl = format.format(jTanggalLahir.getDate());
        }
    }//GEN-LAST:event_jTanggalLahirPropertyChange

    private void btnEditDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseClicked
        try{
            if(kdData.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi masukkan data!");
            }else if(fieldNamaAnggota.getText().equals("") || jTanggalLahir.getDate().equals("") || fieldAlamat.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            } else{
            String sql = "UPDATE detail_anggota SET nama_anggota = ?, tgl_lahir = ?, alamat = ?, tgl_daftar = ? WHERE kode_anggota = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, fieldNamaAnggota.getText());
            stat.setString(2, tgl);
            stat.setString(3, fieldAlamat.getText());
            stat.setString(4, tglHariIni);
            stat.setString(5, kodeAnggota.getText());
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di ubah");
            kdAnggota();
            resetInput();
            fieldNamaAnggota.requestFocus();
            datatable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah "+e);
    }
    }//GEN-LAST:event_btnEditDataMouseClicked

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
         try{
            String sql = "DELETE FROM detail_anggota WHERE kode_anggota ='" + kdData + "'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            kdAnggota();
            resetInput();
            datatable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        try{
            Object [] Baris = {"Kode Anggota","Nama Anggota","Tanggal Lahir","Alamat","Tanggal Daftar"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelDetailAnggota.setModel(tabmode);

            String sql = "SELECT * FROM detail_anggota WHERE kode_anggota like '%" + fieldCariData.getText() + "%' " 
                        + "or nama_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_lahir like '%" + fieldCariData.getText() + "%' "
                        + "or alamat like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_daftar like '%" + fieldCariData.getText() + "%' "
                        + "ORDER BY kode_anggota ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_anggota");
                String b = hasil.getString("nama_anggota");
                String c = hasil.getString("tgl_lahir");
                String d = hasil.getString("alamat");
                String e = hasil.getString("tgl_daftar");
                
                String[] data = {a,b,c,d,e};
                tabmode.addRow(data);
            }
            
            editTampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_btnCariMouseClicked

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(fieldCariData.getText().equals("")){
            datatable();
        }
    }//GEN-LAST:event_btnCariKeyPressed

    private void fieldCariDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyPressed
        if(fieldCariData.getText().equals("")){
            datatable();
        }
    }//GEN-LAST:event_fieldCariDataKeyPressed

    private void tabelDetailAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDetailAnggotaMouseClicked
        try{
            int table = tabelDetailAnggota.getSelectedRow();
            kdData = tabelDetailAnggota.getValueAt(table, 0).toString();

            Statement s = conn.createStatement();
            String sql = "SELECT * FROM detail_anggota WHERE kode_anggota = '"+kdData+"'";
            ResultSet rs = s.executeQuery(sql);
            System.out.println(table);
            if(rs.next()){
                kodeAnggota.setText(rs.getString("kode_anggota"));
                fieldNamaAnggota.setText(rs.getString("nama_anggota"));
                jTanggalLahir.setDate(rs.getDate("tgl_lahir"));
                fieldAlamat.setText(rs.getString("alamat"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tabelDetailAnggotaMouseClicked

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

    private void menuDataPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataPetugasMouseClicked
        // TODO add your handling code here:
        dataPetugas dp = new dataPetugas();
        dp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataPetugasMouseClicked

    private void menuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku pb = new peminjamanBuku();
        pb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPeminjamanMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian pe = new pengembalian();
        pe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

    private void menuKeluarAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuKeluarAkunMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_menuKeluarAkunMouseClicked

    private void btnCetakKartuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakKartuMouseClicked
        // TODO add your handling code here:
        String nt = JOptionPane.showInputDialog("Masukkan Kode Anggota Yang Akan DiCetak");
        try{
            Map<String, Object> parameter = new HashMap <String, Object>();
            File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/CetakKartuAnggota.jrxml");
            parameter.put("kode", nt);
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan","root","");
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport (jasperReport, parameter, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Data Tidak Dapat DiCetak!"+e.getMessage(),"Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakKartuMouseClicked

    private void btnCetakDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakDataMouseClicked
        // TODO add your handling code here:
        try{
            getKondisiCetak = (String) kondisiCetak.getSelectedItem();

            if(getKondisiCetak.equals("== Pilih Kondisi ==")){
                //kasih alert
                JOptionPane.showMessageDialog(this, "Pilih kondisi terlebih dahulu!!");
            }else if(getKondisiCetak.equals("Seluruh Data")){
                //cetak semua
                Map<String, Object> parameter2 = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/DataAnggota.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/DataAnggota.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/DataAnggota.jrxml");
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

    private void kondisiCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kondisiCetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kondisiCetakActionPerformed

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
            java.util.logging.Logger.getLogger(detailAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detailAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detailAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detailAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new detailAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JumlahAnggota;
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnCari;
    private javax.swing.JLabel btnCetakData;
    private javax.swing.JLabel btnCetakKartu;
    private javax.swing.JLabel btnEditData;
    private javax.swing.JLabel btnHapusData;
    private javax.swing.JLabel btnMasukkanData;
    private javax.swing.JTextArea fieldAlamat;
    private javax.swing.JTextField fieldCariData;
    private javax.swing.JTextField fieldNamaAnggota;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jTanggalLahir;
    private javax.swing.JLabel kodeAnggota;
    private javax.swing.JComboBox<String> kondisiCetak;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable tabelDetailAnggota;
    // End of variables declaration//GEN-END:variables
}
