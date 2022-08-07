import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Podcast} from "../../models/podcast-models";
import {PodcastService} from "../../services/podcast-services/podcast-service";

@Component({
  selector:"app-podcast-details",
  templateUrl:"podcast-details.component.html"
})
export class PodcastDetailsComponent implements OnInit {

  public podcast!: Podcast;
  public podcastData!: any;
  public audioList: any[] = [];
  public imageData!: any;
  public showAudioPlayer = false;

  constructor(private route: ActivatedRoute, private podcastService: PodcastService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
        this.getPodcastDetails(+params['id']);
      }
    )
  }

  private getPodcastDetails(podcastId: number){
    this.podcastService.getPodcastDetails(podcastId).subscribe( podcast => {
      this.podcast = podcast
      this.podcastData = 'data:audio/mp3;base64,' + this.podcast.podcastData;
      this.imageData = 'data:image/png;base64,' + this.podcast.image.data;
      this.audioList.push({url: this.podcastData});
      this.showAudioPlayer = true;
    })
  }
}
