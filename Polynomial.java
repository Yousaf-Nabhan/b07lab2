import java.io.*;
import java.util.*;
public class Polynomial
{
    double coef[];
    double exponent[];
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();
        String[] terms = line.replaceAll("\\s", "").split("(?=[-+])");
        int maxExponent = 0;
        for (String term : terms) {
            String[] parts = term.split("x");
            if (parts.length > 1) {
                int exponent = Integer.parseInt(parts[1]);
                maxExponent = Math.max(maxExponent, exponent);
            }
        }
        exponent = new double[maxExponent + 1];
        coef = new double[maxExponent + 1];
        for (String term : terms) {
            String[] parts = term.split("x");
            if (parts.length == 1) {
                exponent[0] = 0;
                coef[0] = Double.parseDouble(parts[0]);
            } else {
                double coefficient = (parts[0].charAt(0) == '-') ? -1.0 : 1.0;
                int exponents = (parts.length > 1) ? Integer.parseInt(parts[1]) : 1;
                exponent[exponents] = exponents;
                coef[exponents] = coefficient;
            }
        }
    }
    public Polynomial()
    {
        coef = new double[]{0};
        exponent = new double[]{0};
    }
    public Polynomial(double coef[], double exponent[])
    {
        this.coef = coef;
        this.exponent = exponent;
    }
    public Polynomial add(Polynomial new_polynomial)
    {
        int oldl = this.coef.length;
        int newl = (new_polynomial.coef).length;
        int big = Math.max(oldl, newl);
        double new_coef[] = new double[oldl + newl];
        double new_exponent[] = new double[oldl + newl];
        int z = 0;
        for(int i = 0; i < oldl; i++)
        {
            new_exponent[z] = this.exponent[i];
            z++;
        }
        for(int i = 0; i < newl; i++)
        {
            int check = 0;
            int explen = new_exponent.length;
            for(int j = 0; j < explen; j++)
            {
                if(new_exponent[j] == new_polynomial.exponent[i])
                {
                    check = 1;
                }
            }
            if(check == 0)
            {
                new_exponent[z] = new_polynomial.exponent[i];
                z++;
            }
        }
        for(int i = 0; i < new_exponent.length; i++)
        {
            new_coef[i] = 0;
        }
        for(int i = 0; i < oldl; i++)
        {
            for(int j = 0; j < new_exponent.length; j++)
            {
                if(this.exponent[i] == new_exponent[j])
                {
                    new_coef[j] = new_coef[j] + this.coef[i];
                }
            }
        }
        for(int i = 0; i < newl; i++)
        {
            for(int j = 0; j < new_exponent.length; j++)
            {
                if(new_polynomial.exponent[i] == new_exponent[j])
                {
                    new_coef[j] = new_coef[j] + new_polynomial.coef[i];
                }
            }
        }
        Polynomial result = new Polynomial(new_coef, new_exponent);
        return result;
    }
    public double evaluate(double value)
    {
        int len = coef.length;
        double sum = 0;
        for(int i = 0; i < len; i++)
        {
            double part = this.coef[i] * Math.pow(value, this.exponent[i]);
            sum = sum + part;
        }
        return sum;
    }
    public boolean hasRoot(double value)
    {
        return((evaluate(value) == 0)? true : false);
    }
    public Polynomial multiply(Polynomial new_polynomial)
    {
        int oldl = this.coef.length;
        int newl = (new_polynomial.coef).length;
        double new_coef[] = new double[oldl + newl];
        double new_exponent[] = new double[oldl + newl];
        for(int i = 0, z = 0; i < oldl; i++)
        {
            for(int j = 0; j < newl; j++)
            {
                new_coef[z] = this.coef[i] * new_polynomial.coef[j];
                new_exponent[z] = this.exponent[i] + new_polynomial.exponent[j];
                z++;
            }
        }
        double new_coef1[] = new double[oldl + newl];
        double new_exponent1[] = new double[oldl + newl];
        int z = 0;
        for(int i = 0; i < new_coef.length; i++)
        {
            double sum = new_coef[i];
            if(new_coef[i] != 0)
            {   
                for(int j = i + 1; i < new_coef.length; j++)
                {
                    if(new_exponent[j] == new_exponent[i])
                    {
                        sum = sum + new_coef[j];
                        new_coef[j] = 0;
                    }
                }
                new_coef1[z] = sum;
                new_exponent1[z] = new_exponent[i];
                z++;
            }
        }
        Polynomial result = new Polynomial(new_coef1, new_exponent);
        return result;
    }
    public void saveToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < exponent.length; i++) {
            double coefficient = coef[i];
            double exponents = exponent[i];
            if (coefficient != 0) {
                if (i > 0 && coefficient > 0) {
                    writer.write("+");
                }
                writer.write(Double.toString(coefficient));
                if (exponents > 0) {
                    writer.write("x");
                    if (exponents > 1) {
                        writer.write(Double.toString(exponents));
                    }
                }
            }
        }
        writer.close();
    }
    public static void main(String[] args) {
        try {
            Polynomial poly = new Polynomial(new File("polynomial.txt"));
            poly.saveToFile("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

