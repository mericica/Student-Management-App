import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // <-- NgModel lives here
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { StudentListComponent } from './student-list/student-list.component';
import { StudentDetailsComponent } from './student-details/student-details.component';
import { AddStudentComponent } from './add-student/add-student.component';
import { MessagesComponent } from './messages/messages.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from "@angular/material/sidenav";
import { MatCheckbox } from "@angular/material/checkbox";
import { MatRadioButton, MatRadioGroup } from "@angular/material/radio";
import { MatButton } from "@angular/material/button";
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatError, MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput, MatInputModule } from "@angular/material/input";
import { MatIcon, MatIconModule } from "@angular/material/icon";
import { MatCell, MatColumnDef, MatHeaderCell, MatHeaderRow, MatRow, MatTable } from "@angular/material/table";
import { MatSelectModule } from '@angular/material/select';

import { StudentsComparisonComponent } from './students-comparison/students-comparison.component';
import { HighchartsChartModule } from 'highcharts-angular';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    StudentListComponent,
    StudentDetailsComponent,
    AddStudentComponent,
    MessagesComponent,
    StudentsComparisonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatSlideToggleModule,
    MatSidenavContainer,
    MatSidenav,
    MatSidenavContent,
    MatCheckbox,
    MatRadioGroup,
    MatRadioButton,
    MatButton,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardSubtitle,
    MatCardTitle,
    MatFormField,
    MatError,
    MatLabel,
    MatInput,
    MatInputModule,
    MatIcon,
    MatIconModule,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    HighchartsChartModule,
    MatSelectModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
