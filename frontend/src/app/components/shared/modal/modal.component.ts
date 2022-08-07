import {Component, EventEmitter, Input, Output} from "@angular/core"

@Component({
  selector: 'modal',
  templateUrl:'modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {

  @Input() title: string = 'Title here';
  @Input() submitButtonName = "Submit";

  @Output() close: EventEmitter<void> = new EventEmitter<void>();
  @Output() submit: EventEmitter<void> = new EventEmitter<void>();

  public closeModal(){
    this.close.emit();
  }

  public submitClicked(){
    this.submit.emit();
  }
}
