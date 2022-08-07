import {Component, OnDestroy} from "@angular/core";
import {User} from "../../models/user-models";
import {UserService} from "../../services/user-services/user.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  templateUrl:"register-page.component.html"
})
export class RegisterPageComponent implements OnDestroy {

  public user: User = {
    username: '',
    name: '',
    lastName: '',
    password: '',
    repeatPassword: '',
    role: ''
  }

  private userRegisterSubscription: Subscription | null = null;

  constructor(private userService:UserService,private router:Router) {
  }

  public registerUser(){
    this.userRegisterSubscription = this.userService.registerUser(this.user).subscribe(
      user => console.log(user)
    );
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.userRegisterSubscription?.unsubscribe();
  }

}
