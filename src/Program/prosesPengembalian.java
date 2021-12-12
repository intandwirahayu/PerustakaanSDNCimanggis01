package Program;
import static Program.prosesPeminjaman.kodepetugas;
import com.placeholder.PlaceHolder;
import java.sql.*;
import java.util.Calendar;
import java.awt.Color;
import java.awt.Font;
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
import koneksi.koneksi;


/**
 *
 * @author USER
 */
public class prosesPengembalian extends javax.swing.JFrame {
    private Connection conn = new koneksi().getkoneksi();
    private String kdPeminjaman, kdPetugas, kodeBuku, kdAnggota, waktuPeminjaman, tglPinjam, tglBatasPinjam;
    private String kdDataPinjam = "", tglHariIni; 
    private DefaultTableModel tabmode;
    Statement stm;
    ResultSet rs;
    public static String idPetugas;
    
    /** Creates new form prosesPengembalian */
    public prosesPengembalian() {
        initComponents();
        idPetugas = masukAkun.getIdPetugas();
        PlaceHolder holder1 = new PlaceHolder(cariData, "masukkan kata kunci");
        tampilPeminjam();
        datatable();
        
        tablePengembalian.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        tablePengembalian.getTableHeader().setOpaque(false);
        tablePengembalian.getTableHeader().setBackground(new Color(206, 18, 18));
        tablePengembalian.getTableHeader().setForeground(new Color(255, 255, 255));

        tanggalHariIni();
        tanggalBatasPinjam.setText("0000-00-00");
        kodePengembalian.setText("xxxxxxx");
    }
    
    private void tanggalHariIni(){
        try{
            Date ys = new Date(); // membuat oject ys dari class Date
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); //membuat object s dari class SimpleDateFormat dengan format (dd-MM-yyyy)yaitu (tanggal-bulan-tahun).
            tanggalPengembalian.setText(s.format(ys));
            tglHariIni = s.format(ys);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    

    private void tampilJumlahBuku(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_buku) FROM peminjaman_buku";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlahBukuDipinjam.setText(rs.getString("COUNT(kode_buku)"));
            }
        } catch(Exception e){
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
            Object [] Baris = {"Kode Pinjam","Kode Petugas" ,"Kode Buku","Kode Anggota","Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tablePengembalian.setModel(tabmode);

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
            
            tampilJumlahBuku();
        } catch(Exception e){
            System.out.print(e);
        }
    }
    
    private void pernyataanPengembalian(){
        try{
            //menentukan pernyataan telat/tidak dalam mengembalikan buku
            int tahunBatasPengembalian = Integer.parseInt(tglBatasPinjam.substring(0,4));
            int bulanBatasPengembalian = Integer.parseInt(tglBatasPinjam.substring(5,7));
            int hariBatasPengembalian = Integer.parseInt(tglBatasPinjam.substring(8));

            int tahunHariIni = Integer.parseInt(tglHariIni.substring(0,4));
            int bulanHariIni = Integer.parseInt(tglHariIni.substring(5,7));
            int hariIni = Integer.parseInt(tglHariIni.substring(8));
                
            //cek tahun
            boolean telatTahun = false;
            if(tahunBatasPengembalian >= tahunHariIni){
                telatTahun = false;
            }else{
                telatTahun = true;
            }
                
            //cek bulan
            boolean telatBulan = false;
            if(bulanBatasPengembalian >= bulanHariIni){
                telatBulan = false;
            }else{
                telatBulan = true;
            }
                
            //cek hari
            boolean telatHari = false;
            if(hariBatasPengembalian >= hariIni){
                telatHari = false;
            }else{
                telatHari = true;
            }
            
            
            if(telatTahun == false && telatBulan == false && telatHari == false){
                keteranganPengembalian.setText("ANGGOTA TIDAK TERLAMBAT MENGEMBALIKAN BUKU");
            }else{
                keteranganPengembalian.setText("ANGGOTA TERLAMBAT MENGEMBALIKAN BUKU!!");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void menentukanKodePengembalian(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT kode_pengembalian FROM pengembalian_buku WHERE "
                    + "kode_peminjaman = '"+ kdPeminjaman +"' AND kode_anggota = '"+ kdAnggota +"' "
                    + "AND tgl_peminjaman = '"+ tglPinjam +"'";
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.next()){
                kodePengembalian.setText(rs.getString("kode_pengembalian"));
            }else{
                newKodePengembalian();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void newKodePengembalian(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT kode_pengembalian FROM pengembalian_buku ORDER BY kode_pengembalian DESC";
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.next()){
                String noKode = rs.getString("kode_pengembalian").substring(5);
                String an = "" + (Integer.parseInt(noKode) + 1);
                
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
                
                kodePengembalian.setText("PBI" + nol + an);
            }else{
                kodePengembalian.setText("PBI0001");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void resetElement(){
        tanggalHariIni();
        tampilPeminjam();
        tampilJumlahBuku();
        datatable();
        keteranganPengembalian.setText("");
        tanggalBatasPinjam.setText("0000-00-00");
        kodePengembalian.setText("xxxxxxx");
        kondisiBuku.setSelectedIndex(0);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        jumlahPeminjaman = new javax.swing.JLabel();
        jumlahBukuDipinjam = new javax.swing.JLabel();
        kondisiBuku = new javax.swing.JComboBox<>();
        btnSelesaiPengembalian = new javax.swing.JLabel();
        tanggalPengembalian = new javax.swing.JLabel();
        tanggalBatasPinjam = new javax.swing.JLabel();
        btncari = new javax.swing.JLabel();
        cariData = new javax.swing.JTextField();
        keteranganPengembalian = new javax.swing.JLabel();
        kodePengembalian = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePengembalian = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
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
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 189, -1, -1));

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 248, -1, -1));

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
        getContentPane().add(menuDataPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 365, -1, -1));

        menuPeminjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuPeminjaman.png"))); // NOI18N
        menuPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPeminjamanMouseClicked(evt);
            }
        });
        getContentPane().add(menuPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 422, -1, -1));

        menuPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuPengembalian.png"))); // NOI18N
        menuPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(menuPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 498, -1, -1));

        keluarAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuKeluarAkun.png"))); // NOI18N
        keluarAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keluarAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarAkunMouseClicked(evt);
            }
        });
        getContentPane().add(keluarAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 702, -1, -1));

        jumlahPeminjaman.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahPeminjaman.setForeground(new java.awt.Color(206, 18, 18));
        jumlahPeminjaman.setText("0");
        getContentPane().add(jumlahPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 126, 80, 60));

        jumlahBukuDipinjam.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahBukuDipinjam.setForeground(new java.awt.Color(206, 18, 18));
        jumlahBukuDipinjam.setText("0");
        getContentPane().add(jumlahBukuDipinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 126, 110, 60));

        kondisiBuku.setFont(new java.awt.Font("Poppins Light", 0, 13)); // NOI18N
        kondisiBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- pilih -", "Bagus", "Rusak", "Hilang" }));
        getContentPane().add(kondisiBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(979, 688, 169, 35));

        btnSelesaiPengembalian.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        btnSelesaiPengembalian.setForeground(new java.awt.Color(47, 53, 66));
        btnSelesaiPengembalian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSelesaiPengembalian.setText("selesai pengembalian");
        btnSelesaiPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelesaiPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSelesaiPengembalianMouseClicked(evt);
            }
        });
        getContentPane().add(btnSelesaiPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(1173, 688, 148, 35));

        tanggalPengembalian.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        tanggalPengembalian.setForeground(new java.awt.Color(47, 53, 66));
        getContentPane().add(tanggalPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 702, 120, 26));

        tanggalBatasPinjam.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        tanggalBatasPinjam.setForeground(new java.awt.Color(47, 53, 66));
        getContentPane().add(tanggalBatasPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 702, 120, 26));

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
        getContentPane().add(btncari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 127, 50, 40));

        cariData.setBackground(new java.awt.Color(238, 235, 221));
        cariData.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        cariData.setForeground(new java.awt.Color(47, 53, 66));
        cariData.setBorder(null);
        cariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cariDataKeyTyped(evt);
            }
        });
        getContentPane().add(cariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1033, 127, 227, 40));

        keteranganPengembalian.setFont(new java.awt.Font("Poppins Medium", 0, 19)); // NOI18N
        keteranganPengembalian.setForeground(new java.awt.Color(47, 53, 66));
        getContentPane().add(keteranganPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 566, 690, 40));

        kodePengembalian.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        kodePengembalian.setForeground(new java.awt.Color(47, 53, 66));
        getContentPane().add(kodePengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 690, 120, 30));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        tablePengembalian.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        tablePengembalian.setForeground(new java.awt.Color(47, 53, 66));
        tablePengembalian.setModel(new javax.swing.table.DefaultTableModel(
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
        tablePengembalian.setMinimumSize(new java.awt.Dimension(60, 100));
        tablePengembalian.setPreferredSize(new java.awt.Dimension(300, 1000));
        tablePengembalian.setRowHeight(30);
        tablePengembalian.setSelectionBackground(new java.awt.Color(255, 214, 57));
        tablePengembalian.setSelectionForeground(new java.awt.Color(47, 53, 66));
        tablePengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePengembalianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePengembalian);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(284, 205, 1020, 240));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 490, 260, 70));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/prosesPengembalian.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBerandaMouseClicked
//        // TODO add your handling code here:
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
            tablePengembalian.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE "
                        + "kode_peminjaman like '%" + cariData.getText() + "%' " 
                        + "or kode_buku like '%" + cariData.getText() + "%' "
                        + "or kode_anggota like '%" + cariData.getText() + "%' ";
            
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
            
            tampilJumlahBuku();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_btncariMouseClicked

    private void cariDataKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariDataKeyTyped
        // TODO add your handling code here:
        try{
            Object [] Baris = {"Kode Pinjam", "Kode Petugas", "Kode Buku", "Kode Anggota", "Waktu Peminjaman","Tanggal Pinjam","Tanggal Batas Pinjam"};
            tabmode = new DefaultTableModel(null,Baris);
            tablePengembalian.setModel(tabmode);

            String sql = "SELECT  kode_peminjaman, kode_petugas, kode_buku, kode_anggota, waktu_pinjam, tgl_peminjaman, tgl_batas_peminjaman FROM  peminjaman_buku WHERE kode_peminjaman like '%" + cariData.getText() + "%' " 
                        + "or kode_buku like '%" + cariData.getText() + "%' "
                        + "or kode_anggota like '%" + cariData.getText() + "%' ";
            
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
            
            tampilJumlahBuku();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_cariDataKeyTyped

    private void btnSelesaiPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelesaiPengembalianMouseClicked
        try{
            if(kodePengembalian.getText().equals("xxxxxxx")){
                JOptionPane.showMessageDialog(null, "Pilih data pada tabel!");
            } else if(kondisiBuku.getSelectedItem().toString() == "- pilih -"){
                JOptionPane.showMessageDialog(null, "Pilih kondisi buku");
            } else {
            //input data ke table pengembalian_buku
            String sql_insert = "INSERT INTO pengembalian_buku VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stat_insert = conn.prepareStatement(sql_insert);

            stat_insert.setString(1, kodePengembalian.getText());
            stat_insert.setString(2, kdPeminjaman);
            stat_insert.setString(3, idPetugas);
            stat_insert.setString(4, kodeBuku);
            stat_insert.setString(5, kdAnggota);
            stat_insert.setString(6, kondisiBuku.getSelectedItem().toString()); 
            stat_insert.setString(7, waktuPeminjaman);
            stat_insert.setString(8, tglPinjam);
            stat_insert.setString(9, tglBatasPinjam);
            stat_insert.setString(10, tanggalPengembalian.getText());

            stat_insert.executeUpdate();
            
     
            //hapus data di table peminjaman_buku
            String sql_delete = "DELETE FROM peminjaman_buku WHERE "
                    + "kode_peminjaman = '"+ kdPeminjaman +"' AND "
                    + "kode_buku = '"+ kodeBuku +"' AND "
                    + "kode_anggota = '"+ kdAnggota+"' AND "
                    + "tgl_batas_peminjaman = '"+ tglBatasPinjam +"'";
            PreparedStatement stat_delete = conn.prepareStatement(sql_delete);
            stat_delete.executeUpdate();
            
            
            //ubah data jumlah_dipinjam dan sisa_buku pada table kategori_buku
            //cari data dahulu di table kategori_buku
            String kdkategori = kodeBuku.substring(0,4);
            
            Statement s = conn.createStatement();
            String sql_select = "SELECT * FROM kategori_buku WHERE kode_kategori = '"+ kdkategori +"'";
            ResultSet rs_select = s.executeQuery(sql_select);
            System.out.println(kdkategori);
            if(rs_select.next()){
                System.out.println("masuk if select btn");
                int jumlah_dipinjam = Integer.parseInt(rs_select.getString("jumlah_dipinjam"));
                int sisa_buku = Integer.parseInt(rs_select.getString("sisa_buku"));
            
                //proses ubah data
                String sql_update = "UPDATE kategori_buku SET jumlah_dipinjam = ?, sisa_buku = ? WHERE kode_kategori = '" + kdkategori +"'";
                    
                PreparedStatement stat = conn.prepareStatement(sql_update);

                jumlah_dipinjam = jumlah_dipinjam - 1;
                sisa_buku = sisa_buku + 1;
                
                System.out.println(jumlah_dipinjam);
                System.out.println(sisa_buku);
                
                stat.setInt(1, jumlah_dipinjam);
                stat.setInt(2, sisa_buku);

                stat.executeUpdate();
                
                resetElement();
                JOptionPane.showMessageDialog(null, "Berhasil Dikembalikan");
            }else{
                JOptionPane.showMessageDialog(null, "Gagal Dikembalikan");
            }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btnSelesaiPengembalianMouseClicked

    private void menuPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPeminjamanMouseClicked
        // TODO add your handling code here:
        peminjamanBuku da = new peminjamanBuku();
        da.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPeminjamanMouseClicked

    private void tablePengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePengembalianMouseClicked
       try{
            int table = tablePengembalian.getSelectedRow();
            kdPeminjaman = tablePengembalian.getValueAt(table, 0).toString();
            kodeBuku = tablePengembalian.getValueAt(table, 2).toString();
            kdAnggota = tablePengembalian.getValueAt(table, 3).toString();
            waktuPeminjaman = tablePengembalian.getValueAt(table, 4).toString();
            tglPinjam = tablePengembalian.getValueAt(table, 5).toString();
            tglBatasPinjam = tablePengembalian.getValueAt(table, 6).toString();
            
            tanggalBatasPinjam.setText(tglBatasPinjam);
            
            pernyataanPengembalian();
            menentukanKodePengembalian();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tablePengembalianMouseClicked

    private void menuPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPengembalianMouseClicked
        // TODO add your handling code here:
        pengembalian pe = new pengembalian();
        pe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuPengembalianMouseClicked

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
            java.util.logging.Logger.getLogger(prosesPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(prosesPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(prosesPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(prosesPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new prosesPengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnSelesaiPengembalian;
    private javax.swing.JLabel btncari;
    private javax.swing.JTextField cariData;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jumlahBukuDipinjam;
    private javax.swing.JLabel jumlahPeminjaman;
    private javax.swing.JLabel keluarAkun;
    private javax.swing.JLabel keteranganPengembalian;
    private javax.swing.JLabel kodePengembalian;
    private javax.swing.JComboBox<String> kondisiBuku;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable tablePengembalian;
    private javax.swing.JLabel tanggalBatasPinjam;
    private javax.swing.JLabel tanggalPengembalian;
    // End of variables declaration//GEN-END:variables

}
