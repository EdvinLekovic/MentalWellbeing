import {Component} from "@angular/core";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../../services/video-services/video-service";
import {PodcastService} from "../../services/podcast-services/podcast-service";

@Component({
  templateUrl:"podcast-info.component.html"
})
export class PodcastInfoComponent {
  public podcast!: MultimediaMetadata;

  constructor(private route:ActivatedRoute,private podcastService: PodcastService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.getPodcastMetadata(+params['id']);
    })
  }

  private getPodcastMetadata(videoId: number){
    this.podcastService.getPodcastMetadata(videoId).subscribe(podcast => this.podcast = podcast);
  }

  updateChanges(){
    this.getPodcastMetadata(this.podcast.id);
  }
}
