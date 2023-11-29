public class Pemesanan {
    Pemesanan next;
    int kodepesanan;
    String nama;
    int kodebarangpesanan;
    String namabarang;
    int jumlahpesanan;
    int kota_tujuan;
    int kota_asal;
    int tanggal;
    int bulan;
    int tahun;
    Barang Brg;

    Pemesanan(int kodepesanan, String nama, int kodebarangpesanan, String namabarang, int jumlahpesanan,
            int kota_tujuan, int kota_asal, int tanggal, int bulan, int tahun) {
        this.next = null;
        this.kodepesanan = kodepesanan;
        this.nama = nama;
        this.kodebarangpesanan = kodebarangpesanan;
        this.namabarang = namabarang;
        this.jumlahpesanan = jumlahpesanan;
        this.kota_tujuan = kota_tujuan;
        this.kota_asal = kota_asal;
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.Brg = null;
    }
}
