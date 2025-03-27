import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  //1 set token
  set token(token: string) {
    localStorage.setItem('token', token);
  }
  //2 get token
  get token(): string {
    return localStorage.getItem('token') as string ;
  }
}
