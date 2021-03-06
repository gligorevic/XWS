package com.example.RequestService.controller;

import com.baeldung.springsoap.gen.GetContainerRequest;
import com.baeldung.springsoap.gen.GetContainerResponse;
import com.baeldung.springsoap.gen.GetRequestRequest;
import com.baeldung.springsoap.gen.GetRequestResponse;
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
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@RestController
public class RequestController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

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
            List<RequestDTO> requests = requestService.getAllRequestsForAd(Long.parseLong(adId), userEmail);
            log.info("Successful request fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{requestId}")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getAllRequestsInBundle(@PathVariable("requestId") String requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            List<RequestBundleDTO> requestBundles = requestService.getAllRequestsInBundle(Long.parseLong(requestId));
            log.info("Successful bundle request fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestBundles, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info/{username}")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getAllRequestsInfo(@PathVariable("username") String username, Authentication authentication, @RequestHeader("Auth") String auth) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(username)) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            List<RequestInfoDTO> requestInfos = requestService.getAllRequestsInfoByReciverUsername(username, auth);
            log.info("Successful request info fetching by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestInfos, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REQUEST_CREATING')")
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO, Authentication authentication,  @RequestHeader("Auth") String auth) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(requestDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Request request = requestService.add(requestDTO, auth);
            log.info("Successfully created request by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    @PreAuthorize("hasAuthority('REQUEST_CREATING')")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO, Authentication authentication,  @RequestHeader("Auth") String auth) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(requestContainerDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            RequestContainer requestContainer = requestService.addBundle(requestContainerDTO, auth);
            log.info("Successfully created bundle request by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requestContainer, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('AGENT_PAID_REQUEST_CREATING')")
    public ResponseEntity<?> cancelRequestsReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            List<Request> requests = requestService.cancelRequestsReservationPeriod(reservationPeriodDTO);
            log.info("Requests canceled after adding reservation period by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/bundle/{bundleId}/pay")
    @PreAuthorize("hasAuthority('RENT_PAYING')")
    public ResponseEntity<?> payBundleRequest(@PathVariable("bundleId") Long bundleId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            RequestContainer requestContainer = requestService.payBundleRequest(bundleId, userEmail);
            log.info("User {} paid bundle request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(requestContainer.getId().toString()));
            return new ResponseEntity<>(requestContainer, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/bundle/{requestId}/cancel")
    @PreAuthorize("hasAuthority('ENDUSER_REQUEST_CANCELING')")
    public ResponseEntity<?> cancelBundleRequest(@PathVariable("requestId") Long requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            RequestContainer requestContainer = requestService.cancelBundleRequest(requestId, userEmail);
            log.info("User {} canceled bundle request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(requestContainer.getId().toString()));
            return new ResponseEntity<>(requestContainer, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}/pay")
    @PreAuthorize("hasAuthority('RENT_PAYING')")
    public ResponseEntity<?> payRequest(@PathVariable("requestId") Long requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            Request request = requestService.payRequest(requestId, userEmail);
            log.info("User {} paid request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(request.getId().toString()));
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}/cancel")
    @PreAuthorize("hasAuthority('ENDUSER_REQUEST_CANCELING')")
    public ResponseEntity<?> cancelRequest(@PathVariable("requestId") Long requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            Request request = requestService.cancelRequest(requestId, userEmail);
            log.info("User {} canceled request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(request.getId().toString()));
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}")
    @PreAuthorize("hasAuthority('REQUEST_ACCEPTING')")
    public ResponseEntity<?> changeRequestStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(requestStatusDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            if (requestService.requestInBundle(requestId))
                return new ResponseEntity<>("This request is in bundle.", HttpStatus.BAD_REQUEST);

            switch (requestStatusDTO.getPaidState()) {
                case RESERVED:
                    Request acceptedRequest = requestService.acceptRequest(requestId);
                    log.info("User {} accepted request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(acceptedRequest.getId().toString()));
                    return new ResponseEntity<>(acceptedRequest, HttpStatus.OK);
                case CANCELED:
                    Request canceledRequest = requestService.declineRequest(requestId);
                    log.info("User {} canceled request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(canceledRequest.getId().toString()));
                    return new ResponseEntity<>(canceledRequest, HttpStatus.OK);
                default:
                    log.error("Paid state not found for request {}. Acction initiated by {}.", bCryptPasswordEncoder.encode(requestId.toString()), bCryptPasswordEncoder.encode(userEmail));
                    throw new Exception("Paid state not found");

            }
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bundle/{requestId}")
    @PreAuthorize("hasAuthority('REQUEST_ACCEPTING')")
    public ResponseEntity<?> changeBundleStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
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
                            Request acceptedRequest = requestService.acceptRequest(request.getId());
                            log.info("User {} accepted request in bundle {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(acceptedRequest.getId().toString()));
                        }
                        return new ResponseEntity<>(requests, HttpStatus.OK);
                    case CANCELED:
                        for (Request request : requests) {
                            Request declinedRequest = requestService.declineRequest(request.getId());
                            log.info("User {} canceled request in bundle {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(declinedRequest.getId().toString()));
                        }
                        return new ResponseEntity<>(requests, HttpStatus.OK);
                    default:
                        log.error("Paid state not found for bundle request {}. Acction initiated by {}.", bCryptPasswordEncoder.encode(requestId.toString()), bCryptPasswordEncoder.encode(userEmail));
                        throw new Exception("Paid state not found");

                }
            }
            return new ResponseEntity<>("Something is wrong with bundle list", HttpStatus.BAD_REQUEST);

        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));

            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/passed")
    public ResponseEntity<?> getPassedRequests(Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(requestService.getPassedRequests(userEmail), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRequestRequest")
    @ResponsePayload
    public GetRequestResponse postRequest(@RequestPayload GetRequestRequest request) {
        try {
            GetRequestResponse response = new GetRequestResponse();
            response.setId(requestService.saveAgentRequest(request.getRequest()));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContainerRequest")
    @ResponsePayload
    public GetContainerResponse postRequestContainersAgent(@RequestPayload GetContainerRequest request) {
        try {
            GetContainerResponse response = new GetContainerResponse();
            response.getId().addAll(requestService.saveAgentContainer(request.getContainer()));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/resPeriod")
    public ResponseEntity<?> cancelRequestsReservationPeriodAgent(@RequestBody ReservationPeriodDTO reservationPeriodDTO) {
        try {
            Boolean b = requestService.cancelRequestsAgent(reservationPeriodDTO);
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
