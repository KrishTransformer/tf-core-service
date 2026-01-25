package com.tf.core_service;

import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.ETransCostType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TfCoreServiceApplicationTests {

    @Test
    public void getKValueTest() {
        double kValue = TwoWindingsFormulas.getKValue(0.0,0.0, "Al", ETransCostType.ECONOMIC);
//        Asserts.check(kValue == 0.5 , "KValue test for Al");
    }


}
