package com.wedaa.stt.web.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.speech.v2.AutoDetectDecodingConfig;
import com.google.cloud.speech.v2.RecognitionConfig;
import com.google.cloud.speech.v2.RecognizeRequest;
import com.google.cloud.speech.v2.RecognizeResponse;
import com.google.cloud.speech.v2.SpeechClient;
import com.google.cloud.speech.v2.SpeechRecognitionAlternative;
import com.google.cloud.speech.v2.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

@RestController
@RequestMapping("/api/stt-v2")
public class SpeechToTextV2 {

  private static final String PROJECT_ID = "";
  private static final String RECOGNIZER_ID = "";

  private SpeechClient speechClient;

  @Autowired
  public SpeechToTextV2(SpeechClient speechClient) {
    this.speechClient = speechClient;
  }

  @PostMapping("/short")
  public List<String> shortFile(@RequestBody String filePath)
      throws IOException, InterruptedException, ExecutionException {
    List<String> transcriptions = new ArrayList<>();

      Path path = Paths.get(filePath);
      byte[] data = Files.readAllBytes(path);
      ByteString audioBytes = ByteString.copyFrom(data);

      String recognizerName =
          String.format("projects/%s/locations/global/recognizers/%s", PROJECT_ID, RECOGNIZER_ID);

      RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
          .setAutoDecodingConfig(AutoDetectDecodingConfig.newBuilder().build()).build();

      RecognizeRequest request = RecognizeRequest.newBuilder().setConfig(recognitionConfig)
          .setRecognizer(recognizerName).setContent(audioBytes).build();

      RecognizeResponse response = speechClient.recognize(request);
      List<SpeechRecognitionResult> results = response.getResultsList();

      for (SpeechRecognitionResult result : results) {
        if (result.getAlternativesCount() > 0) {
          SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
          transcriptions.add(alternative.getTranscript());
        }
      }

      return transcriptions;
  }

}
