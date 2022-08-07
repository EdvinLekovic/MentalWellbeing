import {Component, OnDestroy, OnInit} from "@angular/core";
import {AddVideoRequest} from "../../models/video-models";
import {VideoService} from "../../services/video-services/video-service";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {Store} from "@ngrx/store";
import {User, UserState} from "../../models/user-models";
import {Subscription} from "rxjs";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {Filter} from "../../models/filter-models";

@Component({
  selector: "app-videos",
  templateUrl: "videos.component.html"
})
export class VideosComponent implements OnDestroy, OnInit {

  public modalOpened = false;
  public videos!: MultimediaMetadata[];
  public filteredVideos!: MultimediaMetadata[];
  public videoRequest: AddVideoRequest = {
    video: '',
    title: '',
    description: '',
    price: null,
    creatorUsername: '',
    image: ''
  }
  public userEligible = false;

  private addVideoSubscription: Subscription | null = null;

  constructor(private store: Store<UserState>, private videoService: VideoService) {
  }

  ngOnInit(): void {
    this.getAllVideos();
    this.store.select(getLoggedUser).subscribe(
      user => {
        this.videoRequest.creatorUsername = user?.username
        // @ts-ignore
        this.userEligible = ['CREATOR','ADMIN'].includes(user?.role)
      }
    )
  }

  openModal() {
    this.modalOpened = true;
  }

  closeModal() {
    this.modalOpened = false;
  }

  getAllVideos() {
    this.videoService.getAllVideos().subscribe(videos => {
      this.videos = videos
      this.filteredVideos = videos;
    });
  }

  addVideo() {
    const formData = new FormData();
    formData.set("video", this.videoRequest.video);
    formData.set("title", this.videoRequest.title);
    formData.set("description", this.videoRequest.description);
    formData.set("price", JSON.stringify(this.videoRequest.price));
    formData.set("creatorUsername", JSON.stringify({username: this.videoRequest.creatorUsername}));
    formData.set("image", this.videoRequest.image)
    this.addVideoSubscription = this.videoService.addVideo(formData).subscribe(
      response => {
        console.log(response)
        this.getAllVideos();
      }
    )
    this.modalOpened = false;
  }

  onFileChange(event: any, prop: any) {
    this.videoRequest[prop == 'video' ? 'video' : 'image'] = event.target.files[0];
  }

  ngOnDestroy(): void {
    this.addVideoSubscription?.unsubscribe();
  }

  filteredElements(filteredElements: Filter) {
    this.filteredVideos = this.videos;
    if (filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredVideos = this.filteredVideos.filter(videos => filteredElements.priceFrom &&
        videos.price >= filteredElements.priceFrom && filteredElements.priceTo && videos.price <= filteredElements.priceTo);
    } else if (!filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredVideos = this.filteredVideos.filter(videos => filteredElements.priceTo && videos.price <= filteredElements.priceTo);
    } else if (filteredElements.priceFrom && !filteredElements.priceTo) {
      this.filteredVideos = this.filteredVideos.filter(videos => filteredElements.priceFrom && videos.price >= filteredElements.priceFrom);
    }
    if(filteredElements.rating && filteredElements.rating?.length > 0){
      this.filteredVideos = this.filteredVideos.filter(video =>
        filteredElements.rating?.find(rating => {
          // @ts-ignore
          return parseInt(rating) === Math.round(video.rating)
        }));
    }
    if (filteredElements.productName) {
      this.filteredVideos = this.filteredVideos.filter(videos => videos.title.toLowerCase().includes(filteredElements.productName.toLowerCase()));
    }
    if (filteredElements.instructorName) {
      this.filteredVideos = this.filteredVideos.filter(videos => videos.creatorFullName.toLowerCase().includes(filteredElements.instructorName.toLowerCase()));
    }
  }

}
