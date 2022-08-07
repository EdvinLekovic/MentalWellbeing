import {User} from "./user-models";

export interface Book {
  author:string,
  title:string,
  description:string,
  price:number,
  bookData:File | string,
  creator:User,
}

export interface AddBookRequest {
  book:File | string,
  author:string,
  title:string,
  description:string,
  price:number | null,
  username:string | undefined,
  image: File | string
}

