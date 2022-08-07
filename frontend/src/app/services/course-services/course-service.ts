import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {bookUrlApi, podcastApi, videoCourseApi} from "../../config/url-api.config";
import {Observable} from "rxjs";
import {ProductUserRequest} from "../../models/user-models";
import {AddVideoToCourseRequest} from "../../models/course-models";

@Injectable({
  providedIn:"root"
})
export class CourseService {

  constructor(private http:HttpClient) {}

  public addCourse(formData: FormData){
    return this.http.post(videoCourseApi + '/add-new-video-course',formData,{
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    })
  }

  public getAllCourses(): Observable<any>{
    return this.http.get(videoCourseApi);
  }

  public getCourseMetadata(courseId: number):Observable<any>{
    return this.http.get(videoCourseApi + `/metadata/${courseId}`);
  }

  public checkIfYouBoughtCourse(productUserRequest: ProductUserRequest): Observable<any> {
    return this.http.post(videoCourseApi + `/check-course-own`,productUserRequest);
  }

  public addVideoToCourse(formData:FormData){
    return this.http.post(videoCourseApi + `/add-videos-to-video-course`,formData);
  }

  public listAllVideosByCourse(courseId:number): Observable<any>{
    return this.http.get(videoCourseApi+`/videos-by-course/${courseId}`);
  }
}
