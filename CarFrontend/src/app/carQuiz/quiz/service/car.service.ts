import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Questions} from "../model/questions";
import {Observable} from "rxjs";
import {Car} from "../model/car";
import {Statistik} from "../model/statistik";

@Injectable({
    providedIn: 'root'
})
export class CarService {
    constructor(private http: HttpClient) {}
    createQuizByAttribute(attribute: string): Observable<Questions[]> {
        return this.http.get<Questions[]>(`http://localhost:8080/car/createQuizByAttribute?attribute=${attribute}`);
    }

    getRandomUniqueValues(arr: string[], count: number): string[] {
        const uniqueValues = new Set<string>();
        while (uniqueValues.size < count) {
            const randomIndex = Math.floor(Math.random() * arr.length);
            uniqueValues.add(arr[randomIndex]);
        }

        return Array.from(uniqueValues);
    }

    createNewQuiz() {
        return this.http.get<Car>('http://localhost:8080/car/createTestCars');
    }
    createStatistik(statistik: Statistik) {
        return this.http.post<Questions[]>('http://localhost:8080/car/saveStatistik', statistik);
    }

    getTop3Statistik() {
        return this.http.get<Statistik[]>('http://localhost:8080/car/top3Statistik');
    }

    createTestAnswers(attributeToGenerate: string) {
        let randomAnswers: string[] = [];

        switch (attributeToGenerate) {
            case "name":
                const carNames: string[] = [
                    "Toyota Camry",
                    "Ford F-150",
                    "Honda Civic",
                    "Chevrolet Silverado",
                    "Volkswagen Golf",
                    "Tesla Model 3",
                    "BMW 3 Series",
                    "Jeep Wrangler",
                    "Mercedes-Benz C-Class",
                    "Nissan Altima",
                    "Audi A4",
                    "Subaru Outback",
                    "Hyundai Elantra",
                    "Kia Sportage",
                    "Lexus ES",
                    "Ram 1500",
                    "Mazda3",
                    "Tesla Model S",
                    "Ford Escape",
                    "Chevrolet Malibu"
                ];
                randomAnswers = this.getRandomUniqueValues(carNames, 3);
                break;
            case "PS":
                const psValues: string[] = [
                    "100 PS",
                    "150 PS",
                    "200 PS",
                    "250 PS",
                    "300 PS",
                    "350 PS",
                    "400 PS",
                    "450 PS",
                    "500 PS",
                    "550 PS",
                    "600 PS",
                    "650 PS",
                    "700 PS",
                    "750 PS",
                    "800 PS",
                    "850 PS",
                    "900 PS",
                    "950 PS",
                    "1000 PS",
                    "1050 PS"
                ];
                randomAnswers = this.getRandomUniqueValues(psValues, 3);
                break;
            case "Baujahr":
                const yearValues: string[] = [
                    "2000",
                    "2005",
                    "2010",
                    "2015",
                    "2020",
                    "2021",
                    "2022",
                    "2023",
                    "2024",
                    "2025",
                    "2030"
                ];
                randomAnswers = this.getRandomUniqueValues(yearValues, 3);
                break;
            case "Model":
                const carModels: string[] = [
                    "Sedan",
                    "Truck",
                    "Hatchback",
                    "Electric",
                    "SUV"
                ];
                randomAnswers = this.getRandomUniqueValues(carModels, 3);
                break;
            default:
                break;
        }

        return randomAnswers;
    }

}
