package be.sgerard.springai.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageModel imageModel;

    @Override
    public byte[] textToImage(String instruction) {
        final ImagePrompt prompt = createPrompt(instruction);

        final String b64Content = imageModel.call(prompt)
                .getResult()
                .getOutput().getB64Json();

        return Base64.getDecoder()
                .decode(b64Content.getBytes());
    }

    private ImagePrompt createPrompt(String instruction) {
        return new ImagePrompt(
                instruction,
                OpenAiImageOptions.builder()
                        .withResponseFormat("b64_json")
                        .build()
        );
    }
}
