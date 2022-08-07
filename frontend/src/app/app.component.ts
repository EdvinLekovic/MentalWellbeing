import {Component, OnDestroy, OnInit} from '@angular/core';
import * as UserActions from "./services/user-store/user-action";
import {UserState} from "./models/user-models";
import {Store} from "@ngrx/store";
import {Router} from "@angular/router";
import {UserService} from "./services/user-services/user.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit,OnDestroy {


  public validationCheckFinished = false;

  private checkValiditySubscription!: Subscription | null;

  constructor(private store: Store<UserState>,private router:Router,private userService:UserService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  ngOnDestroy(): void {
    this.checkValiditySubscription?.unsubscribe();
  }

  public loadUser() {
    const token = localStorage.getItem("token");
    console.log(token);
      if(token){
        console.log("here");
        this.checkValiditySubscription = this.userService.checkUserValidity({token: token}).subscribe(
          isAuthenticated => {
            console.log(isAuthenticated);
            if(!isAuthenticated){
              localStorage.removeItem("token");
              localStorage.removeItem("user");
            }
            else {
              console.log("getUserAction");
              this.store.dispatch(UserActions.getUserAction({userAndTokenRequest:{user:null,token:token}}));
            }
            this.validationCheckFinished = true;
            console.log(this.validationCheckFinished);
          },
          error => this.validationCheckFinished = true
        )
      }
      else {
        this.validationCheckFinished = true;
      }
  }
}
