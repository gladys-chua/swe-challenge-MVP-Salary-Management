import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Employee } from '../emp-info/emp.model';

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css']
})
export class EditDialogComponent implements OnInit {

  user: Employee;
  

  constructor(public dialogRef: MatDialogRef<EditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data:Employee) { 

    this.user = data;
  }

  ngOnInit(): void {
  }

  save(){
    this.dialogRef.close(this.user);
  }

  close(){
    this.dialogRef.close();
  }

}
