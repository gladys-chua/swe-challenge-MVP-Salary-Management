import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { map } from 'rxjs';
import { Employee } from './emp.model';

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

  constructor(private http:HttpClient, private route: ActivatedRoute, private router:Router) { 
  }

  ngOnInit(){
    this.fetchUsers();
  }

  private fetchUsers(){
    this.http.get<Employee[]>("http://localhost:8081/users")
    .subscribe(u=>{
      console.log(u);
      this.users=u.slice(this.offset,this.limit);
    });
  }

  valChange() {
    // console.log(this.minS,this.maxS);
    this.router.navigate(['/employees'],{queryParams: {minSalary:this.minS, maxSalary:this.maxS, offset:this.offset, limit:this.limit}});
    this.http.get<Employee[]>("http://localhost:8081/users/"+this.minS+"/"+this.maxS+"/"+this.offset+"/"+this.limit)
    .subscribe(data=>{
      // console.log(data);
      if (data === null){
        this.users=[];
      } else {
        this.users=data.slice(this.offset,this.limit);
      }
    });
  }
  
  onEmpDelete(u:Employee){
    console.log(u);
    this.http.delete<Employee[]>("http://localhost:8081/user/"+u.id).subscribe(v=>{
      // console.log(v)
      if (v === null){
        this.users=[];
      } else {
        var index = this.users.indexOf(u)
        if (index !== -1) {
          this.users.splice(index, 1);
        }
      }
    })
  }

}
