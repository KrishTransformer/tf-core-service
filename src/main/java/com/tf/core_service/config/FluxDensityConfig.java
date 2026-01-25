package com.tf.core_service.config;

import com.tf.core_service.utils.CommonFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Configuration
public class FluxDensityConfig {

    private static final String CSV_FILE_PATH = "CoreMaterialsMulti.csv";

    @Bean
    public Map<String, Map<Double, Float>> fluxDensity() throws IOException {
        Map<String, Map<Double, Float>> fluxDensityConfig = new HashMap<>();
        ClassPathResource resource = new ClassPathResource(CSV_FILE_PATH);
        InputStream inputStream = resource.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            String[] attributeNames = line.split(",");
            while ((line = br.readLine()) != null) {
                Map<Double, Float> specificLoss = new HashMap<>();
                String[] attributeValues = line.split(",");
                for(int i = 1; i < attributeValues.length - 1; i++) {
                    if (CommonFunctions.checkNullOrEmptyString(attributeValues[i])) {
                        specificLoss.put(Double.parseDouble(attributeNames[i]), Float.parseFloat(attributeValues[i]));
                    }
                }
                fluxDensityConfig.put(attributeValues[0], specificLoss);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fluxDensityConfig;
    }
}
