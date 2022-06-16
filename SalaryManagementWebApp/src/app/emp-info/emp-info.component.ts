import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Employee } from './emp.model';
import { MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { EditDialogComponent } from '../edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-emp-info',
  templateUrl: './emp-info.component.html',
  styleUrls: ['./emp-info.component.css']
})
export class EmpInfoComponent implements OnInit {

  users:Employee[] = [];
  headElements:string[] = ['id',"name","login","salary"];
  minS: number = 0;
  maxS: number = 99999;
  offset: number = 0;
  limit: number = 30;
  private apiUrl:string = "http://localhost:8081/users/"; 

  constructor(private http:HttpClient, 
              private route: ActivatedRoute, 
              private router:Router,
              private editDialog: MatDialog) { 
  }

  ngOnInit(){
    this.fetchUsers();
  }

  private fetchUsers(){
    this.http.get<Employee[]>(this.apiUrl)
    .subscribe(u=>{
      console.log(u);
      this.users=u.slice(this.offset,this.limit);
    });
  }

  valChange() {
    this.router.navigate(['/employees'],{queryParams: {minSalary:this.minS, maxSalary:this.maxS, offset:this.offset, limit:this.limit}});
    this.http.get<Employee[]>(this.apiUrl+this.minS+"/"+this.maxS+"/"+this.offset+"/"+this.limit)
    .subscribe(data=>{
      if (data === null){
        this.users=[];
      } else {
        this.users=data.slice(this.offset,this.limit);
      }
    });
  }
  
  onEmpDelete(u:Employee){
    this.http.delete<Employee[]>(this.apiUrl+u.id).subscribe(v=>{
      if (v === null){
        this.users=[];
      } else {
        var index = this.users.indexOf(u)
        if (index !== -1) {
          this.users.splice(index, 1);
        }
      }
    });
  }

  openDialog(u:Employee): void{

    const dialogRef = this.editDialog.open(EditDialogComponent,{
      width: '300px',
      data: u
    });
    
    dialogRef.afterClosed().subscribe( data => {
      this.http.get<Employee>(this.apiUrl+"update/"+data.id+"/"+data.login+"/"+data.name+"/"+data.salary).subscribe()
    });
  }

}
