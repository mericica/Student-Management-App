import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { StudentService } from "../student.service";
import { Router } from "@angular/router";
import { Student } from "../models";

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  styleUrl: './add-student.component.scss'
})
export class AddStudentComponent {

  hide = true;

  registerForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    cnp: ['', Validators.required],
    email: new FormControl('', [Validators.required, Validators.email]),
    password: ['', Validators.required],
  });
  isSubmitted = false;

  constructor(private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router) {
  }


  ngOnInit(): void {
    this.registerForm.get('roleId')?.valueChanges.subscribe((roleId) => {
      console.log('SEND API REQUEST AND UPDATE ROLE', roleId);
    });
  }


  onSubmit() {
    this.studentService.createUser(this.registerForm.value)
      .subscribe(() => this.router.navigateByUrl('/students'));
  }
}
