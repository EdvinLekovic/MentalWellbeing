import {User} from "./user-models";
import {Course} from "./course-models";

export interface Multimedia {
  id: number,
  multimediaData: any,
  title: string,
  description: string,
  createdOn: Date,
  price: number,
  image: Image,
  creator: User,
  users: User[];
  videoCourse?: Course,
  author?: string
}

export interface MultimediaMetadata {
  id: number,
  title: string,
  description: string,
  price: number,
  numOfUsers: number,
  image: Image,
  creatorFullName: string,
  rating: number
}

export interface Image {
  name: string,
  type: string,
  data: any
}

export interface EditMultimediaRequest {
  id: number | null,
  title: string,
  description: string
}
