import {createAction, props} from "@ngrx/store";
import {
  EditUserRequest,
  TokenUserAuthenticationRequest,
  User,
  UserAndTokenRequest,
  UserLoginRequest
} from "../../models/user-models";

export const getUserTokenAction = createAction(
  "[User] Get User Token",
  props<{userRequest: UserLoginRequest}>());

export const checkUserValidityAction = createAction(
  "[User] Check User Validity",
  props<{tokenValidityRequest: TokenUserAuthenticationRequest}>());

export const getUserAction = createAction(
  "[User] Get Logged User",
  props<{userAndTokenRequest: UserAndTokenRequest}>());

export const logoutUser = createAction(
  "[User] Logout User"
)

export const getUserAfterEdit = createAction(
  "[User] Get User After Edit",
  props<{user: User}>())
