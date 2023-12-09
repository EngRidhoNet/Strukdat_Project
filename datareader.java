import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class datareader {
    static final int MAX_CITIES = 100;
    static int[][] graph = new int[MAX_CITIES][MAX_CITIES];
    static int numCities = 0;
    static String[] kota = { "Surabaya", "Sidoarjo", "Gresik", "Situbondo", "Madiun", "Magetan", "Malang",
            "Mojokerto" };

    public static void main(String[] args) {
        bacaDataKotaDariFile();

        tambahKota(graph, kota);
        // Menggunakan numCities sebagai batas
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                // Menampilkan keterangan jarak dari kota i ke kota j
                System.out.println("Jarak dari Kota " + i + " ke Kota " + j + " = " + graph[i][j]);
            }
        }
    }

    static void bacaDataKotaDariFile() {
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

    static void tambahKota(int[][] graph, String[] kota) {
        int jarakBaru;
        String namaKotaBaru = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t=============================================");
        System.out.println("\t=            Tambah Data Kota                =");
        System.out.println("\t=============================================");
        System.out.print("\tMasukkan Nama Kota Baru: ");
        namaKotaBaru = scanner.nextLine();
        int indexKotaBaru = numCities;
        String[] newKota = new String[MAX_CITIES];
        System.arraycopy(kota, 0, newKota, 0, kota.length);
        newKota[indexKotaBaru] = namaKotaBaru;
        kota = newKota;
        int[][] newGraph = new int[indexKotaBaru + 1][indexKotaBaru + 1];
        // Copy the values from the existing graph to the newGraph
        for (int i = 0; i < indexKotaBaru; i++) {
            for (int j = 0; j < indexKotaBaru; j++) {
                newGraph[i][j] = graph[i][j];
            }
        }
        // Tambah jarak antara kota baru dan kota yang sudah ada
        for (int i = 0; i < indexKotaBaru; i++) {
            System.out.print("\tMasukkan jarak antara " + kota[i] + " dan " + namaKotaBaru + ": ");
            jarakBaru = scanner.nextInt();
            newGraph[i][indexKotaBaru] = jarakBaru;
            newGraph[indexKotaBaru][i] = jarakBaru;
        }
        // Tambah jarak antara kota baru dengan dirinya sendiri
        newGraph[indexKotaBaru][indexKotaBaru] = 0;
        graph = newGraph;
        numCities++;
        System.out.println("\n\tKota " + namaKotaBaru + " berhasil ditambahkan.");
        simpanDataKotaKeFile(graph, numCities); // Save the updated graph to the file
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
}
