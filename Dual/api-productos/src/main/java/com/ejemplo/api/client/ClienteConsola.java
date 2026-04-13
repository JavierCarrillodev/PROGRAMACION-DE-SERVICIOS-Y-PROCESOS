package com.ejemplo.api.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class ClienteConsola {

    public static void main(String[] args) {
        // Esto desactiva la comprobación de que el certificado se llame "localhost"
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        try {
            // 1. Configurar un "TrustManager" que lo acepte TODO (Evita el error PKIX)
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            // 2. Crear un contexto SSL con ese TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 3. Crear el HttpClient usando ese contexto "permisivo"
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            // 4. Preparar la autenticación (admin:admin123)
            String auth = "admin:admin123";
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            // 5. Configurar la URL correcta (HTTPS y puerto 8443)
            String url = "https://localhost:8443/api/productos";

            System.out.println("Enviando petición a: " + url);

            // 6. Crear la petición
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .GET()
                    .build();

            // 7. Enviar y recibir respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 8. Imprimir resultados
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Cuerpo de la respuesta:");
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}