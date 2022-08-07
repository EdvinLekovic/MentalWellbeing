import {Component, OnDestroy, OnInit} from "@angular/core";
import {AddBookRequest} from "../../models/book-models";
import {BookService} from "../../services/book-services/book-service";
import {Subscription} from "rxjs";
import {User, UserState} from "../../models/user-models";
import {Store} from "@ngrx/store";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {Multimedia, MultimediaMetadata} from "../../models/multimedia-models";
import {bookUrlApi} from "../../config/url-api.config";
import {Filter} from "../../models/filter-models";

@Component({
  selector: "app-books",
  templateUrl: "books.component.html"
})
export class BooksComponent implements OnDestroy, OnInit {

  public modalOpened = false;
  public user!: User | null;
  public books!: MultimediaMetadata[];
  public filteredBooks!: MultimediaMetadata[];
  public bookRequest: AddBookRequest = {
    book: '',
    author: '',
    title: '',
    description: '',
    price: null,
    username: '',
    image: ''
  };
  public userEligible = false;
  public bookUrlApi = bookUrlApi + '/get-book-image/'

  private addBookSubscription: Subscription | null = null;
  private getBooksSubscription: Subscription | null = null;

  constructor(private store: Store<UserState>, private bookService: BookService) {
  }

  ngOnInit(): void {
    this.getAllBooks();
    this.store.select(getLoggedUser).subscribe(
      user => {this.bookRequest.username = user?.username
        // @ts-ignore
        this.userEligible = ['CREATOR','ADMIN'].includes(user?.role);
      }
    )
  }

  ngOnDestroy(): void {
    this.addBookSubscription?.unsubscribe();
    this.getBooksSubscription?.unsubscribe();
  }

  getAllBooks() {
    this.getBooksSubscription?.unsubscribe();
    this.getBooksSubscription = this.bookService.getAllBooks().subscribe(books => {
      this.books = books
      this.filteredBooks = books;
    });
  }

  addBook() {
    console.log(this.bookRequest);
    const formData = new FormData();
    formData.set("title", this.bookRequest.title);
    formData.set("author", this.bookRequest.author);
    formData.set("description", this.bookRequest.description);
    formData.set("price", JSON.stringify(this.bookRequest.price));
    formData.set("book", this.bookRequest.book);
    formData.set("usernameRequest", JSON.stringify({username: this.bookRequest.username}))
    formData.set("image", this.bookRequest.image);
    this.addBookSubscription = this.bookService.addBook(formData).subscribe(
      book => {
        console.log(book)
        this.getAllBooks();
      }
    );
    this.modalOpened = false;
  }

  openModal() {
    this.modalOpened = true;
  }

  closeModal() {
    this.modalOpened = false;
  }

  onFileChange(event: any, prop: string) {
    this.bookRequest[prop == 'book' ? 'book' : 'image'] = event.target.files[0];
  }

  filteredElements(filteredElements: Filter) {
    this.filteredBooks = this.books;
    if (filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredBooks = this.filteredBooks.filter(book => filteredElements.priceFrom &&
        book.price >= filteredElements.priceFrom && filteredElements.priceTo && book.price <= filteredElements.priceTo);
    } else if (!filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredBooks = this.filteredBooks.filter(book => filteredElements.priceTo && book.price <= filteredElements.priceTo);
    } else if (filteredElements.priceFrom && !filteredElements.priceTo) {
      this.filteredBooks = this.filteredBooks.filter(book => filteredElements.priceFrom && book.price >= filteredElements.priceFrom);
    }
    if(filteredElements.rating && filteredElements.rating?.length > 0){
      this.filteredBooks = this.filteredBooks.filter(book =>
        filteredElements.rating?.find(rating => {
          // @ts-ignore
          return parseInt(rating) === Math.round(book.rating)
        }));
    }

    if (filteredElements.productName) {
      this.filteredBooks = this.filteredBooks.filter(book => book.title.toLowerCase().includes(filteredElements.productName.toLowerCase()));
    }
    if (filteredElements.instructorName) {
      this.filteredBooks = this.filteredBooks.filter(book => book.creatorFullName.toLowerCase().includes(filteredElements.instructorName.toLowerCase()));
    }
  }

  updateChanges(){
    this.getAllBooks();
  }

}
