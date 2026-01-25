package com.tf.core_service.service;

import com.tf.core_service.model.files.Lom;
import com.tf.core_service.request.LomRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FileServices {
    public List<Lom> calculateLom(LomRequest lomRequest) {
        List<Lom> loms = new ArrayList<>();

        if(lomRequest.isTrue) {
            Lom lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Lamination");
            lom.setSpecification("Nip M4 CRGO Sheet");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getLamination());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getLamination(), 220.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("HV Conductor");
            lom.setSpecification("CondSize, CondInsu, Material");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getHvConductor());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getHvConductor(),125.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("LV Conductor");
            lom.setSpecification("CondSize, CondInsu, Material");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getLvConductor());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getLvConductor(), 125.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("HV Connection Leads");
            lom.setSpecification("(MPC) HV 3sq mm");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getHvConnectionLeads());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getHvConnectionLeads(), 520.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("LV Connection Leads");
            lom.setSpecification("Flats+Flex 173sq mm");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getLvConnectionLeads());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getLvConnectionLeads(), 520.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Insulation Material");
            lom.setSpecification("PB/Paper/Blocks");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getInsulationMaterial());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getInsulationMaterial(), 120.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Transformer Oil");
            lom.setSpecification("40kV grade, Mineral oil");
            lom.setUnit("ltr");
            lom.setQuantity(lomRequest.getLomQuantity().getTransformerOil());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getTransformerOil(), 65.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Tank, Lid, etc.");
            lom.setSpecification("L 885 xB 355 xH 865, MS");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getTankLidEtc());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getTankLidEtc(), 32.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            if(lomRequest.getLomBooleans().isHvCableBox()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Cable Box, HV");
                lom.setSpecification("Indoor type MS");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getHvCableBox());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getHvCableBox(), 7000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isLvCableBox()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Cable Box, LV");
                lom.setSpecification("Indoor type MS");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getLvCableBox());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getLvCableBox(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isHvBushing()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Bushing, HV");
                lom.setSpecification("17.5 kV/250 A, Porcelain");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getHvBushing());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getHvBushing(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isLvBushing()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Bushing, LV");
                lom.setSpecification("1.1 kv/630 A, Porcelain");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getLvBushing());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getLvBushing(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Radiator/ Heat Exch");
            lom.setSpecification("600 x 520 - 4 x 4, MS");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getRadiatorsAndHeatExc());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getRadiatorsAndHeatExc(), 150.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            if(lomRequest.getLomBooleans().isPermaWood()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Perma Wood Items");
                lom.setSpecification("");
                lom.setUnit("kg");
                lom.setQuantity(lomRequest.getLomQuantity().getPermaWood());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getPermaWood(), 1000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isDrainValve()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Drain Valve");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getDrainValve());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getDrainValve(),3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isFilterValve()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Filter Valve");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getFilterValve());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getFilterValve(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isSamplingValve()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Sampling Valve");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getSamplingValve());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getSamplingValve(), 1000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isRelayShutOffValve()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Relay Shut-off Valve");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getRelayShutOffValve());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getRelayShutOffValve(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Breather Si Gel");
            lom.setSpecification("gms");
            lom.setUnit("nos.");
            lom.setQuantity(lomRequest.getLomQuantity().getBreatherSilicaGel());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getBreatherSilicaGel(), 500.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Rating Plate");
            lom.setSpecification("");
            lom.setUnit("nos.");
            lom.setQuantity(lomRequest.getLomQuantity().getRatingPlate());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getRatingPlate(), 1000.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);


            if(lomRequest.getLomBooleans().isThermometerPocket()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Thermometer Pocket");
                lom.setSpecification("");
                lom.setUnit("");
                lom.setQuantity(lomRequest.getLomQuantity().getThermometerPocket());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getThermometerPocket(), 75.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isAirReleasePlug()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Air Release Plug");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getAirReleasePlug());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getAirReleasePlug(), 75.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Core bolts and tie rods");
            lom.setSpecification("C20Br");
            lom.setUnit("kg");
            lom.setQuantity(lomRequest.getLomQuantity().getCoreBoltsAndTieRods());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getCoreBoltsAndTieRods(), 35.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            if(lomRequest.getLomBooleans().isOltc()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("OLTC");
                lom.setSpecification("With Controls, 12kV/6");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getOltc());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOltc(), 350000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isOctc()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("OCTC");
                lom.setSpecification("With Controls, 12kV/6");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getOctc());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOctc(),20000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isOti()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("OTI");
                lom.setSpecification("");
                lom.setUnit("");
                lom.setQuantity(lomRequest.getLomQuantity().getOti());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOti(), 3000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isWti()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("WTI and WTICT");
                lom.setSpecification("");
                lom.setUnit("");
                lom.setQuantity(lomRequest.getLomQuantity().getWti());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getWti(), 5000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isBuchholzRelay()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Buchholz Relay");
                lom.setSpecification("GOR-3");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getBuchholzRelay());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getBuchholzRelay(), 5000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isMarshallingBox()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Marshalling Box");
                lom.setSpecification("DM/SM");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getMarshallingBox());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getMarshallingBox(), 10000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isOilLevelGauge()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Oil Level gauge");
                lom.setSpecification("");
                lom.setUnit("pc");
                lom.setQuantity(lomRequest.getLomQuantity().getOilLevelGauge());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOilLevelGauge(), 750.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isMog()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("MOG");
                lom.setSpecification("");
                lom.setUnit("");
                lom.setQuantity(lomRequest.getLomQuantity().getMog());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getMog(), 5000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isPressureReliefValve()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Pressure Relief Valve");
                lom.setSpecification("");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getPressureReliefValve());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getPressureReliefValve(), 5000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isOilCirculatingPump()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Oil Circulating Pump");
                lom.setSpecification("HP");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getOilCirculatingPump());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOilCirculatingPump(), 25000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isAvrrtcc()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("AVR and RTCC Panel");
                lom.setSpecification("");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getAvrrtcc());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getAvrrtcc(), 10000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isRollers()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("CI Rollers Assembly");
                lom.setSpecification("mm dia");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getRollers());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getRollers(), 200.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isPumpControlCubicle()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Pump Control Cubicle");
                lom.setSpecification("HP");
                lom.setUnit("");
                lom.setQuantity(lomRequest.getLomQuantity().getPumpControlCubicle());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getPumpControlCubicle(), 45000.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isBiMetallicConn()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Bi-metallic Connector");
                lom.setSpecification("HT/LT, Amps");
                lom.setUnit("nos.");
                lom.setQuantity(lomRequest.getLomQuantity().getBiMetallicConnector());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getBiMetallicConnector(), 800.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            if(lomRequest.getLomBooleans().isFasteners()){
                lom = new Lom();
                lom.setIndex(loms.size());
                lom.setDescription("Fasteners");
                lom.setSpecification("C20BR");
                lom.setUnit("kg");
                lom.setQuantity(lomRequest.getLomQuantity().getFasteners());
                lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getFasteners(), 42.0));
                lom.setCost(lom.getQuantity() * lom.getRate());
                loms.add(lom);
            }

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Other Materials");
            lom.setSpecification("");
            lom.setUnit("");
            lom.setQuantity(lomRequest.getLomQuantity().getOtherMaterials());
            lom.setRate(Objects.requireNonNullElse(lomRequest.getLomRate().getOtherMaterials(), 20000.0));
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);

            lom = new Lom();
            lom.setIndex(loms.size());
            lom.setDescription("Total Material Cost");
            lom.setSpecification("");
            lom.setUnit("");
            lom.setQuantity(0.0);
            lom.setRate(20000.0);
            lom.setCost(lom.getQuantity() * lom.getRate());
            loms.add(lom);
        }
        return loms;
    }
}
