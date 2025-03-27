import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {
  message = ''; // for displaying message to user about activation status
  isOk = true; // for displaying success message
  submitted = false; // for disabling submit button after submission
  constructor(
    private router : Router,
    private authService: AuthenticationService

  ) {

  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);

  }

  redirectTologin() {
    this.router.navigate(['/login']);

  }

  private confirmAccount(token: string) {
    this.authService.confirm({ token
    }).subscribe(
      () => {
        this.message = 'Account activated successfully. You can now login';
        this.submitted = true;
        this.isOk = true;
      },
     error => {
        this.message = 'Token has been expired or invalid. Please try again';
        this.isOk = false;
        this.submitted = true;
      }
    );
  }
}
