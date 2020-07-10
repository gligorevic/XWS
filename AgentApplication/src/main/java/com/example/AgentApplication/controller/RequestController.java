package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.RequestContainer;
import com.example.AgentApplication.dto.*;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO, Authentication authentication){
        try{
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(requestDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(requestService.add(requestDTO), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO, Authentication authentication){
        try{
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(requestContainerDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(requestService.addBundle(requestContainerDTO), HttpStatus.CREATED);

        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ad/{adId}")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> getAllRequestsForAd(@PathVariable("adId") String adId) {
        try {
            List<RequestDTO> requests = requestService.getAllRequestsForAd(Long.parseLong(adId));
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> getAllRequestsInfo() {
        try {
            List<RequestInfoDTO> requestInfos = requestService.getAllRequestsInfoByReciverUsername();
            return new ResponseEntity<>(requestInfos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/passed")
    public ResponseEntity<?> getPassedRequests(){
        try{
            return new ResponseEntity<>(requestService.getPassedRequests(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reserved")
    public ResponseEntity<?> getReservedRequests(){
        try{
            return new ResponseEntity<>(requestService.getReservedRequests(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{requestId}")
    public ResponseEntity<?> getAllRequestsInBundle(@PathVariable("requestId") String requestId) {
        try {
            List<RequestBundleDTO> requestBundles = requestService.getAllRequestsInBundle(Long.parseLong(requestId));
            return new ResponseEntity<>(requestBundles, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{requestId}")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> changeRequestStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication) {
        try {
            String username = authentication.getPrincipal().toString();
            System.out.println(username);
            if (requestService.getRequestById(requestId).isInBundle())
                return new ResponseEntity<>("This request is in bundle.", HttpStatus.BAD_REQUEST);

            switch (requestStatusDTO.getPaidState()) {
                case RESERVED:
                    Request acceptedRequest = requestService.acceptRequest(requestId);
                   return new ResponseEntity<>(acceptedRequest, HttpStatus.OK);
                case CANCELED:
                    Request canceledRequest = requestService.declineRequest(requestId);
                    return new ResponseEntity<>(canceledRequest, HttpStatus.OK);
                default:
                    throw new Exception("Paid state not found");

            }
        } catch (CustomException e) {
             return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bundle/{requestId}")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> changeBundleStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId) {
        try {
            if (!requestService.getRequestById(requestId).isInBundle())
                return new ResponseEntity<>("This request is not in the bundle", HttpStatus.BAD_REQUEST);

            List<Request> requests = requestService.getRequestsInBundle(requestId);

            if (requests.size() > 0) {

                switch (requestStatusDTO.getPaidState()) {
                    case RESERVED:
                        requestService.acceptBundle(requests);
                        return new ResponseEntity<>("Requests accepted", HttpStatus.OK);
                    case CANCELED:
                        requestService.declineBundle(requests);
                        return new ResponseEntity<>("Requests canceled", HttpStatus.OK);
                    default:
                        throw new Exception("Paid state not found");

                }
            }
            return new ResponseEntity<>("Something is wrong with bundle list", HttpStatus.BAD_REQUEST);

        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/bundle/{bundleId}/pay")
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> PayBundleRequest(@PathVariable("bundleId") Long bundleId) {
        try {
            RequestContainer requestContainer = requestService.payBundleRequest(bundleId);
            return new ResponseEntity<>(requestContainer, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}/pay")
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> PayRequest(@PathVariable("requestId") Long requestId) {
        try {
            Request request = requestService.payRequest(requestId);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{email}/created")
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> getAllCreatedRequests(@PathVariable("email") String email){
        try{
            return new ResponseEntity<>(requestService.getAllCreatedRequests(email), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{email}/paid")
    public ResponseEntity<?> getAllPaid(@PathVariable("email") String email){
        try{
            return new ResponseEntity<>(requestService.getAllPaid(email), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
