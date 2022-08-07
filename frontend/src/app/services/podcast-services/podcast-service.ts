import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {bookUrlApi, podcastApi, videoApi} from "../../config/url-api.config";
import {Observable} from "rxjs";
import {Podcast} from "../../models/podcast-models";
import {ProductUserRequest} from "../../models/user-models";

@Injectable({
  providedIn:"root"
})
export class PodcastService{

  constructor(private http:HttpClient) {}

  public addPodcast(formData: FormData){
    return this.http.post(podcastApi + '/add-new-podcast',formData,{
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    })
  }

  public getAllPodcasts(): Observable<any>{
    return this.http.get(podcastApi);
  }

  public getPodcastDetails(podcastId: number): Observable<any>{
    return this.http.get(podcastApi + `/${podcastId}`);
  }

  public getPodcastMetadata(podcastId: number): Observable<any> {
    return this.http.get(podcastApi + `/metadata/${podcastId}`);
  }

  public checkIfYouBoughtPodcast(productUserRequest: ProductUserRequest): Observable<any> {
    return this.http.post(podcastApi + `/check-podcast-own`,productUserRequest);
  }
}
