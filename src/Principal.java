import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();

        try {
            converter.fetchExchangeRates();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese la cantidad a Cambiar:");
            double amount = scanner.nextDouble();

            System.out.println("Ingrese la divisa a cambiar (ejemplo, USD):");
            String fromCurrency = scanner.next().toUpperCase();

            System.out.println("Ingrese la divisa que desea obtener (ejemplo, PEN):");
            String toCurrency = scanner.next().toUpperCase();

            double result = converter.convert(fromCurrency, toCurrency, amount);
            System.out.println("El total a recibir es: " + result + " " +toCurrency);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener los tipos de cambio: " + e.getMessage());
        }
    }

}
