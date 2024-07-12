import { Component } from '@angular/core';
import {MatDrawerMode, MatSidenavContent} from "@angular/material/sidenav";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'student-app';

  isNavBarActive = false;

  openNavBar(){
    this.isNavBarActive = !this.isNavBarActive
  }

  events: string[] = [];
  opened!: boolean;

  mode = new FormControl('over' as MatDrawerMode);
}
