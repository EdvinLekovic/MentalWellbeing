import {Component} from "@angular/core";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {ActivatedRoute} from "@angular/router";
import {PodcastService} from "../../services/podcast-services/podcast-service";
import {PaymentService} from "../../services/payment-services/payment-service";
import {BookService} from "../../services/book-services/book-service";

@Component({
  templateUrl:"book-info.component.html"
})
export class BookInfoComponent {

  public books!: MultimediaMetadata;
  public paymentModalOpened = false;

  constructor(private route:ActivatedRoute,private bookService: BookService,private paymentService:PaymentService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.getBookMetadata(+params['id']);
    })
  }

  private getBookMetadata(bookId: number){
    this.bookService.getBookMetadata(bookId).subscribe(books => {
      this.books = books
      console.log(this.books);
    });
  }

  openModal(){
    console.log("open modal");
    this.paymentModalOpened = true;
  }

  closeModal(){
    this.paymentModalOpened = false;
  }

  updateChanges(){
    console.log('updateChanges');
    this.getBookMetadata(this.books.id);
  }
}
