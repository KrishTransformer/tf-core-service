package com.tf.core_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class SecretConfig {

    private static final String SERVICE_SECRETS_BASE_PATH = "/secretConfig.json";
    private static final String SERVICE_SECRETS_FILE_EXTENSION = ".txt";


}