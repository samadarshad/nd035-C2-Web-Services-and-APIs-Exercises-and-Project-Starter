package com.udacity.vehicles.client.prices;

import com.udacity.vehicles.client.graphqlclient.GraphqlClientMvc;
import com.udacity.vehicles.client.prices.graphql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);
    private final GraphqlClientMvc graphqlClientMvc;

    public PriceClient(GraphqlClientMvc pricingGraphQl) {
        this.graphqlClientMvc = pricingGraphQl;
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time
    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     *   error message that the vehicle ID is invalid, or note that the
     *   service is down.
     */
    public String getByVehicleId(Integer vehicleId) {
        try {
            Optional<FindPricesByVehicleIdQuery.Data> rsp = graphqlClientMvc.exchange(new FindPricesByVehicleIdQuery(Math.toIntExact(vehicleId)));
            if (rsp.isPresent()) {
                String currency = rsp.get().getFindPricesByVehicleId().get(0).getCurrency();
                double price = rsp.get().getFindPricesByVehicleId().get(0).getPrice();
                return String.format("%s %s", price, currency);
            }

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

    public void create(String currency, Integer vehicleId) throws Exception {
        Optional<GenerateAndAssignPriceMutation.Data> rsp = graphqlClientMvc.exchange(new GenerateAndAssignPriceMutation(currency, vehicleId));
        if (rsp.isEmpty()) {
            throw new Exception("Couldnt assign price");
        }
    }

    public void deleteByVehicleId(Integer vehicleId) throws Exception {
        Optional<DeleteAllByVehicleIdMutation.Data> rsp = graphqlClientMvc.exchange(new DeleteAllByVehicleIdMutation(vehicleId));
        if (rsp.isEmpty()) {
            throw new Exception("Couldnt delete price");
        }
    }
}
