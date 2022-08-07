package com.example.backend.services.impl;

import com.example.backend.models.Image;
import com.example.backend.models.Podcast;
import com.example.backend.models.User;
import com.example.backend.models.requests.podcast_requests.AddPodcastRequest;
import com.example.backend.models.requests.podcast_requests.EditPodcastRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.PodcastRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PodcastService;
import com.example.backend.services.ReviewPodcastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository podcastRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ReviewPodcastService reviewPodcastService;

    public PodcastServiceImpl(PodcastRepository podcastRepository,
                              UserRepository userRepository,
                              ImageRepository imageRepository, ReviewPodcastService reviewPodcastService) {
        this.podcastRepository = podcastRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.reviewPodcastService = reviewPodcastService;
    }

    @Override
    public List<MultimediaMetadataResponse> allPodcasts() {
        return podcastRepository.findAll().stream()
                .map(podcast -> new MultimediaMetadataResponse(podcast.getId(),
                        podcast.getTitle(),
                        podcast.getDescription(),
                        podcast.getPrice(),
                        podcast.getUsers().size(),
                        podcast.getImage(),
                        podcast.getCreator().getFullName(),
                        reviewPodcastService.averagePodcastReviewsStars(podcast.getId()))).toList();
    }

    @Override
    public Podcast getPodcastById(Long id) {
        return podcastRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean checkIfUserOwnThePodcast(ProductUserRequest productUserRequest) {
        Podcast podcast = podcastRepository.findById(productUserRequest.getProductId()).orElseThrow();
        return podcast.getUsers()
                .stream()
                .anyMatch(user -> productUserRequest.getUsername().equals(user.getUsername())) ||
                podcast.getCreator().getUsername().equals(productUserRequest.getUsername());
    }

    @Override
    public MultimediaMetadataResponse getPodcastMetadata(Long id) {
        return podcastRepository.findById(id).map(podcast -> new MultimediaMetadataResponse(podcast.getId(),
                podcast.getTitle(),
                podcast.getDescription(),
                podcast.getPrice(),
                podcast.getUsers().size(),
                podcast.getImage(),
                podcast.getCreator().getFullName(),
                reviewPodcastService.averagePodcastReviewsStars(podcast.getId()))).orElseThrow();
    }

    @Override
    public List<Podcast> allPodcastsByUsersUsername(UsernameRequest usernameRequest) {
        return podcastRepository.findAll()
                .stream()
                .filter(podcast -> podcast.getUsers()
                        .stream()
                        .anyMatch(user -> user.getUsername().equals(usernameRequest.getUsername())))
                .toList();
    }

    @Override
    public Optional<Podcast> addPodcast(AddPodcastRequest addPodcastRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UsernameRequest usernameRequest = mapper.readValue(addPodcastRequest.getCreatorUsername(), UsernameRequest.class);
            User creator = userRepository.findById(usernameRequest.getUsername()).orElseThrow();
            Image image = new Image(addPodcastRequest.getImage().getName(), addPodcastRequest.getImage().getContentType(), addPodcastRequest.getImage().getBytes());
            imageRepository.save(image);
            Double price = mapper.readValue(addPodcastRequest.getPrice(), Double.class);
            return Optional.of(this.podcastRepository.save(new Podcast(addPodcastRequest.getPodcast().getBytes(),
                    addPodcastRequest.getTitle(),
                    addPodcastRequest.getDescription(),
                    price,
                    image,
                    creator)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePodcast(Long podcastId) {
        this.podcastRepository.deleteById(podcastId);
    }

    @Override
    public Optional<Podcast> editPodcast(EditPodcastRequest editPodcastRequest) {
        Podcast podcast = podcastRepository.findById(editPodcastRequest.getId()).orElseThrow();
        if (!editPodcastRequest.getTitle().isEmpty()) {
            podcast.setTitle(editPodcastRequest.getTitle());
        }
        if (!editPodcastRequest.getDescription().isEmpty()) {
            podcast.setDescription(editPodcastRequest.getDescription());
        }
        return Optional.of(podcastRepository.save(podcast));
    }
}
