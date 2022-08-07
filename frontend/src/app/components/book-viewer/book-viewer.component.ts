import {Component, Input} from "@angular/core";

@Component({
  selector:"app-book-viewer",
  templateUrl:"book-viewer.component.html"
})
export class BookViewerComponent {

  @Input() src!: any;
  @Input() title!: string;

  public zoom = 1;

  public zoomIn(){
    this.zoom+=1;
    console.log(this.zoom);
  }

  public zoomOut(){
    this.zoom= this.zoom > 1 ? this.zoom-1 : 1;
  }

}
