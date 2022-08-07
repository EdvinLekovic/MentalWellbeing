package com.example.backend.controllers;

import com.example.backend.models.Podcast;
import com.example.backend.models.requests.podcast_requests.AddPodcastRequest;
import com.example.backend.models.requests.podcast_requests.EditPodcastRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.services.PodcastService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/podcasts")
@CrossOrigin("http://localhost:4200")
public class PodcastController {

    private final PodcastService podcastService;

    public PodcastController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @GetMapping
    public List<MultimediaMetadataResponse> getAllPodcasts(){
        return podcastService.allPodcasts();
    }

    @GetMapping("/{id}")
    public Podcast getPodcastById(@PathVariable Long id){
        return podcastService.getPodcastById(id);
    }

    @GetMapping("/metadata/{id}")
    public MultimediaMetadataResponse getPodcastMetadata(@PathVariable Long id){
        return podcastService.getPodcastMetadata(id);
    }

    @PostMapping("/check-podcast-own")
    public boolean checkIfUserOwnPodcast(@RequestBody ProductUserRequest productUserRequest){
        return this.podcastService.checkIfUserOwnThePodcast(productUserRequest);
    }

    @GetMapping("/podcasts-by-user")
    public List<Podcast> getAllPodcastsByUser(@RequestBody UsernameRequest usernameRequest){
        return podcastService.allPodcastsByUsersUsername(usernameRequest);
    }

    @PostMapping(value = "/add-new-podcast",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<Podcast> addNewPodcast(@RequestParam MultipartFile podcast,
                                           @RequestParam String title,
                                           @RequestParam String price,
                                           @RequestParam String description,
                                           @RequestParam String creatorUsername,
                                           @RequestParam MultipartFile image){
        AddPodcastRequest addPodcastRequest = new AddPodcastRequest(podcast,title,description,price,creatorUsername,image);
        return podcastService.addPodcast(addPodcastRequest);
    }

    @PostMapping("/edit-podcast")
    public Optional<Podcast> editPodcast(@RequestBody EditPodcastRequest editPodcastRequest){
        return podcastService.editPodcast(editPodcastRequest);
    }

    @DeleteMapping("/delete-podcast/{podcastId}")
    public void deletePodcast(@PathVariable Long podcastId){
        podcastService.deletePodcast(podcastId);
    }

}
