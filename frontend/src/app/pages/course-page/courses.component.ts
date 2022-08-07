import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs";
import {Store} from "@ngrx/store";
import {UserState} from "../../models/user-models";
import {AddCourseRequest, Course} from "../../models/course-models";
import {getLoggedUser} from "../../services/user-store/user-reducer";
import {CourseService} from "../../services/course-services/course-service";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {Filter} from "../../models/filter-models";

@Component({
  selector: "app-courses",
  templateUrl: "courses.component.html"
})
export class CoursesComponent implements OnInit, OnDestroy {

  public modalOpened = false;
  public courses!: MultimediaMetadata[];
  public filteredCourses!: MultimediaMetadata[];
  public courseRequest: AddCourseRequest = {
    image: '',
    title: '',
    description: '',
    price: null,
    creatorUsername: ''
  }
  public userEligible = false;

  private addCourseSubscription: Subscription | null = null;

  constructor(private store: Store<UserState>, private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.getAllCourses();
    this.store.select(getLoggedUser).subscribe(
      user => {
        this.courseRequest.creatorUsername = user?.username
        // @ts-ignore
        this.userEligible = ['CREATOR', 'ADMIN'].includes(user?.role)
      }
    )
  }

  getAllCourses() {
    this.courseService.getAllCourses().subscribe(courses => {
      this.courses = courses
      this.filteredCourses = courses;
    });
  }

  ngOnDestroy(): void {
    this.addCourseSubscription?.unsubscribe();
  }

  openModal() {
    this.modalOpened = true;
  }

  closeModal() {
    this.modalOpened = false;
  }

  addCourse() {
    const formData = new FormData();
    formData.set("image", this.courseRequest.image);
    formData.set("title", this.courseRequest.title);
    formData.set("description", this.courseRequest.description);
    formData.set("price", JSON.stringify(this.courseRequest.price));
    formData.set("creatorUsername", JSON.stringify({username: this.courseRequest.creatorUsername}));
    formData.set("image", this.courseRequest.image);
    this.addCourseSubscription = this.courseService.addCourse(formData).subscribe(
      response => {
        console.log(response)
        this.getAllCourses();
      }
    )
    this.modalOpened = false;
  }

  onFileChange(event: any) {
    this.courseRequest.image = event.target.files[0];
  }

  filteredElements(filteredElements: Filter) {
    this.filteredCourses = this.courses;
    if (filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredCourses = this.filteredCourses.filter(course => filteredElements.priceFrom &&
        course.price >= filteredElements.priceFrom && filteredElements.priceTo && course.price <= filteredElements.priceTo);
    } else if (!filteredElements.priceFrom && filteredElements.priceTo) {
      this.filteredCourses = this.filteredCourses.filter(course => filteredElements.priceTo && course.price <= filteredElements.priceTo);
    } else if (filteredElements.priceFrom && !filteredElements.priceTo) {
      this.filteredCourses = this.filteredCourses.filter(course => filteredElements.priceFrom && course.price >= filteredElements.priceFrom);
    }

    if (filteredElements.rating && filteredElements.rating?.length > 0) {
      this.filteredCourses = this.filteredCourses.filter(courses =>
        filteredElements.rating?.find(rating => {
          // @ts-ignore
          return parseInt(rating) === Math.round(courses.rating)
        }));
    }

    if (filteredElements.productName) {
      this.filteredCourses = this.filteredCourses.filter(course => course.title.toLowerCase().includes(filteredElements.productName.toLowerCase()));
    }
    if (filteredElements.instructorName) {
      this.filteredCourses = this.filteredCourses.filter(course => course.creatorFullName.toLowerCase().includes(filteredElements.instructorName.toLowerCase()));
    }
  }
}
