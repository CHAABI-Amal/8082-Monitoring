import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from "@angular/forms";
import {TypeStatus} from "../model/composants.model";
import {ActivatedRoute} from "@angular/router";
import {ComposantsService} from "../services/composants.service";

@Component({
  selector: 'app-new-composant',
  templateUrl: './new-composant.component.html',
  styleUrls: ['./new-composant.component.css']
})
export class NewComposantComponent implements OnInit {
  composantFormGroup!: FormGroup;
  typeStatus: string[]=[];
  private showProgress: boolean=false;



  constructor(private fb: FormBuilder,
              private activatedRoute:ActivatedRoute,
              private composantsService : ComposantsService) {

  }

  ngOnInit(){
    for(let elt in TypeStatus){
      let value=TypeStatus[elt];
      if(typeof value === 'string'){
        this.typeStatus.push(value);
      }
    }
    this.composantFormGroup = this.fb.group({
      code: this.fb.control(''),
      name: this.fb.control(''),
     // composantCreatedDate: this.fb.control(''),
      value: this.fb.control(''),
      lastStatus: this.fb.control(''),
     // lastStatusChangeTime: this.fb.control(''),
     // lastStatusChangeTime: this.fb.control(''),
      model: this.fb.control(''),

    });
  }

  saveComposant() {
    this.showProgress=true;
   // let date=new Date(this.composantFormGroup.value.lastStatusChangeDate);
   // let formattedDate=date.getDate()+"/"+(date.getMonth()+1)+'/'+date.getFullYear();
    let formData=new FormData();
    //***************
    //formData.set('lastStatusChangeTime',formattedDate );
    //*************
    formData.set('code',this.composantFormGroup.value.code);
    formData.set('name',this.composantFormGroup.value.name);
    formData.set('value',this.composantFormGroup.value.value);
    formData.set('lastStatus',this.composantFormGroup.value.lastStatus);
    //formData.set('lastStatusChangeTime',this.composantFormGroup.value.lastStatusChangeTime);
    formData.set('model',this.composantFormGroup.value.model);
    this.composantsService.saveComposant(formData).subscribe({
      next: value => {
        this.showProgress=false;
        alert('Composant  Saved successfully')
      },
      error:err => {
        console.log(err);
      }
    });
  }

  private combineDateAndTime(date: string, time: string): string {
    return `${date}T${time}:00`; // Combine date and time into an ISO 8601 string
  }
}
