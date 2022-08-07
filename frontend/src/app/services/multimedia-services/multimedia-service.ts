import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {EditMultimediaRequest} from "../../models/multimedia-models";
import {api} from "../../config/url-api.config";

@Injectable({
  providedIn:"root"
})
export class MultimediaService {

  constructor(private http: HttpClient) {
  }

  getImage(url: string){
    return this.http.get(url, {
      headers: {
        'Access-Control-Allow-Origin': '*'
      },
      responseType: 'blob'
    });
  }

  deleteMultimedia(url: string){
    return this.http.delete(api + url);
  }

  editMultimedia(editMultimediaRequest: EditMultimediaRequest,url: string): Observable<any>{
    return this.http.post(api + url,editMultimediaRequest);
  }
}
