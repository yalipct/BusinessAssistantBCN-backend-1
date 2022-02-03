package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.service.config.DataConfigService;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {

    private static final Logger log = LoggerFactory.getLogger(OpendataController.class);

    @Autowired
    BigMallsService bigMallsService;
    @Autowired
    TestService testService;
    @Autowired
    EconomicActivitiesCensusService economicActivitiesCensusService;
    @Autowired
    CommercialGalleriesService commercialGaleriesService;
    @Autowired
    MarketFairsService marketFairsService;
    @Autowired
    DataConfigService bcnZonesService;
    @Autowired
    LargeEstablishmentsService largeEstablishmentsService;
    @Autowired
    MunicipalMarketsService municipalMarketsService;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //reactive
    @GetMapping(value="/test-reactive")
    @ApiOperation("Get test")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Mono.class),
        @ApiResponse(code = 503, message = "Resource Not Found", response = String.class)
    })
    public Mono<?> testReactive()
    {
        try{
            return testService.getTestData();
        }catch(MalformedURLException mue) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    public Mono<?> largeEstablishments()
    {
        try{
            return largeEstablishmentsService.getLargeEstablishmentsAll();
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/commercial-galleries")
    @ApiOperation("Get commercial galleries SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    public Mono<?> commercialGalleries()
    {
        try{
            return commercialGaleriesService.getCommercialGalleriesAll();
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/district/{district}")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> largeEstablishmentsByDistrict(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @PathVariable("district") String district
    ){
         try{
        	 return largeEstablishmentsService.getPageByDistrict(
                this.getValidOffset(offset),
                this.getValidLimit(limit),
                district);
         }catch (Exception mue){
             throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
         }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/activity/{activity}")
    @ApiOperation("Get large establishments by activity SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> largeEstablishmentsByActivity(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @PathVariable("activity") String activity
    ){
         try{
        	 return largeEstablishmentsService.getPageByActivity(
                this.getValidOffset(offset),
                this.getValidLimit(limit),
                activity);
         }catch (Exception mue){
             throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
         }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    @ApiOperation("Get big malls SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable")
    })
    public Mono<?> bigMalls(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
        try{
            return bigMallsService.getPage(
                this.getValidOffset(offset),
                this.getValidLimit(limit)
            );
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/municipal-markets")
    @ApiOperation("Get municipal markets SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> municipalMarkets(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
        try{
            return municipalMarketsService.getPage(
                this.getValidOffset(offset),
                this.getValidLimit(limit)
            );
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }
    
	//GET ?offset=0&limit=10
	@GetMapping("/market-fairs")
	@ApiOperation("Get market fairs SET 0 LIMIT 10")
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
	})
	public Mono<?> marketFairs(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
		try {
			return marketFairsService.getPage(
                this.getValidOffset(offset),
                this.getValidLimit(limit)
            );
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/activity")
    @ApiOperation("Get large establishment activity SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
    })
    public String largeEstablishmentsActivity()
    {
        return "Large-Estabilshments-Activity";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/economic-activities-census")
    @ApiOperation("Get markets fairs SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> economicActivitiesCensus(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
        try {
            return economicActivitiesCensusService.getPage(
                this.getValidOffset(offset),
                this.getValidLimit(limit)
            );
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    @GetMapping("/bcn-zones")
    @ApiOperation("Get bcn Zones")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    public Mono<?> bcnZones()
    {
        try {
            return bcnZonesService.getBcnZones();
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    private int getValidOffset(String offset) {
        if (offset == null || offset.isEmpty()) {
            return 0;
        }
        if (offset.isBlank() || !this.stringIsNumber(offset) || Integer.parseInt(offset) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(offset);
    }

    private int getValidLimit(String limit) {
        if (limit == null || limit.isEmpty()) {
            return -1;
        }
        if (limit.isBlank() || !this.stringIsNumber(limit) || Integer.parseInt(limit) < -1) { // || is not digit
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(limit);
    }

    private boolean stringIsNumber(String s) {
        for(char c : s.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

}
