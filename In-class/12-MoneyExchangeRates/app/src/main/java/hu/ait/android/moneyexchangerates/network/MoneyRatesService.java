package hu.ait.android.moneyexchangerates.network;

import hu.ait.android.moneyexchangerates.model.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyRatesService {

    @GET("latest")
    Call<MoneyResult> getRates(@Query("base") String base);
}
