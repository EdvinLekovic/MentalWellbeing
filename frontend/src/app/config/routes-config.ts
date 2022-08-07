import {Routes} from "@angular/router";
import {HomeComponent} from "../pages/home-page/home.component";
import {CoursesComponent} from "../pages/course-page/courses.component";
import {VideosComponent} from "../pages/videos-page/videos.component";
import {PodcastsComponent} from "../pages/podcasts-page/podcasts.component";
import {BooksComponent} from "../pages/books-page/books.component";
import {VideoDetailsComponent} from "../components/video-details/video-details.component";
import {PodcastDetailsComponent} from "../components/podcast-details/podcast-details.component";
import {BookDetailsComponent} from "../components/book-details/book-details.component";
import {LoginPageComponent} from "../pages/login-page/login-page.component";
import {RegisterPageComponent} from "../pages/register-page/register-page.component";
import {CourseWatchComponent} from "../components/course-watch/course-watch.component";
import {PersonalInfoComponent} from "../components/my-profile/personal-info/personal-info.component";
import {PaymentInfoComponent} from "../components/my-profile/payment-info/payment-info.component";
import {CourseInfoComponent} from "../components/course-info/course-info.component";
import {ContactUsComponent} from "../pages/contact-us-page/contact-us.component";
import {VideoInfoComponent} from "../components/video-info/video-info.component";
import {PodcastInfoComponent} from "../components/podcast-info/podcast-info.component";
import {BookInfoComponent} from "../components/book-info/book-info.component";

export const ROUTES: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'courses/:id', component: CourseInfoComponent},
  { path: 'courses/info/:id',component: CourseWatchComponent},
  { path: 'videos', component: VideosComponent },
  { path: 'videos/:id',component:VideoInfoComponent },
  { path: 'videos/info/:id', component: VideoDetailsComponent },
  { path: 'podcasts', component: PodcastsComponent },
  { path: 'podcasts/:id', component: PodcastInfoComponent },
  { path: 'podcasts/info/:id',component: PodcastDetailsComponent},
  { path: 'books', component: BooksComponent },
  { path: 'books/:id', component: BookInfoComponent },
  { path: 'books/info/:id',component: BookDetailsComponent},
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent},
  { path: 'my-profile/personal-info', component: PersonalInfoComponent },
  { path: 'my-profile/payment-info', component: PaymentInfoComponent },
  { path: 'contact-us',component: ContactUsComponent }
];
