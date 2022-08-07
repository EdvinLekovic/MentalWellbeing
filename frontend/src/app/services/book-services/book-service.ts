import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {bookUrlApi, videoApi} from "../../config/url-api.config";
import {Observable} from "rxjs";
import {ProductUserRequest} from "../../models/user-models";
import {EditMultimediaRequest} from "../../models/multimedia-models";

@Injectable({
  providedIn:"root"
})
export class BookService {

  constructor(private http:HttpClient) {}

  addBook(formData: FormData){
   return this.http.post(bookUrlApi + "/add-new-book",formData,{
     headers:{
       'Access-Control-Allow-Origin' : '*'
     },
   })
  }

  getAllBooks(): Observable<any> {
    return this.http.get(bookUrlApi);
  }

  getBookDetails(bookId: number): Observable<any> {
    return this.http.get(bookUrlApi + `/${bookId}`)
  }

  getBookFile(bookId:number){
    return this.http.get(bookUrlApi + `/${bookId}`,{
      responseType:'blob'
    })
  }

  public getBookMetadata(bookId: number): Observable<any> {
    return this.http.get(bookUrlApi + `/metadata/${bookId}`);
  }

  public checkIfYouBoughtBook(productUserRequest: ProductUserRequest): Observable<any> {
    return this.http.post(bookUrlApi + `/check-book-own`,productUserRequest);
  }

}
