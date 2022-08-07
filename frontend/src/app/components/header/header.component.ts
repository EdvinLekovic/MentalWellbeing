import {Component, OnDestroy, OnInit} from "@angular/core";
import {User, UserState} from "../../models/user-models";
import {Observable, Subscription} from "rxjs";
import {Store} from "@ngrx/store";
import {authentication, getLoggedUser, getUserToken} from "../../services/user-store/user-reducer";
import * as UserActions from "../../services/user-store/user-action";
import {Router} from "@angular/router";

@Component({
  selector:"app-header",
  templateUrl:"header.component.html"
})
export class HeaderComponent implements OnInit {

  public user$!: Observable<User | null>;
  public user!: User | null;

  constructor(private store:Store<UserState>,private router: Router) {
  }

  ngOnInit(): void {
    this.store.select(getLoggedUser).subscribe(
      user => {
        console.log('get user in header comp')
        this.user = user
      }
    );
    console.log(this.user);
  }

  logoutUser(){
    this.store.dispatch(UserActions.logoutUser());
    localStorage.removeItem("token");
  }

}
