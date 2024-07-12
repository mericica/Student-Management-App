import {Component, EventEmitter, Output} from '@angular/core';
import {Student} from "../models";
import {StudentService} from "../student.service";
import {Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, Observable, Subject, switchMap} from "rxjs";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent {
  constructor(private studentService: StudentService, private router: Router) {
  }

  @Output() studentClicked: EventEmitter<any> = new EventEmitter<any>();
  students: Student[] = [];
  name: string = "";

  ngOnInit(): void {
    this.getStudents();
  }

  goToStudentDetails(student: Student): void {
    console.log(student.id);
    this.router.navigateByUrl(`/student-details/${student.id}`);
  }

  filterUser(name:string){
    this.studentService.filterUser(name).subscribe((fetchedStudents: Student[]) => this.students = fetchedStudents);
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((fetchedStudents: Student[]) => this.students = fetchedStudents);
  }

}
