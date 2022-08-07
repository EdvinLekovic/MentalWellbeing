import {User} from "./user-models";
import {Image} from "./multimedia-models";

export interface AddPodcastRequest {
  podcast: File | string,
  title: string,
  description: string,
  price: number | null,
  creatorUsername: string | undefined,
  image: File | string
}

export interface Podcast {
  id: number,
  podcastData: File,
  title: string,
  description: string,
  createdOn: Date,
  price: number,
  image: Image,
  creator: User
}
