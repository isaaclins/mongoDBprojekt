package com.backend.AutoDB.controller;

import com.backend.AutoDB.model.Auto;
import com.backend.AutoDB.model.Questions;
import com.backend.AutoDB.model.Statistik;
import com.backend.AutoDB.repository.AutoRepository;
import com.backend.AutoDB.repository.StatistikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/car")
public class AutoController {

    @Autowired
    private final AutoRepository autoRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    private final StatistikRepository statistikRepository;

    public AutoController(AutoRepository leagueRepository, MongoTemplate mongoTemplate, StatistikRepository statistikRepository) {
        this.autoRepository = leagueRepository;
        this.mongoTemplate = mongoTemplate;
        this.statistikRepository = statistikRepository;
    }

    @GetMapping("/createQuizByAttribute")
    public ResponseEntity<?> createQuizByAttribute(@RequestParam String attribute) {
        try {
            List<Auto> cars = autoRepository.findAll();
            ArrayList<Questions> quizQuestions = new ArrayList<>();

            if (attribute.equals("name")) {
                for (Auto car : cars) {
                    Questions questions = new Questions();
                    var question = "Welcher " + attribute + " Hatte das Auto mit insgesamt " + car.getPs() + "?";
                    String answer = car.getName();
                    questions.setAnswers(answer);
                    questions.setQuestion(question);

                    quizQuestions.add(questions);

                }
            } else if (attribute.equals("PS")) {
                for (Auto car : cars) {
                    Questions questions = new Questions();
                    String question = "Wie viel " + attribute + " hat das Auto " + car.getName() + "?";
                    String answer = car.getPs();
                    questions.setAnswers(answer);
                    questions.setQuestion(question);

                    quizQuestions.add(questions);
                }

            } else if (attribute.equals("Baujahr")) {
                for (Auto car : cars) {
                    Questions questions = new Questions();
                    String question = "Welches " + attribute + " hat das Auto " + car.getName() + "?";
                    String answer = car.getBaujahr();
                    questions.setAnswers(answer);
                    questions.setQuestion(question);

                    quizQuestions.add(questions);
                }

            } else if (attribute.equals("Model")) {
                for (Auto car : cars) {
                    Questions questions = new Questions();
                    String question = "Was für ein " + attribute + " ist " + car.getName() + "?";
                    String answer = car.getModel();
                    questions.setAnswers(answer);
                    questions.setQuestion(question);

                    quizQuestions.add(questions);
                }

            }

            return ResponseEntity.ok(quizQuestions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fehler beim Erstellen des Quiz.");
        }
    }



    @PostMapping("/saveStatistik")
    public ResponseEntity<?> saveStatistik(@RequestBody Statistik statistik) {

        try {
            this.statistikRepository.save(statistik);
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/top3Statistik")
    public ResponseEntity<?> getTop3Statistik() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "time")),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "points")),
                Aggregation.limit(3)
        );
        Statistik[] top3 = mongoTemplate.aggregate(aggregation, "statistik", Statistik.class).getMappedResults().toArray(new Statistik[0]);
        return ResponseEntity.ok(top3);
    }

    @GetMapping("/createTestCars")
    public ResponseEntity<?> createPerson() {

        try {
            String[][] carData = {
                    {"Toyota Camry", "150 PS", "2023", "Sedan"},
                    {"Ford F-150", "325 PS", "2022", "Truck"},
                    {"Honda Civic", "130 PS", "2023", "Sedan"},
                    {"Chevrolet Silverado", "355 PS", "2022", "Truck"},
                    {"Volkswagen Golf", "150 PS", "2023", "Hatchback"},
                    {"Tesla Model 3", "346 PS", "2022", "Electric"},
                    {"BMW 3 Series", "255 PS", "2023", "Sedan"},
                    {"Jeep Wrangler", "285 PS", "2022", "SUV"},
                    {"Mercedes-Benz C-Class", "255 PS", "2023", "Sedan"},
                    {"Nissan Altima", "188 PS", "2022", "Sedan"},
                    {"Audi A4", "201 PS", "2023", "Sedan"},
                    {"Subaru Outback", "182 PS", "2022", "SUV"},
                    {"Hyundai Elantra", "147 PS", "2023", "Sedan"},
                    {"Kia Sportage", "181 PS", "2022", "SUV"},
                    {"Lexus ES", "302 PS", "2023", "Sedan"},
                    {"Ram 1500", "395 PS", "2022", "Truck"},
                    {"Mazda3", "186 PS", "2023", "Hatchback"},
                    {"Tesla Model S", "778 PS", "2022", "Electric"},
                    {"Ford Escape", "181 PS", "2023", "SUV"},
                    {"Chevrolet Malibu", "160 PS", "2022", "Sedan"}
            };


            for (String[] data : carData) {
                Auto car = new Auto();
                car.setName(data[0]);
                car.setPs(data[1]);
                car.setBaujahr(data[2]);
                car.setModel(data[3]);

                this.autoRepository.save(car);
            }
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

}
