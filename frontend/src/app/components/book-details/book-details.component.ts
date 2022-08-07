import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/book-services/book-service";
import {Multimedia} from "../../models/multimedia-models";
import {DomSanitizer} from "@angular/platform-browser";
import {Book} from "../../models/book-models";

@Component({
  selector: "app-book-details",
  templateUrl: "book-details.component.html"
})
export class BookDetailsComponent implements OnInit {

  public book!: Book;
  public bookData!: any;
  public thumbnail: any;

  constructor(private route: ActivatedRoute, private bookService: BookService,private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    console.log('book details');
    this.route.params.subscribe(params => {
        this.getBookDetails(+params['id']);
      }
    )
  }

  private getBookDetails(bookId: number) {
      this.bookService.getBookDetails(bookId).subscribe( book => {
        this.book = book
        this.bookData = 'data:application/pdf;base64,' + this.book.bookData;
      })
  }

}
