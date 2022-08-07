import {Injectable} from "@angular/core";
import {UserService} from "../user-services/user.service";
import {act, Actions, createEffect, ofType} from "@ngrx/effects";
import * as UserAction from "./user-action";
import {catchError, concatMap, exhaustMap, map, mergeMap, of, switchMap, tap} from "rxjs";
import {User} from "../../models/user-models";

@Injectable()
export class UserEffects {

  constructor(private actions$: Actions, private userService: UserService) {
  }

  getUserToken$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(UserAction.getUserTokenAction),
      exhaustMap(action =>
        this.userService.authenticateUser(action.userRequest).pipe(
          tap((token: string) => console.log('token',token)),
          map((token: string) => {
            console.log("token in effect here");
            console.log(token);
            localStorage.setItem("token",token);
            return UserAction.getUserTokenAction(
            {
              userRequest: {
                username: action.userRequest.username,
                password: action.userRequest.password,
                token: token
              }
            })}),
          catchError(error => {
            console.log("error");
            console.log(error);
            return of(error);
          })
        )
      ))
  });

  checkUserValidity$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(UserAction.checkUserValidityAction),
      exhaustMap(action => this.userService.checkUserValidity({token: action.tokenValidityRequest.token})
        .pipe(
          tap((isUserAuthenticated:any) => console.log(isUserAuthenticated)),
            map((isUserAuthenticated: boolean) => UserAction.checkUserValidityAction({
              tokenValidityRequest: {
                token: action.tokenValidityRequest.token,
                isAuthenticated: isUserAuthenticated
              }
            }))
          )))
  })

  getUser$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(UserAction.getUserAction),
      exhaustMap(action =>
        this.userService.loginUser({token: action.userAndTokenRequest.token}).pipe(
          tap((response: any) => console.log(response)),
          map((user: User) => UserAction.getUserAction({
            userAndTokenRequest:
              {
                user: user,
                token: action.userAndTokenRequest.token
              }
          }))
        ))
    )
  })
}
