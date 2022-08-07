import {Component, OnInit} from "@angular/core";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../../services/video-services/video-service";

@Component({
  templateUrl:"video-info.component.html"
})
export class VideoInfoComponent implements OnInit {
  public video!: MultimediaMetadata;

  constructor(private route:ActivatedRoute,private videoService: VideoService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
        this.getVideoMetadata(+params['id']);
    })
  }

  private getVideoMetadata(videoId: number){
    this.videoService.getVideoMetadata(videoId).subscribe(video => this.video = video);
  }

  updateChanges(){
    this.getVideoMetadata(this.video.id);
  }

}
