import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  isEdit = false;
  selectedFile: string | ArrayBuffer | null = null;

  profile = {
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@example.com',
    phoneNumber: '123-456-7890',
    designation: 'Software Engineer',
    //imageUrl: 'assets/default-machine.jpg'
  };

  profileDetails = [
    { label: 'First Name', value: this.profile.firstName },
    { label: 'Last Name', value: this.profile.lastName },
    { label: 'Email', value: this.profile.email },
    { label: 'Phone Number', value: this.profile.phoneNumber },
    { label: 'Designation', value: this.profile.designation }
  ];

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      firstName: [this.profile.firstName, Validators.required],
      lastName: [this.profile.lastName, Validators.required],
      email: [this.profile.email, [Validators.required, Validators.email]],
      phoneNumber: [this.profile.phoneNumber, Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      designation: [this.profile.designation, Validators.required],
     // imageUrl: [this.profile.imageUrl]
    });
  }

  editProfile() {
    this.isEdit = true;
  }

  cancelEdit() {
    this.isEdit = false;
    this.selectedFile = null;
  }

/*
  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedFile = reader.result;
        this.profileForm.patchValue({ imageUrl: reader.result });
      };
      reader.readAsDataURL(file);
    }
  }
*/

  submitProfile() {
    if (this.profileForm.valid) {
      console.log('Form submitted:', this.profileForm.value);
      this.profile = this.profileForm.value;
      this.isEdit = false;
      this.selectedFile = null;
      this.updateProfileDetails();
    }
  }

  private updateProfileDetails() {
    this.profileDetails = [
      { label: 'First Name', value: this.profile.firstName },
      { label: 'Last Name', value: this.profile.lastName },
      { label: 'Email', value: this.profile.email },
      { label: 'Phone Number', value: this.profile.phoneNumber },
      { label: 'Designation', value: this.profile.designation }
    ];
  }
}
