public class Barang {
    int kodebarang;
    String namabarang;
    int stock;
    Barang next;

    Barang(int kodebarang, String namabarang, int stock) {
        this.kodebarang = kodebarang;
        this.namabarang = namabarang;
        this.stock = stock;
        this.next = null;
    }
}
