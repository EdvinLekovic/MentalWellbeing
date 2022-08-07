import {Component, OnDestroy, OnInit} from "@angular/core";
import {UserLoginRequest, UserState} from "../../models/user-models";
import {Store} from "@ngrx/store";
import * as UserActions from "../../services/user-store/user-action";
import {Observable, Subscription} from "rxjs";
import {getUserToken} from "../../services/user-store/user-reducer";
import {UserService} from "../../services/user-services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "login-page.component.html"
})
export class LoginPageComponent implements OnDestroy {

  public user: UserLoginRequest = {
    username: '',
    password: '',
    token: ''
  };

  public user$!: Observable<string | null>;
  public userTokenSubscription!: Subscription | null;
  public buttonStyle = {backgroundColor: 'rgba(51,26,71,1)'};

  constructor(private userService: UserService, private store: Store<UserState>, private router: Router) {
  }

  ngOnDestroy(): void {
    this.userTokenSubscription?.unsubscribe();
  }

  public onHover() {
    this.buttonStyle.backgroundColor = 'rgba(51,26,51,1)'
  }

  public onLeave() {
    this.buttonStyle.backgroundColor = 'rgba(51,26,71,1)';
  }

  public async loginUser() {
    this.userTokenSubscription = this.store.select(getUserToken).subscribe(
      token => {
        console.log("token arrived");
        if (!token) {
          this.store.dispatch(UserActions.getUserTokenAction({userRequest: this.user}))
        } else {
          console.log(token);
          this.store.dispatch(UserActions.getUserAction({userAndTokenRequest: {user: null, token: token}}))
          this.router.navigate(['/home']);
        }
      },
      error => console.log(error),
      () => console.log("complete logging user")
    )
  }

}
