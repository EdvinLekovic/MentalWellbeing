import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ROUTES} from "./config/routes-config";
import {HomeComponent} from "./pages/home-page/home.component";
import {HeaderComponent} from "./components/header/header.component";
import {FooterComponent} from "./components/footer/footer.component";
import {CoursesComponent} from "./pages/course-page/courses.component";
import {VideosComponent} from "./pages/videos-page/videos.component";
import {PodcastsComponent} from "./pages/podcasts-page/podcasts.component";
import {BooksComponent} from "./pages/books-page/books.component";
import {MultimediaItemListComponent} from "./components/shared/multimedia-content/multimedia-item-list.component";
import {MultimediaItemComponent} from "./components/shared/multimedia-content/multimedia-item/multimedia-item.component";
import {VideoDetailsComponent} from "./components/video-details/video-details.component";
import { VimeModule } from '@vime/angular';
import {SharedVideoPlayerComponent} from "./components/shared/video-player/shared-video-player.component";
import {ReviewComponent} from "./components/shared/review/review.component";
import {ReviewListComponents} from "./components/shared/review-list/review-list.components";
import {AngMusicPlayerModule} from "ang-music-player";
import {AudioPlayerComponent} from "./components/shared/audio-player/audio-player.component";
import {PodcastDetailsComponent} from "./components/podcast-details/podcast-details.component";
import {ModalComponent} from "./components/shared/modal/modal.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";
import {PdfViewerModule} from "ng2-pdf-viewer";
import {BookViewerComponent} from "./components/book-viewer/book-viewer.component";
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import {userReducer} from "./services/user-store/user-reducer";
import {UserEffects} from "./services/user-store/user-effects";
import {HttpClientModule} from "@angular/common/http";
import {MultimediaDetailsComponent} from "./components/multimedia-details/multimedia-details.component";
import {CourseWatchComponent} from "./components/course-watch/course-watch.component";
import {MyProfileComponent} from "./components/my-profile/my-profile.component";
import {PersonalInfoComponent} from "./components/my-profile/personal-info/personal-info.component";
import {PaymentInfoComponent} from "./components/my-profile/payment-info/payment-info.component";
import {PaymentModalComponent} from "./components/payment-modal/payment-modal.component";
import {CourseInfoComponent} from "./components/course-info/course-info.component";
import {ContactUsComponent} from "./pages/contact-us-page/contact-us.component";
import {VideoInfoComponent} from "./components/video-info/video-info.component";
import {PodcastInfoComponent} from "./components/podcast-info/podcast-info.component";
import {BookInfoComponent} from "./components/book-info/book-info.component";
import {FilterBarComponent} from "./components/shared/filter-bar/filter-bar.component";
import {SuccessfulPaymentModalComponent} from "./components/payment-modal/successful-payment-modal.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    CoursesComponent,
    VideosComponent,
    PodcastsComponent,
    BooksComponent,
    MultimediaItemListComponent,
    MultimediaItemComponent,
    VideoDetailsComponent,
    SharedVideoPlayerComponent,
    ReviewComponent,
    ReviewListComponents,
    PodcastDetailsComponent,
    AudioPlayerComponent,
    ModalComponent,
    BookDetailsComponent,
    BookViewerComponent,
    LoginPageComponent,
    RegisterPageComponent,
    MultimediaDetailsComponent,
    CourseWatchComponent,
    MyProfileComponent,
    PersonalInfoComponent,
    PaymentInfoComponent,
    PaymentModalComponent,
    CourseInfoComponent,
    ContactUsComponent,
    VideoInfoComponent,
    PodcastInfoComponent,
    BookInfoComponent,
    FilterBarComponent,
    SuccessfulPaymentModalComponent
  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot(ROUTES),
        RouterModule,
        VimeModule,
        AngMusicPlayerModule,
        PdfViewerModule,
        StoreModule.forRoot({}, {}),
        EffectsModule.forRoot([]),
        StoreModule.forFeature("user", userReducer),
        EffectsModule.forFeature([UserEffects]),
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
