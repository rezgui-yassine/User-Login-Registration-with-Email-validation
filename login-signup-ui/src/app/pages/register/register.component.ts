import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  registerRequest: RegistrationRequest = {

    firstName:'',
    lastName:'',
    email:'',
    password:'',
  };
  // error message to display on the form
  errorMsg:Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,

  ) {
  }


  register() {
    this.errorMsg=[];
    this.authService.register(
      {
        body:this.registerRequest
      }
    ).subscribe(
      {
        next: () => {
          this.router.navigate(['activate-account']);
        },
        error:(err)=>{
          this.errorMsg=err.error.validationErrors;
        }
      }
    )

  }

  login() {
    this.router.navigate(['/login']);

  }
}
