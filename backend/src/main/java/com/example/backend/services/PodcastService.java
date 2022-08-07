package com.example.backend.services;

import com.example.backend.models.Podcast;
import com.example.backend.models.Video;
import com.example.backend.models.requests.podcast_requests.AddPodcastRequest;
import com.example.backend.models.requests.podcast_requests.EditPodcastRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;

import java.util.List;
import java.util.Optional;

public interface PodcastService {
    List<MultimediaMetadataResponse> allPodcasts();
    Podcast getPodcastById(Long id);
    boolean checkIfUserOwnThePodcast(ProductUserRequest productUserRequest);
    MultimediaMetadataResponse getPodcastMetadata(Long id);
    List<Podcast> allPodcastsByUsersUsername(UsernameRequest usernameRequest);
    Optional<Podcast> addPodcast(AddPodcastRequest addPodcastRequest);
    void deletePodcast(Long podcastId);
    Optional<Podcast> editPodcast(EditPodcastRequest editPodcastRequest);

}
