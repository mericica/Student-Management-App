import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import { StudentListComponent } from "./student-list/student-list.component";
import { StudentDetailsComponent } from "./student-details/student-details.component";
import { AddStudentComponent } from "./add-student/add-student.component";
import { StudentsComparisonComponent } from './students-comparison/students-comparison.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'students', component: StudentListComponent },
  { path: 'student-details/:id', component: StudentDetailsComponent },
  { path: 'add-student', component: AddStudentComponent },
  { path: 'students-comparison', component: StudentsComparisonComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
