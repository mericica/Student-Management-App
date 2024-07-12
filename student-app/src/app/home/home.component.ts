import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {


  studentListActive: boolean = false;
  studentDetailsActive: boolean = false;

  constructor(private router: Router) {
  }

  goToStudentList(): void {
    this.studentListActive = !this.studentListActive;
    this.studentDetailsActive = false; // Close student details
    this.router.navigateByUrl('/students');
  }

}
