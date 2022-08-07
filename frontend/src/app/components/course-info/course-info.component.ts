import {Component} from "@angular/core";
import {MultimediaMetadata} from "../../models/multimedia-models";
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../services/course-services/course-service";

@Component({
  templateUrl:"course-info.component.html"
})
export class CourseInfoComponent {
  public course!: MultimediaMetadata;

  constructor(private route:ActivatedRoute,private courseService: CourseService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.getCourseMetadata(+params['id']);
    })
  }

  private getCourseMetadata(courseId: number){
    this.courseService.getCourseMetadata(courseId).subscribe(course => this.course = course);
  }

  updateChanges(){
    this.getCourseMetadata(this.course.id);
  }


}
