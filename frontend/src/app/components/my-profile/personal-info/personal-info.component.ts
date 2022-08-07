import {Component, OnInit} from "@angular/core";
import {EditUserRequest, User, UserState} from "../../../models/user-models";
import {UserService} from "../../../services/user-services/user.service";
import * as UserActions from "../../../services/user-store/user-action";
import {Store} from "@ngrx/store";
import {getLoggedUser} from "../../../services/user-store/user-reducer";

@Component({
  templateUrl:"personal-info.component.html"
})
export class PersonalInfoComponent implements OnInit {

  public editUserRequest: EditUserRequest = {
    username: '',
    name: '',
    lastName:'',
    password:'',
    repeatPassword:''
  };
  public user!: User | null;

  constructor(private userService: UserService,private store: Store<UserState>) {}

  ngOnInit(): void {
    this.store.select(getLoggedUser).subscribe(user =>
      this.editUserRequest = {username: user?.username,
        name:user?.name,
        lastName:user?.lastName,
        password: '',
        repeatPassword:''});
  }

  public editUser(){
      this.userService.editUser(this.editUserRequest).subscribe( editedUser =>
        this.store.dispatch(UserActions.getUserAfterEdit({user: editedUser})));
  }

}
