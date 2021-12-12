package Program;

import static Program.prosesPengembalian.idPetugas;
import com.placeholder.PlaceHolder;
import java.sql.*;
import java.util.Calendar;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class dataPengembalian extends javax.swing.JFrame {
    private Connection conn = new koneksi().getkoneksi();
   ///cek lagi bagian var nya. sama atau kaga
    private String kdDataPengembalian= "", kdKondisi, tglHariIni;
    private String kdPengembalian,kdPeminjaman, kdBuku, kdAnggota, tglPengembalianBefore, getKondisiCetak, kndsiBuku;
    private DefaultTableModel tabmode;
    
    ///tambahin yg ini
    public String tglPengembalian;
    SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
    ///
    Statement stm;
    ResultSet rs;
    public static String kodeBuku;
    
    /**
     * Creates new form dataPengembalian
     */
    public dataPengembalian() {
        initComponents();
        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "masukkan kata kunci");
        tampilPengembalian();
        tampilBukuDikembalikan();
        tanggalHariIni();
        datatable();
       
        
        kode_kategori.hide();
        tabelPengembalian.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tabelPengembalian.getTableHeader().setOpaque(false);
        tabelPengembalian.getTableHeader().setBackground(new Color(206, 18, 18));
        tabelPengembalian.getTableHeader().setForeground(new Color(255, 255, 255));
    }
    
    private void resetInput(){
        tampilPengembalian();
        tampilBukuDikembalikan();
        datatable();
        labelKodePengembalian.setText("XXXXX");
        kode_kategori.setText("");
        kondisiBuku.setSelectedIndex(0);
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
    
    private void tampilPengembalian(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(DISTINCT kode_pengembalian) FROM pengembalian_buku";
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()){
                System.out.println(rs.getString("COUNT(DISTINCT kode_pengembalian)"));
                jumlahPengembalian.setText(rs.getString("COUNT(DISTINCT kode_pengembalian)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilBukuDikembalikan(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_buku) FROM pengembalian_buku";
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()){
                jumlahBukuDikembalikan.setText(rs.getString("COUNT(kode_buku)"));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    protected void datatable(){
        try{
            Object [] Baris = {"Kode Pengembalian","Kode Pinjam","Kode Petugas","Kode Buku","Kode Anggota","Kondisi Buku","Tanggal Pengembalian"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelPengembalian.setModel(tabmode);

            String sql = "SELECT * FROM pengembalian_buku ORDER BY kode_pengembalian ASC";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_pengembalian");
                String b = hasil.getString("kode_peminjaman");
                String c = hasil.getString("kode_petugas"); 
                String d = hasil.getString("kode_buku");
                String e = hasil.getString("kode_anggota");
                String f = hasil.getString("kondisi_buku");
                String g = hasil.getString("tgl_pengembalian");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            tampilBukuDikembalikan();
        }catch(Exception e){
            System.out.print(e);
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

        kondisiBuku = new javax.swing.JComboBox<>();
        fieldCariData = new javax.swing.JTextField();
        menuKeluarAkun = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        jumlahPengembalian = new javax.swing.JLabel();
        jumlahBukuDikembalikan = new javax.swing.JLabel();
        btncari = new javax.swing.JLabel();
        btnCetakData = new javax.swing.JLabel();
        urutkanData = new javax.swing.JComboBox<>();
        btnUbahData = new javax.swing.JLabel();
        btnHapusData = new javax.swing.JLabel();
        labelKodePengembalian = new javax.swing.JLabel();
        kode_kategori = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPengembalian = new javax.swing.JTable();
        jTanggalPengembalian = new com.toedter.calendar.JDateChooser();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kondisiBuku.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        kondisiBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- pilih -", "Bagus", "Rusak", "Hilang" }));
        getContentPane().add(kondisiBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 683, 170, 40));

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
        getContentPane().add(fieldCariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1028, 116, 234, 40));

        menuKeluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        menuKeluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuKeluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuKeluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(menuKeluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 703, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 495, -1, -1));

        menuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPeminjaman.png"))); // NOI18N
        menuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(menuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 423, -1, -1));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataPetugas.png"))); // NOI18N
        menuDataPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataPetugasMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 365, -1, -1));

        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataAnggota.png"))); // NOI18N
        menuDataAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataAnggotaMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 307, -1, -1));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 249, -1, -1));

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuBeranda.png"))); // NOI18N
        menuBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBerandaMouseClicked(evt);
            }
        });
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        jumlahPengembalian.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahPengembalian.setForeground(new java.awt.Color(206, 18, 18));
        jumlahPengembalian.setText("0");
        getContentPane().add(jumlahPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 115, 90, 60));

        jumlahBukuDikembalikan.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahBukuDikembalikan.setForeground(new java.awt.Color(206, 18, 18));
        jumlahBukuDikembalikan.setText("0");
        getContentPane().add(jumlahBukuDikembalikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 115, 180, 60));

        btncari.setBackground(new java.awt.Color(255, 255, 255));
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
        getContentPane().add(btncari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 116, 50, 40));

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
        getContentPane().add(btnCetakData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1221, 500, 100, 30));

        urutkanData.setBackground(new java.awt.Color(249, 249, 249));
        urutkanData.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        urutkanData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kondisi ==", "Seluruh Data", "Data Hari Ini", "Kondisi Pencarian" }));
        getContentPane().add(urutkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1056, 500, 150, 30));

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
        getContentPane().add(btnUbahData, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 683, 140, 40));

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
        getContentPane().add(btnHapusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 683, 143, 40));

        labelKodePengembalian.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        labelKodePengembalian.setText("XXXXX");
        getContentPane().add(labelKodePengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 606, 180, 25));

        kode_kategori.setBorder(null);
        kode_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_kategoriActionPerformed(evt);
            }
        });
        getContentPane().add(kode_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 610, 170, -1));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        tabelPengembalian.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        tabelPengembalian.setForeground(new java.awt.Color(47, 53, 66));
        tabelPengembalian.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPengembalian.setMinimumSize(new java.awt.Dimension(60, 100));
        tabelPengembalian.setPreferredSize(new java.awt.Dimension(300, 1000));
        tabelPengembalian.setRowHeight(30);
        tabelPengembalian.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tabelPengembalian.setSelectionForeground(new java.awt.Color(47, 53, 66));
        tabelPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPengembalianMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelPengembalianMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPengembalian);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, 1030, 240));

        jTanggalPengembalian.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalPengembalianPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 683, 180, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/dataPengembalian.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseClicked
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pengembalian","Kode Pinjam","Kode Petugas","Kode Buku","Kode Anggota","Kondisi Buku","Tanggal Pengembalian"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelPengembalian.setModel(tabmode);

            String sql = "SELECT * FROM pengembalian_buku WHERE kode_pengembalian like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_pengembalian like '%" + fieldCariData.getText() + "%' ";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_pengembalian");
                String b = hasil.getString("kode_peminjaman");
                String c = hasil.getString("kode_petugas"); 
                String d = hasil.getString("kode_buku");
                String e = hasil.getString("kode_anggota");
                String f = hasil.getString("kondisi_buku");
                String g = hasil.getString("tgl_pengembalian");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_btncariMouseClicked

    private void kode_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_kategoriActionPerformed

    private void fieldCariDataKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyTyped
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pengembalian","Kode Pinjam","Kode Petugas","Kode Buku","Kode Anggota","Kondisi Buku","Tanggal Pengembalian"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelPengembalian.setModel(tabmode);

            String sql = "SELECT * FROM pengembalian_buku WHERE kode_pengembalian like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or kode_petugas like '%" + fieldCariData.getText() + "%' "
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kondisi_buku like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_pengembalian like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' ";
                     //   + "ORDER BY kode_anggota ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_pengembalian");
                String b = hasil.getString("kode_peminjaman");
                String c = hasil.getString("kode_petugas"); 
                String d = hasil.getString("kode_buku");
                String e = hasil.getString("kode_anggota");
                String f = hasil.getString("kondisi_buku");
                String g = hasil.getString("tgl_pengembalian");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyTyped

    private void btnUbahDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUbahDataMouseClicked
        // TODO add your handling code here:
        kdKondisi = (String) kondisiBuku.getSelectedItem();
        try{
            if(!kdDataPengembalian.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(kondisiBuku.getSelectedItem().toString() == "- pilih -"){
                JOptionPane.showMessageDialog(null, "Pilih kondisi buku");
            }else{
                ///tambahin yg ini
                String sql = "UPDATE pengembalian_buku SET kondisi_buku = ?, tgl_pengembalian = ? "
                        + "WHERE kode_pengembalian = ? AND kode_peminjaman = ? AND kode_buku = ? "
                        + "AND kode_anggota = ? AND tgl_pengembalian = ?";

                PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
                stat.setString(1, kdKondisi);
                stat.setString(2, tglPengembalian);
                stat.setString(3, kdPengembalian);
                stat.setString(4, kdPeminjaman);
                stat.setString(5, kdBuku);
                stat.setString(6, kdAnggota);
                stat.setString(7, tglPengembalianBefore);
                stat.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Di ubah");
                datatable();
                tampilPengembalian();
                tampilBukuDikembalikan();
            }
        }catch (SQLException ex) {
             Logger.getLogger(dataPeminjaman.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_btnUbahDataMouseClicked

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
        // TODO add your handling code here:
        try{
            int table = tabelPengembalian.getSelectedRow();
            kodeBuku = tabelPengembalian.getValueAt(table, 3).toString();
            
            int ok = JOptionPane.showConfirmDialog(null,"Apakah anda yakin akan menghapus data?","Konfirmasi",JOptionPane.YES_NO_CANCEL_OPTION);
        
            if(ok == 0){
                String sql = "DELETE FROM pengembalian_buku WHERE kode_buku ='"+kodeBuku+"'";

                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data Berhasil DiHapus");
                resetInput();
                tampilPengembalian();
                tampilBukuDikembalikan();
                datatable(); 
            }else{
                JOptionPane.showMessageDialog(this, "Anda tidak jadi menghapus data");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian p = new pengembalian();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

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

    private void menuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku pb = new peminjamanBuku();
        pb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPeminjamanMouseClicked

    private void tabelPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPengembalianMouseClicked
        try{
            int table = tabelPengembalian.getSelectedRow();
            kdPengembalian = tabelPengembalian.getValueAt(table, 0).toString();
            ///tambahin yg ini
            kdPeminjaman = tabelPengembalian.getValueAt(table, 1).toString();
            kdBuku = tabelPengembalian.getValueAt(table, 3).toString();
            kdAnggota = tabelPengembalian.getValueAt(table, 4).toString();
            kndsiBuku = tabelPengembalian.getValueAt(table, 5).toString();
            tglPengembalianBefore = tabelPengembalian.getValueAt(table, 6).toString();
            
            ///
            
            ///tambahin yg ini
            Statement s = conn.createStatement();
            String sql = "SELECT * FROM pengembalian_buku WHERE kode_pengembalian = '"+ kdPengembalian +"' "
                    + "AND kode_buku = '"+ kdBuku +"' "
                    + "AND kode_anggota = '"+ kdAnggota +"'";
            ResultSet rs = s.executeQuery(sql);
            System.out.println("mulai tampil = " + kdPengembalian);
            System.out.println(kdPeminjaman);
            System.out.println(kdBuku);
            System.out.println(kdAnggota);
            System.out.println(tglPengembalianBefore);
            System.out.println(kndsiBuku);
            if(rs.next()){
                labelKodePengembalian.setText(rs.getString("kode_pengembalian"));
                kondisiBuku.setSelectedItem(rs.getString("kondisi_buku"));
                ///tambahin yg ini
                jTanggalPengembalian.setDate(rs.getDate("tgl_pengembalian"));
                ///
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tabelPengembalianMouseClicked

    private void jTanggalPengembalianPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalPengembalianPropertyChange
        ///tambahin yg ini
        if(jTanggalPengembalian.getDate() != null){
            tglPengembalian = format.format(jTanggalPengembalian.getDate());
        }
    }//GEN-LAST:event_jTanggalPengembalianPropertyChange

    private void menuKeluarAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuKeluarAkunMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_menuKeluarAkunMouseClicked

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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPengembalian.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPengembalian.jrxml");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
                parameter1.put("kondisi", tglHariIni);
                System.out.println(tglHariIni);
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter1, conn);
                JasperViewer.viewReport(jasperPrint, false);
            }else if(getKondisiCetak.equals("Kondisi Pencarian")){
                //data hari ini
                Map<String, Object> parameter = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/LaporanPengembalian.jrxml");
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

    private void tabelPengembalianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPengembalianMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPengembalianMouseEntered

    private void fieldCariDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyPressed
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pengembalian","Kode Pinjam","Kode Petugas","Kode Buku","Kode Anggota","Kondisi Buku","Tanggal Pengembalian"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelPengembalian.setModel(tabmode);

            String sql = "SELECT * FROM pengembalian_buku WHERE kode_pengembalian like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or kode_petugas like '%" + fieldCariData.getText() + "%' "
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kondisi_buku like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_pengembalian like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' ";
                     //   + "ORDER BY kode_anggota ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_pengembalian");
                String b = hasil.getString("kode_peminjaman");
                String c = hasil.getString("kode_petugas"); 
                String d = hasil.getString("kode_buku");
                String e = hasil.getString("kode_anggota");
                String f = hasil.getString("kondisi_buku");
                String g = hasil.getString("tgl_pengembalian");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyPressed

    private void fieldCariDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyReleased
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pengembalian","Kode Pinjam","Kode Petugas","Kode Buku","Kode Anggota","Kondisi Buku","Tanggal Pengembalian"};
            tabmode = new DefaultTableModel(null,Baris);
            tabelPengembalian.setModel(tabmode);

            String sql = "SELECT * FROM pengembalian_buku WHERE kode_pengembalian like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_peminjaman like '%" + fieldCariData.getText() + "%' "
                        + "or kode_petugas like '%" + fieldCariData.getText() + "%' "
                        + "or kode_buku like '%" + fieldCariData.getText() + "%' "
                        + "or kondisi_buku like '%" + fieldCariData.getText() + "%' "
                        + "or tgl_pengembalian like '%" + fieldCariData.getText() + "%' "
                        + "or kode_anggota like '%" + fieldCariData.getText() + "%' ";
                     //   + "ORDER BY kode_anggota ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_pengembalian");
                String b = hasil.getString("kode_peminjaman");
                String c = hasil.getString("kode_petugas"); 
                String d = hasil.getString("kode_buku");
                String e = hasil.getString("kode_anggota");
                String f = hasil.getString("kondisi_buku");
                String g = hasil.getString("tgl_pengembalian");
                
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_fieldCariDataKeyReleased

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
            java.util.logging.Logger.getLogger(dataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPengembalian().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jTanggalPengembalian;
    private javax.swing.JLabel jumlahBukuDikembalikan;
    private javax.swing.JLabel jumlahPengembalian;
    private javax.swing.JTextField kode_kategori;
    private javax.swing.JComboBox<String> kondisiBuku;
    private javax.swing.JLabel labelKodePengembalian;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable tabelPengembalian;
    private javax.swing.JComboBox<String> urutkanData;
    // End of variables declaration//GEN-END:variables
}
