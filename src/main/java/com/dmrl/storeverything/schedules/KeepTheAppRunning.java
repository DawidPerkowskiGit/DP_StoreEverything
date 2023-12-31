package com.dmrl.storeverything.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

/**
 * Schedule that keeps the app running on Render
 */
@Component
public class KeepTheAppRunning {

    /**
     * Perform HTTP call to the app's webpage
     */
    @Scheduled(fixedRate = 600000, initialDelay = 600000)
    public void keepTheAppRunning(){
        LocalDateTime localDateTime = LocalDateTime.now();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://dp-storeeverything-zri6.onrender.com/"))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e) {
            System.out.println("Failed to perform scheduled task. Exception: "+ e);
        }
        System.out.println("Successfully kept the app from un-allocating resources. Time: " + localDateTime.toString());
    }
}
