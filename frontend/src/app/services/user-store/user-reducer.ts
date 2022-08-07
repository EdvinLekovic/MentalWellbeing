import {createFeatureSelector, createReducer, createSelector, on} from "@ngrx/store";
import {TokenUserAuthenticationRequest, UserState} from "../../models/user-models";
import {checkUserValidityAction, getUserAction, getUserAfterEdit, getUserTokenAction, logoutUser} from "./user-action";

const initialUser: UserState = {
  isAuthenticated: false,
  token: null,
  currentUser: null,
  authentication: null
}

export const getUserState = createFeatureSelector<UserState>("user");

export const getUserToken = createSelector(
  getUserState,
  state => state.token
)

export const authentication = createSelector(
  getUserState,
  state => {
    const authentication: TokenUserAuthenticationRequest = {
      token: state.token,
      isAuthenticated: state.isAuthenticated
    }
    return authentication;
  }
)

export const getLoggedUser = createSelector(
  getUserState,
  state => state.currentUser
)

export const userReducer = createReducer<UserState>(
  initialUser,
  on(getUserTokenAction, (state, action): UserState => {
    console.log(action.userRequest);
    return {
      ...state,
      token: action.userRequest.token
    }
  }),
  on(checkUserValidityAction, (state, action): UserState => {
    return {
      ...state,
      isAuthenticated: action.tokenValidityRequest.isAuthenticated
    }
  }),
  on(getUserAction, (state, action): UserState => {
    console.log(action.userAndTokenRequest);
    return {
      ...state,
      currentUser: action.userAndTokenRequest.user
    }
  }),
  on(logoutUser, (): UserState => {
    return {
      ...initialUser
    }
  }),
  on(getUserAfterEdit, (state,action): UserState => {
    console.log(action.user);
    return {
      ...state,
      currentUser: action.user
    }
  })
)
