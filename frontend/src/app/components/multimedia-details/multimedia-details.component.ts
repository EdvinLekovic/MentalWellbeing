import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from "@angular/core";
import {EditMultimediaRequest, Multimedia, MultimediaMetadata} from "../../models/multimedia-models";
import {AddReviewRequest, EditReviewRequest, Review} from "../../models/review-models";
import {ProductUserRequest, User, UserState} from "../../models/user-models";
import {Store} from "@ngrx/store";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {ReviewService} from "../../services/review-services/review-service";
import {Book} from "../../models/book-models";
import {BookService} from "../../services/book-services/book-service";
import {CourseService} from "../../services/course-services/course-service";
import {PodcastService} from "../../services/podcast-services/podcast-service";
import {VideoService} from "../../services/video-services/video-service";
import {ActivatedRoute, Router} from "@angular/router";
import {MultimediaService} from "../../services/multimedia-services/multimedia-service";

@Component({
  selector: "multimedia-details",
  templateUrl: "multimedia-details.component.html"
})
export class MultimediaDetailsComponent implements OnInit, OnChanges {
  @Input() reviewsUrl!: string;
  @Input() multimedia!: MultimediaMetadata;

  @Output() buyButtonClicked: EventEmitter<void> = new EventEmitter<void>();
  @Output() updateChanges: EventEmitter<void> = new EventEmitter<void>();

  public reviews!: Review[];
  public averageRating!: number;
  public listingStars!: number[];
  public imageData!: string;
  public modalOpened = false;
  public editModalOpened = false;
  public paymentModalOpened = false;
  public productOwned = false;
  public checkFinished = false;
  public multimediaInfoUrl!: string;
  public buttonTextForOwnedProduct!: string;
  public editModalTitle = '';
  public user!: User | null;
  public userFullName = '';
  public productUserRequest: ProductUserRequest = {
    username: '',
    productId: null
  }
  public addReviewRequest: AddReviewRequest = {
    username: '',
    description: '',
    rating: 1,
    mediaId: null
  }
  public editReviewRequest: EditReviewRequest = {
    id: null,
    description: '',
    rating: null
  }
  public editMultimediaRequest: EditMultimediaRequest = {
    id:null,
    title:'',
    description:''
  }


  constructor(private store: Store<UserState>,
              private reviewService: ReviewService,
              private bookService: BookService,
              private courseService: CourseService,
              private podcastService: PodcastService,
              private videoService: VideoService,
              private multimediaService: MultimediaService,
              private router: Router) {
  }

  ngOnChanges(changes: any): void {
    if (changes['multimedia']) {
      this.checkIfYouBoughtProduct();
    }
  }

  ngOnInit(): void {
    this.editModalTitle = `Edit ${this.reviewsUrl.substring(0,this.reviewsUrl.length - 1)}`
    console.log(this.multimedia);
    console.log(this.reviewsUrl);
    this.editMultimediaRequest.id = this.multimedia.id;
    this.multimediaInfoUrl = `${this.reviewsUrl}/info/${this.multimedia.id}`;
    this.imageData = 'data:image/jpeg;base64,' + this.multimedia.image.data;
    this.getMultimediaReviews();
    this.store.select(getLoggedUser).subscribe(user => {
      // @ts-ignore
      this.userFullName = user?.name + " "+ user?.lastName;
      this.user = user;
      this.addReviewRequest.username = user?.username;
      this.productUserRequest.username = user?.username;
      this.checkIfYouBoughtProduct();
    });
    this.addReviewRequest.mediaId = this.multimedia.id;
  }

  getMultimediaReviews() {
    const url = "/" + this.reviewsUrl + "/" + this.multimedia.id;
    this.reviewService.getAllReviews(url).subscribe(reviews => {
      this.reviews = reviews
      this.getAverageRatings();
    });
  }

  ///videos-average-rating/{id}
  getAverageRatings() {
    const url = `/${this.reviewsUrl}-average-rating/${this.multimedia.id}`;
    this.reviewService.getAverageMultimediaRating(url).subscribe(averageRating => {
      this.averageRating = averageRating
      this.listingStars = Array(Math.round(averageRating)).fill(0).map((x, i) => i);
    });
  }

  countStars(rating: number) {
    return Array(Math.round(rating)).fill(0).map((x, i) => i)
  }

  ///add-video-review
  addReview() {
    console.log('add review');
    const url = "/add-" + this.reviewsUrl.substring(0, this.reviewsUrl.length - 1) + "-review";
    this.reviewService.addReview(this.addReviewRequest, url).subscribe(() => this.getMultimediaReviews());
    this.modalOpened = false;
  }

  editReview() {
    const url = "/edit-" + this.reviewsUrl.substring(0, this.reviewsUrl.length - 1) + "-review";
    this.reviewService.editReview(this.editReviewRequest, url);
  }

  deleteReview() {
    const url = "/delete-" + this.reviewsUrl.substring(0, this.reviewsUrl.length - 1) + "-review/" + this.multimedia.id;
    this.reviewService.getAllReviews(url);
  }

  openModal() {
    this.modalOpened = true;
  }

  closeModal() {
    this.modalOpened = false;
  }

  checkIfYouBoughtProduct() {
    this.productUserRequest.productId = this.multimedia.id;
    if (this.reviewsUrl === 'books') {
      this.buttonTextForOwnedProduct = 'Read now';
      this.bookService.checkIfYouBoughtBook(this.productUserRequest).subscribe(productOwned => {
          this.productOwned = productOwned;
          this.checkFinished = true;
        }
      );
    } else if (this.reviewsUrl === 'courses') {
      this.buttonTextForOwnedProduct = 'Watch now';
      this.courseService.checkIfYouBoughtCourse(this.productUserRequest).subscribe(productOwned => {
        this.productOwned = productOwned
        this.checkFinished = true;
      });
    } else if (this.reviewsUrl === 'podcasts') {
      this.buttonTextForOwnedProduct = 'Listen now';
      this.podcastService.checkIfYouBoughtPodcast(this.productUserRequest).subscribe(productOwned => {
        this.productOwned = productOwned
        this.checkFinished = true;
      });
    } else if (this.reviewsUrl === 'videos') {
      this.buttonTextForOwnedProduct = 'Watch now';
      this.videoService.checkIfYouBoughtVideo(this.productUserRequest).subscribe(productOwned => {
          this.productOwned = productOwned
          this.checkFinished = true;
        }
      );
    }
  }

  afterBuyProduct() {
    this.updateChanges.emit();
  }

  navigateInfoPage() {
    this.router.navigate([this.multimediaInfoUrl]);
  }

  deleteMultimedia(){
    const url = `/${this.reviewsUrl}/delete-${this.reviewsUrl.substring(0,this.reviewsUrl.length - 1)}/${this.multimedia.id}`
    this.multimediaService.deleteMultimedia(url).subscribe(() => this.router.navigate(['/'+this.reviewsUrl]));
  }

  editMultimedia(){
    const url = `/${this.reviewsUrl}/edit-${this.reviewsUrl.substring(0,this.reviewsUrl.length - 1)}`
    this.multimediaService.editMultimedia(this.editMultimediaRequest,url).subscribe(result => this.updateChanges.emit());
    this.editModalOpened = false;
  }

  openEditModal() {
    this.editModalOpened = true;
  }

  closeEditModal() {
    this.editModalOpened = false;
  }


}
