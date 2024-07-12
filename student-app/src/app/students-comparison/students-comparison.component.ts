import { Component, ViewChild } from '@angular/core';

import * as Highcharts from 'highcharts';
import { Student } from '../models';
import { StudentService } from '../student.service';



@Component({
  selector: 'app-students-comparison',
  templateUrl: './students-comparison.component.html',
  styleUrl: './students-comparison.component.scss'
})
export class StudentsComparisonComponent {

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.getStudents();
  }


  getStudents(): void {
    this.studentService.getStudents()
      .subscribe(
        (fetchedStudents: Student[]) => this.allStudents = fetchedStudents.sort((s1, s2) => s1.firstName.localeCompare(s2.firstName)),
      );
  }
  allStudents: Student[] = [];

  selectedStudents: Student[] = [{ id: 0, cnp: "", firstName: "Student 1", lastName: "", email: "", password: "", grades: [] },
  { id: 0, cnp: "", firstName: "Student 2", lastName: "", email: "", password: "", grades: [] },
  { id: 0, cnp: "", firstName: "Student 3", lastName: "", email: "", password: "", grades: [] }];;

  selectStudent(student: Student, position: number): void {
    console.log("Selected student: ", student);
    this.selectedStudents[position] = student;
    console.log(this.selectedStudents);
    this.updateChartOptions();
  }

  @ViewChild('chart') chart: any; // Template reference variable for the chart

  Highcharts: typeof Highcharts = Highcharts;

  // Method to update the chart
  updateChart(): void {
    if (this.chart && this.chart.chart) {
      this.chart.chart.update(this.chartOptions);
    }
  }

  chartOptions: Highcharts.Options = {
    chart: {
      type: 'column'
    },
    title: {
      text: 'Students Comparison'
    },
    xAxis: {
      categories: []
    },
    yAxis: {
      min: 1,
      max: 10,
      title: {
        text: 'Grades'
      }
    },
    series: [
      {
        name: this.selectedStudents[0].firstName,
        data: this.selectedStudents[0].grades.map(grade => parseInt(grade.grade)),
        type: 'column'
      },
      {
        name: this.selectedStudents[1].firstName,
        data: this.selectedStudents[1].grades.map(grade => parseInt(grade.grade)),
        type: 'column'
      },
      {
        name: this.selectedStudents[2].firstName,
        data: this.getGrades(this.selectedStudents[2]),
        type: 'column'
      }
    ]
  }

  updateChartOptions(): void {
    this.chartOptions.xAxis = {
      categories: this.getCategories()
    };
    console.log("this.chartOptions.xAxis", this.chartOptions.xAxis);
    this.chartOptions.series = this.selectedStudents.map((student, index) => ({
      name: student.firstName,
      data: this.getGrades(student),
      type: 'column'
    }));
    console.log("this.chartOptions.series", this.chartOptions.series);
    this.updateChart();
  }

  getCategories(): string[] {
    const categories: string[] = [];
    for (let s of this.selectedStudents) {
      s.grades.forEach(grade => {
        if (!categories.includes(grade.course.name)) {
          categories.push(grade.course.name);
        }
      });
    }

    return categories;
  }

  getGrades(student: Student): number[] {
    const grades: number[] = [];
    this.getCategories().forEach(category => {
      const grade = parseInt(student.grades.find(g => g.course.name === category)?.grade || '0');
      grades.push(grade);
    });
    console.log("gradeeeees:", grades);
    return grades;
  };
}


