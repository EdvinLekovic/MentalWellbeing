import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {Podcast} from "../../../models/podcast-models";
import {Multimedia, MultimediaMetadata} from "../../../models/multimedia-models";

@Component({
  selector:"app-multimedia-item-list",
  templateUrl:"multimedia-item-list.component.html"
})
export class MultimediaItemListComponent implements OnInit{
  @Input() imageUrl!: string;
  @Input() detailsUrl!: string;
  @Input() multimediaList!: MultimediaMetadata[]

  @Output() updateChange: EventEmitter<void> = new EventEmitter<void>();

  ngOnInit(): void {
    console.log("multimedia list")
    console.log(this.multimediaList);
  }

  updateChanges(){
    this.updateChange.emit();
  }
}
