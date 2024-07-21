package be.sgerard.springai.service.chat;

import be.sgerard.springai.model.answer.PromptAnswer;
import be.sgerard.springai.model.request.PromptRequest;
import be.sgerard.springai.model.answer.AnswerType;

public interface ChatService {

    /**
     * Prompts the specified message over the optional document.
     */
    PromptAnswer prompt(PromptRequest prompt, String documentId, AnswerType answerType);

}
