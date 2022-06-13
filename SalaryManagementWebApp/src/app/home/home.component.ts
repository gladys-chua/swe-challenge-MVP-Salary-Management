import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }

  filename = ''; 
  uploadMsg:string = "";
  error=null;

  onFileSelected(event:any) {
    const file:File = event.target.files[0];
    // console.log(file)

    if (file) {
      this.filename = file.name;

      const formData = new FormData();
      formData.append("file", file);
      const upload$ = this.http.post<{msg:string}>("http://localhost:8081/users/upload", formData);
      upload$.subscribe(responseData=>{
        console.log(responseData);
        this.uploadMsg = responseData.msg;
      }, error=>{
        this.uploadMsg = error.message;
      });
    }
  }

}
