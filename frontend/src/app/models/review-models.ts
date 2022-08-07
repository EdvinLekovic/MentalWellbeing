import {Podcast} from "./podcast-models";
import {Book} from "./book-models";
import {Video} from "./video-models";
import {User} from "./user-models";

export interface Review {
  description: string,
  user: User,
  rating: number
}

export interface AddReviewRequest {
  description: string,
  rating: number | null,
  mediaId: number | null
  username: string | undefined
}

export interface EditReviewRequest {
  id: number | null,
  description: string,
  rating: number | null
}

