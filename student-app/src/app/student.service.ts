import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of, tap } from "rxjs";
import { catchError } from 'rxjs/operators';
import { Course, Grade, Student } from "./models";
import { MessageService } from "./message.service";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {
  }

  private studentsUrl = 'http://localhost:8080/user'; //muss bearbeitet werden
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error);

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`StudentService: ${message}`);
  }


  getStudents(): Observable<Student[]> {

    return this.http.get<Student[]>(this.studentsUrl + "/")
      .pipe(
        catchError(this.handleError<Student[]>('getStudents', []))
      );
  }

  filterUser(name: string): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsUrl + `?name=${name}`);
  }


  getCourses(): Observable<Course[]> {

    return this.http.get<Course[]>(this.studentsUrl + "/courses")
      .pipe(
        catchError(this.handleError<Course[]>('getCourses', []))
      );
  }




  createUser(user: any): Observable<Student> {
    console.log(user);
    return this.http.post<Student>(this.studentsUrl + '/create', user).pipe(
      tap((newHero: Student) => this.log(`added Student w/ id=${newHero.id}`)),
      catchError(this.handleError<Student>('createStudent'))
    );
  }

  updateUser(user: any): Observable<any> {
    return this.http.put(this.studentsUrl + `/${user.id}`, user, this.httpOptions).pipe(
      tap(_ => this.log(`updated hero id=${user.id}`)),
      catchError(this.handleError<any>('updateStudent'))
    );
  }

  assignGrade(grade: any): Observable<string> {
    console.log("grade", grade);
    return this.http.put<string>(this.studentsUrl + `/assignGrade`, grade);
  }

  getUser(id: number): Observable<Student> {
    return this.http.get<Student>(`/user/${id}`);
  }

  deleteUser(id: number): Observable<boolean> {
    const url = `${this.studentsUrl}/${id}`;

    return this.http.delete<boolean>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted student id=${id}`)),
      catchError(this.handleError<boolean>('deleteStudent'))
    );
  }

  deleteGrade(userid: number, courseid: number): Observable<boolean> {
    const url = `${this.studentsUrl}/deleteGrade/${userid}/${courseid}`;

    return this.http.delete<boolean>(url,).pipe(
      tap(_ => this.log(`deleted grade id=${courseid}`)),
      catchError(this.handleError<boolean>('deleteGrade'))
    );

  }


  assignRolesToUser(userEmail: string, roleId: number): Observable<string> {
    return this.http.post<string>(`/user/${userEmail}/${roleId}`, null);
  }
}
