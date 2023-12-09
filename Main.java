import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProsesApp prosesApp = new ProsesApp(); // membuat objek prosesApp
        Scanner scanner = new Scanner(System.in);
        prosesApp.bacaDataDariFile();
        System.out.println("\n\t====================================================");
        System.out.println("\t=          FINAL PROJECT STRUKTUR DATA A           =");
        System.out.println("\t= SISTEM INFORMASI PEMESANAN BARANG DAN PENGIRIMAN =");
        System.out.println("\t====================================================");
        System.out.println("\t=        Wafiy Anwarul Hikam    [220605110022]     =");
        System.out.println("\t=        Ridho Aulia Rahman     [220605110044]     =");
        System.out.println("\t====================================================");
        System.out.println("\tTekan terserah untuk memulai program!!!");
        scanner.nextLine();
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("\n\t===================================================================");
            System.out.println("\t=      Selamat Datang Aplikasi Pemesanan dan Pengiriman Barang     =");
            System.out.println("\t===================================================================");
            System.out.println("\n\tMenu");
            System.out.println("\t1. Login");
            System.out.println("\t2. Register");
            System.out.print("\tMasukkan Pilihan : ");
            int pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1:
                    prosesApp.loginAkun(prosesApp.head, scanner);
                    isValidInput = true;
                    break;
                case 2:
                    prosesApp.head = prosesApp.RegisterAkun(prosesApp.head, scanner);
                    isValidInput = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
                    break;
            }
        }
        // Menu untuk kembali ke menu sebelumnya
        System.out.println("Kembali ke menu sebelumnya? (Y/N)");
        String pilihanMenu = scanner.nextLine();
        if (pilihanMenu.equals("Y")) {
            main(args);
        } else {
            ProsesApp.simpanDataKeFile();
            System.out.println("Keluar dari aplikasi.");
        }
    }
}
