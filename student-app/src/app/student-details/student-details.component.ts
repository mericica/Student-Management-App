import { booleanAttribute, Component } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { StudentService } from "../student.service";
import { Course, Student } from "../models";
import { Grade } from "../models";

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.scss'
})
export class StudentDetailsComponent {

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private router: Router
    //private location: Location for going back
  ) {
  }


  selectedStudent: Student = { id: 0, cnp: "", firstName: "", lastName: "", email: "", password: "", grades: [] };

  newFirstName: string = "";
  newLastName: string = "";
  newCNP: string = "";


  average = 0;
  windowMode = "default";
  courses: Course[] = [];
  newGrade: Grade = { id: 0, grade: "", course: { id: 0, name: "", semester: 0 } };
  selectedCourse!: any;

  ngOnInit(): void {
    this.getStudent();
    this.getCourses();
  }

  getStudent(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.studentService.getStudents().subscribe(students => {

      const foundStudent = students.find(student => student.id === id);
      if (foundStudent !== undefined)
        this.selectedStudent = foundStudent;
      console.log(this.selectedStudent); this.calculateAverage();
    });
  }

  getCourses() {
    this.studentService.getCourses().subscribe(courses => {
      this.courses = courses; this.selectedCourse = courses[0];
    }
    )
  }

  calculateAverage(): void {
    if (this.selectedStudent.grades.length === 0) return;
    let sum = 0;
    for (let grade of this.selectedStudent.grades) {
      console.log(grade.grade)
      sum = sum + parseInt(grade.grade)
    }
    this.average = sum / this.selectedStudent.grades.length;
  }


  save(): void {
    this.selectedStudent.firstName = this.newFirstName;
    this.selectedStudent.lastName = this.newLastName;
    this.selectedStudent.cnp = this.newCNP;

    if (this.selectedStudent) {
      this.studentService.updateUser(this.selectedStudent)
        .subscribe(() => this.router.navigateByUrl("/students"));
    }
  }

  delete(): void {
    this.studentService.deleteUser(this.selectedStudent.id)
      .subscribe(() => this.router.navigateByUrl("/students"));
  }

  deleteGrade(grade: any) {
    this.studentService.deleteGrade(this.selectedStudent.id, grade.course.id)
      .subscribe(() => {
        this.getStudent();
      });
  }

  goToDefault() {
    this.windowMode = "default";
  }

  goToUpdate() {
    //this.updateToggle = !this.updateToggle;
    this.newFirstName = this.selectedStudent.firstName;
    this.newLastName = this.selectedStudent.lastName;
    this.newCNP = this.selectedStudent.cnp;

    if (this.windowMode === "update") {
      this.windowMode = "default";
    }
    else {
      this.windowMode = "update";
    }
  }

  addGrade() {
    this.windowMode = 'addGrade';
  }

  selectCourse(course: Course) {
    this.selectedCourse = course;
  }

  addGradeToStudent() {
    if (this.newGrade && this.selectedCourse) {

      this.studentService.assignGrade({
        user: { id: this.selectedStudent.id },
        course: {
          id: this.selectedCourse.id,
        },
        grade: this.newGrade.grade,
      })
        .subscribe(() => {
          this.getStudent();
          this.windowMode = 'default';
        });
    }
  }

}
