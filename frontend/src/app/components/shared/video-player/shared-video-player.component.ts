import {Component, Input, OnChanges, OnInit} from "@angular/core";

@Component({
  selector:"app-shared-video-player",
  templateUrl:"shared-video-player.component.html",
  styleUrls: ['shared-video-player.component.css']
})
export class SharedVideoPlayerComponent implements OnChanges, OnInit{
  @Input() image!: any;
  @Input() video!: any;

  ngOnChanges(changes: any): void {
    console.log(changes);
    if(changes.video){
      this.ngOnInit();
    }
  }

  ngOnInit(): void {
    console.log(this.video);
  }


}
