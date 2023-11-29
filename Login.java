public class Login {
    Login next;
    String username;
    String password;
    Pemesanan pesan;

    Login(String username, String password) {
        this.next = null;
        this.username = username;
        this.password = password;
        this.pesan = null;
    }
}
