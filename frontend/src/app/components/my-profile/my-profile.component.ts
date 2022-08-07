import {Component, OnInit} from "@angular/core";
import {User, UserState} from "../../models/user-models";
import {Store} from "@ngrx/store";
import {getUserAfterEdit} from "../../services/user-store/user-action";
import {getLoggedUser} from "../../services/user-store/user-reducer";

@Component({
  selector:"my-profile",
  templateUrl:"my-profile.component.html"
})
export class MyProfileComponent implements OnInit {
  public user!: User | null;

  constructor(private store: Store<UserState>) {
  }

  ngOnInit(): void {
    this.store.select(getLoggedUser).subscribe( user => this.user = user);
  }


}
