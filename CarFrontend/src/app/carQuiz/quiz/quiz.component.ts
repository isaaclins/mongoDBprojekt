import {Component, OnInit} from '@angular/core';
import {Statistik} from "./model/statistik";
import {interval, Subject, takeUntil} from "rxjs";
import {QuizQuestions} from "./model/quizQuestions";
import {Questions} from "./model/questions";
import {CarService} from "./service/car.service";

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit{
  attributes = ["name", "PS", "Baujahr", "Model"];
  top3Players: Statistik[] = [];
  timer = new Subject<string>();
  destroy$ = new Subject<void>();
  startTime!: Date;
  newName: string = '';
  quizCount: number = 0;
  isFinished: boolean = false;
  isSelected: boolean = false;
  endTime!: Date;
  quiz: QuizQuestions = {
    answers: [],
    question: ''
  };
  userName: string = '';
  questions: Questions[] = []
  selectedAttribute: string = "";
  playerPoints: number = 0;

  constructor(private carService: CarService) {}

  ngOnInit() {
    this.carService.getTop3Statistik().subscribe(response => {
      console.log('Statistik gespeichert', response);
      this.top3Players = response
    });
    this.carService.createQuizByAttribute("name").subscribe(response => {
      if(response.length === 0) {
        this.carService.createNewQuiz().subscribe(data => {

        })
      }
    })
  }
  onLoadData(quizAttribut: string) {
    this.selectedAttribute = quizAttribut;
    if (this.selectedAttribute) {
      this.carService.createQuizByAttribute(this.selectedAttribute).subscribe(data => {
        this.questions = data;

        this.startTime = new Date();
        this.loadNewQuiz(data[this.quizCount]);

        interval(1000).pipe(takeUntil(this.destroy$)).subscribe(() => {
          const currentTime = new Date();
          const diff = currentTime.getTime() - this.startTime.getTime();
          const timeString = new Date(diff).toISOString().substr(11, 8);
          this.timer.next(timeString);
        });

        this.isSelected = true;
      });
    } else {
      console.log("Bitte wählen Sie ein Attribut aus.");
    }
  }
  loadNewQuiz(data: Questions) {

    const testAnswers = this.carService.createTestAnswers(this.selectedAttribute);
    const randomIndex = Math.floor(Math.random() * (testAnswers.length + 1));
    testAnswers.splice(randomIndex, 0, data.answers);

    this.quiz.answers = testAnswers;
    this.quiz.question = data.question;

  }
  nextQuiz(answer: string) {
    this.quizCount += 1;
    if (this.questions[this.quizCount - 1].answers == answer) {
      this.playerPoints += 1;
    }
    console.log(this.quizCount < this.questions.length)
    if (this.quizCount < this.questions.length) {
      let league: Questions = this.questions[this.quizCount];

      this.loadNewQuiz(league);
    } else {
      this.endTime = new Date();
      const timeTaken = new Date(this.endTime.getTime() - this.startTime.getTime()).toISOString().substr(11, 8);
      this.saveStatistik(timeTaken);
      this.isSelected = false;
      console.log("Quiz Finished");
    }
  }
  onSaveName() {
    this.userName = this.newName;
  }
  saveStatistik(time: string) {
    const statistik: Statistik = {
      points: this.playerPoints,
      time: time,
      userName: this.userName,
    };
    this.carService.createStatistik(statistik).subscribe(response => {
    });
    this.resetQuiz();
    this.isFinished = true;
  }
  resetQuiz() {
    this.userName = '';
    this.newName = '';
    this.isSelected = false;
  }
}
