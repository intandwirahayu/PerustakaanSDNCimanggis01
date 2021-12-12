package Program;
//import com.placeholder.PlaceHolder;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.PrintStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
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
 * @author ASUS
 */
public class kategoriBuku extends javax.swing.JFrame {
    private Connection conn = new koneksi().getkoneksi();
    private DefaultTableModel tabmode;
    private String kdData, getCombo, getKondisiCetak;
    public String tglHariIni;
    /**
     * Creates new form kategoriBuku
     */
    public kategoriBuku() {
        initComponents();
        fieldNamaKategori.requestFocus();
        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "masukkan kata kunci");
        //membuat placeholder
//        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "kode / nama kategori / kelas");
//        PlaceHolder holder2 = new PlaceHolder(fieldKodeAwal, "masukkan tiga huruf");
        
        //menjalankan fungsi pemanggilan isi table
        datatable();
        tanggalHariIni();
        
        this.setLocationRelativeTo(null);

        //atur table
        table_kategori_buku.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        table_kategori_buku.getTableHeader().setOpaque(false);
        table_kategori_buku.getTableHeader().setBackground(new Color(206, 18, 18));
        table_kategori_buku.getTableHeader().setForeground(new Color(255, 255, 255));
    }
    
    private void resetInput(){
        fieldNamaKategori.setText("");
        fieldKodeAwal.setText("");
        jenjangKelas.setSelectedIndex(0);
        fieldJumlahAwal.setText("");
        fieldJumlahDipinjam.setText("");
        fieldSisaBuku.setText("");
    }
    
    private void tanggalHariIni(){
        try{
            java.util.Date ys = new java.util.Date(); // membuat oject ys dari class Date
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); //membuat object s dari class SimpleDateFormat dengan format (dd-MM-yyyy)yaitu (tanggal-bulan-tahun).
            tglHariIni = s.format(ys);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    protected void datatable(){
        try{
            Object [] Baris = {"Kode Kategori","Nama Kategori","Kode Awal", "Jenjang Kelas","Jumlah Awal","Jumlah Dipinjam","Sisa Buku", "Tanggal Input"};
            tabmode = new DefaultTableModel(null,Baris);
            table_kategori_buku.setModel(tabmode);

            String sql = "SELECT * FROM kategori_buku ORDER BY kode_kategori DESC";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_kategori");
                String b = hasil.getString("nama_kategori"); 
                String c = hasil.getString("kode_awal_kategori");
                String d = hasil.getString("jenjang_kelas");
                String e = hasil.getString("jumlah_awal");
                String f = hasil.getString("jumlah_dipinjam");
                String g = hasil.getString("sisa_buku");
                String h = hasil.getString("tanggal_input");
                
                String[] data = {a,b,c,d,e,f, g, h};
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
            String sql = "SELECT COUNT(nama_kategori) FROM kategori_buku";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlah_kategori_buku.setText(rs.getString("COUNT(nama_kategori)"));
                
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void editTampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(nama_kategori) FROM kategori_buku WHERE nama_kategori like '%" 
                        + fieldCariData.getText().toLowerCase() + "%'" 
                        + "or jenjang_kelas like '%" + fieldCariData.getText().toLowerCase() + "%'"
                        + "or kode_kategori like '%" + fieldCariData.getText().toLowerCase() + "%'"
                        + "or kode_awal_kategori like '%" + fieldCariData.getText().toLowerCase() + "%' ORDER BY jenjang_kelas ASC";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlah_kategori_buku.setText(rs.getString("COUNT(nama_kategori)"));
                
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void editData(){
        try{
            if(jenjangKelas.getSelectedItem().toString().equals("semua")){
                getCombo = "S";
            }else{
                getCombo = (String) jenjangKelas.getSelectedItem();
            }
            
            String query_update = "UPDATE kategori_buku SET kode_kategori = ?, nama_kategori = ?, kode_awal_kategori = ?, jenjang_kelas = ?, jumlah_awal = ?, jumlah_dipinjam = ?, sisa_buku = ? "
                                    + "WHERE kode_kategori = '" + kdData +"'";
                    
            PreparedStatement stat = conn.prepareStatement(query_update);
                    
            String kodeAwalKategori = fieldKodeAwal.getText().toUpperCase() + getCombo;
            stat.setString(1, kodeAwalKategori);
            stat.setString(2, fieldNamaKategori.getText().toLowerCase());
            stat.setString(3, fieldKodeAwal.getText().toUpperCase());
            stat.setString(4, getCombo);
            stat.setInt(5, Integer.valueOf(fieldJumlahAwal.getText()));
            stat.setInt(6, Integer.valueOf(fieldJumlahDipinjam.getText()));
            stat.setInt(7, Integer.valueOf(fieldSisaBuku.getText()));

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah");
            resetInput();
            datatable();
        }catch(Exception e){
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

        jumlah_kategori_buku = new javax.swing.JLabel();
        btnCari = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_kategori_buku = new javax.swing.JTable();
        btnHapusData = new javax.swing.JLabel();
        btnCetakData = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnEditData = new javax.swing.JLabel();
        btnMasukkanData = new javax.swing.JLabel();
        fieldSisaBuku = new javax.swing.JTextField();
        fieldJumlahDipinjam = new javax.swing.JTextField();
        fieldJumlahAwal = new javax.swing.JTextField();
        jenjangKelas = new javax.swing.JComboBox<>();
        fieldNamaKategori = new javax.swing.JTextField();
        fieldKodeAwal = new javax.swing.JTextField();
        menuKeluarAkun = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        fieldCariData = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jumlah_kategori_buku.setDisplayedMnemonic('2');
        jumlah_kategori_buku.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlah_kategori_buku.setForeground(new java.awt.Color(206, 18, 18));
        jumlah_kategori_buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jumlah_kategori_buku.setText("0");
        getContentPane().add(jumlah_kategori_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 127, 200, 52));

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
        getContentPane().add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 126, 50, 40));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        table_kategori_buku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        table_kategori_buku.setForeground(new java.awt.Color(47, 53, 66));
        table_kategori_buku.setModel(new javax.swing.table.DefaultTableModel(
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
        table_kategori_buku.setMinimumSize(new java.awt.Dimension(60, 100));
        table_kategori_buku.setRowHeight(25);
        table_kategori_buku.setSelectionBackground(new java.awt.Color(255, 214, 57));
        table_kategori_buku.setSelectionForeground(new java.awt.Color(47, 53, 66));
        table_kategori_buku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kategori_bukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_kategori_buku);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(643, 205, 661, 290));

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

        jComboBox1.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(47, 53, 66));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kondisi ==", "Seluruh Data", "Data Hari Ini", "Kondisi Pencarian" }));
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 566, 120, 30));

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

        fieldSisaBuku.setBackground(new java.awt.Color(238, 235, 221));
        fieldSisaBuku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldSisaBuku.setForeground(new java.awt.Color(47, 53, 66));
        fieldSisaBuku.setBorder(null);
        getContentPane().add(fieldSisaBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 583, 275, 40));

        fieldJumlahDipinjam.setBackground(new java.awt.Color(238, 235, 221));
        fieldJumlahDipinjam.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldJumlahDipinjam.setForeground(new java.awt.Color(47, 53, 66));
        fieldJumlahDipinjam.setBorder(null);
        getContentPane().add(fieldJumlahDipinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 496, 275, 40));

        fieldJumlahAwal.setBackground(new java.awt.Color(238, 235, 221));
        fieldJumlahAwal.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldJumlahAwal.setForeground(new java.awt.Color(47, 53, 66));
        fieldJumlahAwal.setBorder(null);
        getContentPane().add(fieldJumlahAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 409, 275, 40));

        jenjangKelas.setBackground(new java.awt.Color(249, 249, 249));
        jenjangKelas.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jenjangKelas.setForeground(new java.awt.Color(47, 53, 66));
        jenjangKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kelas ==", "1", "2", "3", "4", "5", "6", "semua" }));
        jenjangKelas.setBorder(null);
        getContentPane().add(jenjangKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 322, 300, 40));

        fieldNamaKategori.setBackground(new java.awt.Color(238, 235, 221));
        fieldNamaKategori.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldNamaKategori.setForeground(new java.awt.Color(47, 53, 66));
        fieldNamaKategori.setBorder(null);
        getContentPane().add(fieldNamaKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 148, 275, 40));

        fieldKodeAwal.setBackground(new java.awt.Color(238, 235, 221));
        fieldKodeAwal.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldKodeAwal.setForeground(new java.awt.Color(47, 53, 66));
        fieldKodeAwal.setBorder(null);
        getContentPane().add(fieldKodeAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 235, 275, 40));

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

        menuDataBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsBlack/menuDataBuku.png"))); // NOI18N
        menuDataBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDataBukuMouseClicked(evt);
            }
        });
        getContentPane().add(menuDataBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 264, -1, -1));

        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsWhite/menuBeranda.png"))); // NOI18N
        menuBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBerandaMouseClicked(evt);
            }
        });
        getContentPane().add(menuBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 204, -1, -1));

        fieldCariData.setBackground(new java.awt.Color(238, 235, 221));
        fieldCariData.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        fieldCariData.setForeground(new java.awt.Color(47, 53, 66));
        fieldCariData.setBorder(null);
        fieldCariData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fieldCariDataMouseExited(evt);
            }
        });
        fieldCariData.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                fieldCariDataInputMethodTextChanged(evt);
            }
        });
        fieldCariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyPressed(evt);
            }
        });
        getContentPane().add(fieldCariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1082, 126, 180, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/kategoriBuku.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasukkanDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseClicked
        try {
            if(jenjangKelas.getSelectedItem().toString() == "semua"){
                getCombo = "S";
            }else{
                getCombo = (String) jenjangKelas.getSelectedItem();
            }
            
            if(fieldKodeAwal.getText().length() > 3 || fieldKodeAwal.getText().length() < 3){
                //kondisi pengecekkan jumlah kode awal
                JOptionPane.showMessageDialog(null, "Kode awal kategori harus berjumlah tiga!");
            }else if(getCombo.equals("== Pilih Kelas ==")){
                //kondisi pengecekkan kelas
                JOptionPane.showMessageDialog(null, "Pilih kelas dahulu!");
            }else if(fieldNamaKategori.getText().equals("") || fieldKodeAwal.getText().equals("") || fieldJumlahAwal.getText().equals("") || fieldJumlahDipinjam.getText().equals("") || fieldSisaBuku.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            }else{
                //kondisi pengecekkan apakah kode kategori sudah digunakan atau belum
                //ambil data kode awal kategori dan jenjang kelas yang diinputkan
                String kodeAwalKelas = fieldKodeAwal.getText() + getCombo;
                //select kode kategori
                Statement s = conn.createStatement();
                String query_select = "SELECT kode_kategori FROM kategori_buku WHERE kode_kategori = '"+ kodeAwalKelas +"'";
                ResultSet rs = s.executeQuery(query_select);
                
                if(rs.next()){
                    //jika kode awal kategori sudah digunakan
                    JOptionPane.showMessageDialog(null, "Kode awal kategori dan jenjang kelas sudah dibuat!");
                }else{
                    //jika kode awal kategori belum digunakan maka masukkan data ke db
                    String sql = "INSERT INTO kategori_buku VALUES (?,?,?,?,?,?,?, ?)";
                
                    PreparedStatement stat = conn.prepareStatement(sql);

                    String kodeAwalKategori = fieldKodeAwal.getText().toUpperCase() + getCombo;
                    stat.setString(1, kodeAwalKategori);
                    stat.setString(2, fieldNamaKategori.getText().toLowerCase());
                    stat.setString(3, fieldKodeAwal.getText().toUpperCase());
                    stat.setString(4, getCombo);
                    stat.setInt(5, Integer.valueOf(fieldJumlahAwal.getText()));
                    stat.setInt(6, Integer.valueOf(fieldJumlahDipinjam.getText()));
                    stat.setInt(7, Integer.valueOf(fieldSisaBuku.getText()));
                    stat.setString(8, tglHariIni);
                    
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");
                    resetInput();
                    datatable();
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Data Gagal Di Tambahkan" + e);
        }
    }//GEN-LAST:event_btnMasukkanDataMouseClicked

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        try{
            Object [] Baris = {"Kode Kategori","Nama Kategori","Jenjang Kelas","Jumlah Awal","Jumlah Dipinjam","Sisa Buku"};
            tabmode = new DefaultTableModel(null,Baris);
            table_kategori_buku.setModel(tabmode);

            String sql = "SELECT * FROM kategori_buku WHERE kode_kategori like '%" + fieldCariData.getText() + "%' " 
                        + "or nama_kategori like '%" + fieldCariData.getText() + "%' "
                        + "or jenjang_kelas like '%" + fieldCariData.getText() + "%' "
                        + "or kode_awal_kategori like '%" + fieldCariData.getText() + "%' "
                        + "or tanggal_input like '%" + fieldCariData.getText() + "%' "
                        + "ORDER BY jenjang_kelas ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_kategori");
                String b = hasil.getString("nama_kategori");
                String c = hasil.getString("jenjang_kelas");
                String d = hasil.getString("jumlah_awal");
                String e = hasil.getString("jumlah_dipinjam");
                String f = hasil.getString("sisa_buku");
                
                String[] data = {a,b,c,d,e,f};
                tabmode.addRow(data);
            }
            
            editTampilJumlah();
        } catch(Exception e){
            System.out.print(e);
        }
    }//GEN-LAST:event_btnCariMouseClicked

    private void btnEditDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseClicked
        try {
            if(jenjangKelas.getSelectedItem().toString().equals("semua")){
                getCombo = "S";
            }else{
                getCombo = (String) jenjangKelas.getSelectedItem();
            }
            
            if(fieldKodeAwal.getText().length() > 3 || fieldKodeAwal.getText().length() < 3){
                //kondisi pengecekkan jumlah kode awal
                JOptionPane.showMessageDialog(null, "Kode awal kategori harus berjumlah tiga!");
            }else if(getCombo.equals("== Pilih Kelas ==")){
                //kondisi pengecekkan kelas
                JOptionPane.showMessageDialog(null, "Pilih kelas dahulu!");
            }else if(fieldNamaKategori.getText().equals("") || fieldKodeAwal.getText().equals("") || fieldJumlahAwal.getText().equals("") || fieldJumlahDipinjam.getText().equals("") || fieldSisaBuku.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            }else{
                //pengecekkan kode kategori
                String kodeAwalKategori = fieldKodeAwal.getText().toUpperCase() + getCombo;
                
                if(kodeAwalKategori.equals(kdData)){
                    //jika kode kategori ditentukan sama seperti sebelumnya, maka lolos untuk edit data
                    editData();
                }else{
                    //jika kode kategori terdapat perbedaan dari yang sebelumnya maka
                    Statement s = conn.createStatement();
                    String query_select = "SELECT kode_kategori FROM kategori_buku WHERE kode_kategori = '"+kodeAwalKategori+"'";
                    ResultSet rs = s.executeQuery(query_select);

                    if(rs.next()){
                        //jika kode awal kategori sudah digunakan
                        JOptionPane.showMessageDialog(null, "Kode awal kategori dan jenjang kelas sudah dibuat!");
                    }else{
                        editData();
                    }
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Gagal Ubah Data");
        }
    }//GEN-LAST:event_btnEditDataMouseClicked

    private void table_kategori_bukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kategori_bukuMouseClicked
        try{
            int table = table_kategori_buku.getSelectedRow();
            kdData = table_kategori_buku.getValueAt(table, 0).toString();
            
            Statement s = conn.createStatement();
            String sql = "SELECT * FROM kategori_buku WHERE kode_kategori = '"+kdData+"'";
            ResultSet rs = s.executeQuery(sql);
            System.out.println(table);
            if(rs.next()){
                fieldNamaKategori.setText(rs.getString("nama_kategori"));
                fieldKodeAwal.setText(rs.getString("kode_awal_kategori"));
                
                if(rs.getString("jenjang_kelas").equals("S")){
                    jenjangKelas.setSelectedItem("semua");
                }else{
                    jenjangKelas.setSelectedItem(rs.getString("jenjang_kelas"));
                }
                
                fieldJumlahAwal.setText(rs.getString("jumlah_awal"));
                fieldJumlahDipinjam.setText(rs.getString("jumlah_dipinjam"));
                fieldSisaBuku.setText(rs.getString("sisa_buku"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_table_kategori_bukuMouseClicked

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
        try{
            String sql = "DELETE FROM kategori_buku WHERE kode_kategori ='" + kdData + "'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            resetInput();
            datatable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void fieldCariDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldCariDataKeyPressed
        if(fieldCariData.getText().equals("")){
            datatable();
        }
    }//GEN-LAST:event_fieldCariDataKeyPressed

    private void fieldCariDataInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_fieldCariDataInputMethodTextChanged
        if(fieldCariData.getText().equals("")){
            datatable();
        }
    }//GEN-LAST:event_fieldCariDataInputMethodTextChanged

    private void fieldCariDataMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldCariDataMouseExited
        if(fieldCariData.getText().equals("")){
            datatable();
        }
    }//GEN-LAST:event_fieldCariDataMouseExited

    private void menuBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBerandaMouseClicked
        // TODO add your handling code here:
        beranda b = new beranda();
        b.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuBerandaMouseClicked

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

    private void menuDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDataBukuMouseClicked
        // TODO add your handling code here:
        dataBuku db = new dataBuku();
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuDataBukuMouseClicked

    private void btnCetakDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakDataMouseClicked
        try{
            getKondisiCetak = (String) jComboBox1.getSelectedItem();

            if(getKondisiCetak.equals("== Pilih Kondisi ==")){
                //kasih alert
                JOptionPane.showMessageDialog(this, "Pilih kondisi terlebih dahulu!!");
            }else if(getKondisiCetak.equals("Seluruh Data")){
                //cetak semua
                Map<String, Object> parameter2 = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/kategoriBuku.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/kategoriBuku.jrxml");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
                parameter1.put("kondisi", tglHariIni);
                
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter1, conn);
                JasperViewer.viewReport(jasperPrint, false);
            } else if(getKondisiCetak.equals("Kondisi Pencarian")){
                //data hari ini
                Map<String, Object> parameter = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/kategoriBuku.jrxml");
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
            java.util.logging.Logger.getLogger(kategoriBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kategoriBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kategoriBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kategoriBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kategoriBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnCari;
    private javax.swing.JLabel btnCetakData;
    private javax.swing.JLabel btnEditData;
    private javax.swing.JLabel btnHapusData;
    private javax.swing.JLabel btnMasukkanData;
    private javax.swing.JTextField fieldCariData;
    private javax.swing.JTextField fieldJumlahAwal;
    private javax.swing.JTextField fieldJumlahDipinjam;
    private javax.swing.JTextField fieldKodeAwal;
    private javax.swing.JTextField fieldNamaKategori;
    private javax.swing.JTextField fieldSisaBuku;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jenjangKelas;
    private javax.swing.JLabel jumlah_kategori_buku;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JTable table_kategori_buku;
    // End of variables declaration//GEN-END:variables
}
