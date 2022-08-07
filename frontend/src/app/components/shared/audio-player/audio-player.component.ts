import {Component, Input} from "@angular/core";

@Component({
  selector:"app-shared-audio-player",
  templateUrl:"audio-player.component.html"
})
export class AudioPlayerComponent {
  @Input() title!: string;
  @Input() image!: any;
  @Input() audioList!: any[];
}
