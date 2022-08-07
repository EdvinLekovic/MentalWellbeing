import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {EditMultimediaRequest, Multimedia, MultimediaMetadata} from "../../../../models/multimedia-models";
import {MultimediaService} from "../../../../services/multimedia-services/multimedia-service";
import {DomSanitizer} from "@angular/platform-browser";
import {Router} from "@angular/router";
import {ReviewService} from "../../../../services/review-services/review-service";

@Component({
  selector:"app-multimedia-item",
  templateUrl:"multimedia-item.component.html",
})
export class MultimediaItemComponent implements OnInit {
  @Input() imageUrl!: string;
  @Input() detailsUrl!: string;
  @Input() multimedia!: MultimediaMetadata;

  @Output() updateChanges: EventEmitter<void> = new EventEmitter<void>();

  public cardStyle: any;
  public imageData!: string;
  public thumbnail: any;
  public detailsPageUrl!: string;
  public averageRating!: number;
  public listingStars!: number[];
  public modalOpened = false;
  public editModalTitle = '';

  constructor(private multimediaService: MultimediaService,
              private sanitizer: DomSanitizer,
              private router:Router,
              private reviewService: ReviewService) {
  }

  public onHover(){
    console.log("onHover");
    this.cardStyle.opacity = 0.7;
  }

  public leaveHover(){
    this.cardStyle.opacity = 1;
  }

  ngOnInit(): void {
    this.editModalTitle = 'Edit '+this.detailsUrl.substring(1,this.detailsUrl.length - 1);
    this.detailsPageUrl = this.detailsUrl + '/' + this.multimedia.id;
    this.listingStars = Array(Math.round(this.multimedia.rating)).fill(0).map((x,i)=>i);
    console.log("multimediaItem");
    console.log(this.multimedia);
    this.initializeCardStyle();
    this.getImage();
  }

  private initializeCardStyle(){
    this.cardStyle = { width: '350px',height: '410px'}
  }

  private getImage(){
    this.imageData = 'data:image/jpeg;base64,' + this.multimedia.image.data;
    this.thumbnail = this.sanitizer.bypassSecurityTrustUrl(this.imageData);
  }

  getAverageRatings(){
    const url = `${this.detailsUrl}-average-rating/${this.multimedia.id}`;
    this.reviewService.getAverageMultimediaRating(url).subscribe(averageRating => {
      this.averageRating = averageRating
      this.listingStars = Array(Math.round(averageRating)).fill(0).map((x,i)=>i);
    });
  }
}
