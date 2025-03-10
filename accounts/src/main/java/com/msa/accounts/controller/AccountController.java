package com.msa.accounts.controller;

import org.springframework.web.bind.annotation.RestController;

import com.msa.accounts.DTO.AccContactDTO;
import com.msa.accounts.DTO.CustomerDTO;
import com.msa.accounts.DTO.ResponseDTO;
import com.msa.accounts.constants.accConstants;
import com.msa.accounts.service.IAccountService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Tag(name = "CRUD controller")
@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
// @AllArgsConstructor
@Validated
public class AccountController {

private final IAccountService iAccServ;

public AccountController(IAccountService iAccServ){
    this.iAccServ = iAccServ;
}


// * is read from application.yml!
@Value("${build.version}")
private String buildVersion;


// All details derfined in local system!
@Autowired
private Environment environment;
//Everyone trying use this method must pass data as specified in DTO.
@Autowired
private AccContactDTO accContact;

@Operation(
    summary="Create account!"
)
@ApiResponse(
    responseCode = "201",
    description = "Succesfully created"
)
@PostMapping("/create")
public ResponseEntity<ResponseDTO> createAcc(@Valid @RequestBody CustomerDTO custdto){
    iAccServ.createAccount(custdto);    
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDTO(accConstants.STATUS_201, accConstants.MESSAGE_200));
    }

    //Here param because we only find by mobile number - string!
    @GetMapping("/find")
    public ResponseEntity<CustomerDTO> fetchAccs(@RequestParam("mobileNumber") String mobileNumber){
        CustomerDTO custDTO = iAccServ.findAccount(mobileNumber);  
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(custDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDto) {
        boolean isUpdated = iAccServ.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(accConstants.STATUS_200, accConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(accConstants.STATUS_417, accConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam  String mobileNumber){
        boolean isDeleted = iAccServ.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(accConstants.STATUS_200, accConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(accConstants.STATUS_417, accConstants.MESSAGE_417_DELETE));
        }
    }


    @Retry(name="getBuildInfo", fallbackMethod = "getBuildInfoFallBack")
    @GetMapping("/build-info")
    public ResponseEntity<String> getVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    // Executed after retries

    public ResponseEntity<String> getBuildInfoFallBack(Throwable throwable){
        return ResponseEntity.status(HttpStatus.OK).body("0.9");
    }

    @RateLimiter(name="getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-info")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK)
        .body(environment.getProperty("MAVEN_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable){
        return ResponseEntity.status(HttpStatus.OK)
        .body("Java 17");
    }

    @GetMapping("/contacts")
    public ResponseEntity<AccContactDTO> getContact(){
        return ResponseEntity.status(HttpStatus.OK)
        .body(accContact);
    }

    }


