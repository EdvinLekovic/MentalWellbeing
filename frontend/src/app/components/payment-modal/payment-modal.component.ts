import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import { Store } from "@ngrx/store";
import {AddCreditCardRequest, CreditCard, PaymentForm} from "../../models/payment-models";
import {User, UserState} from "../../models/user-models";
import {PaymentService} from "../../services/payment-services/payment-service";
import {getLoggedUser} from "../../services/user-store/user-reducer";

@Component({
  selector:"payment-modal",
  templateUrl:"payment-modal.component.html"
})
export class PaymentModalComponent implements OnInit{

  @Input() productId!: number;
  @Input() productPrice!:number;
  @Input() url!: string;

  @Output() closeModal: EventEmitter<void> = new EventEmitter<void>();
  @Output() afterBuyProduct: EventEmitter<void> = new EventEmitter<void>();

  public paymentForm: PaymentForm = {
    name:'',
    lastName:'',
    transactionCardNumber:'',
    cvvCode:'',
    usernameRequest:'',
    productPrice:0,
    productId:0
  }

  public addCreditCardRequest: AddCreditCardRequest = {
    transactionalNumber:'',
    creatorUsername: '',
    cvvCode: '',
    expirationMonth: null,
    expirationYear: null
  }
  public user!: User | null;
  public creditCards!:CreditCard[]
  public selectedCardNumber!:string;

  constructor(private store: Store<UserState>,private paymentService: PaymentService) {
  }

  public selectTransactionToBuyWith(transactionalNumber: string){
    this.selectedCardNumber = transactionalNumber;
  }

  ngOnInit(): void {
    this.getCreditCards();
    this.store.select(getLoggedUser).subscribe(user => {
      this.addCreditCardRequest.creatorUsername = user?.username;
      this.paymentForm.usernameRequest = user?.username;
    })
    this.paymentForm.productId = this.productId;
    this.paymentForm.productPrice = this.productPrice;
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

  public addCreditCard(){
    console.log(this.addCreditCardRequest);
    this.paymentService.addCreditCard(this.addCreditCardRequest).subscribe(creditCard => this.getCreditCards());
  }

  public buyProduct(){
    this.paymentForm.transactionCardNumber = this.selectedCardNumber;
    const url = `/buy-${this.url}`
    this.paymentService.buyProduct(this.paymentForm,url).subscribe(product => {
      // @ts-ignore
      document.getElementById("close").click();
      // @ts-ignore
      document.getElementById("openSuccessfulModal").click();
      console.log(product)
      this.afterBuyProduct.emit();
    });
  }

  closeModalForm(){
    this.closeModal.emit();
  }
}
