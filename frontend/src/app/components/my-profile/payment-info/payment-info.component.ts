import {Component, OnInit} from "@angular/core";
import {AddCreditCardRequest, CreditCard, CreditCardNumberRequest} from "../../../models/payment-models";
import {PaymentService} from "../../../services/payment-services/payment-service";
import {paymentApi} from "../../../config/url-api.config";
import {User, UsernameRequest, UserState} from "../../../models/user-models";
import {Store} from "@ngrx/store";
import {UserService} from "../../../services/user-services/user.service";
import {getLoggedUser} from "../../../services/user-store/user-reducer";

@Component({
  templateUrl:"payment-info.component.html"
})
export class PaymentInfoComponent implements OnInit{

  public modalOpened = false;
  public creditCards!: CreditCard[];
  public addCreditCardRequest: AddCreditCardRequest = {
    transactionalNumber:'',
    creatorUsername: '',
    cvvCode: '',
    expirationMonth: null,
    expirationYear: null
  };

  constructor(private paymentService: PaymentService,private store:Store<UserState>) {
  }

  ngOnInit() {
    this.getCreditCards();
  }

  getCreditCards(){
    this.store.select(getLoggedUser).subscribe(user => {
      if(user) {
        this.addCreditCardRequest.creatorUsername = user.username;
        this.paymentService.getCreditCardsByUser({username: user?.username})
          .subscribe(creditCards => this.creditCards = creditCards)
      }
    }
  );
  }

  openModal(){
    this.modalOpened = true;
  }

  closeModal(){
  this.modalOpened = false;
  }

  addCreditCard(){
      this.paymentService.addCreditCard(this.addCreditCardRequest).subscribe(() => this.getCreditCards());
      this.modalOpened = false;
  }

  deleteCreditCard(transactionalNumber: string){
    let creditCardNumberRequest: CreditCardNumberRequest = { cardNumber: transactionalNumber }
    this.paymentService.deleteCreditCard(creditCardNumberRequest);
  }
}
