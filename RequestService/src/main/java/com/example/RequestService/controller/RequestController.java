package com.example.RequestService.controller;

import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.*;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(RequestController.class);

    @GetMapping("/ad/{adId}")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getAllRequestsForAd(@PathVariable("adId") String adId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            List<Request> requests = requestService.getAllRequestsForAd(Long.parseLong(adId), userEmail);
            log.info("Successful request fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{requestId}")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getAllRequestsInBundle(@PathVariable("requestId") String requestId, @RequestHeader("Auth") String auth,  Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            List<RequestBundleDTO> requestBundles = requestService.getAllRequestsInBundle(Long.parseLong(requestId), auth);
            log.info("Successful bundle request fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestBundles, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info/{username}")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getAllRequestsInfo(@PathVariable("username") String username, @RequestHeader("Auth") String auth, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(username)) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            List<RequestInfoDTO> requestInfos =  requestService.getAllRequestsInfoByReciverUsername(username, auth);
            log.info("Successful request info fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestInfos, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REQUEST_CREATING')")
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(requestDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Request request = requestService.add(requestDTO);
            log.info("Successfully created request by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    @PreAuthorize("hasAuthority('REQUEST_CREATING')")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(requestContainerDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            RequestContainer requestContainer = requestService.addBundle(requestContainerDTO);
            log.info("Successfully created bundle request by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestContainer, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> cancelRequestsReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO) {
        try {
            return new ResponseEntity<>(requestService.cancelRequestsReservationPeriod(reservationPeriodDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/bundle/{bundleId}/pay")
    public ResponseEntity<?> PayBundleRequest(@PathVariable("bundleId") Long bundleId, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();
            return new ResponseEntity<>(requestService.payBundleRequest(bundleId, userEmail), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}/pay")
    public ResponseEntity<?> PayRequest(@PathVariable("requestId") Long requestId, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();

            return new ResponseEntity<>(requestService.payRequest(requestId, userEmail), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<?> changeRequestStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(requestStatusDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            if (requestService.requestInBundle(requestId))
                return new ResponseEntity<>("This request is in bundle.", HttpStatus.BAD_REQUEST);

            switch (requestStatusDTO.getPaidState()) {
                case RESERVED:
                    return new ResponseEntity<>(requestService.acceptRequest(requestId), HttpStatus.OK);
                case CANCELED:
                    return new ResponseEntity<>(requestService.declineRequest(requestId), HttpStatus.OK);
//                case PAID:
//                    return new ResponseEntity<>(requestService.payRequest(requestId), HttpStatus.OK);
//                    break;
                default:
                    throw new Exception("Paid state not found");

            }
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bundle/{requestId}")
    public ResponseEntity<?> changeBundleStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(requestStatusDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            if (!requestService.requestInBundle(requestId))
                return new ResponseEntity<>("This request is not in the bundle", HttpStatus.BAD_REQUEST);

            List<Request> requests = requestService.getRequestsInBundle(requestId);

            if (requests.size() > 0) {

                switch (requestStatusDTO.getPaidState()) {
                    case RESERVED:
                        for (Request request : requests) {
                            requestService.acceptRequest(request.getId());
                        }
                        return new ResponseEntity<>(requests, HttpStatus.OK);
                    case CANCELED:
                        for (Request request : requests) {
                            requestService.declineRequest(request.getId());
                        }
                        return new ResponseEntity<>(requests, HttpStatus.OK);
//                case PAID:
//
//                    break;
                    default:
                        throw new Exception("Paid state not found");

                }
            }
            return new ResponseEntity<>("Something is wrong with bundle list", HttpStatus.BAD_REQUEST);

        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
