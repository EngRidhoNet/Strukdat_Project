import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProsesApp prosesApp = new ProsesApp(); // membuat objek prosesApp
        Scanner scanner = new Scanner(System.in);
        int choice, feature;

        System.out.println("\n\t====================================================");
        System.out.println("\t=          FINAL PROJECT STRUKTUR DATA A           =");
        System.out.println("\t= SISTEM INFORMASI PEMESANAN BARANG DAN PENGIRIMAN =");
        System.out.println("\t====================================================");
        System.out.println("\t=        Wafiy Anwarul Hikam    [220605110022]     =");
        System.out.println("\t=        Ridho Aulia Rahman     [220605110044]     =");
        System.out.println("\t====================================================");
        System.out.println("\tTekan terserah untuk memulai program!!!");
        scanner.nextLine();

        // Register
        System.out.println("\n\t===================================================================");
        System.out.println("\t=      Selamat Datang Aplikasi Pemesanan dan Pengiriman Barang     =");
        System.out.println("\t===================================================================");
        System.out.println("\n\tSilahkan Register terlebih dahulu! ");
        System.out.println("\tPress Enter to continue...");
        scanner.nextLine();
        prosesApp.head = prosesApp.RegisterAkun(prosesApp.head, scanner);
        // Login
        System.out.println("\n\t===================================================================");
        System.out.println("\t=      Selamat Datang Aplikasi Pemesanan dan Pengiriman Barang     =");
        System.out.println("\t===================================================================");
        System.out.println("\n\tSilahkan Login terlebih dahulu! ");
        
        
        

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
                        prosesApp.tambahBarang(scanner);
                        break;
                    case 2:
                        prosesApp.updateBarang(scanner);
                        break;
                    case 3:
                        prosesApp.lihatBarang();
                        break;
                    case 4:
                        prosesApp.hapusBarang(scanner);
                        break;
                    case 5:
                        prosesApp.detailBarang(scanner);
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
                        prosesApp.tambahPemesanan(scanner);
                        break;
                    case 2:
                        prosesApp.lihatPemesanan(prosesApp.graph);
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
                        prosesApp.lihatAkun(prosesApp.head);
                        break;
                    case 2:
                        prosesApp.ubahGraph(prosesApp.graph, scanner);
                        break;
                }
            } else if (feature == 4) {
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
}
