package be.sgerard.springai.service.audio;

import be.sgerard.springai.model.audio.Language;
import org.springframework.core.io.Resource;

public interface AudioService {

    String speechToText(Resource resource, Language language);

    Resource textToSpeech(String text);

}
