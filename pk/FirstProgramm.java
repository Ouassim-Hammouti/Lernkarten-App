package pk;
public class FirstProgramm {

   public static void maleTreppe(int hoehe, int stufentiefe) {
        for (int i = 1; i <= hoehe; i++) {
            
            for (int j = 1; j <= i * stufentiefe; j++) {
                System.out.print("*");
            }
            System.out.println(); }
    }

    public static void main(String[] args) {
        maleTreppe(6, 2); 
    }
}






