package pk;

public class FirstProgramm {

    public static void maleTreppe(int hoehe, int stufentiefe) {
        for (int i = 1; i <= hoehe; i++) {
            
            for (int s = 0; s < (hoehe - i) * stufentiefe; s++) {
                System.out.print(" ");
            }
        
            for (int j = 0; j < i * stufentiefe; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        maleTreppe(6, 2);
    }

}








