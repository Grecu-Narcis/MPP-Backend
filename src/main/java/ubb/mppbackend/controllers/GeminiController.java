package ubb.mppbackend.controllers;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.dto.PromptDTO;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.repositories.CarsRepositoryJPA;

import java.util.Optional;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {
    private final CarsRepositoryJPA carsRepositoryJPA;
    private final GenerativeModel model;
    private static final String projectId = "infinite-zephyr-426814-n1";
    private static final String location = "europe-west3";

    @Autowired
    public GeminiController(CarsRepositoryJPA carsRepositoryJPA) {
        this.carsRepositoryJPA = carsRepositoryJPA;
        VertexAI vertexAI = new VertexAI(projectId, location);
        String modelName = "gemini-1.5-flash-001";
        this.model = new GenerativeModel(modelName, vertexAI);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generatePromptResponse(@RequestBody PromptDTO promptRequest) {
//        String modelName = "gemini-1.5-flash-001";

        Optional<Car> requiredCar = this.carsRepositoryJPA.findById(Long.parseLong(promptRequest.getCarId()));

        if (requiredCar.isEmpty()) return ResponseEntity.ok().body("Sorry, I encountered an error!");

        String textPrompt = this.getPromptFromCar(requiredCar.get(), promptRequest.getMessage());
        String promptResult = this.getAnswer(textPrompt);

        return ResponseEntity.ok().body(promptResult);
    }

    private String getAnswer(String textPrompt) {
        try {
            GenerateContentResponse response = model.generateContent(textPrompt);
            return ResponseHandler.getText(response);
        }
        catch (Exception e) {
            return "Sorry, I cannot answer right now!";
        }
    }

    private String getPromptFromCar(Car requiredCar, String question) {
        return String.format("""
            Given the following car information, answer the question. Answer in maximum 200 words.
            The car provided is from a dealer. You are talking with the client.
            Give a response without expecting extra info. Answer based on the given question and nothing more.
            Avoid any formatting on the response. Use only letters and digits!
            If the question is not related to the given car, answer with 'Sorry, I am here to assist you about this car. How can I help you?'
            Answer like you are a car specialist. Don't give answers like we cannot know!
            Car: brand: %s, model: %s, mileage: %d, fuel type: %s, manufacturing year: %d.
            Question: %s
            """, requiredCar.getBrand(), requiredCar.getModel(), requiredCar.getMileage(),
            requiredCar.getFuelType(), requiredCar.getYear(), question);
    }
}
