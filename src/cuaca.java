
import javax.swing.JOptionPane; 
import javax.swing.table.DefaultTableModel; 
import java.io.FileWriter; 
import java.io.IOException; 
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author asus0
 */
public class cuaca extends javax.swing.JFrame {

    // HashMap untuk menyimpan kota dan frekuensi pengecekan
    private HashMap<String, Integer> kotaCheckCount = new HashMap<>();

    public cuaca() {
        initComponents();
        loadWeatherDataFromFile();
        loadCSVToTable();

    }

    private String getWeatherData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();
        return content.toString();
    }

    private void loadCSVToTable() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        // Bersihkan tabel sebelum memuat data baru
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("riwayat_cuaca.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Pisahkan berdasarkan koma
                if (data.length == 3) { // Pastikan ada 3 kolom (Kota, Cuaca, Suhu)
                    model.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
        } catch (IOException e) {
            // Jika file tidak ditemukan, tampilkan pesan error
            System.err.println("File CSV tidak ditemukan atau gagal dibaca: " + e.getMessage());
        }
    }

    private void addCityToFavoritesIfFrequent(String kota) {
        int count = kotaCheckCount.getOrDefault(kota, 0) + 1;
        kotaCheckCount.put(kota, count);

        if (count >= 3 && !isCityInComboBox(kota)) {
            lokasiComboBox.addItem(kota);
        }
    }

    private boolean isCityInComboBox(String kota) {
        for (int i = 0; i < lokasiComboBox.getItemCount(); i++) {
            if (lokasiComboBox.getItemAt(i).equalsIgnoreCase(kota)) {
                return true;
            }
        }
        return false;
    }

    private void loadWeatherDataFromFile() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        // Bersihkan tabel sebelum memuat data baru
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("riwayat_cuaca.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) { // Validasi format baris (3 kolom: Kota, Cuaca, Suhu)
                    model.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
        } catch (FileNotFoundException e) {
            // Jika file tidak ditemukan, buat file baru tanpa notifikasi gagal
            try (FileWriter writer = new FileWriter("riwayat_cuaca.csv")) {
                writer.write(""); // Buat file kosong
                System.out.println("File 'riwayat_cuaca.csv' tidak ditemukan. File baru berhasil dibuat.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal membuat file CSV baru.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            // Jika terjadi kesalahan lain saat membaca file
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data dari file CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String translateWeatherDescription(String description) {
        switch (description.toLowerCase()) {
            case "clear sky":
                return "cerah";
            case "few clouds":
                return "berawan sebagian";
            case "scattered clouds":
                return "awan tersebar";
            case "broken clouds":
                return "awan terputus";
            case "shower rain":
                return "hujan rintik-rintik";
            case "rain":
                return "hujan";
            case "thunderstorm":
                return "badai petir";
            case "snow":
                return "salju";
            case "mist":
                return "kabut";
            default:
                return description;
        }
    }

    private void setWeatherIcon(String kondisiCuaca) {
        String iconPath = "";

        switch (kondisiCuaca.toLowerCase()) {
            case "cerah":
            case "langit cerah":
                iconPath = "/icons/cerah.png";
                break;
            case "berawan sebagian":
            case "awan tersebar":
            case "awan terputus":
            case "sedikit berawan":
            case "awan mendung":
            case "awan pecah":
                iconPath = "/icons/cloudy.png";
                break;
            case "hujan":
            case "hujan rintik-rintik":
                iconPath = "/icons/rain.png";
                break;
            case "badai petir":
                iconPath = "/icons/thunderstorm.png";
                break;
            case "kabut":
            case "kabut asap":
                iconPath = "/icons/mist.png";
                break;
            default:
                iconPath = "/icons/default.png";
        }

        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            iconLabel.setIcon(new javax.swing.ImageIcon(imgURL));
        } else {
            System.err.println("Gambar ikon cuaca tidak ditemukan: " + iconPath);
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

        jPanel1 = new javax.swing.JPanel();
        lblCity = new javax.swing.JLabel();
        cuacaLabel = new javax.swing.JLabel();
        lokasiComboBox = new javax.swing.JComboBox<>();
        kotaTextField = new javax.swing.JTextField();
        cekCuacaButton = new javax.swing.JButton();
        iconLabel = new javax.swing.JLabel();
        simpanButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblCity.setText("Masukan Nama Kota");

        lokasiComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lokasiComboBoxActionPerformed(evt);
            }
        });

        kotaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotaTextFieldActionPerformed(evt);
            }
        });

        cekCuacaButton.setText("Cek Cuaca");
        cekCuacaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekCuacaButtonActionPerformed(evt);
            }
        });

        simpanButton.setText("Simpan");
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cuaca", "Kota"
            }
        ));
        jScrollPane1.setViewportView(dataTable);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Aplikasi Cek Cuaca");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCity)
                                .addGap(43, 43, 43)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(kotaTextField)
                                    .addComponent(cekCuacaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(simpanButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lokasiComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cuacaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kotaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lokasiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCity))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cuacaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cekCuacaButton)
                            .addComponent(simpanButton))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kotaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotaTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotaTextFieldActionPerformed

    private void lokasiComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lokasiComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lokasiComboBoxActionPerformed

    private void cekCuacaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekCuacaButtonActionPerformed
        String kota = kotaTextField.getText().trim();
        String apiKey = "abb850853cd82a520eab50d4ab88275e"; // Ganti dengan API key Anda
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + apiKey + "&units=metric&lang=id";

        // Validasi input kota
        if (kota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap masukkan nama kota!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ambil data dari API
            String response = getWeatherData(url);
            JSONObject jsonResponse = new JSONObject(response);

            // Parsing data JSON
            String kondisiCuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            double suhu = jsonResponse.getJSONObject("main").getDouble("temp");

            // Menampilkan hasil di cuacaLabel
            cuacaLabel.setText("Cuaca: " + kondisiCuaca + ", Suhu: " + suhu + "°C");

            // Panggil setWeatherIcon untuk menampilkan ikon
            setWeatherIcon(kondisiCuaca);

            // Tambahkan kota ke frekuensi pengecekan
            addCityToFavoritesIfFrequent(kota);
        } catch (Exception e) {
            // Tampilkan pesan error jika terjadi kesalahan
            cuacaLabel.setText("Kota tidak ditemukan atau terjadi kesalahan API.");
            JOptionPane.showMessageDialog(this, "Kota tidak ditemukan atau terjadi kesalahan API.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_cekCuacaButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        String kota = kotaTextField.getText().trim();
        String hasilCuaca = cuacaLabel.getText().trim();

        // Validasi input
        if (kota.isEmpty() || hasilCuaca.isEmpty() || hasilCuaca.contains("Kota tidak ditemukan")) {
            JOptionPane.showMessageDialog(this, "Data tidak valid atau belum tersedia. Harap cek cuaca terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pisahkan data hasilCuaca untuk mendapatkan deskripsi cuaca dan suhu
        String[] hasil = hasilCuaca.replace("Cuaca: ", "").split(", Suhu: ");
        if (hasil.length < 2) {
            JOptionPane.showMessageDialog(this, "Format data cuaca tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String kondisiCuaca = hasil[0].trim();
        String suhu = hasil[1].replace("°C", "").trim();

        // Tambahkan data ke tabel
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.addRow(new Object[]{kota, kondisiCuaca, suhu});

        // Simpan ke file CSV
        try (FileWriter writer = new FileWriter("riwayat_cuaca.csv", true)) {
            writer.append(kota).append(",").append(kondisiCuaca).append(",").append(suhu).append("\n");
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke file CSV.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke file CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_simpanButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new cuaca().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cekCuacaButton;
    private javax.swing.JLabel cuacaLabel;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kotaTextField;
    private javax.swing.JLabel lblCity;
    private javax.swing.JComboBox<String> lokasiComboBox;
    private javax.swing.JButton simpanButton;
    // End of variables declaration//GEN-END:variables
}
