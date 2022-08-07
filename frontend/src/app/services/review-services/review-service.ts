import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AddReviewRequest, EditReviewRequest, Review} from "../../models/review-models";
import {Observable} from "rxjs";
import {reviewApi} from "../../config/url-api.config";

@Injectable({
  providedIn:"root"
})
export class ReviewService {

  constructor(private http:HttpClient) {}

  public addReview(addReviewRequest: AddReviewRequest,url:string): Observable<any>{
    return this.http.post(reviewApi + url,addReviewRequest);
  }

  public getAllReviews(url:string): Observable<any>{
    return this.http.get(reviewApi + url);
  }

  public editReview(editReviewRequest:EditReviewRequest,url: string){
    return this.http.post(reviewApi + url,editReviewRequest);
  }

  public deleteReview(reviewId: number,url: string){
    return this.http.delete(reviewApi + url);
  }

  public getAverageMultimediaRating(url: string): Observable<any>{
    return this.http.get(reviewApi + url);
  }
}
