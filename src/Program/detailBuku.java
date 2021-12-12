package Program;

//import com.placeholder.PlaceHolder;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author ASUS
 */
public class detailBuku extends javax.swing.JFrame {
    Connection conn = new koneksi().getkoneksi();
    private String kdkategori, kdBuku = "", getCombo, getKondisiCetak;
    private DefaultTableModel tabmode;
    SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
    String getNamaKategori;
    String getJenjangKelas;
    public String tgl, tglHariIni;
    /**
     * Creates new form kategoriBuku
     */
    public detailBuku() {
        initComponents();
        fieldJudulBuku.requestFocus();
        
        //membuat placeholder
        PlaceHolder holder1 = new PlaceHolder(fieldCariData, "masukkan kata kunci");
        
        //isi data ke select option nama kategori dan jenjang kelas
        showNamaKelaskategori();
        
        //menjalankan fungsi pemanggilan isi table
        datatable();
        
        this.setLocationRelativeTo(null);
        tanggalHariIni();
        
        //atur table
        table_detail_buku.getTableHeader().setFont(new Font("Poppins Light", Font.PLAIN, 13));
        table_detail_buku.getTableHeader().setOpaque(false);
        table_detail_buku.getTableHeader().setBackground(new Color(206, 18, 18));
        table_detail_buku.getTableHeader().setForeground(new Color(255, 255, 255));
    }
    
    private void resetInput(){
        selectNamaKategori.setSelectedIndex(0);
        selectJenjangKelas.setSelectedIndex(0);
        fieldJudulBuku.setText("");
        fieldPenulis.setText("");
        fieldPenerbit.setText("");
        selectStatusBuku.setSelectedIndex(0);
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
    
    private void resetKodeBuku(){
        try{
            getNamaKategori = (String) selectNamaKategori.getSelectedItem();
            getJenjangKelas = (String) selectJenjangKelas.getSelectedItem();
            String getStatusBuku = (String) selectStatusBuku.getSelectedItem();
           
            if(getNamaKategori.equals("pilih nama kat") && getJenjangKelas.equals("pilih kelas")){
                labelKodeBuku.setText("xxxxxx");
            }else if(getNamaKategori.equals("pilih nama kat") || !getJenjangKelas.equals("pilih kelas")){
                labelKodeBuku.setText("Kategori Belum Dibuat");
            }else if(!getNamaKategori.equals("pilih nama kat") || getJenjangKelas.equals("pilih kelas")){
                labelKodeBuku.setText("Kategori Belum Dibuat");
            }else{
                labelKodeBuku.setText("xxxxxx");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    protected void datatable(){
        try{
            Object [] Baris = {"Kode Buku", "Kode Kategori", "Judul Buku", "Penulis","Penerbit","Status Buku", "Tanggal Input"};
            tabmode = new DefaultTableModel(null,Baris);
            table_detail_buku.setModel(tabmode);

            String sql = "SELECT * FROM detail_buku ORDER BY tanggal_input DESC";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_buku");
                String b = hasil.getString("kode_kategori"); 
                String c = hasil.getString("judul_buku");
                String d = hasil.getString("penulis");
                String e = hasil.getString("penerbit");
                String f = hasil.getString("status_buku");
                String g = hasil.getString("tanggal_input");
                
                String[] data = {a,b,c,d,e,f, g};
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
            String sql = "SELECT COUNT(judul_buku) FROM detail_buku";
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlahDetailBuku.setText(rs.getString("COUNT(judul_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void editTampilJumlah(){
        try{
            Statement s = conn.createStatement();
            String sql = "SELECT COUNT(kode_buku) FROM detail_buku WHERE kode_buku like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_kategori like '%" + fieldCariData.getText() + "%' "
                        + "or judul_buku like '%" + fieldCariData.getText() + "%' "
                        + "or penulis like '%" + fieldCariData.getText() + "%' "
                        + "or penerbit like '%" + fieldCariData.getText() + "%' "
                        + "or status_buku like '%" + fieldCariData.getText() + "%' "
                        + "ORDER BY kode_buku DESC"; 
            ResultSet rs = s.executeQuery(sql);

            if(rs.next()){
                jumlahDetailBuku.setText(rs.getString("COUNT(kode_buku)"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void showNamaKelaskategori(){
        try{
            //select data nama kategori lalu menampilkan di select option
            Statement stm_nama_kategri = conn.createStatement();
            String select_nama_kategori = "SELECT DISTINCT nama_kategori FROM kategori_buku ORDER BY nama_kategori ASC";
            ResultSet rs_nama_kategori = stm_nama_kategri.executeQuery(select_nama_kategori);

            while(rs_nama_kategori.next()){
                selectNamaKategori.addItem(rs_nama_kategori.getString("nama_kategori"));
            }
            
            //select data jenjang kelas lalu menampilkan di select option
            Statement stm_jenjang_kelas = conn.createStatement();
            String select_jenjang_kelas = "SELECT DISTINCT jenjang_kelas FROM kategori_buku ORDER BY jenjang_kelas ASC";
            ResultSet rs_jenjang_kelas = stm_jenjang_kelas.executeQuery(select_jenjang_kelas);

            while(rs_jenjang_kelas.next()){
                if(rs_jenjang_kelas.getString("jenjang_kelas").equals("S")){
                    selectJenjangKelas.addItem("semua");
                }else{
                    selectJenjangKelas.addItem(rs_jenjang_kelas.getString("jenjang_kelas"));
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void findKodeKategori(){
        try{  
            //ambil data dari select option
            getNamaKategori = (String) selectNamaKategori.getSelectedItem();
            getJenjangKelas = (String) selectJenjangKelas.getSelectedItem();
            
            if(getJenjangKelas.equals("semua")){
                getJenjangKelas = "S";
            }else{
                getJenjangKelas = (String) selectJenjangKelas.getSelectedItem();
            }
 
            //cari kode kategori di table kategori buku berdasarkan data dari select option
            Statement stm = conn.createStatement();
            String query_select = "SELECT kode_kategori from kategori_buku where nama_kategori = '"+ getNamaKategori +"' AND jenjang_kelas = '"+ getJenjangKelas +"'";
            ResultSet rs = stm.executeQuery(query_select);

            if(rs.next()){
                //klo kategori buku sudah dibuat sebelumnya, maka masukkan data ke var lalu panggil method
                kdkategori = rs.getString("kode_kategori");
                makeKodeBuku();
            }else{
                //jika belum maka tampilkan dilabel dengan tulisan
//                labelKodeBuku.setText("Kategori Belum Dibuat");
                resetKodeBuku();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void makeKodeBuku(){
        try{
            Statement stm = conn.createStatement();
            ResultSet rs_select_kdData = stm.executeQuery("SELECT kode_buku, kode_kategori FROM detail_buku WHERE kode_buku = '" + kdBuku + "'");
            
            //untuk fungsi edit agar kode buku nilainya tidak naik
            //jika kode buku sudah terdapat didb dan 
            //jika kode kategori yang terdapat di select option sama seperti yng diambil di db
            //maka cetak kode yg dikasih dari tabel
            if(rs_select_kdData.next() && rs_select_kdData.getString("kode_kategori").equals(kdkategori)){
                labelKodeBuku.setText(kdBuku);
            }else{
                //untuk input data baru
                //dan juga untuk fungsi edit jika kode kategori yang terdapat di select option berbeda seperti yng diambil di db
                //maka ubah kode buku
                ResultSet rs = stm.executeQuery("SELECT kode_buku FROM detail_buku WHERE kode_kategori = '" + kdkategori + "' ORDER BY kode_buku DESC");

                if(rs.next()){
                    String noKode = rs.getString("kode_buku").substring(5);
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

                    labelKodeBuku.setText(kdkategori + "-" + nol + an);
                }else{
                    //jika belum terdapat kode buku
                    labelKodeBuku.setText(kdkategori + "-" + "0001");
                }
            }
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

        jumlahDetailBuku = new javax.swing.JLabel();
        btnCari = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_detail_buku = new javax.swing.JTable();
        btnHapusData = new javax.swing.JLabel();
        btnCetakData = new javax.swing.JLabel();
        kondisiCetak = new javax.swing.JComboBox<>();
        fieldCariData = new javax.swing.JTextField();
        btnEditData = new javax.swing.JLabel();
        btnMasukkanData = new javax.swing.JLabel();
        selectNamaKategori = new javax.swing.JComboBox<>();
        selectJenjangKelas = new javax.swing.JComboBox<>();
        fieldJudulBuku = new javax.swing.JTextField();
        fieldPenerbit = new javax.swing.JTextField();
        fieldPenulis = new javax.swing.JTextField();
        selectStatusBuku = new javax.swing.JComboBox<>();
        labelKodeBuku = new javax.swing.JLabel();
        menuKeluarAkun = new javax.swing.JLabel();
        menuPengembalian = new javax.swing.JLabel();
        menuPeminjaman = new javax.swing.JLabel();
        menuDataPetugas = new javax.swing.JLabel();
        menuDataAnggota = new javax.swing.JLabel();
        menuDataBuku = new javax.swing.JLabel();
        menuBeranda = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jumlahDetailBuku.setDisplayedMnemonic('2');
        jumlahDetailBuku.setFont(new java.awt.Font("Poppins", 0, 37)); // NOI18N
        jumlahDetailBuku.setForeground(new java.awt.Color(206, 18, 18));
        jumlahDetailBuku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jumlahDetailBuku.setText("0");
        getContentPane().add(jumlahDetailBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(681, 127, 200, 52));

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
        getContentPane().add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1271, 128, 50, 40));

        jScrollPane1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane1.setBorder(null);

        table_detail_buku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        table_detail_buku.setForeground(new java.awt.Color(47, 53, 66));
        table_detail_buku.setModel(new javax.swing.table.DefaultTableModel(
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
        table_detail_buku.setMinimumSize(new java.awt.Dimension(60, 100));
        table_detail_buku.setPreferredSize(new java.awt.Dimension(300, 1000));
        table_detail_buku.setRowHeight(30);
        table_detail_buku.setSelectionBackground(new java.awt.Color(255, 214, 57));
        table_detail_buku.setSelectionForeground(new java.awt.Color(47, 53, 66));
        table_detail_buku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_detail_bukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_detail_buku);

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

        kondisiCetak.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        kondisiCetak.setForeground(new java.awt.Color(47, 53, 66));
        kondisiCetak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih Kondisi ==", "Seluruh Data", "Data Hari Ini", "Kondisi Pencarian" }));
        kondisiCetak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(kondisiCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 566, 120, 30));

        fieldCariData.setBackground(new java.awt.Color(238, 235, 221));
        fieldCariData.setFont(new java.awt.Font("Poppins Light", 0, 11)); // NOI18N
        fieldCariData.setForeground(new java.awt.Color(47, 53, 66));
        fieldCariData.setBorder(null);
        fieldCariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldCariDataKeyPressed(evt);
            }
        });
        getContentPane().add(fieldCariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1082, 128, 180, 40));

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
        btnMasukkanData.setText("masukkan data");
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

        selectNamaKategori.setBackground(new java.awt.Color(249, 249, 249));
        selectNamaKategori.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        selectNamaKategori.setForeground(new java.awt.Color(47, 53, 66));
        selectNamaKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "= pilih nama kat =" }));
        selectNamaKategori.setBorder(null);
        selectNamaKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectNamaKategoriActionPerformed(evt);
            }
        });
        getContentPane().add(selectNamaKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 226, 144, 40));

        selectJenjangKelas.setBackground(new java.awt.Color(249, 249, 249));
        selectJenjangKelas.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        selectJenjangKelas.setForeground(new java.awt.Color(47, 53, 66));
        selectJenjangKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "= pilih kelas = " }));
        selectJenjangKelas.setBorder(null);
        selectJenjangKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectJenjangKelasActionPerformed(evt);
            }
        });
        getContentPane().add(selectJenjangKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(421, 226, 144, 40));

        fieldJudulBuku.setBackground(new java.awt.Color(238, 235, 221));
        fieldJudulBuku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldJudulBuku.setForeground(new java.awt.Color(47, 53, 66));
        fieldJudulBuku.setBorder(null);
        getContentPane().add(fieldJudulBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 318, 275, 40));

        fieldPenerbit.setBackground(new java.awt.Color(238, 235, 221));
        fieldPenerbit.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldPenerbit.setForeground(new java.awt.Color(47, 53, 66));
        fieldPenerbit.setBorder(null);
        fieldPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPenerbitActionPerformed(evt);
            }
        });
        getContentPane().add(fieldPenerbit, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 502, 275, 40));

        fieldPenulis.setBackground(new java.awt.Color(238, 235, 221));
        fieldPenulis.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        fieldPenulis.setForeground(new java.awt.Color(47, 53, 66));
        fieldPenulis.setBorder(null);
        getContentPane().add(fieldPenulis, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 410, 275, 40));

        selectStatusBuku.setBackground(new java.awt.Color(249, 249, 249));
        selectStatusBuku.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        selectStatusBuku.setForeground(new java.awt.Color(47, 53, 66));
        selectStatusBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "= pilih status =", "bantuan", "beli", "sumbangan", "lainnya" }));
        selectStatusBuku.setBorder(null);
        selectStatusBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectStatusBukuActionPerformed(evt);
            }
        });
        getContentPane().add(selectStatusBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 594, 300, 40));

        labelKodeBuku.setFont(new java.awt.Font("Poppins Medium", 0, 22)); // NOI18N
        labelKodeBuku.setForeground(new java.awt.Color(47, 53, 66));
        labelKodeBuku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKodeBuku.setText("xxxxxx");
        getContentPane().add(labelKodeBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 145, 300, 31));

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

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(47, 53, 66));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("reset input");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 688, 80, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/detailBuku.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectStatusBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectStatusBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectStatusBukuActionPerformed

    private void fieldPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPenerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPenerbitActionPerformed

    private void selectNamaKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectNamaKategoriActionPerformed
        findKodeKategori();
    }//GEN-LAST:event_selectNamaKategoriActionPerformed

    private void selectJenjangKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectJenjangKelasActionPerformed
        findKodeKategori();
    }//GEN-LAST:event_selectJenjangKelasActionPerformed

    private void btnMasukkanDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseClicked
        try {
            String getStatusBuku = (String) selectStatusBuku.getSelectedItem();
            getNamaKategori = (String) selectNamaKategori.getSelectedItem();
            getJenjangKelas = (String) selectJenjangKelas.getSelectedItem();
            
            if(selectJenjangKelas.getSelectedItem().toString().equals("semua")){
                getCombo = "S";
            }else{
                getCombo = (String) selectJenjangKelas.getSelectedItem();
            }
            
            if(!kdBuku.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi edit data!");
            }else if(labelKodeBuku.getText().equals("xxxxxx") || labelKodeBuku.getText().equals("Kategori Belum Dibuat")){
                //kondisi jika kodebuku masih nilai default
                JOptionPane.showMessageDialog(null, "Atur kode buku terlebih dahulu!");
            }else if(getNamaKategori.equals("= pilih nama kat =") || getJenjangKelas.equals("= pilih kelas =")){
                //kondisi pengecekkan nama kategori dan kelas
                JOptionPane.showMessageDialog(null, "Tentukan nama kategori dan jenjang kelas dahulu!");
            }else if(fieldJudulBuku.getText().equals("") || fieldPenulis.getText().equals("") || fieldPenerbit.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            }else if(getStatusBuku.equals("= pilih status =")){
                //kondisi pengecekkan nama kategori dan kelas
                JOptionPane.showMessageDialog(null, "Tentukan status buku dahulu!");
            }else{
                String sql = "INSERT INTO detail_buku VALUES (?,?,?,?,?,?,?)";
                
                PreparedStatement stat = conn.prepareStatement(sql);
                
                stat.setString(1, labelKodeBuku.getText());
                stat.setString(2, kdkategori);
                stat.setString(3, fieldJudulBuku.getText());
                stat.setString(4, fieldPenulis.getText());
                stat.setString(5, fieldPenerbit.getText());
                stat.setString(6, getStatusBuku);
                stat.setString(7, tglHariIni);

                stat.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");
                kdBuku = "";
                resetKodeBuku();
                datatable();
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Data Gagal Di Tambahkan" + e);
        }
    }//GEN-LAST:event_btnMasukkanDataMouseClicked

    private void table_detail_bukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_detail_bukuMouseClicked
        try{
            int table = table_detail_buku.getSelectedRow();
            kdBuku = table_detail_buku.getValueAt(table, 0).toString();
            
            Statement s = conn.createStatement();
            String sql = "SELECT detail_buku.kode_buku, kategori_buku.nama_kategori, kategori_buku.jenjang_kelas, detail_buku.judul_buku, "
                    + "detail_buku.penulis, detail_buku.penerbit, detail_buku.status_buku "
                    + "FROM kategori_buku, detail_buku WHERE kategori_buku.kode_kategori = detail_buku.kode_kategori AND "
                    + "detail_buku.kode_buku = '"+kdBuku+"'";
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.next()){
                labelKodeBuku.setText(rs.getString("detail_buku.kode_buku"));
                selectNamaKategori.setSelectedItem(rs.getString("kategori_buku.nama_kategori"));
                
                if(rs.getString("kategori_buku.jenjang_kelas").equals("S")){
                    selectJenjangKelas.setSelectedItem("semua");
                }else{
                    selectJenjangKelas.setSelectedItem(rs.getString("kategori_buku.jenjang_kelas"));
                }               
                
                fieldJudulBuku.setText(rs.getString("detail_buku.judul_buku"));
                fieldPenulis.setText(rs.getString("detail_buku.penulis"));
                fieldPenerbit.setText(rs.getString("detail_buku.penerbit"));
                selectStatusBuku.setSelectedItem(rs.getString("detail_buku.status_buku"));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_table_detail_bukuMouseClicked

    private void btnHapusDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusDataMouseClicked
        try{
            String sql = "DELETE FROM detail_buku WHERE kode_buku ='" + kdBuku + "'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            resetKodeBuku();
            resetInput();
            datatable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiHapus" + e);
        }
    }//GEN-LAST:event_btnHapusDataMouseClicked

    private void btnEditDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseClicked
        try {
            String getStatusBuku = (String) selectStatusBuku.getSelectedItem();
            getNamaKategori = (String) selectNamaKategori.getSelectedItem();
            getJenjangKelas = (String) selectJenjangKelas.getSelectedItem();
            
            if(kdBuku.equals("")){
                JOptionPane.showMessageDialog(null, "hanya bisa melakukan aksi masukkan data!");
            }else if(labelKodeBuku.getText().equals("xxxxxx") || labelKodeBuku.getText().equals("Kategori Belum Dibuat")){
                //kondisi jika kodebuku masih nilai default
                JOptionPane.showMessageDialog(null, "Atur kode buku terlebih dahulu!");
            }else if(getNamaKategori.equals("= pilih nama kat =") || getJenjangKelas.equals("= pilih kelas =")){
                //kondisi pengecekkan nama kategori dan kelas
                JOptionPane.showMessageDialog(null, "Tentukan nama kategori dan jenjang kelas dahulu!");
            }else if(fieldJudulBuku.getText().equals("") || fieldPenulis.getText().equals("") || fieldPenerbit.getText().equals("")){
                //kondisi pengecekkan jika terdapat form kosong
                JOptionPane.showMessageDialog(null, "Form tidak boleh kosong!");
            }else if(getStatusBuku.equals("= pilih status =")){
                //kondisi pengecekkan nama kategori dan kelas
                JOptionPane.showMessageDialog(null, "Tentukan status buku dahulu!");
            }else{
                String query_update = "UPDATE detail_buku SET kode_buku = ?, kode_kategori = ?, judul_buku = ?, penulis = ?, penerbit = ?, status_buku = ? "
                                  + "WHERE kode_buku = '" + kdBuku +"'";
                    
                PreparedStatement stat = conn.prepareStatement(query_update);

                stat.setString(1, labelKodeBuku.getText());
                stat.setString(2, kdkategori);
                stat.setString(3, fieldJudulBuku.getText());
                stat.setString(4, fieldPenulis.getText());
                stat.setString(5, fieldPenerbit.getText());
                stat.setString(6, getStatusBuku);

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah");
                resetKodeBuku();
                resetInput();
                datatable();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Gagal Di Ubah" + e);
        }
    }//GEN-LAST:event_btnEditDataMouseClicked

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        try{
            Object [] Baris = {"Kode Buku","Kode Kategori","Judul Buku","Penulis","Penerbit","Status Buku"};
            tabmode = new DefaultTableModel(null,Baris);
            table_detail_buku.setModel(tabmode);

            String sql = "SELECT * FROM detail_buku WHERE kode_buku like '%" + fieldCariData.getText() + "%' " 
                        + "or kode_kategori like '%" + fieldCariData.getText() + "%' "
                        + "or judul_buku like '%" + fieldCariData.getText() + "%' "
                        + "or penulis like '%" + fieldCariData.getText() + "%' "
                        + "or penerbit like '%" + fieldCariData.getText() + "%' "
                        + "or status_buku like '%" + fieldCariData.getText() + "%' "
                        + "ORDER BY kode_buku ASC"; 
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                String a = hasil.getString("kode_buku");
                String b = hasil.getString("kode_kategori");
                String c = hasil.getString("judul_buku");
                String d = hasil.getString("penulis");
                String e = hasil.getString("penerbit");
                String f = hasil.getString("status_buku");
                
                String[] data = {a,b,c,d,e,f};
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

    private void btnEditDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditDataMouseEntered

    private void btnMasukkanDataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukkanDataMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMasukkanDataMouseEntered

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
            getKondisiCetak = (String) kondisiCetak.getSelectedItem();

            if(getKondisiCetak.equals("== Pilih Kondisi ==")){
                //kasih alert
                JOptionPane.showMessageDialog(this, "Pilih kondisi terlebih dahulu!!");
            }else if(getKondisiCetak.equals("Seluruh Data")){
                //cetak semua
                Map<String, Object> parameter2 = new HashMap <String, Object>();
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/detailBuku.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/detailBuku.jrxml");
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
                File file = new File("D:/Project KKP/perpustakaanSDNCimanggis01/src/Laporan/detailBuku.jrxml");
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

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        resetInput();
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(detailBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detailBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detailBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detailBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new detailBuku().setVisible(true);
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
    private javax.swing.JTextField fieldJudulBuku;
    private javax.swing.JTextField fieldPenerbit;
    private javax.swing.JTextField fieldPenulis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jumlahDetailBuku;
    private javax.swing.JComboBox<String> kondisiCetak;
    private javax.swing.JLabel labelKodeBuku;
    private javax.swing.JLabel menuBeranda;
    private javax.swing.JLabel menuDataAnggota;
    private javax.swing.JLabel menuDataBuku;
    private javax.swing.JLabel menuDataPetugas;
    private javax.swing.JLabel menuKeluarAkun;
    private javax.swing.JLabel menuPeminjaman;
    private javax.swing.JLabel menuPengembalian;
    private javax.swing.JComboBox<String> selectJenjangKelas;
    private javax.swing.JComboBox<String> selectNamaKategori;
    private javax.swing.JComboBox<String> selectStatusBuku;
    private javax.swing.JTable table_detail_buku;
    // End of variables declaration//GEN-END:variables
}
