import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AddCreditCardRequest, CreditCardNumberRequest, PaymentForm} from "../../models/payment-models";
import {paymentApi} from "../../config/url-api.config";
import {Observable} from "rxjs";
import {ProductUserRequest, UsernameRequest} from "../../models/user-models";

@Injectable({
  providedIn:"root"
})
export class PaymentService {

  constructor(private http:HttpClient) {}

  public getCreditCardsByUser(usernameRequest:UsernameRequest): Observable<any>{
    return this.http.post(paymentApi,usernameRequest);
  }

  public addCreditCard(addCreditCardRequest: AddCreditCardRequest): Observable<any>{
    return this.http.post(paymentApi + '/add-credit-card-by-user',addCreditCardRequest);
  }

  public deleteCreditCard(creditCardNumberRequest: CreditCardNumberRequest){
    this.http.post(paymentApi+ "/delete-credit-card",creditCardNumberRequest);
  }

  public buyProduct(paymentForm: PaymentForm,url:string): Observable<any>{
    return this.http.post(paymentApi + url,paymentForm);
  }

}
