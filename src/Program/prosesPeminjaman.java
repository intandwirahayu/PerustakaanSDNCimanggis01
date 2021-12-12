package Program;

import static Program.prosesPengembalian.idPetugas;
import com.placeholder.PlaceHolder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

public class prosesPeminjaman extends javax.swing.JFrame {
     private Connection conn = new koneksi().getkoneksi();
     private String kdkategori, kdDataPinjam = "", getKondisiCetak;
     private DefaultTableModel tabmode;
     public String kdPeminjaman, kdBuku, kdAnggota,tglPinjam, tglBatasPinjam;
     String getKurunPeminjaman;
     SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
     Statement stm;
     ResultSet rs;
     public static String idPetugas , tglHariIni;
     
  
     public prosesPeminjaman() {
        initComponents();
        idPetugas = masukAkun.getIdPetugas();
        loadData();
        tampilKdPinjam();
        tanggalHariIni();
        labelKodePeminjaman.requestFocus();
        
        tabel_peminjaman_buku.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tabel_peminjaman_buku.getTableHeader().setOpaque(false);
        tabel_peminjaman_buku.getTableHeader().setBackground(new Color(206, 18, 18));
        tabel_peminjaman_buku.getTableHeader().setForeground(new Color(255, 255, 255));
        
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
     
     private void tampilKdPinjam(){
        try {
                String sql="SELECT kode_peminjaman FROM peminjaman_buku ORDER BY kode_peminjaman DESC";
                Statement s = conn.createStatement();
                ResultSet rs=s.executeQuery(sql);

                if (rs.next()) {
                    String kda = rs.getString("kode_peminjaman").substring(4);
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

                   labelKodePeminjaman.setText("PJM" + nol + an);
                } else {
                   labelKodePeminjaman.setText("PJM0001");
                }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
            }
     }
     
     private void resetInput(){
        fieldKodeBuku.setText("");
        fieldKodeAnggota.setText("");
        selectKurunWaktu.setSelectedIndex(0);
        jTanggalPinjam.setDate(null);
        jTanggalBatasPinjam.setDate(null);
        
    }

     private void resetKodePeminjaman(){
        try{
            getKurunPeminjaman= (String) selectKurunWaktu.getSelectedItem();
            
            if(getKurunPeminjaman.equals("=Pilih Waktu Peminjaman=")){
                labelKodePeminjaman.setText("xxxxxx");
            }else{
                labelKodePeminjaman.setText("xxxxxx");
            }
        }catch(Exception e){
            System.out.println(e);
        }
     }
         
    public void loadData(){    
        try{  
            Object [] Baris = {"Kode Peminjaman", "Kode Buku", "Kode Anggota", "Waktu Pinjam", "Tanggal Peminjaman", "Tanggal Batas Peminjaman"};
            tabmode = new DefaultTableModel(null, Baris);
            tabel_peminjaman_buku.setModel(tabmode);

            String sql = "SELECT * FROM peminjaman_buku WHERE kode_peminjaman = '"+labelKodePeminjaman.getText()+"'";
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            while(r.next()){
                String a = r.getString("kode_peminjaman");
                String b = r.getString("kode_buku");
                String c = r.getString("kode_anggota");
                String d = r.getString("waktu_pinjam");
                String e = r.getString("tgl_peminjaman");
                String f = r.getString("tgl_batas_peminjaman");
                
                String[] data = {a, b, c, d, e, f};
                tabmode.addRow(data);
            }   
            
            tampilJumlah();
        }catch(Exception e){  
          System.out.println("Terjadi Kesalahan di table");  
        }
    }

     private void tampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_buku) FROM peminjaman_buku WHERE kode_peminjaman='"+labelKodePeminjaman.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                System.out.println("tampil jumlah");
                jumlahPinjamBuku.setText(rs.getString("COUNT(kode_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_peminjaman_buku = new javax.swing.JTable();
        jTanggalPinjam = new com.toedter.calendar.JDateChooser();
        jTanggalBatasPinjam = new com.toedter.calendar.JDateChooser();
        selectKurunWaktu = new javax.swing.JComboBox<>();
        fieldKodeAnggota = new javax.swing.JTextField();
        fieldKodeBuku = new javax.swing.JTextField();
        labelkodebuku = new javax.swing.JLabel();
        btnSelesaiTambahkanData = new javax.swing.JLabel();
        btnHapusData = new javax.swing.JLabel();
        btnEditData = new javax.swing.JLabel();
        btnMasukkanData = new javax.swing.JLabel();
        jumlahPinjamBuku = new javax.swing.JLabel();
        labelKodePeminjaman = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        MenuPeminjaman = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuKeluarAkun = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_peminjaman_buku.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_peminjaman_buku.setMaximumSize(new java.awt.Dimension(2147483647, 120));
        tabel_peminjaman_buku.setMinimumSize(new java.awt.Dimension(60, 100));
        tabel_peminjaman_buku.setPreferredSize(new java.awt.Dimension(300, 1000));
        tabel_peminjaman_buku.setRowHeight(30);
        tabel_peminjaman_buku.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tabel_peminjaman_buku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_peminjaman_bukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_peminjaman_buku);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(643, 202, 661, 315));

        jTanggalPinjam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalPinjamPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 494, 300, 40));

        jTanggalBatasPinjam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTanggalBatasPinjamPropertyChange(evt);
            }
        });
        getContentPane().add(jTanggalBatasPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 586, 300, 40));

        selectKurunWaktu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=Pilih Waktu Peminjaman=", "Seminggu", "Enam Bulan", "Satu Tahun", " " }));
        getContentPane().add(selectKurunWaktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 402, 300, 40));

        fieldKodeAnggota.setBackground(new java.awt.Color(238, 235, 221));
        fieldKodeAnggota.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldKodeAnggota.setForeground(new java.awt.Color(47, 53, 66));
        fieldKodeAnggota.setBorder(null);
        getContentPane().add(fieldKodeAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 310, 275, 40));

        fieldKodeBuku.setBackground(new java.awt.Color(238, 235, 221));
        fieldKodeBuku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldKodeBuku.setForeground(new java.awt.Color(47, 53, 66));
        fieldKodeBuku.setBorder(null);
        fieldKodeBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldKodeBukuKeyTyped(evt);
            }
        });
        getContentPane().add(fieldKodeBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 218, 275, 40));
        getContentPane().add(labelkodebuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 218, 275, 40));

        btnSelesaiTambahkanData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnSelesaiTambahkanData.setForeground(new java.awt.Color(255, 255, 255));
        btnSelesaiTambahkanData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSelesaiTambahkanData.setText("selesai tambah data");
        btnSelesaiTambahkanData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelesaiTambahkanData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSelesaiTambahkanDataMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSelesaiTambahkanDataMouseEntered(evt);
            }
        });
        getContentPane().add(btnSelesaiTambahkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1122, 673, 171, 40));

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
        getContentPane().add(btnHapusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1221, 566, 100, 30));

        btnEditData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnEditData.setForeground(new java.awt.Color(47, 53, 66));
        btnEditData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEditData.setText("edit data");
        btnEditData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditDataMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditDataMouseEntered(evt);
            }
        });
        getContentPane().add(btnEditData, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 683, 143, 40));

        btnMasukkanData.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        btnMasukkanData.setForeground(new java.awt.Color(255, 255, 255));
        btnMasukkanData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMasukkanData.setText("tambahkan data");
        btnMasukkanData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMasukkanData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMasukkanDataMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMasukkanDataMouseEntered(evt);
            }
        });
        getContentPane().add(btnMasukkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 683, 143, 40));

        jumlahPinjamBuku.setDisplayedMnemonic('2');
        jumlahPinjamBuku.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahPinjamBuku.setForeground(new java.awt.Color(206, 18, 18));
        jumlahPinjamBuku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jumlahPinjamBuku.setText("0");
        getContentPane().add(jumlahPinjamBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(717, 118, 200, 52));

        labelKodePeminjaman.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        labelKodePeminjaman.setText("XXXXX");
        getContentPane().add(labelKodePeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 141, 150, 35));

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuBeranda.png"))); // NOI18N
        menuBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBerandaMouseClicked(evt);
            }
        });
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 95, 21));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 249, 99, 20));

        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataAnggota.png"))); // NOI18N
        menuDataAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataAnggotaMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 306, 130, 20));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataPetugas.png"))); // NOI18N
        menuDataPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataPetugasMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 365, 123, 20));

        MenuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuPeminjaman.png"))); // NOI18N
        MenuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(MenuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 510, -1, -1));

        menuKeluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        menuKeluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuKeluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuKeluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(menuKeluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 703, -1, -1));

        background.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        background.setForeground(new java.awt.Color(47, 53, 66));
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/prosesPeminjaman.png"))); // NOI18N
        background.setText("jLabel1");
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));
        getContentPane().add(kodepetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, 100, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasukkanDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseClicked
        try{
            if(!kdDataPinjam.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(fieldKodeBuku.getText().equals("") ||fieldKodeAnggota.getText().equals("") || 
                    selectKurunWaktu.equals("=Pilih Waktu Peminjaman=") || jTanggalPinjam.getDate().equals("") ||
                    jTanggalBatasPinjam.getDate().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            } else{
            //input data ke table peminjaman_buku
            String sql = "INSERT INTO peminjaman_buku VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setString(1, labelKodePeminjaman.getText());
            stat.setString(2, idPetugas);
            stat.setString(3, fieldKodeBuku.getText());
            stat.setString(4, fieldKodeAnggota.getText());
            stat.setString(5, selectKurunWaktu.getSelectedItem().toString()); 
            stat.setString(6, tglPinjam);
            stat.setString(7, tglBatasPinjam);
            stat.setString(8, tglHariIni);

            stat.executeUpdate();
            
            //pengubahan jumlah_dipinjam dan sisa_buku ditable kategori_buku
            //select data dahulu
            String kdKategoriBuku = fieldKodeBuku.getText().substring(0,4);

            Statement stm_select = conn.createStatement();
            String sql_select = "SELECT * FROM kategori_buku WHERE kode_kategori = '"+ kdKategoriBuku +"'";
            ResultSet rs_select = stm_select.executeQuery(sql_select);
            
            if(rs_select.next()){

                int jumlah_dipinjam = Integer.parseInt(rs_select.getString("jumlah_dipinjam"));
                int sisa_buku = Integer.parseInt(rs_select.getString("sisa_buku"));

                //proses ubah data
                String sql_update = "UPDATE kategori_buku SET jumlah_dipinjam = ?, sisa_buku = ? WHERE kode_kategori = '" + kdKategoriBuku +"'";

                PreparedStatement stat_update = conn.prepareStatement(sql_update);

                jumlah_dipinjam = jumlah_dipinjam + 1;
                sisa_buku = sisa_buku - 1;

                stat_update.setInt(1, jumlah_dipinjam);
                stat_update.setInt(2, sisa_buku);

                stat_update.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    
                loadData();
                resetInput();
            }
            }
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak tersimpan"+e);
        }
    }//GEN-LAST:event_btnMasukkanDataMouseClicked

    private void btnMasukkanDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMasukkanDataMouseEntered

    private void btnEditDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseClicked
        getKurunPeminjaman= (String) selectKurunWaktu.getSelectedItem();  
        
        try{
            if(!kdDataPinjam.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(fieldKodeBuku.getText().equals("") || fieldKodeAnggota.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            }else if(getKurunPeminjaman.equals("Pilih Kurun Waktu Peminjaman")){ 
                //kondisi pengecekkan kurun waktu peminjaman
                JOptionPane.showMessageDialog(null, "Tentukan kurun waktu peminjaman dahulu!");
            }else if(jTanggalPinjam.getDate().equals("") || jTanggalBatasPinjam.getDate().equals("")){ 
                JOptionPane.showMessageDialog(null, "Tentukan tanggal peminjaman dan tanggal batas waktu peminjaman dahulu!");
            }else{
                String sql = "UPDATE peminjaman_buku "
                        + "SET kode_buku = ?, kode_anggota = ?, waktu_pinjam = ?, tgl_peminjaman= ?, tgl_batas_peminjaman = ? , tgl_input = ?"
                        + "WHERE kode_peminjaman = '"+ kdPeminjaman  +"' AND kode_buku = '"+ kdBuku +"' AND kode_anggota = '"+ kdAnggota +"'";

                PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
                stat.setString(1, fieldKodeBuku.getText());
                stat.setString(2, fieldKodeAnggota.getText());
                stat.setString(3, (String) selectKurunWaktu.getSelectedItem());
                stat.setString(4, tglPinjam);
                stat.setString(5, tglBatasPinjam);
                stat.setString(6, tglHariIni);
                

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Di ubah");
                
                loadData();
                //resetInput();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah "+e);
    }
    }//GEN-LAST:event_btnEditDataMouseClicked

    private void btnEditDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditDataMouseEntered

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
        try{
            //proses delete data dari table peminjaman_buku
            String sql_delete = "DELETE FROM peminjaman_buku WHERE "
                    + "kode_peminjaman = '"+ kdPeminjaman +"' AND kode_buku = '"+ kdBuku +"' AND kode_anggota = '"+ kdAnggota +"'";
            PreparedStatement stat_delete = conn.prepareStatement(sql_delete);
            stat_delete.executeUpdate();
            
            //pengubahan jumlah_dipinjam dan sisa_buku ditable kategori_buku
            //select data dahulu
            String kdKategoriBuku = kdBuku.substring(0,4);
            
            Statement stm_select = conn.createStatement();
            String sql_select = "SELECT * FROM kategori_buku WHERE kode_kategori = '"+ kdKategoriBuku +"'";
            ResultSet rs_select = stm_select.executeQuery(sql_select);

            if(rs_select.next()){

                int jumlah_dipinjam = Integer.parseInt(rs_select.getString("jumlah_dipinjam"));
                int sisa_buku = Integer.parseInt(rs_select.getString("sisa_buku"));
            
                //proses ubah data
                String sql_update = "UPDATE kategori_buku SET jumlah_dipinjam = ?, sisa_buku = ? WHERE kode_kategori = '" + kdKategoriBuku +"'";
                    
                PreparedStatement stat_update = conn.prepareStatement(sql_update);

                jumlah_dipinjam = jumlah_dipinjam - 1;
                sisa_buku = sisa_buku + 1;
                
                stat_update.setInt(1, jumlah_dipinjam);
                stat_update.setInt(2, sisa_buku);

                stat_update.executeUpdate();
                loadData();
                resetInput();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void btnSelesaiTambahkanDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelesaiTambahkanDataMouseClicked
        int ok = JOptionPane.showConfirmDialog(null,"Apakah anda yakin menyelesaikan peminjaman","Konfirmasi Dialog",JOptionPane.YES_NO_CANCEL_OPTION);
        
        if(ok == 0){
            JOptionPane.showMessageDialog(this, "Berhasil meminjam buku");
               
            tampilKdPinjam();
            loadData();
            resetInput(); 
        }else{
            JOptionPane.showMessageDialog(this, "Gagal meminjam buku");
        }
    }//GEN-LAST:event_btnSelesaiTambahkanDataMouseClicked

    private void btnSelesaiTambahkanDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelesaiTambahkanDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelesaiTambahkanDataMouseEntered

    private void jTanggalPinjamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalPinjamPropertyChange
        // TODO add your handling code here:
        if (jTanggalPinjam.getDate()!=null){
            tglPinjam = format.format(jTanggalPinjam.getDate());
        }
    }//GEN-LAST:event_jTanggalPinjamPropertyChange

    private void jTanggalBatasPinjamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTanggalBatasPinjamPropertyChange
        // TODO add your handling code here:
        if (jTanggalBatasPinjam.getDate()!=null){
            tglBatasPinjam = format.format(jTanggalBatasPinjam.getDate());
        }
    }//GEN-LAST:event_jTanggalBatasPinjamPropertyChange

    private void tabel_peminjaman_bukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_peminjaman_bukuMouseClicked
         try{
            int table = tabel_peminjaman_buku.getSelectedRow();
            
            kdPeminjaman = tabel_peminjaman_buku.getValueAt(table, 0).toString();
            kdBuku = tabel_peminjaman_buku.getValueAt(table, 1).toString();
            kdAnggota = tabel_peminjaman_buku.getValueAt(table, 2).toString();

            Statement s = conn.createStatement();
            String sql = "SELECT * FROM peminjaman_buku WHERE kode_buku = '"+kdBuku+"' OR kode_peminjaman = '"+ kdPeminjaman +"'";
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.next()){
                labelKodePeminjaman.setText(rs.getString("kode_peminjaman"));
                fieldKodeBuku.setText(rs.getString("kode_buku"));
                fieldKodeAnggota.setText(rs.getString("kode_anggota"));
                selectKurunWaktu.setSelectedItem(rs.getString("waktu_pinjam"));
                jTanggalPinjam.setDate(rs.getDate("tgl_peminjaman"));
                jTanggalBatasPinjam.setDate(rs.getDate("tgl_batas_peminjaman"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tabel_peminjaman_bukuMouseClicked

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

    private void menuDataAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataAnggotaMouseClicked
        // TODO add your handling code here:
        detailAnggota da = new detailAnggota();
        da.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataAnggotaMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian pe = new pengembalian();
        pe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

    private void fieldKodeBukuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldKodeBukuKeyTyped

    }//GEN-LAST:event_fieldKodeBukuKeyTyped

    private void MenuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku pb = new peminjamanBuku();
        pb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MenuPeminjamanMouseClicked

    private void menuKeluarAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuKeluarAkunMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_menuKeluarAkunMouseClicked

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
            java.util.logging.Logger.getLogger(prosesPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(prosesPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(prosesPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(prosesPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 new prosesPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MenuPeminjaman;
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnEditData;
    private javax.swing.JLabel btnHapusData;
    private javax.swing.JLabel btnMasukkanData;
    private javax.swing.JLabel btnSelesaiTambahkanData;
    private javax.swing.JTextField fieldKodeAnggota;
    private javax.swing.JTextField fieldKodeBuku;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jTanggalBatasPinjam;
    private com.toedter.calendar.JDateChooser jTanggalPinjam;
    private javax.swing.JLabel jumlahPinjamBuku;
    public static final javax.swing.JLabel kodepetugas = new javax.swing.JLabel();
    private javax.swing.JLabel labelKodePeminjaman;
    private javax.swing.JLabel labelkodebuku;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JComboBox<String> selectKurunWaktu;
    private javax.swing.JTable tabel_peminjaman_buku;
    // End of variables declaration//GEN-END:variables

}
