import {User} from "./user-models";
import {Image} from "./multimedia-models";

export interface AddVideoRequest {
  video: File | string,
  title: string,
  description: string,
  price: number | null,
  creatorUsername: string | undefined,
  image: File | string
}

export interface Video {
  videoData: File | string,
  title: string,
  description: string,
  createdOn: Date,
  price: number,
  image: Image,
  creator: User,
}

export interface VideoMetadata {
  id: number,
  title: string
}
