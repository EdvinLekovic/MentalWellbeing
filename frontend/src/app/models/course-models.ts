import {User} from "./user-models";

export interface AddCourseRequest {
  title: string,
  description: string,
  creatorUsername: string | undefined,
  price: number | null,
  image: File | string
}

export interface Course {
  id: number,
  name:string,
  description:string,
  createdOn: Date,
  lastUpdatedOn: Date,
  price: number,
  creator: User,
  users: User[],
  image: File,
}

export interface AddVideoToCourseRequest {
  id: number,
  videos:File[] | string,
  title: string,
  description: string,

}
