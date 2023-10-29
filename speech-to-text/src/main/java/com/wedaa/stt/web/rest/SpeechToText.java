package com.wedaa.stt.web.rest;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

@RestController
@RequestMapping("/api/stt")
public class SpeechToText {

  @Autowired
  public SpeechToText() {

  }

  @GetMapping("/short")
  public String shortFile() throws IOException {
    String textResponse = null;

    try (SpeechClient speechClient = SpeechClient.create()) {
      // The path to the audio file to transcribe
      String gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw";

      // Builds the sync recognize request
      RecognitionConfig config =
          RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16)
          .setSampleRateHertz(16000).setLanguageCode("en-US").build();
      RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

      // Performs speech recognition on the audio file
      RecognizeResponse response = speechClient.recognize(config, audio);
      List<SpeechRecognitionResult> results = response.getResultsList();

      for (SpeechRecognitionResult result : results) {
        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
        System.out.printf("Transcription: %s%n", alternative.getTranscript());
        textResponse = alternative.getTranscript();
      }
    }

    return textResponse;
  }

}
