import java.io.File;
import java.io.IOException;
public class Driver {
    public static void main(String [] args) {
        try {
            Polynomial poly = new Polynomial(new File("polynomial.txt"));
            poly.saveToFile("output.txt");
            double[] coef1 = { 1.0, 2.0, 3.0 };
            double[] exponents1 = { 0.0, 1.0, 2.0 };
            Polynomial polynomial1 = new Polynomial(coef1, exponents1);
            double[] coef2 = { -1.0, 2.0, 4.0 };
            double[] exponents2 = { 0.0, 1.0, 3.0 };
            Polynomial polynomial2 = new Polynomial(coef2, exponents2);
            Polynomial sum = polynomial1.add(polynomial2);
            sum.saveToFile("sum_output.txt");
            double result = poly.evaluate(2.0);
            System.out.println("Result of polynomial evaluation at x = 2.0: " + result);
            boolean hasRoot = poly.hasRoot(3.0);
            System.out.println("Does the polynomial have a root at x = 3.0? " + hasRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
