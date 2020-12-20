public class Main {
    public static void main(String[] args) {
        Core core = new Core(256000);
        System.out.println("Без прерываний: " + core.getWoBreaking());
        System.out.println("С прерываниями: " + core.getwBreaking());
    }
}
