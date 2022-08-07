export interface AddCreditCardRequest {
  transactionalNumber: string,
  creatorUsername: string | undefined,
  cvvCode: string,
  expirationMonth: number | null,
  expirationYear: number | null
}

export interface CreditCardNumberRequest {
  cardNumber: string
}

export interface CreditCard {
  transactionalNumber:string,
  cvvCode: string,
  expirationMonth: number,
  expirationYear: number
}

export interface PaymentForm {
  name: string,
  lastName:string,
  transactionCardNumber: string,
  cvvCode:string,
  usernameRequest:string | undefined,
  productPrice: number,
  productId: number
}
