import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.util.Pair;

public class ProsesApp {
    static Login head = null;
    static Barang headBarang = null;
    static Pemesanan headPemesanan = null;
    Scanner scanner = new Scanner(System.in);
    static final int MAX_CITIES = 100;
    static String[] kota = new String[MAX_CITIES];
    static int[][] graph = new int[MAX_CITIES][MAX_CITIES];
    static int numCities = 0;
    static int jumlahDataKota = 0;

    // menu
    static void menu() {
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
                System.out.println("\t1.  Lihat Akun Saya");
                System.out.println("\t2.  Ubah Data Graph");
                System.out.println("\t3.  Tambah Kota");
                System.out.println("\t====================================================");
                System.out.print("\tMasukkan Pilihan Anda : ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        lihatAkun(head);
                        break;
                    case 2:
                        ubahGraph(graph, scanner, kota);
                        break;
                    case 3:
                        tambahKota(graph, kota);
                        break;
                    case 4:
                        tampilkanDataKota(graph);
                    default:
                        System.out.println("Pilihan tidak valid.");
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
        bacaDataNamaKota();
        bacaDataKotaDariFile(graph);
    }
    
    static void tampilkanDataKota(int[][] graph) {
        System.out.println("Data Kota:");
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                // Menampilkan keterangan jarak dari kota i ke kota j
                System.out.println("Jarak dari Kota " + i + " ke Kota " + j + " = " + graph[i][j]);
            }
        }
    }

    static void bacaDataNamaKota() {
        try {
            FileReader fileReader = new FileReader("kota_nama.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" -> ");
                if (parts.length == 2) {
                    int index = Integer.parseInt(parts[0].trim());
                    String cityName = parts[1].trim();
                    if (index >= 0 && index < MAX_CITIES) {
                        kota[index] = cityName;
                        numCities = Math.max(numCities, index + 1);
                    } else {
                        throw new IllegalArgumentException("Invalid index in line: " + line);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid data format in line: " + line);
                }
            }
            bufferedReader.close();
            System.out.println("Data nama kota berhasil dibaca.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca data nama kota dari file: " + e.getMessage());
        }
    }

    static void bacaDataKotaDariFile(int [][] graph) {
        try {
            FileReader fileReader = new FileReader("kota_info.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(" -> ");
                if (data.length >= 2) {
                    int city1 = Integer.parseInt(data[0].trim());
                    String cityStr = data[1].trim();

                    // Menghapus kurung siku dan spasi, dan membagi berdasarkan koma dan kurung
                    // biasa
                    String[] connections = cityStr.replaceAll("\\[|\\]|\\s", "").split("\\),");

                    for (String connection : connections) {
                        String[] cityDist = connection.replaceAll("\\(|\\)", "").split(",");
                        if (cityDist.length >= 2) {
                            int city2 = Integer.parseInt(cityDist[0].trim());
                            int distance = Integer.parseInt(cityDist[1].trim());

                            // Memastikan ukuran matriks tidak melebihi MAX_CITIES
                            if (city1 < MAX_CITIES && city2 < MAX_CITIES) {
                                graph[city1][city2] = distance;
                                graph[city2][city1] = distance;
                            } else {
                                throw new IllegalArgumentException(
                                        "Ukuran matriks tidak mencukupi untuk kota tersebut.");
                            }
                        } else {
                            throw new IllegalArgumentException("Invalid connection format in line: " + line);
                        }
                    }

                    // Mengupdate nilai numCities jika ditemukan kota baru
                    numCities = Math.max(numCities, city1 + 1);
                } else {
                    throw new IllegalArgumentException("Invalid data format in line: " + line);
                }
            }
            bufferedReader.close();
            System.out.println("Data kota berhasil dibaca dan diperbarui pada graph.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca data kota dari file: " + e.getMessage());
        }
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
        simpanDataKotaKeFile(graph,numCities);
        simpanDataNamaKota();
    }

    static void simpanDataNamaKota() {
        try {
            FileWriter fileWriter = new FileWriter("kota_nama.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i = 0; i < numCities; i++) {
                printWriter.println(i + " -> " + kota[i]);
            }
            printWriter.close();
            System.out.println("Data nama kota berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data nama kota ke file.");
        }
    }

    static void simpanDataKotaKeFile(int[][] graph, int numCities) {
        try {
            FileWriter fileWriter = new FileWriter("kota_info.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i = 0; i < numCities; i++) {
                printWriter.print(i + " -> [");
                for (int j = 0; j < numCities; j++) {
                    if (graph[i][j] != 0) {
                        printWriter.print("(" + j + ", " + graph[i][j] + "), ");
                    }
                }
                printWriter.println("]");
            }
            printWriter.close();
            System.out.println("Data kota berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data kota ke file.");
        }
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
        System.out.print("\tKode Pesanan   : ");
        int kodpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tNama Pemesan   : ");
        String nampes = scanner.nextLine();
        System.out.print("\tKode Barang    : ");
        int kodbarpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tNama Barang    : ");
        String nambar = scanner.nextLine();
        System.out.print("\tJumlah Pesanan : ");
        int jumpes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tTanggal        : ");
        int tgl = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tBulan          : ");
        int bln = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("\tTahun          : ");
        int thn = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("\tKota Asal: ");
        for (int i = 0; i < numCities; i++) {
            System.out.println("\t" + i + ". " + kota[i]);
        }
        System.out.print("\tPilih Kota Asal : ");
        int asal = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("\tKota Tujuan: ");
        for (int i = 0; i < numCities; i++) {
            System.out.println("\t" + i + ". " + kota[i]);
        }
        System.out.print("\tPilih Kota Tujuan : ");
        int tujuan = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        masukPemesanan(kodpes, kodbarpes, jumpes, tgl, bln, thn, nampes, nambar, tujuan, asal);

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
        if (kotaCode >= 0 && kotaCode < numCities) {
            return kota[kotaCode];
        } else {
            return "Invalid Kota Code";
        }
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

    static int[][] ubahGraph(int[][] graph, Scanner scanner, String[] kota) {
        int kotaAwal, kotaTujuan, jarakBaru;
        System.out.println("\t=============================================");
        System.out.println("\t=          Update Data Jarak Antar Kota      =");
        System.out.println("\t=============================================");
        System.out.println("\tPilih Kota yang Ingin di Rubah Jaraknya\n");
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
        graph[kotaTujuan][kotaAwal] = jarakBaru;
        System.out.println("\n\tJarak " + kota[kotaAwal] + " ke " + kota[kotaTujuan] + " yang baru adalah "
                + jarakBaru + " KM");
        return graph;
    }

    static void tambahKota(int[][] graph, String[] kota) {
        int jarakBaru;
        String namaKotaBaru = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t=============================================");
        System.out.println("\t=            Tambah Data Kota                =");
        System.out.println("\t=============================================");
        System.out.print("\tMasukkan Nama Kota Baru: ");
        namaKotaBaru = scanner.nextLine();

        // Update the arrays with new city data
        kota[numCities] = namaKotaBaru;

        for (int i = 0; i <= numCities; i++) {
            System.out.print("\tMasukkan jarak antara " + kota[i] + " dan " + namaKotaBaru + ": ");
            jarakBaru = scanner.nextInt();
            graph[i][numCities] = jarakBaru;
            graph[numCities][i] = jarakBaru;
        }

        // Tambah jarak antara kota baru dengan dirinya sendiri
        graph[numCities][numCities] = 0;

        numCities++;
        System.out.println("\n\tKota " + namaKotaBaru + " berhasil ditambahkan.");
        simpanDataKotaKeFile(graph, numCities); // Save the updated graph to the file
    }
}