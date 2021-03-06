/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;


import com.placeholder.PlaceHolder;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

import java.util.Date;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
/**
 *
 * @author LENOVO
 */
public class dataPetugas extends javax.swing.JFrame {
    private Connection conn = new koneksi().getkoneksi();
    private DefaultTableModel tabmode;
    SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
    public String tgl;
private String kdData = "";
    
    protected void kosong() {
        fieldnamapetugas.setText("");
//        tgl_lahir.setDateFormatString("");
        fieldnamaakun.setText("");
        fieldkatasandi.setText("");
    }
    
    protected void totalpetugas() {
        try {
            // TODO add your handling code here:
            String sql = "select count(*) from petugas";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) {
                int no = rs.getInt(1);
                String total=(String)String.valueOf(no);
                jml_petugas.setText(total);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dataPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void kode_petugas_otomatis() {
    try {
            String sql = "select max(right(kode_petugas,3)) from petugas";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) {
                String kode;
                if (rs.first() == false) {
                    Kodepetugas.setText("PGS001");
                } else {
                    rs.last();
                    int set_no = rs.getInt(1)+1;
                    String no = String.valueOf(set_no);
                    int no_next = no.length();
                    for(int a=0; a<3 - no_next; a++) {
                        no = "0"+no;
                    }
                    kode="PGS";
                    Kodepetugas.setText(kode+no);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(dataPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }
    
    protected void datapetugas() {
        Object[] baris = {"Kode Petugas","Nama Petugas","Tanggal Lahir","Nama Akun","Kata Sandi"};
        tabmode = new DefaultTableModel(null,baris);
        tabelpetugas.setModel(tabmode);
        String sql = "select * from petugas";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String a = hasil.getString("kode_petugas");
                String b = hasil.getString("nama_petugas");
                String c = hasil.getString("tgl_lahir");
                String d = hasil.getString("nama_akun");
                String e = hasil.getString("kata_sandi");
                Object[] data = {a,b,c,d,e};
                tabmode.addRow(data);
            }
        } catch (Exception e){
        }
    }
    /**
     * Creates new form dataPetugas
     */
    public dataPetugas() {
        initComponents();
        PlaceHolder holder1 = new PlaceHolder(fieldcari, "masukkan kata kunci");
        datapetugas();
        totalpetugas();
        kode_petugas_otomatis();
        tabelpetugas.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tabelpetugas.getTableHeader().setOpaque(false);
        tabelpetugas.getTableHeader().setBackground(new Color(206, 18, 18));
        tabelpetugas.getTableHeader().setForeground(new Color(255, 255, 255));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fieldnamapetugas = new javax.swing.JTextField();
        fieldnamaakun = new javax.swing.JTextField();
        fieldcari = new javax.swing.JTextField();
        Kodepetugas = new javax.swing.JLabel();
        btnmasukkandata = new javax.swing.JLabel();
        btneditdata = new javax.swing.JLabel();
        btnhapus = new javax.swing.JLabel();
        btncari = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuKeluarAkun = new javax.swing.JLabel();
        jml_petugas = new javax.swing.JLabel();
        jTanggalLahir = new com.toedter.calendar.JDateChooser();
        fieldkatasandi = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpetugas = new javax.swing.JTable();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fieldnamapetugas.setBackground(new java.awt.Color(238, 235, 221));
        fieldnamapetugas.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldnamapetugas.setForeground(new java.awt.Color(47, 53, 66));
        fieldnamapetugas.setBorder(null);
        getContentPane().add(fieldnamapetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 241, 275, 40));

        fieldnamaakun.setBackground(new java.awt.Color(238, 235, 221));
        fieldnamaakun.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldnamaakun.setForeground(new java.awt.Color(47, 53, 66));
        fieldnamaakun.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fieldnamaakun.setBorder(null);
        fieldnamaakun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldnamaakunActionPerformed(evt);
            }
        });
        getContentPane().add(fieldnamaakun, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 455, 270, 40));

        fieldcari.setBackground(new java.awt.Color(238, 235, 221));
        fieldcari.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldcari.setForeground(new java.awt.Color(47, 53, 66));
        fieldcari.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fieldcari.setBorder(null);
        fieldcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldcariActionPerformed(evt);
            }
        });
        getContentPane().add(fieldcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1081, 139, 182, 39));

        Kodepetugas.setFont(new java.awt.Font("Poppins Medium", 0, 22)); // NOI18N
        Kodepetugas.setForeground(new java.awt.Color(47, 53, 66));
        Kodepetugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(Kodepetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 156, 203, 31));

        btnmasukkandata.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnmasukkandata.setForeground(new java.awt.Color(255, 255, 255));
        btnmasukkandata.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnmasukkandata.setText("masukkan data");
        btnmasukkandata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmasukkandata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnmasukkandataMouseClicked(evt);
            }
        });
        getContentPane().add(btnmasukkandata, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 683, 143, 40));

        btneditdata.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btneditdata.setForeground(new java.awt.Color(47, 53, 66));
        btneditdata.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btneditdata.setText("edit data");
        btneditdata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btneditdata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btneditdataMouseClicked(evt);
            }
        });
        getContentPane().add(btneditdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 683, 143, 40));

        btnhapus.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnhapus.setForeground(new java.awt.Color(255, 255, 255));
        btnhapus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnhapus.setText("hapus");
        btnhapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnhapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhapusMouseClicked(evt);
            }
        });
        getContentPane().add(btnhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1221, 582, 100, 30));

        btncari.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btncari.setForeground(new java.awt.Color(255, 255, 255));
        btncari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btncari.setText("cari");
        btncari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncariMouseClicked(evt);
            }
        });
        getContentPane().add(btncari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 139, 50, 40));

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuBeranda.png"))); // NOI18N
        menuBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBerandaMouseClicked(evt);
            }
        });
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 204, -1, -1));

        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataAnggota.png"))); // NOI18N
        menuDataAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataAnggotaMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 307, 130, 20));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 249, 98, 20));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuDataPetugas.png"))); // NOI18N
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 380, -1, -1));

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

        jml_petugas.setDisplayedMnemonic('2');
        jml_petugas.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jml_petugas.setForeground(new java.awt.Color(206, 18, 18));
        jml_petugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(jml_petugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(692, 140, 200, 52));

        jTanggalLahir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalLahirPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 350, 300, 40));

        fieldkatasandi.setBackground(new java.awt.Color(238, 235, 221));
        fieldkatasandi.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldkatasandi.setForeground(new java.awt.Color(47, 53, 66));
        fieldkatasandi.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fieldkatasandi.setBorder(null);
        fieldkatasandi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldkatasandiActionPerformed(evt);
            }
        });
        getContentPane().add(fieldkatasandi, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 563, 275, 39));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        tabelpetugas.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        tabelpetugas.setForeground(new java.awt.Color(47, 53, 66));
        tabelpetugas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpetugas.setMinimumSize(new java.awt.Dimension(60, 100));
        tabelpetugas.setPreferredSize(new java.awt.Dimension(300, 1000));
        tabelpetugas.setRowHeight(30);
        tabelpetugas.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tabelpetugas.setSelectionForeground(new java.awt.Color(47, 53, 66));
        tabelpetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpetugasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpetugas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 670, 290));

        background.setBackground(new java.awt.Color(249, 249, 249));
        background.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        background.setForeground(new java.awt.Color(47, 53, 66));
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/dataPetugas.png"))); // NOI18N
        background.setText(" ");
        background.setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldnamaakunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldnamaakunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldnamaakunActionPerformed

    private void fieldcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldcariActionPerformed

    private void fieldkatasandiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldkatasandiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldkatasandiActionPerformed

    private void btncariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseClicked
        // TODO add your handling code here:
        Object[] baris = {"Kode Petugas","Nama Petugas","Tanggal Lahir","Nama Akun","Kata Sandi"};
        tabmode = new DefaultTableModel(null,baris);
        tabelpetugas.setModel(tabmode);
        String sql = "select kode_petugas, nama_petugas, tgl_lahir, nama_akun, kata_sandi from petugas where kode_petugas like '%"+fieldcari.getText()+"%'"
                + " or nama_petugas like '%"+fieldcari.getText()+"%' or nama_akun like '%"+fieldcari.getText()+"%' ";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String a = hasil.getString("kode_petugas");
                String b = hasil.getString("nama_petugas");
                String c = hasil.getString("tgl_lahir");
                String d = hasil.getString("nama_akun");
                String e = hasil.getString("kata_sandi");
                Object[] data = {a,b,c,d,e};
                tabmode.addRow(data);
                fieldcari.setText("");
                kode_petugas_otomatis();
                kosong();
            }
        } catch (Exception e){
        }
    }//GEN-LAST:event_btncariMouseClicked

    private void btnmasukkandataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmasukkandataMouseClicked
        // TODO add your handling code here:
        try {
            if(!kdData.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(fieldnamapetugas.getText().equals("") || jTanggalLahir.getDate().equals("") || fieldnamaakun.getText().equals("") || fieldkatasandi.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            } else{
            String sql = "INSERT INTO petugas VALUES (?,?,?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, Kodepetugas.getText());
            stat.setString(2, fieldnamapetugas.getText());
            stat.setString(3, tgl);
            stat.setString(4, fieldnamaakun.getText());
            stat.setString(5, fieldkatasandi.getText());
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");
            kode_petugas_otomatis();
            kosong();
            datapetugas();
            totalpetugas();
            jTanggalLahir.setDate(null);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Di Tambahkan"+e);
        }
    }//GEN-LAST:event_btnmasukkandataMouseClicked

    private void btnhapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhapusMouseClicked
        // TODO add your handling code here:
        try{
            String sql = "DELETE FROM petugas WHERE kode_petugas ='" + kdData + "'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            kosong();
            datapetugas();
            totalpetugas();
            jTanggalLahir.setDate(null);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnhapusMouseClicked

    private void btneditdataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditdataMouseClicked
        // TODO add your handling code here:
        try{
            if(kdData.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi masukkan data!");
            }else if(fieldnamapetugas.getText().equals("") || jTanggalLahir.getDate().equals("") || fieldnamaakun.getText().equals("") || fieldkatasandi.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            } else{
            String sql = "UPDATE petugas SET nama_petugas = ?, tgl_lahir = ?, nama_akun = ?, kata_sandi = ? WHERE kode_petugas = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, fieldnamapetugas.getText());
            stat.setString(2, tgl);
            stat.setString(3, fieldnamaakun.getText());
            stat.setString(4, fieldkatasandi.getText());
            stat.setString(5, Kodepetugas.getText());
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di ubah");
            kosong();
            fieldcari.requestFocus();
            datapetugas();
            totalpetugas();
            jTanggalLahir.setDate(null);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah "+e);
        } 
    }//GEN-LAST:event_btneditdataMouseClicked

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

    private void tabelpetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpetugasMouseClicked
        try{
            int table = tabelpetugas.getSelectedRow();
            kdData = tabelpetugas.getValueAt(table, 0).toString();

            Statement s = conn.createStatement();
            String sql = "SELECT * FROM petugas WHERE kode_petugas = '"+kdData+"'";
            ResultSet rs = s.executeQuery(sql);
            System.out.println(table);
            if(rs.next()){
                Kodepetugas.setText(rs.getString("kode_petugas"));
                fieldnamapetugas.setText(rs.getString("nama_petugas"));
                jTanggalLahir.setDate(rs.getDate("tgl_lahir"));
                fieldnamaakun.setText(rs.getString("nama_akun"));
                fieldkatasandi.setText(rs.getString("kata_sandi"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tabelpetugasMouseClicked

    private void jTanggalLahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalLahirPropertyChange
        if (jTanggalLahir.getDate()!=null){
            tgl = format.format(jTanggalLahir.getDate());
        }
    }//GEN-LAST:event_jTanggalLahirPropertyChange

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
            java.util.logging.Logger.getLogger(dataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPetugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Kodepetugas;
    private javax.swing.JLabel background;
    private javax.swing.JLabel btncari;
    private javax.swing.JLabel btneditdata;
    private javax.swing.JLabel btnhapus;
    private javax.swing.JLabel btnmasukkandata;
    private javax.swing.JTextField fieldcari;
    private javax.swing.JPasswordField fieldkatasandi;
    private javax.swing.JTextField fieldnamaakun;
    private javax.swing.JTextField fieldnamapetugas;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jTanggalLahir;
    private javax.swing.JLabel jml_petugas;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable tabelpetugas;
    // End of variables declaration//GEN-END:variables
}
