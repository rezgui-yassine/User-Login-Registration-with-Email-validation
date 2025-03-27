import { Component } from '@angular/core';
import {LoginRequest} from "../../services/models/login-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest:LoginRequest = {email:'', password :''};
  errorMsg:Array<String>=[];


  constructor(
    private router:Router,
    private authService: AuthenticationService,
    //todo : add the service here

  ) { }

  login() {
    this.errorMsg = [];
    this.authService.login({
      body:this.authRequest
    }).subscribe(
      {
        next:():void => {
          //todo save the token

          this.router.navigate(['/']);
        },
        error:(err):void => {
          console.error(err);
        }
      }
    );

  }

  register() {
    this.router.navigate(['/register']);

  }
}
