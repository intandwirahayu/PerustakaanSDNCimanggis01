/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.koneksi;

/**
 *
 * @author Kyyyy
 */
public class beranda extends javax.swing.JFrame {
    private Connection conn = new koneksi().getkoneksi();
    private String tglHariIni;
    public static String idPetugas;
    /**
     * Creates new form beranda
     */
    public beranda() {
        initComponents();
        idPetugas = masukAkun.getIdPetugas();
        tanggalHariIni();
        tampilJumlahKategori();
        tampilJumlahDetailAnggota();
        tampilJumlahDetailBuku();
        tampilJumlahDetailAnggotaHariIni();
        totalpetugas();
        tampilbukubagus();
        tampilbukuhilang();
        tampilbukurusak();
        tampiljumlahpinjamseminggu();
        tampiljumlahpinjambulan();
        tampiljumlahpinjamtahun();
        tampiljumlahpinjam();
        tampiljumlahpengembalianminggu();
        tampiljumlahpengembalianbulan();
        tampiljumlahpengembaliantahun();
        tampiljumlahpengembalian();
        
        new Thread(){
            public void run(){
                while (true){
                    Calendar kal=new GregorianCalendar();
                    int jam= kal.get(Calendar.HOUR_OF_DAY);
                    int menit= kal.get(Calendar.MINUTE);
                    String waktu= jam+":"+menit;
                    jwaktu.setText(waktu);
                }
            }
        }.start();
    }
    
    
    private void tanggalHariIni(){
        try{
            Date ys = new Date(); // membuat oject ys dari class Date
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); //membuat object s dari class SimpleDateFormat dengan format (dd-MM-yyyy)yaitu (tanggal-bulan-tahun).
            jtanggal.setText(s.format(ys));
            tglHariIni = s.format(ys);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilJumlahKategori(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(nama_kategori) FROM kategori_buku";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jKategoriBuku.setText(rs.getString("COUNT(nama_kategori)"));
                
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilJumlahDetailBuku(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(judul_buku) FROM detail_buku";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jDetailBuku.setText(rs.getString("COUNT(judul_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilJumlahDetailAnggota(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_anggota) FROM detail_anggota";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jDetailAnggota.setText(rs.getString("COUNT(kode_anggota)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilJumlahDetailAnggotaHariIni(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(tgl_daftar) FROM detail_anggota where tgl_daftar='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jPendaftarBaru.setText(rs.getString("COUNT(tgl_daftar)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
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
                jDaftarPetugas.setText(total);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dataPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void tampilbukubagus(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kondisi_buku) FROM pengembalian_buku where kondisi_buku='bagus'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jbagus.setText(rs.getString("COUNT(kondisi_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilbukurusak(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kondisi_buku) FROM pengembalian_buku where kondisi_buku='rusak'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jrusak.setText(rs.getString("COUNT(kondisi_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilbukuhilang(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kondisi_buku) FROM pengembalian_buku where kondisi_buku='hilang'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jhilang.setText(rs.getString("COUNT(kondisi_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampiljumlahpinjamseminggu(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM peminjaman_buku where waktu_pinjam='seminggu' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pinjamseminggu.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpinjambulan(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM peminjaman_buku where waktu_pinjam='enam bulan' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pinjambulan.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpinjamtahun(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM peminjaman_buku where waktu_pinjam='satu tahun' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pinjamtahun.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpinjam(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM peminjaman_buku where tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                totalJumlahPeminjam.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpengembalianminggu(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM pengembalian_buku where waktu_pinjam='seminggu' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pengembalianminggu.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpengembalianbulan(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM pengembalian_buku where waktu_pinjam='enam bulan' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pengembalianbulan.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpengembaliantahun(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM pengembalian_buku where waktu_pinjam='satu tahun' and tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                pengembaliantahun.setText(rs.getString("COUNT(waktu_pinjam)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    } 
    
    private void tampiljumlahpengembalian(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(waktu_pinjam) FROM pengembalian_buku where tgl_peminjaman='"+jtanggal.getText()+"'";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                totalJumlahPengembali.setText(rs.getString("COUNT(waktu_pinjam)"));
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

        menuKeluarAkun = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        jtanggal = new javax.swing.JLabel();
        jwaktu = new javax.swing.JLabel();
        totalJumlahPengembali = new javax.swing.JLabel();
        pengembalianbulan = new javax.swing.JLabel();
        pengembalianminggu = new javax.swing.JLabel();
        totalJumlahPeminjam = new javax.swing.JLabel();
        pinjamtahun = new javax.swing.JLabel();
        pinjamseminggu = new javax.swing.JLabel();
        jDendaKelompok = new javax.swing.JLabel();
        jDaftarPetugasMasuk = new javax.swing.JLabel();
        jDaftarPetugas = new javax.swing.JLabel();
        jPendaftarBaru = new javax.swing.JLabel();
        jDetailAnggota = new javax.swing.JLabel();
        jDetailBuku = new javax.swing.JLabel();
        jKategoriBuku = new javax.swing.JLabel();
        jhilang = new javax.swing.JLabel();
        jbagus = new javax.swing.JLabel();
        jrusak = new javax.swing.JLabel();
        pinjambulan = new javax.swing.JLabel();
        pengembaliantahun = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuKeluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        menuKeluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuKeluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuKeluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(menuKeluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 703, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 510, -1, -1));

        menuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPeminjaman.png"))); // NOI18N
        menuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(menuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 452, -1, -1));

        menuDataPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataPetugas.png"))); // NOI18N
        menuDataPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataPetugasMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 394, -1, -1));

        menuDataAnggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataAnggota.png"))); // NOI18N
        menuDataAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataAnggotaMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 336, -1, -1));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 278, -1, -1));

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuBeranda.png"))); // NOI18N
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 204, -1, -1));

        jtanggal.setBackground(new java.awt.Color(255, 255, 255));
        jtanggal.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jtanggal.setForeground(new java.awt.Color(50, 56, 70));
        jtanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jtanggal.setText("mon, 11 mar 2021");
        getContentPane().add(jtanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1192, 632, 100, 17));

        jwaktu.setBackground(new java.awt.Color(255, 255, 255));
        jwaktu.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jwaktu.setForeground(new java.awt.Color(50, 56, 70));
        jwaktu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jwaktu.setText("22:22 PM");
        getContentPane().add(jwaktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1192, 648, 100, 17));

        totalJumlahPengembali.setBackground(new java.awt.Color(255, 255, 255));
        totalJumlahPengembali.setFont(new java.awt.Font("Poppins Medium", 0, 19)); // NOI18N
        totalJumlahPengembali.setForeground(new java.awt.Color(50, 56, 70));
        totalJumlahPengembali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalJumlahPengembali.setText("1000");
        getContentPane().add(totalJumlahPengembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1224, 520, 43, 27));

        pengembalianbulan.setBackground(new java.awt.Color(255, 255, 255));
        pengembalianbulan.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pengembalianbulan.setForeground(new java.awt.Color(255, 255, 255));
        pengembalianbulan.setText("1000");
        getContentPane().add(pengembalianbulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1061, 580, 50, 17));

        pengembalianminggu.setBackground(new java.awt.Color(255, 255, 255));
        pengembalianminggu.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pengembalianminggu.setForeground(new java.awt.Color(255, 255, 255));
        pengembalianminggu.setText("1000");
        getContentPane().add(pengembalianminggu, new org.netbeans.lib.awtextra.AbsoluteConstraints(983, 580, 50, 17));

        totalJumlahPeminjam.setBackground(new java.awt.Color(255, 255, 255));
        totalJumlahPeminjam.setFont(new java.awt.Font("Poppins Medium", 0, 19)); // NOI18N
        totalJumlahPeminjam.setForeground(new java.awt.Color(50, 56, 70));
        totalJumlahPeminjam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalJumlahPeminjam.setText("1000");
        getContentPane().add(totalJumlahPeminjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(867, 520, 43, 27));

        pinjamtahun.setBackground(new java.awt.Color(255, 255, 255));
        pinjamtahun.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pinjamtahun.setForeground(new java.awt.Color(255, 255, 255));
        pinjamtahun.setText("1000");
        getContentPane().add(pinjamtahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(782, 580, 50, 17));

        pinjamseminggu.setBackground(new java.awt.Color(255, 255, 255));
        pinjamseminggu.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pinjamseminggu.setForeground(new java.awt.Color(255, 255, 255));
        pinjamseminggu.setText("1000");
        getContentPane().add(pinjamseminggu, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 580, 60, 17));

        jDendaKelompok.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jDendaKelompok.setForeground(new java.awt.Color(50, 56, 70));
        jDendaKelompok.setText(" ");
        getContentPane().add(jDendaKelompok, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, 64, 19));

        namaAkun.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        namaAkun.setForeground(new java.awt.Color(50, 56, 70));
        namaAkun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(namaAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1154, 325, 130, 16));

        namaPetugas.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        namaPetugas.setForeground(new java.awt.Color(50, 56, 70));
        namaPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(namaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1138, 307, 165, 17));

        jDaftarPetugasMasuk.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jDaftarPetugasMasuk.setForeground(new java.awt.Color(206, 18, 18));
        jDaftarPetugasMasuk.setText("1");
        getContentPane().add(jDaftarPetugasMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(899, 330, 30, 17));

        jDaftarPetugas.setFont(new java.awt.Font("Poppins Medium", 0, 75)); // NOI18N
        jDaftarPetugas.setForeground(new java.awt.Color(206, 18, 18));
        jDaftarPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDaftarPetugas.setText("3");
        getContentPane().add(jDaftarPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(917, 204, 90, 90));

        jPendaftarBaru.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jPendaftarBaru.setForeground(new java.awt.Color(50, 56, 70));
        jPendaftarBaru.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPendaftarBaru.setText("1000");
        getContentPane().add(jPendaftarBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 273, 50, 19));

        jDetailAnggota.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jDetailAnggota.setForeground(new java.awt.Color(50, 56, 70));
        jDetailAnggota.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jDetailAnggota.setText("1000");
        getContentPane().add(jDetailAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 234, 50, 19));

        jDetailBuku.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jDetailBuku.setForeground(new java.awt.Color(50, 56, 70));
        jDetailBuku.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jDetailBuku.setText("100");
        getContentPane().add(jDetailBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 273, 60, 19));

        jKategoriBuku.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jKategoriBuku.setForeground(new java.awt.Color(50, 56, 70));
        jKategoriBuku.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jKategoriBuku.setText("1000");
        getContentPane().add(jKategoriBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 234, 60, 19));

        jhilang.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jhilang.setForeground(new java.awt.Color(50, 56, 70));
        jhilang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jhilang.setText("50000000");
        getContentPane().add(jhilang, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 570, 70, 19));

        jbagus.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jbagus.setForeground(new java.awt.Color(50, 56, 70));
        jbagus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbagus.setText("50000000");
        getContentPane().add(jbagus, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 494, 70, 19));

        jrusak.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        jrusak.setForeground(new java.awt.Color(50, 56, 70));
        jrusak.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jrusak.setText("50000000");
        getContentPane().add(jrusak, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 533, 70, 19));

        pinjambulan.setBackground(new java.awt.Color(255, 255, 255));
        pinjambulan.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pinjambulan.setForeground(new java.awt.Color(255, 255, 255));
        pinjambulan.setText("1000");
        getContentPane().add(pinjambulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(704, 580, 50, 17));

        pengembaliantahun.setBackground(new java.awt.Color(255, 255, 255));
        pengembaliantahun.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        pengembaliantahun.setForeground(new java.awt.Color(255, 255, 255));
        pengembaliantahun.setText("1000");
        getContentPane().add(pengembaliantahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1139, 580, 50, 17));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/beranda.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku b = new peminjamanBuku();
        b.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPeminjamanMouseClicked

    private void menuDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataBukuMouseClicked
        // TODO add your handling code here:
        dataBuku a = new dataBuku();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataBukuMouseClicked

    private void menuDataAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataAnggotaMouseClicked
        // TODO add your handling code here:
        detailAnggota c = new detailAnggota();
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataAnggotaMouseClicked

    private void menuDataPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataPetugasMouseClicked
        // TODO add your handling code here:
        dataPetugas d = new dataPetugas();
        d.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataPetugasMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian e = new pengembalian();
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

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
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new beranda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel jDaftarPetugas;
    private javax.swing.JLabel jDaftarPetugasMasuk;
    private javax.swing.JLabel jDendaKelompok;
    private javax.swing.JLabel jDetailAnggota;
    private javax.swing.JLabel jDetailBuku;
    private javax.swing.JLabel jKategoriBuku;
    private javax.swing.JLabel jPendaftarBaru;
    private javax.swing.JLabel jbagus;
    private javax.swing.JLabel jhilang;
    private javax.swing.JLabel jrusak;
    private javax.swing.JLabel jtanggal;
    private javax.swing.JLabel jwaktu;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    public static final javax.swing.JLabel namaAkun = new javax.swing.JLabel();
    public static final javax.swing.JLabel namaPetugas = new javax.swing.JLabel();
    private javax.swing.JLabel pengembalianbulan;
    private javax.swing.JLabel pengembalianminggu;
    private javax.swing.JLabel pengembaliantahun;
    private javax.swing.JLabel pinjambulan;
    private javax.swing.JLabel pinjamseminggu;
    private javax.swing.JLabel pinjamtahun;
    private javax.swing.JLabel totalJumlahPeminjam;
    private javax.swing.JLabel totalJumlahPengembali;
    // End of variables declaration//GEN-END:variables
}
