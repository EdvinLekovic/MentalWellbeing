import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {
  EditUserRequest,
  TokenRequest,
  TokenUserAuthenticationRequest,
  User,
  UserLoginRequest
} from "../../models/user-models";
import {userUrlApi} from "../../config/url-api.config";
import {map, Observable, tap} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService{

  constructor(private http:HttpClient) {
  }

  public registerUser(user: User){
    console.log(userUrlApi + "/register");
    console.log(user);
    return this.http.post(userUrlApi + "/register",user,
      {
        headers: {
        'Access-Control-Allow-Origin': '*'
      }
    },);
  }

  public authenticateUser(userLoginRequest: UserLoginRequest): Observable<any>{
    console.log(userLoginRequest);
    console.log("authenticateUser");
    return this.http.post(userUrlApi + "/authenticate",userLoginRequest,{
      headers:{
        'Access-Control-Allow-Origin' : '*'
      },
      responseType: 'text'
    }).pipe(
      map(response => response)
    );
  }

  public checkUserValidity(tokenRequest: TokenRequest){
    return this.http.post(userUrlApi + "/user-expiration",tokenRequest);
  }

  public loginUser(tokenRequest: TokenRequest){
    return this.http.post(userUrlApi + "/get-user-by-token",tokenRequest);
  }

  public editUser(editUserRequest: EditUserRequest): Observable<User>{
    return this.http.post<User>(userUrlApi + "/edit-user",editUserRequest);
  }

}
