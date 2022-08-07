import {Component} from "@angular/core";
import {Video} from "../../models/video-models";
import {ActivatedRoute} from "@angular/router";
import {PodcastService} from "../../services/podcast-services/podcast-service";
import {VideoService} from "../../services/video-services/video-service";

@Component({
  selector:"app-video-details",
  templateUrl:"video-details.component.html",
})
export class VideoDetailsComponent {
  public video!: Video
  public videoData!: any;
  public imageData!: any;
  public showVideoPlayer = false;

  constructor(private route: ActivatedRoute, private videoService: VideoService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
        this.getVideoDetails(+params['id']);
      }
    )
  }

  private getVideoDetails(videoId: number){
    this.videoService.getVideoById(videoId).subscribe( video => {
      this.video = video
      this.videoData = 'data:video/mp4;base64,' + this.video.videoData;
      this.imageData = 'data:image/png;base64,' + this.video.image.data;
      this.showVideoPlayer = true;
    })
  }
}
