package com.wedaa.stt.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.speech.v2.SpeechClient;

@Configuration
public class SpeechClientConfiguration {

  @Bean(destroyMethod = "close")
  public SpeechClient speechClient() throws IOException {
    return SpeechClient.create();
  }

}
