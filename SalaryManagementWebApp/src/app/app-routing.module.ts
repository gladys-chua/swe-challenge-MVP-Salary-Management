import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpInfoComponent } from './emp-info/emp-info.component';
import { HomeComponent } from './home/home.component';


const routes: Routes = [
  {path:"",component:HomeComponent},
  {path:"employees",component:EmpInfoComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
