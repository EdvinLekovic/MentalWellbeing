import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {bookUrlApi, videoApi} from "../../config/url-api.config";
import {Observable} from "rxjs";
import {ProductUserRequest} from "../../models/user-models";

@Injectable({
  providedIn:"root"
})
export class VideoService{

  constructor(private http:HttpClient) {}

  public addVideo(formData: FormData){
    return this.http.post(videoApi + '/add-new-video',formData,{
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    })
  }

  public getAllVideos(): Observable<any>{
    return this.http.get(videoApi);
  }

  public getVideoById(videoId: number): Observable<any> {
    return this.http.get(videoApi + `/${videoId}`);
  }

  public getVideoMetadata(videoId: number): Observable<any> {
    return this.http.get(videoApi + `/metadata/${videoId}`);
  }

  public checkIfYouBoughtVideo(productUserRequest: ProductUserRequest): Observable<any> {
    return this.http.post(videoApi + `/check-video-own`,productUserRequest);
  }
}
