export interface Student {
  id: number;
  cnp: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  grades: Grade[]

}

export interface Grade {
  id: number;
  course: Course;
  grade: string;
}

export interface Course {
  id: number;
  name: string;
  semester: number;
}
/*
export interface User {
  cnp: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}
*/