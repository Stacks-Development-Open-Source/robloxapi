import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class java {

    public static void main(String[] args) {

        try {

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpRequest request =
                    HttpRequest.newBuilder()
                    .uri(
                        URI.create("https://api.ipify.org")
                    )
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                    );

            String publicIP = response.body();

            System.out.println("IPv4 Público: " + publicIP);

            HttpRequest request1 =
                    HttpRequest.newBuilder()
                    .uri(
                        URI.create(
                            "https://ipwho.is/" + publicIP
                        )
                    )
                    .header(
                        "User-Agent",
                        "Mozilla/5.0"
                    )
                    .GET()
                    .build();

            HttpResponse<String> response1 =
                    client.send(
                            request1,
                            HttpResponse.BodyHandlers.ofString()
                    );

            String json =
                    response1.body();

            System.out.println(json);

            // GITHUB

            String token =
                    "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";

            String owner =
                    "Stacks-Development-Open-Source";

            String repo =
                    "robloxapi";

            String path =
                    "data.json";

            String encoded =
                    Base64.getEncoder()
                    .encodeToString(
                            json.getBytes()
                    );

            String body =
                    "{"
                    + "\"message\":\"update json\","
                    + "\"content\":\"" + encoded + "\""
                    + "}";

            HttpRequest githubRequest =
                    HttpRequest.newBuilder()
                    .uri(
                        URI.create(
                            "https://api.github.com/repos/"
                            + owner + "/"
                            + repo + "/contents/"
                            + path
                        )
                    )
                    .header(
                        "Authorization",
                        "Bearer " + token
                    )
                    .header(
                        "Accept",
                        "application/vnd.github+json"
                    )
                    .header(
                        "Content-Type",
                        "application/json"
                    )
                    .PUT(
                        HttpRequest.BodyPublishers
                        .ofString(body)
                    )
                    .build();

            HttpResponse<String> githubResponse =
                    client.send(
                        githubRequest,
                        HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    githubResponse.body()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
