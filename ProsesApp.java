import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ProsesApp {
    static Login head = null;
    static Barang headBarang = null;
    static Pemesanan headPemesanan = null;

    static int[][] graph = {
            { 0, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 2, 2, 3, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0, 0, 3, 0 },
            { 0, 0, 0, 2, 0, 3, 0, 0 },
            { 0, 0, 3, 0, 0, 0, 0, 2 },
            { 0, 0, 0, 0, 1, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    // menu
    static void menu () {
        int choice, feature;
        Scanner scanner = new Scanner(System.in);
        do {
            menu: System.out.println("\n\t====================================================");
            System.out.println("\t=                    Menu Fitur                    =");
            System.out.println("\t====================================================");
            System.out.println("\t1. Informasi Barang");
            System.out.println("\t2. Informasi Pemesanan");
            System.out.println("\t3. Informasi Akun & Graph");
            System.out.println("\t4. Keluar");
            System.out.println("\t====================================================");
            System.out.print("\tMasukkan Pilihan Anda : ");
            feature = scanner.nextInt();

            if (feature == 1) {
                System.out.println("\n\t====================================================");
                System.out.println("\t=                    Menu Fitur                    =");
                System.out.println("\t====================================================");
                System.out.println("\t1. Tambah Barang");
                System.out.println("\t2. Ubah Barang");
                System.out.println("\t3. Lihat Barang");
                System.out.println("\t4. Hapus Barang");
                System.out.println("\t5. Lihat Detail Barang");
                System.out.println("\t====================================================");
                System.out.print("\tMasukkan Pilihan Anda : ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        tambahBarang(scanner);
                        break;
                    case 2:
                        updateBarang(scanner);
                        break;
                    case 3:
                        lihatBarang();
                        break;
                    case 4:
                        hapusBarang(scanner);
                        break;
                    case 5:
                        detailBarang(scanner);
                        break;
                }
            } else if (feature == 2) {
                System.out.println("\n\t====================================================");
                System.out.println("\t=                    Menu Fitur                    =");
                System.out.println("\t====================================================");
                System.out.println("\t1.  Pemesanan Barang");
                System.out.println("\t2.  Lihat Proses Pengiriman");
                System.out.println("\t====================================================");
                System.out.print("\tMasukkan Pilihan Anda : ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        tambahPemesanan(scanner);
                        break;
                    case 2:
                        lihatPemesanan(graph);
                        break;
                }
            } else if (feature == 3) {
                System.out.println("\n\t====================================================");
                System.out.println("\t=                    Menu Fitur                    =");
                System.out.println("\t====================================================");
                System.out.println("\t1.  Lihat Akun Saya\n ");
                System.out.println("\t2.  Ubah Data Graph");
                System.out.println("\t====================================================");
                System.out.print("\tMasukkan Pilihan Anda : ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        lihatAkun(head);
                        break;
                    case 2:
                        ubahGraph(graph, scanner);
                        break;
                }
            } else if (feature == 4) {
                simpanDataKeFile();
                System.out.println("\n\tTerimakasih Telah Menggunakan Program ini!!!");
                scanner.nextLine(); // Consume the newline character
                System.exit(0);
            } else {
                System.out.println("\n\tMaaf Pilihan Fitur Tidak Tersedia, Silahkan Menginput Ulang!!!");
                scanner.nextLine(); // Consume the newline character
                break;
            }
        } while (feature != 4);
    }

    // load data dari txt
    static void bacaDataDariFile() {
        bacaLoginDariFile();
        bacaPemesananDariFile();
        bacaBarangDariFile();
    }

    static void bacaLoginDariFile() {
        try {
            File file = new File("login_info.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(", ");
                if (data.length == 2) {
                    String username = data[0].split(": ")[1];
                    String password = data[1].split(": ")[1];
                    Login log = new Login(username, password);
                    if (head == null) {
                        head = log;
                    } else {
                        log.next = head;
                        head = log;
                    }
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File 'login_info.txt' tidak ditemukan. Membuat file baru...");
        }
    }

    static void bacaBarangDariFile() {
        try {
            File file = new File("barang_info.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(", ");

                if (data.length >= 3) {
                    int kodeBarang = Integer.parseInt(data[0].split(": ")[1].trim()); // Ambil bagian setelah "Kode
                                                                                      // Barang: "
                    String namaBarang = data[1].split(": ")[1].trim(); // Ambil bagian setelah "Nama Barang: "
                    int stock = Integer.parseInt(data[2].split(": ")[1].trim()); // Ambil bagian setelah "Stock: "

                    // Tambahkan data barang ke struktur data Barang (misalnya, linked list)
                    Barang barang = new Barang(kodeBarang, namaBarang, stock);
                    if (headBarang == null) {
                        headBarang = barang;
                    } else {
                        barang.next = headBarang;
                        headBarang = barang;
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File 'barang_info.txt' tidak ditemukan. Membuat file baru...");
        }
    }

    static void bacaPemesananDariFile() {
        try {
            File file = new File("pemesanan_info.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(", ");

                if (data.length >= 10) {
                    int kodePesanan = Integer.parseInt(data[0].split(": ")[1].trim()); // Ambil bagian setelah "Kode
                                                                                       // Pesanan: "
                    String namaPemesan = data[1].split(": ")[1].trim(); // Ambil bagian setelah "Nama Pemesan: "
                    int kodeBarangPesanan = Integer.parseInt(data[2].split(": ")[1].trim()); // Ambil bagian setelah
                                                                                             // "Kode Barang: "
                    String namaBarangPesanan = data[3].split(": ")[1].trim(); // Ambil bagian setelah "Nama Barang: "
                    int jumlahPesanan = Integer.parseInt(data[4].split(": ")[1].trim()); // Ambil bagian setelah "Jumlah
                                                                                         // Pesanan: "
                    int tanggal = Integer.parseInt(data[5].split(": ")[1].trim()); // Ambil bagian setelah "Tanggal: "
                    int bulan = Integer.parseInt(data[6].split(": ")[1].trim()); // Ambil bagian setelah "Bulan: "
                    int tahun = Integer.parseInt(data[7].split(": ")[1].trim()); // Ambil bagian setelah "Tahun: "
                    int kotaAsal = Integer.parseInt(data[8].split(": ")[1].trim()); // Ambil bagian setelah "Kota Asal:
                                                                                    // "
                    int kotaTujuan = Integer.parseInt(data[9].split(": ")[1].trim()); // Ambil bagian setelah "Kota
                                                                                      // Tujuan: "
                    // Tambahkan data pemesanan ke struktur data Pemesanan (misalnya, linked list)
                    masukPemesanan(kodePesanan, kodeBarangPesanan, jumlahPesanan, tanggal, bulan, tahun,
                            namaPemesan, namaBarangPesanan, kotaTujuan, kotaAsal);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File 'pemesanan_info.txt' tidak ditemukan. Membuat file baru...");
        }
    }
    // ini untuk simpan
    static void simpanDataKeFile() {
        simpanLoginKeFile();
        simpanBarangKeFile();
        simpanPemesananKeFile();
    }

    static void simpanLoginKeFile() {
        try {
            FileWriter fileWriter = new FileWriter("login_info.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            Login temp = head;
            while (temp != null) {
                printWriter.println("Username: " + temp.username + ", Password: " + temp.password);
                temp = temp.next;
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data Login ke file.");
        }
    }

    static void simpanBarangKeFile() {
        try {
            FileWriter fileWriter = new FileWriter("barang_info.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            Barang temp = headBarang;
            while (temp != null) {
                printWriter.println("Kode Barang: " + temp.kodebarang + ", Nama Barang: " + temp.namabarang
                        + ", Stock: " + temp.stock);
                temp = temp.next;
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data Barang ke file.");
        }
    }

    static void simpanPemesananKeFile() {
        try {
            FileWriter fileWriter = new FileWriter("pemesanan_info.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            Pemesanan temp = headPemesanan;
            while (temp != null) {
                printWriter.println("Kode Pesanan: " + temp.kodepesanan + ", Nama Pemesan: " + temp.nama +
                        ", Kode Barang: " + temp.kodebarangpesanan + ", Nama Barang: " + temp.namabarang +
                        ", Jumlah Pesanan: " + temp.jumlahpesanan + ", Tanggal: " + temp.tanggal +
                        ", Bulan: " + temp.bulan + ", Tahun: " + temp.tahun +
                        ", Kota Asal: " + temp.kota_asal + ", Kota Tujuan: " + temp.kota_tujuan);
                temp = temp.next;
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data Pemesanan ke file.");
        }
    }

    static Login RegisterAkun(Login head, Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\n\t====================================================");
        System.out.println("\t=                    REGISTER AKUN                    =");
        System.out.println("\t====================================================");
        System.out.print("\tUSERNAME	: ");
        String username = scanner.nextLine();
        System.out.print("\tPASSWORD	: ");
        String password = scanner.nextLine();

        Login log = new Login(username, password);
        if (head == null) {
            head = log;
        } else {
            log.next = head;
            head = log;
        }
        System.out.println();
        return head;
    }

    static Login loginAkun(Login head, Scanner scanner) {
        while (true) {
            scanner.nextLine(); // Consume the newline character

            System.out.println("\n\t====================================================");
            System.out.println("\t=                    LOGIN AKUN                    =");
            System.out.println("\t====================================================");

            System.out.print("\tUSERNAME    : ");
            String username = scanner.nextLine();

            System.out.print("\tPASSWORD    : ");
            String password = scanner.nextLine();

            Login temp = head;
            while (temp != null) {
                if (temp.username.equals(username) && temp.password.equals(password)) {
                    System.out.println("\n\tAkun berhasil login!");
                    menu(); // Menu utama
                    return temp;
                }
                temp = temp.next;
            }

            System.out.println("\n\tAkun tidak ditemukan atau kombinasi username dan password salah!");
            System.out.println("\tCoba lagi? (Y/N)");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("Y")) {
                break;
            }
        }
        return null;
    }

    static void lihatAkun(Login head) {
        System.out.println("\n\t=====================================================");
        System.out.println("\t=                  Lihat Akun Saya                  =");
        System.out.println("\t=====================================================");

        Login temp = head;
        while (temp != null) {
            System.out.println("\n\tUsername	: " + temp.username);
            System.out.println("\tPassword	: ******");
            temp = temp.next;
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Consume the newline character
        System.out.println();
        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
        System.out.println("\n\t=====================================================");
    }

    static void masukPemesanan(int kodpes, int kodbarpes, int jumpes, int tgl, int bln, int thn,
            String nampes, String nambar, int tujuan, int asal) {
        Pemesanan newnode = new Pemesanan(kodpes, nampes, kodbarpes, nambar, jumpes, tujuan, asal, tgl, bln, thn);
        newnode.next = headPemesanan;
        headPemesanan = newnode;
    }

    static void tambahPemesanan(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\t=============================================");
        System.out.println("\t            Buat Pemesanan Barang            ");
        System.out.println("\t=============================================");
        System.out.print("\tKode Pesanan	: ");
        int kodpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tNama Pemesan	: ");
        String nampes = scanner.nextLine();
        System.out.print("\tKode Barang	: ");
        int kodbarpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tNama Barang	: ");
        String nambar = scanner.nextLine();
        System.out.print("\tJumlah Pesanan	: ");
        int jumpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tTanggal     	: ");
        int tgl = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tBulan      	: ");
        int bln = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tTahun      	: ");
        int thn = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.println("\tKota Asal: ");
        System.out.println("\n\t0. Surabaya");
        System.out.println("\t1. Sidoarjo");
        System.out.println("\t2. Gresik");
        System.out.println("\t3. Situbondo");
        System.out.println("\t4. Madiun ");
        System.out.println("\t5. Magetan ");
        System.out.println("\t6. Malang ");
        System.out.println("\t7. Mojokerto ");
        System.out.print("\tPilih Kota Asal : ");
        int asal = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.println("\tKota Tujuan");
        System.out.println("\n\t0. Surabaya");
        System.out.println("\t1. Sidoarjo");
        System.out.println("\t2. Gresik");
        System.out.println("\t3. Situbondo");
        System.out.println("\t4. Madiun ");
        System.out.println("\t5. Magetan ");
        System.out.println("\t6. Malang ");
        System.out.println("\t7. Mojokerto ");
        System.out.print("\tPilih Kota Tujuan : ");
        int tujuan = scanner.nextInt();

        masukPemesanan(kodpes, kodbarpes, jumpes, tgl, bln, thn, nampes, nambar, tujuan, asal);

        scanner.nextLine(); // Consume the newline character
        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
    }

    static void lihatPemesanan(int[][] graph) {
        Pemesanan lihat;

        System.out.println("\n\t=============================================");
        System.out.println("\t         Lihat Proses Pemesanan Barang       ");
        System.out.println("\t=============================================");

        if (headPemesanan == null) {
            System.out.println(" Tidak Ada Proses Pemesanan Barang ");
        } else {
            lihat = headPemesanan;
            while (lihat != null) {
                System.out.println("\n\n\tKode Pesanan	: " + lihat.kodepesanan);
                System.out.println("\tNama Pemesan	: " + lihat.nama);
                System.out.println("\tKode Barang 	: " + lihat.kodebarangpesanan);
                System.out.println("\tNama Barang	: " + lihat.namabarang);
                System.out.println("\tJumlah Barang	: " + lihat.jumlahpesanan);
                System.out.println("\tTanggal Pesan	: " + lihat.tanggal + "/" + lihat.bulan + "/" + lihat.tahun);
                System.out.println("\tKota Asal	: " + getKota(lihat.kota_asal));
                System.out.println("\tKota Tujuan	: " + getKota(lihat.kota_tujuan));

                // Proses pengiriman menggunakan Algoritma Dijkstra
                int[] distances = dijkstra(graph, lihat.kota_asal);
                System.out.println("\n\tProses Pengiriman: ");
                System.out.println("\t  " + getKota(lihat.kota_asal) + " -> " + getKota(lihat.kota_tujuan));
                System.out.println("\t  Jarak Tempuh: " + distances[lihat.kota_tujuan] + " KM");

                lihat = lihat.next;
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
    }

    static String getKota(int kotaCode) {
        String[] kota = { "Surabaya", "Sidoarjo", "Gresik", "Situbondo", "Madiun", "Magetan", "Malang", "Mojokerto" };
        return kota[kotaCode];
    }

    static int[] dijkstra(int[][] graph, int startNode) {
        int n = graph.length;
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distances[startNode] = 0;

        for (int i = 0; i < n - 1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && distances[j] <= minDistance) {
                    minDistance = distances[j];
                    minIndex = j;
                }
            }

            visited[minIndex] = true;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && graph[minIndex][j] != 0 &&
                        distances[minIndex] != Integer.MAX_VALUE &&
                        distances[minIndex] + graph[minIndex][j] < distances[j]) {
                    distances[j] = distances[minIndex] + graph[minIndex][j];
                }
            }
        }
        return distances;
    }

    static void tambahBarang(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\n\t=============================================");
        System.out.println("\t             Tambah Data Barang              ");
        System.out.println("\t=============================================");
        System.out.print("\tKode Barang	: ");
        int kode = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tNama Barang	: ");
        String nama = scanner.nextLine();
        System.out.print("\tStock		: ");
        int stock = scanner.nextInt();

        Barang temp = new Barang(kode, nama, stock);

        if (headBarang == null) {
            headBarang = temp;
        } else {
            temp.next = headBarang;
            headBarang = temp;
        }

        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
    }

    static void lihatBarang() {
        Barang temp;

        System.out.println("\n\t=============================================");
        System.out.println("\t            Lihat Data Barang                ");
        System.out.println("\t=============================================");

        if (headBarang == null) {
            System.out.println("\tTidak Ada Data Barang");
        } else {
            temp = headBarang;
            while (temp != null) {
                System.out.println("\tKode Barang	: " + temp.kodebarang);
                System.out.println("\tNama Barang	: " + temp.namabarang);
                System.out.println("\tStock		: " + temp.stock);
                System.out.println();

                temp = temp.next;
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
    }

    static void updateBarang(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\n\t=============================================");
        System.out.println("\t             Ubah Data Barang               ");
        System.out.println("\t=============================================");
        System.out.print("\tMasukkan Kode Barang yang akan diubah: ");
        int kodeUpdate = scanner.nextInt();

        Barang temp = headBarang;
        while (temp != null && temp.kodebarang != kodeUpdate) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("\tData Barang tidak ditemukan!");
        } else {
            System.out.println("\tData Barang Saat Ini:");
            System.out.println("\tKode Barang	: " + temp.kodebarang);
            System.out.println("\tNama Barang	: " + temp.namabarang);
            System.out.println("\tStock		: " + temp.stock);

            System.out.println("\n\tMasukkan Data Barang yang Baru:");
            System.out.print("\tNama Barang	: ");
            temp.namabarang = scanner.nextLine();
            System.out.print("\tStock		: ");
            temp.stock = scanner.nextInt();

            System.out.println("\tData Barang berhasil diubah!");
        }

        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
        scanner.nextLine(); // Consume the newline character
    }

    static void hapusBarang(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\n\t=============================================");
        System.out.println("\t             Hapus Data Barang              ");
        System.out.println("\t=============================================");
        System.out.print("\tMasukkan Kode Barang yang akan dihapus: ");
        int kodeHapus = scanner.nextInt();

        Barang temp = headBarang;
        Barang prev = null;

        while (temp != null && temp.kodebarang != kodeHapus) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("\tData Barang tidak ditemukan!");
        } else {
            if (prev == null) {
                headBarang = temp.next;
            } else {
                prev.next = temp.next;
            }
            System.out.println("\tData Barang berhasil dihapus!");
        }

        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
        scanner.nextLine(); // Consume the newline character
    }

    static void detailBarang(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character

        System.out.println("\n\t=============================================");
        System.out.println("\t            Detail Data Barang              ");
        System.out.println("\t=============================================");
        System.out.print("\tMasukkan Kode Barang yang akan dilihat detailnya: ");
        int kodeDetail = scanner.nextInt();

        Barang temp = headBarang;

        while (temp != null && temp.kodebarang != kodeDetail) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("\tData Barang tidak ditemukan!");
        } else {
            System.out.println("\n\tDetail Data Barang:");
            System.out.println("\tKode Barang	: " + temp.kodebarang);
            System.out.println("\tNama Barang	: " + temp.namabarang);
            System.out.println("\tStock		: " + temp.stock);
        }

        System.out.println("\tPress Enter to continue...");
        scanner.nextLine(); // Consume the newline character
        scanner.nextLine(); // Consume the newline character
    }

    static void ubahGraph(int[][] graph, Scanner scanner) {
        int kotaAwal, kotaTujuan, jarakBaru;
        String[] kota = { "Surabaya", "Malang", "Magetan", "Pacitan", "Situbondo" };

        System.out.println("\t=============================================");
        System.out.println("\t=          Update Data Jarak Antar Kota      =");
        System.out.println("\t=============================================");

        System.out.print("\tPilih Kota yang Ingin di Rubah Jaraknya\n");
        for (int i = 0; i < kota.length; i++) {
            System.out.println("\n\t" + i + ". " + kota[i]);
        }
        System.out.print("\tMasukkan Kota: ");
        kotaAwal = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.println("\n\tKota " + kota[kotaAwal] + " ke Kota? : ");
        for (int i = 0; i < kota.length; i++) {
            System.out.println("\n\t" + i + ". " + kota[i]);
        }
        System.out.print("\tMasukkan Kota: ");
        kotaTujuan = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("\tMasukkan Jarak yang Baru: ");
        jarakBaru = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        graph[kotaAwal][kotaTujuan] = jarakBaru;
        System.out.println("\n\tJarak " + kota[kotaAwal] + " ke " + kota[kotaTujuan] + " yang baru adalah "
                + jarakBaru * 100 + " Km");
    }
}
