package com.example.RequestService.controller;

import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.RequestContainerDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.dto.RequestStatusDTO;
import com.example.RequestService.dto.ReservationPeriodDTO;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAllRequestsForAd(@PathVariable("adId") String adId, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();

            return new ResponseEntity<>(requestService.getAllRequestsForAd(Long.parseLong(adId), userEmail), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{requestId}")
    public ResponseEntity<?> getAllRequestsInBundle(@PathVariable("requestId") String requestId, @RequestHeader("Auth") String auth){
        try{

            return new ResponseEntity<>(requestService.getAllRequestsInBundle(Long.parseLong(requestId), auth), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<?> getAllRequestsInfo(@PathVariable("username") String username, @RequestHeader("Auth") String auth, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!userEmail.equals(username)) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(requestService.getAllRequestsInfoByReciverUsername(username, auth), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!userEmail.equals(requestDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(requestService.add(requestDTO), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!userEmail.equals(requestContainerDTO.getUserSentRequest())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(requestService.addBundle(requestContainerDTO), HttpStatus.CREATED);

        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> cancelRequestsReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO){
        try{
            return new ResponseEntity<>(requestService.cancelRequestsReservationPeriod(reservationPeriodDTO), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/bundle/{bundleId}/pay")
    public ResponseEntity<?> PayBundleRequest(@PathVariable("bundleId") Long bundleId, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            return new ResponseEntity<>(requestService.payBundleRequest(bundleId, userEmail), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}/pay")
    public ResponseEntity<?> PayRequest(@PathVariable("requestId") Long requestId, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();

            return new ResponseEntity<>(requestService.payRequest(requestId, userEmail), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<?> changeRequestStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!userEmail.equals(requestStatusDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            if(requestService.requestInBundle(requestId))
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
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bundle/{requestId}")
    public ResponseEntity<?> changeBundleStatus(@RequestBody RequestStatusDTO requestStatusDTO, @PathVariable("requestId") Long requestId, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!userEmail.equals(requestStatusDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            if(!requestService.requestInBundle(requestId))
                return new ResponseEntity<>("This request is not in the bundle", HttpStatus.BAD_REQUEST);

            List<Request> requests = requestService.getRequestsInBundle(requestId);

            if(requests.size() > 0){

                switch (requestStatusDTO.getPaidState()) {
                    case RESERVED:
                        for(Request request : requests){
                            requestService.acceptRequest(request.getId());
                        }
                        return new ResponseEntity<>(requests, HttpStatus.OK);
                    case CANCELED:
                        for(Request request : requests){
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

        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
