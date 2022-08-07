import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs";
import {Store} from "@ngrx/store";
import {UserState} from "../../models/user-models";
import {AddPodcastRequest, Podcast} from "../../models/podcast-models";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {PodcastService} from "../../services/podcast-services/podcast-service";
import {Multimedia, MultimediaMetadata} from "../../models/multimedia-models";
import {podcastApi} from "../../config/url-api.config";
import {Filter} from "../../models/filter-models";

@Component({
  selector:"app-podcasts",
  templateUrl:"podcasts.component.html"
})
export class PodcastsComponent implements OnDestroy,OnInit {

  public modalOpened = false;
  public podcasts!: MultimediaMetadata[];
  public filteredPodcasts!: MultimediaMetadata[];
  public podcastRequest: AddPodcastRequest = {
    podcast:'',
    title:'',
    description:'',
    price:null,
    creatorUsername:'',
    image: ''
  }
  public userEligible = false;
  private addPodcastSubscription: Subscription | null = null;
  private getUserSubscription: Subscription | null =  null;
  private getPodcastsSubscription: Subscription | null = null;

  constructor(private store:Store<UserState>,private podcastService:PodcastService) {
  }

  ngOnInit(): void {
    this.getAllPodcasts();
    this.getUserSubscription = this.store.select(getLoggedUser).subscribe(
      user => {
        this.podcastRequest.creatorUsername = user?.username
        // @ts-ignore
        this.userEligible = ['CREATOR','ADMIN'].includes(user?.role)
      }
    )
  }

  ngOnDestroy(): void {
    this.addPodcastSubscription?.unsubscribe();
    this.getUserSubscription?.unsubscribe();
    this.getPodcastsSubscription?.unsubscribe();
  }

  openModal(){
    this.modalOpened = true;
  }

  closeModal(){
    this.modalOpened = false;
  }

  getAllPodcasts(){
    this.getPodcastsSubscription = this.podcastService.getAllPodcasts().subscribe( podcasts => {
      this.podcasts = podcasts
      this.filteredPodcasts = podcasts;
    } )
  }

  addPodcast(){
    const formData = new FormData();
    formData.set("podcast",this.podcastRequest.podcast);
    formData.set("title",this.podcastRequest.title);
    formData.set("description",this.podcastRequest.description);
    formData.set("price",JSON.stringify(this.podcastRequest.price));
    formData.set("creatorUsername",JSON.stringify({username: this.podcastRequest.creatorUsername}));
    formData.set("image",this.podcastRequest.image);
    this.addPodcastSubscription = this.podcastService.addPodcast(formData).subscribe(
      response => {
        console.log(response)
        this.getAllPodcasts();
      }
    )
    this.modalOpened = false;
  }

  onFileChange(event:any,prop: any){
    this.podcastRequest[prop == 'podcast' ? 'podcast' : 'image'] = event.target.files[0];
  }

  filteredElements(filteredElements: Filter) {
    this.filteredPodcasts = this.podcasts;
    if (filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast => filteredElements.priceFrom &&
        podcast.price >= filteredElements.priceFrom && filteredElements.priceTo && podcast.price <= filteredElements.priceTo);
    } else if (!filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast => filteredElements.priceTo && podcast.price <= filteredElements.priceTo);
    } else if (filteredElements.priceFrom && !filteredElements.priceTo) {
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast => filteredElements.priceFrom && podcast.price >= filteredElements.priceFrom);
    }

    if(filteredElements.rating && filteredElements.rating?.length > 0){
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast =>
        filteredElements.rating?.find(rating => {
          // @ts-ignore
          return parseInt(rating) === Math.round(podcast.rating)
        }));
    }

    if (filteredElements.productName) {
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast => podcast.title.toLowerCase().includes(filteredElements.productName.toLowerCase()));
    }
    if (filteredElements.instructorName) {
      this.filteredPodcasts = this.filteredPodcasts.filter(podcast => podcast.creatorFullName.toLowerCase().includes(filteredElements.instructorName.toLowerCase()));
    }
  }


}
