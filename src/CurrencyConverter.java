import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private final HttpClient httpClient;
    private final Gson gson;
    private Map<String, Double> exchangeRates;

    public CurrencyConverter() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.exchangeRates = new HashMap<>();
    }

    public void fetchExchangeRates() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/921902ac1f8354bea01a0f1a/latest/USD"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject ratesObject = jsonObject.getAsJsonObject("conversion_rates");

        for (Map.Entry<String, JsonElement> entry : ratesObject.entrySet()) {
            exchangeRates.put(entry.getKey(), entry.getValue().getAsDouble());
        }
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        double fromRate = exchangeRates.get(fromCurrency.toUpperCase());
        double toRate = exchangeRates.get(toCurrency.toUpperCase());
        if (fromRate == 0 || toRate == 0) {
            System.out.println("Invalid currency");
            return 0;
        }
        return amount * (toRate / fromRate);
    }
}
