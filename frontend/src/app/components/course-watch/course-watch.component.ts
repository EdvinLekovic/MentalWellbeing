import {Component, Input, OnChanges, OnDestroy, OnInit} from "@angular/core";
import {Video, VideoMetadata} from "../../models/video-models";
import {VideoService} from "../../services/video-services/video-service";
import {User, UserState} from "../../models/user-models";
import {Store} from "@ngrx/store";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {CourseService} from "../../services/course-services/course-service";
import {AddVideoToCourseRequest} from "../../models/course-models";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  templateUrl:"course-watch.component.html"
})
export class CourseWatchComponent implements OnInit, OnChanges, OnDestroy {
  @Input() title: string = "Title";
  @Input() videoList: VideoMetadata[] = [];
  public courseVideo!: Video;
  public videoData!: string;
  public videoSelected: string = '';
  public user!:User | null;
  public modalOpened = false;
  public courseId!: number;
  public addVideoToCourseRequest: AddVideoToCourseRequest = {
    id: 0,
    videos:'',
    title: '',
    description: '',
  }

  constructor(private videoService: VideoService,private store:Store<UserState>,private courseService:CourseService,private route:ActivatedRoute,private router:Router) {}

  ngOnDestroy(): void {
        localStorage.removeItem("videoId");
    }

  private getVideo(videoId: number){
      this.videoService.getVideoById(videoId).subscribe(video => {
        this.courseVideo = video
        this.videoData = 'data:video/mp4;base64,' + video.videoData;
      });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.courseId = +params['id'];
      this.getAllVideosByCourse();
    })
    this.store.select(getLoggedUser).subscribe(user => this.user = user);
    if(this.videoList.length > 0){
      this.videoSelected = this.videoList[0]?.title;
      this.getVideo(this.videoList[0].id);
    }
  }

  addVideosToCourse(){
    const formData = new FormData();
    formData.set("videoCourseId",JSON.stringify(this.courseId));
    formData.set("title", this.addVideoToCourseRequest.title);
    formData.set("description", this.addVideoToCourseRequest.description);
    for(let i = 0;i<this.addVideoToCourseRequest.videos.length;i++){
      formData.append("videos",this.addVideoToCourseRequest.videos[i]);
    }
    this.courseService.addVideoToCourse(formData).subscribe(video => this.getAllVideosByCourse());
    this.modalOpened = false;
  }

  ngOnChanges(changes: any): void {
    console.log(changes);
  }

  openModal(){
    console.log("open modal");
    this.modalOpened = true;
  }

  closeModal(){
    this.modalOpened = false;
  }

  onFileChange(event: any) {
    this.addVideoToCourseRequest.videos = event.target.files;
  }

  getAllVideosByCourse(){
    return this.courseService.listAllVideosByCourse(this.courseId).subscribe(videos => {
      console.log(videos);
      if(videos){
        console.log(videos);
        this.videoList = videos;
        let videoId = localStorage.getItem("videoId");
        console.log(videoId);
        if(!videoId) {
          this.courseVideo = videos[0];
          this.videoData = 'data:video/mp4;base64,' + this.courseVideo.videoData;
          console.log(this.courseVideo);
        }
        else {
          // @ts-ignore
          this.courseVideo = this.videoList.find(v => v.id === parseInt(videoId));
          this.videoData = 'data:video/mp4;base64,' + this.courseVideo.videoData;
        }
      }
    })
  }

  reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate([currentUrl]);
    });
  }

  videoSelection(event:any){
    console.log(event);
    // console.log(event.target.value);
    localStorage.setItem("videoId",event);
    this.reloadCurrentRoute();
  }

}
