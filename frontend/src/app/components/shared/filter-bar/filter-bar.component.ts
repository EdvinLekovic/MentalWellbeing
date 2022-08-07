import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Filter} from "../../../models/filter-models";

@Component({
  selector: "filter-bar",
  templateUrl: "filter-bar.component.html"
})
export class FilterBarComponent {
  @Input() title!: string;
  @Input() productName!: string;

  @Output() filteredElements: EventEmitter<Filter> = new EventEmitter<Filter>();

  public filter: Filter = {
    priceFrom:0,
    priceTo:0,
    productName:'',
    instructorName: '',
    rating:[]
  }

  filterElements(){
    this.filteredElements.emit(this.filter);
  }

  ratingSelection(event: any){
    const rating = event.target.value;
    const isChecked = event.target.checked;
    if(isChecked){
      this.filter.rating?.push(rating);
    }
    else {
      this.filter.rating = this.filter.rating?.filter(r => rating !== r);
    }
  }

}
