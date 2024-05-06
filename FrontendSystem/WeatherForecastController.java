import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200") // Allow all endpoints in this controller to be accessed by the Angular app
@RestController
public class WeatherController {

    // Example method to return dummy data
    @GetMapping("/api/weather")
    public WeatherData getWeather() {
        WeatherData data = new WeatherData();
        data.setTemperature(72);
        data.setConditions("Sunny");
        // Set more data as needed
        return data;
    }
}